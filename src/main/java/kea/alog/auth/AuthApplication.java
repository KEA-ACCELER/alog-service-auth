package kea.alog.auth;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AuthApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8087"));
		app.run(args);
	}

}
