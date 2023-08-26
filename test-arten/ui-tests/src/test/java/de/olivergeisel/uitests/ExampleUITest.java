package de.olivergeisel.uitests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.*;

@ExtendWith({SoftAssertsExtension.class})
@Owner("Oliver")
class ExampleUITest {

	@BeforeAll
	static void init() {

		Configuration.timeout = 5_000;
		SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
				.screenshots(true)
				.savePageSource(true)
		);
		// restliche Configuration in selenide.properties
	}

	@AfterAll
	static void afterAll() {
		closeWebDriver();
	}

	@AfterEach
	void tearDown() {
		clearBrowserCookies();
		clearBrowserLocalStorage();
		//	closeWindow();
	}

	@Feature("rooms")
	@Test
	void landingTest() {
		open("rooms");
		var inputField = $(".input");
		inputField.shouldBe(Condition.visible);
	}
}
