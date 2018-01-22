package com.paranoia;

import com.paranoia.jwt.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

    @Bean
    //@ConditionalOnProperty(prefix = RestProperties.REST_PREFIX, name = "auth-open", havingValue = "true", matchIfMissing = true)
    public JwtFilter jwtAuthenticationTokenFilter() {
        return new JwtFilter();
    }

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
