package de.olivergeisel.teddjbrary;

import org.salespointframework.EnableSalespoint;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableSalespoint
public class TeDDjbraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeDDjbraryApplication.class, args);
	}


	@Configuration
	@EnableWebSecurity
	public class WebSecurityConfig {

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http
					.authorizeHttpRequests((requests) -> requests
							.requestMatchers("/**").permitAll()
							.anyRequest().authenticated()
					)
					.formLogin((form) -> form
							.loginPage("/login")
							.permitAll()
					)
					.logout((logout) -> logout.permitAll());

			return http.build();
		}

		@Bean
		public UserDetailsService userDetailsService() {
			var builder = User.builder();
			UserDetails user = builder.password("root").username("admin").roles("ADMIN").build();
			return new InMemoryUserDetailsManager(user);
		}
	}

}
