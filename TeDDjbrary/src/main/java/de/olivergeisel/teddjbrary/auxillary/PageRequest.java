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

package de.olivergeisel.teddjbrary.auxillary;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import de.olivergeisel.teddjbrary.AppConfig;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$;

@org.springframework.context.annotation.Configuration
class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

@Component
@EnableScheduling
public class PageRequest {

	private final RestTemplate restTemplate;
	Logger logger = LoggerFactory.getLogger(PageRequest.class);
	private HttpHeaders headers;
	private Cookie cookie;

	public PageRequest(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded");
	}

	@Scheduled(fixedRate = 120_000) // Alle 2 Minuten ausführen (120000 Millisekunden)
	public void requestPage() {
		if (!AppConfig.getInstance().getGeheimerUser()) {
			return;
		}
		String loginUrl = "http://localhost/login";
		// Anmeldung durchführen
		WebDriver driver;
		Configuration.headless = true; // Hintergrundmodus aktivieren
		Configuration.browser = "chrome"; // Verwenden Sie den Chrome-Browser
		if (this.cookie == null) {
			String password = "Wasserfallschutzsystemüberwachungsbeamter";
			String username = "geheim";
			Selenide.open(loginUrl);
			$("#username").setValue(username);
			$("#password").setValue(password);
			$("#submit").click();
			logger.info("Anmeldung erfolgreich");
			driver = WebDriverRunner.getWebDriver();
			var cookies = driver.manage().getCookies();
			for (Cookie c : cookies) {
				if (c.getName().equals("JSESSIONID")) {
					var date = Date.from(Instant.now().plusSeconds(60 * 60L));
					this.cookie = new Cookie(c.getName(), c.getValue(), c.getDomain(), c.getPath(), date, false, false);
					driver.manage().addCookie(cookie);
				}
			}
		}
		// Seite aufrufen
		String pageUrl = "http://localhost/testimonials";
		Selenide.open(pageUrl);
		logger.info("Seite aufgerufen");
	}
}
