package de.olivergeisel.teddjbrary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalTime;

@Configuration
@PropertySource("classpath:/application.properties")
public class AppConfig {

	@Value("${app.name}")
	private String name;

	@Value("${app.version}")
	private String version;

	@Value("${app.opentime}")
	private LocalTime openTime;

	@Value("${app.closetime}")
	private LocalTime closeTime;

	//
	public LocalTime getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(LocalTime closeTime) {
		this.closeTime = closeTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalTime getOpenTime() {
		return openTime;
	}

	public void setOpenTime(LocalTime openTime) {
		this.openTime = openTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
