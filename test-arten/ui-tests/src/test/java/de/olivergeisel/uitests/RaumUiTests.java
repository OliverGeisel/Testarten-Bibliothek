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

package de.olivergeisel.uitests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import de.olivergeisel.uitests.page_objects.LoginPage;
import de.olivergeisel.uitests.page_objects.RaumDetailPage;
import de.olivergeisel.uitests.page_objects.RaumUebersichtPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

@ExtendWith({SoftAssertsExtension.class}) // Bricht nicht ab, wenn ein Test fehlschlägt
@Owner("Oliver")
@Feature("Raum")
@Tag("ui")
@Tag("anwendung")
class RaumUiTests {

	private static final String BASE_URL = "https://localhost:8443/";

	private RaumUebersichtPage raumUebersichtPage = new RaumUebersichtPage();
	private RaumDetailPage     raumDetailPage     = new RaumDetailPage();
	private LoginPage          loginPage          = new LoginPage();

	@BeforeAll
	static void init () {

		Configuration.timeout = 5_000;
		SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
				.screenshots(true)
				.savePageSource(true)
		);
		// restliche Configuration in selenide.properties
	}

	@AfterAll
	static void afterAll () {
		closeWebDriver();
	}

	@AfterEach
	void tearDown () {
		clearBrowserCookies();
		clearBrowserLocalStorage();
		//	closeWindow();
		Configuration.headless = true; // rücksetzen beim Debugging
	}

	@BeforeEach
	void setUp () {
		// Cookies setzen
		// setzt immer auf Deutsch zurück
		webdriver().driver().getWebDriver().manage().addCookie(new Cookie("locale", "de"));
	}

	@Feature("RaumÜbersichtsseite")
	@Test
	@DisplayName("Raumübersichtsseite aufrufen")
	void overviewTest () {
		open("rooms"); // BaseUrl aus selenide.properties wird vorangestellt
		raumUebersichtPage.ueberschrift.shouldBe(visible);
		raumUebersichtPage.ueberschrift.shouldHave(text("Unsere Leseräume"));
		raumUebersichtPage.raumListe.shouldHave(size(9));
	}

	@Feature("RaumDetailseite")
	@Test
	@DisplayName("Raumdetailseite aufrufen")
	void detailsAufrufen () {
		open("rooms");
		raumUebersichtPage.raumListe.get(0).$("button.btn-outline-primary").click();

		raumDetailPage.betretenButton.shouldBe(visible).shouldBe(enabled);
		raumDetailPage.verlassenButton.shouldBe(visible).shouldBe(disabled);
		raumDetailPage.imRaumAnzahl.shouldBe(text("0"));
	}

	@Feature("RaumDetailseite")
	@Test
	@DisplayName("UC: Raum betreten - mit anmelden")
	void betretenMitAnmeldenTest () {
		Configuration.headless = false; // für Debugging
		open("rooms");
		raumUebersichtPage.raumListe.get(0).$("button.btn-outline-primary").click();
		raumDetailPage.betretenButton.shouldBe(visible).shouldBe(enabled);
		raumDetailPage.verlassenButton.shouldBe(visible).shouldBe(disabled);
		raumDetailPage.imRaumAnzahl.shouldBe(text("0"));
		raumDetailPage.betretenButton.click();

		// Anmeldung
		webdriver().shouldHave(url(BASE_URL + "login"));
		loginPage.usernameInput.setValue("besucher");
		loginPage.passwordInput.setValue("besucher");
		loginPage.loginButton.click();
		open("rooms");
		raumUebersichtPage.raumListe.get(0).$("button.btn-outline-primary").click();

		// Prüfen, ob Raum betreten wurde
		raumDetailPage.betretenButton.shouldBe(visible).shouldBe(disabled);
		raumDetailPage.verlassenButton.shouldBe(visible).shouldBe(enabled);
		raumDetailPage.imRaumAnzahl.shouldBe(text("1"));
	}

	@Feature("RaumDetailseite")
	@Test
	@DisplayName("UC: Raum verlassen - mit anmelden")
	void verlassenMitAnmeldenTest () {
		Configuration.headless = false; // für Debugging
		open("rooms");
		var detailsButtonSelector = "button.btn-outline-primary";
		raumUebersichtPage.raumListe.get(0).$(detailsButtonSelector).click();
		raumDetailPage.betretenButton.shouldBe(visible).shouldBe(enabled);
		raumDetailPage.verlassenButton.shouldBe(visible).shouldBe(disabled);
		raumDetailPage.betretenButton.click();
		raumDetailPage.imRaumAnzahl.shouldBe(text("1"));

		// Anmeldung
		webdriver().shouldHave(url(BASE_URL + "login"));
		loginPage.usernameInput.setValue("besucher");
		loginPage.passwordInput.setValue("besucher");
		loginPage.loginButton.click();
		open("rooms");
		raumUebersichtPage.raumListe.get(0).$(detailsButtonSelector).click();

		// Prüfen, ob Raum betreten wurde
		raumDetailPage.betretenButton.shouldBe(visible).shouldBe(disabled);
		raumDetailPage.verlassenButton.shouldBe(visible).shouldBe(enabled);
		raumDetailPage.imRaumAnzahl.shouldBe(text("1"));

		raumDetailPage.verlassenButton.click(); // Hier kommt ein Fehler (gewollt)
		raumUebersichtPage.raumListe.get(0).$(detailsButtonSelector).click();

		raumDetailPage.imRaumAnzahl.shouldBe(text("0"));
		raumDetailPage.betretenButton.shouldBe(visible).shouldBe(enabled);
		raumDetailPage.verlassenButton.shouldBe(visible).shouldBe(disabled);
	}
}


