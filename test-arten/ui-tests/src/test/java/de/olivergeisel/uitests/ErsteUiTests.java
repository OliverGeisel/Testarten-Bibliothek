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
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.codeborne.selenide.logevents.SelenideLogger;
import de.olivergeisel.uitests.page_objects.StartPage;
import de.olivergeisel.uitests.page_objects.SuchePage;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

;

@ExtendWith({SoftAssertsExtension.class})
@Tag("ui")
@Tag("anwendung")
class ErsteUiTests {

	public SelenideElement linkKatalog = $(byText("Katalog\n                "));
	StartPage startPage = new StartPage();
	SuchePage suchePage = new SuchePage();

	@BeforeAll
	public static void setUpAll () {
		Configuration.browserSize = "1280x800";
		SelenideLogger.addListener("allure", new AllureSelenide());
	}

	@BeforeEach
	public void setUp () {

		open("https://localhost:8443/"); // Öffnen der Verbindung zur Seite
	}

	@Test
	@DisplayName("Erster Selenide Test")
	void abfolge () {
		open("https://localhost:8443/"); // Öffnen der Verbindung zur Seite

		SelenideElement navBar = $("nav[class*='visible']"); // Auswahl eines Elements per CSS-Selektor
		var katalogButton = navBar.$(byText("Katalog")); // Auswahl eines Elements per Text --> by... Selektoren

		navBar.shouldBe(visible); // Prüfen, ob Element sichtbar ist
		katalogButton.shouldHave(attribute("href", "https://localhost:8443/inventory"));
		// Prüfen, ob Element ein Attribut mit einem bestimmten Wert hat
	}

	@Test
	@DisplayName("Erster Selenide Test mit Collections")
	void abfolge_collection () {
		// open() ist in beforeEach() bereits aufgerufen worden
		SelenideElement navBar = $("nav[class*='visible']"); // Auswahl eines Elements per CSS-Selektor
		ElementsCollection navBarItems =
				$$("html > body > nav > div > div > ul > li"); // Auswahl mehrerer Elemente per CSS-Selektor
		// navBarItems = navBar.$$("li"); // Äquivalent zu vorheriger Zeile

		navBarItems.shouldHave(size(9)); // Prüfen, ob alle Elemente vorhanden sind

	}

	@Test
	@DisplayName("Erster Selenide Test mit Page Objects")
	void search () {
		// Die Nutzung von Page Objects vereinfacht die Wartung der Tests.
		// Definition der Page Objects ist in src/test/java/de/olivergeisel/uitests/page_objects
		// Page Objects sind Klassen, die die Elemente einer Seite kapseln.

		startPage.navBar.shouldBe(visible); // Prüfen, ob Element sichtbar ist
		startPage.katalogButton.shouldBe(visible); // Prüfen, ob Element sichtbar ist

		startPage.searchInput.setValue("Hallo"); // Eingabe in ein Suchfeld
		startPage.searchInput.pressEnter(); // Enter beim Suchfeld drücken
		// Prüfen, ob die URL passt
		webdriver().shouldHave(url("http://localhost:8080/search?query=Hallo"));
		// Suchbegriff endet
		// Hier bricht er ab, da die Implementierung der Suche nicht vorhanden ist. (Gewollt)
		suchePage.results.shouldHave(sizeGreaterThan(1)); // Definiert wird aber nicht genutzt


	}

}
