package kea.alog.auth.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kea.alog.auth.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter { // 모든 요청마다 토큰을 검사해야하니까 OncePerRequestFilter를 상속받는다.


    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION); // 헤더에서 토큰을 꺼내온다.
        log.info("authorization : " + authorization);

        if (authorization == null || !authorization.startsWith("Bearer ")) { // 토큰이 없으면 권한부여 하지 않기

            log.error(" WARN : authenticationHeader is null");
            filterChain.doFilter(request, response);
            return;
        }

        // Token 꺼내기
        String token = authorization.split(" ")[1];

        // Token Expired 여부
        if (JwtUtil.isExpired(token, secretKey)) {
            log.error(" ERROR : Token Expired");

            filterChain.doFilter(request, response);
            return;
        }
        // Token에서 유저 정보 꺼내기
        String userEmail = JwtUtil.getUserEmail(token, secretKey);
        log.info("userEmail : " + userEmail);

        // 인증 수행
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // GrantedAuthority 타입의 리스트 객체를 생성합니다.
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 리스트 객체에 "USER"라는 권한을 추가합니다.
            authorities.add(new SimpleGrantedAuthority("AUTHORIZED"));

            // 유저 정보 조회
            // UserDetails userDetails = userUserDetailsService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userEmail, null, authorities);
            // authentication 에 추가적인 정보 저장
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // authentication을 SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);

    }

}

