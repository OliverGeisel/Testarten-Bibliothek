/*
 * Copyright 2023 Oliver Geisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.olivergeisel.teddjbrary;

import org.salespointframework.EnableSalespoint;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableSalespoint
public class TeDDjbraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeDDjbraryApplication.class, args);
	}

	@Configuration
	@EnableWebSecurity
	public static class WebSecurityConfig {

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http
					.authorizeHttpRequests(requests -> requests
							.requestMatchers("/**").permitAll()
							.anyRequest().authenticated()
					)
					.formLogin(form -> form
							.loginPage("/login").permitAll().defaultSuccessUrl("/", false)
					)
					.logout(it -> it.permitAll().invalidateHttpSession(true).logoutSuccessUrl("/"));
			http.csrf(AbstractHttpConfigurer::disable);
			http.cors(AbstractHttpConfigurer::disable);
			return http.build();
		}
	}
}
