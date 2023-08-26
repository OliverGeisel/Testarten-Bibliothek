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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalTime;

@Configuration
@PropertySource("classpath:/application.properties")
public class AppConfig {

	private static final String    TIME_FORMAT = "HH:mm";
	private static final String    TIME_ZONE   = "Europe/Berlin";
	private static       AppConfig instance    = null;

	@Value("${app.name}")
	private String    name;
	@Value("${app.geheimeruser}")
	private boolean   geheimerUser;
	@Value("${app.version}")
	private String    version;
	@Value("${app.opentime}")
	private LocalTime openTime;
	@Value("${app.closetime}")
	private LocalTime closeTime;


	//region setter/getter
	public static AppConfig getInstance() {
		if (instance == null) {
			instance = new AppConfig();
		}
		return instance;
	}

	public boolean getGeheimerUser() {
		return geheimerUser;
	}

	public void setGeheimerUser(boolean geheimerUser) {
		this.geheimerUser = geheimerUser;
	}

	public LocalTime getCloseTime() {
		return closeTime;
	}

	public String getName() {
		return name;
	}

	public LocalTime getOpenTime() {
		return openTime;
	}

	public String getVersion() {
		return version;
	}
//endregion
}
