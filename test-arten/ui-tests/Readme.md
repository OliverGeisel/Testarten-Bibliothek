# UI- und Abnahme-Tests

In diesem Projekt werden UI-Tests mit Selenide beschrieben.
UI-Tests gehören zu den Abnahmetests (auch Akzeptanztests genannt).
Es wird erklärt, wie man mit Selenide UI-Tests schreibt, was UI-Tests testen bzw. nicht testen und wie ein Akzeptanztest mit Selenide umgesetzt werden kann.

## Was sind UI-Tests?

Zuerst einmal muss geklärt werden, was eine UI ist.
UI steht für **User Interface**.
Es ist also die Schnittstelle zwischen Benutzer und Anwendung.
Eine UI unterschiedliche Formen haben.
Es kann eine grafische Oberfläche sein, wie z.B. eine Webseite oder eine Desktopanwendung. Es kann aber auch eine Kommandozeile sein, wie z.B. die Bash oder die PowerShell.
Eine Schnittstelle mit einem anderen Sinnesorgan, wie z.B. Sprache (Mikrofon) oder Gestik (Kamera), ist auch eine UI.
Hier konzentrieren wir uns aber nur auf die grafische Oberfläche bzw. Webanwendungen in Browsern.

### Was UI-Tests können/sollen

Eigentlich sind UI-Tests mit das Einfachste, was es gibt.
Grafische Oberflächen bestehen aus Elementen, die der Benutzer bedienen kann.
Und alles, was diese Elemente tun, ist es Funktionen im Backend aufzurufen und vlt. noch ein paar Daten vom Benutzer entgegenzunehmen und mitzuschicken.
Die ganze Logik, das Empfangen, Verarbeiten und Zurückschicken von Daten, wird im Backend gemacht.
In UI-Test muss also nur getestet werden, ob die Elemente auf der Seite richtig funktionieren und ob die richtigen Daten an das Backend geschickt werden.
Man könnte einfach das Backend mocken und die UI-Tests müssten trotzdem funktionieren.

Leider gibt es ein Problem mit UI-Tests.
Sie sind sehr anfällig für Änderungen.
Wenn sich die UI ändert, müssen die UI-Tests angepasst werden.

> In diesem Projekt werden wir nicht so weit gehen und das Backend mocken. Das macht die Tests zwar nicht komplett robust und die Reihenfolge der Tests kann potenziell andere beeinflussen. Für die Einführung in das Thema ist es jedoch ausreichend.

### Was UI-Tests nicht können/sollen

UI-Tests sind nicht dazu da, um die Funktionalität der Anwendung zu testen.
Dafür sind Unit- und Integrationstests da.
UI-Tests sollen nur die Funktionalität der UI testen.
Dazu gehört z.B. die Navigation auf der Seite, das Ausfüllen von Formularen oder das Klicken von Buttons.
Ebenso wenig wird bei einer Webanwendung geprüft, ob die HTTP-Requests den richtigen Statuscode zurückgeben.
Das ist Aufgabe der API-Tests.
Die UI-Tests simulieren nur einen Benutzer, der die Anwendung bedient und diese sehen sehr selten, welchen Statuscode ein Request zurückgibt.
Benutzer interessiert sich nur für das, was auf der Seite angezeigt wird.

<hr>

## Selenium und Selenide

[Selenium](http://www.seleniumhq.org/)
ist ein Framework für die Manipulation von Webbrowsern.
Ein Webdriver steuert den Browser und kann auf Elemente der Webseite zugreifen.
In der TeDDjbrary wird im Hintergrund mithilfe von Selenium ein Benutzer simuliert, der in einem bestimmten Intervall immer eine Webseite aufruft. [^1]
Mit Selenium lassen sich auch UI-Tests schreiben, die die Funktionalität der Anwendung testen.
Jedoch ist das Schreiben von Tests mit Selenium sehr aufwändig, da alles über Java-Kontrollstrukturen und JUnit-Asset-Methoden gemacht werden muss.

Mit [Selenide](http://selenide.org/) können Tests deutlich leichter geschrieben werden.
Zusätzlich können mit Selenide auch Berichte über die Testergebnisse erstellt und Screenshots von den Tests gemacht werden.

[^1]: Siehe dazu auch: `TeDDjbrary/src/main/java/de/olivergeisel/teddjbrary/auxiliary/PageRequest.java`

### Selenide Grundlagen

In Selenide kann eine Seite mit der Methode `open()` geöffnet werden.

~~~jshelllanguage
import static com.codeborne.selenide.Selenide.open;

	open("http://localhost:8443/");
~~~

Damit öffnet sich automatisch ein Browserfenster mit der angegebenen URL.

### Selenide Elemente

Mit Selenide können Elemente auf der Seite angesprochen werden.
Dazu gibt es verschiedene Methoden, die auf der Klasse `SelenideElement` definiert sind.
Die Methode, die in diesem Projekt am häufigsten verwendet wird, ist `$(String cssSelector)`.

```jshelllanguage 
import static com.codeborne.selenide.Selenide.$;

	SelenideElement ueberschrift = $("h1");
```

Dies wählt das **erste** Element aus, das mit dem angegebenen CSS-Selektor gefunden wird.

Es können aber auch alle Elemente ausgewählt werden, die mit dem CSS-Selektor gefunden werden. Das geht mit der Methode `$$()`.

```jshelllanguage
import static com.codeborne.selenide.Selenide.$$;

	ElementsCollection ueberschriften = $$("h1");
```

In diesem Beispiel wird eine Liste von Elementen zurückgegeben, die mit dem CSS-Selektor gefunden werden. Ob die Elemente dabei auf der gleichen Ebenen liegen oder nicht, spielt keine Rolle.

Es gibt einen ziemlich praktischen Weg, die Elemente einer Webseite in Tests zu nutzen.
Dazu werden einfach die Elemente der Seite in einer Klasse definiert.

```jshelllanguage caption="LoginPage Beispiel für Page-Object"
public class LoginPage {

	public SelenideElement usernameInput = $("input#username");
	public SelenideElement passwordInput = $("input#password");
	public SelenideElement loginButton   = $("button#submit");

}
```

Dieses Vorgehen hat den Vorteil, dass die Elemente nur einmal definiert werden müssen und dann in allen Tests verwendet werden können.

### Aktionen auf Elementen

Jedes Element, das mit Selenide ausgewählt wurde, kann manipuliert werden. So kann z.B. ein Button geklickt werden oder ein Input mit Werten ausgefüllt werden.

```jshelllanguage
var input = $("#input");
	var button = $("#button");

	input.setValue("Hallo Welt");
	button.click();
```

Dies ist ein Beispiel, wie ein Input mit einem Wert gefüllt wird und dann ein Button geklickt wird.

### Assertions

Mit Selenide können auch Assertions gemacht werden. Dazu gibt es die Methode `shouldHave()`, die auf einem Selenide-Element aufgerufen werden kann.

```jshelllanguage
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

SelenideElement ueberschrift = $("h1");
ueberschrift.shouldHave(text("Hallo Welt"));
``` 

In diesem Beispiel wird geprüft, ob die Überschrift den Text "Hallo Welt" enthält.

### Beispiel für einen UI-Test

Nachdem jetzt die Grundlagen von Selenide erklärt wurden, kann ein Beispiel für einen UI-Test gezeigt werden.
Dieses Beispiel ist aus der Datei `src/test/java/de/olivergeisel/uitests/ErsteUITests.java`

> **Hinweis:** Die Anwendung muss separat gestartet werden, damit die Tests funktionieren.

~~~jshelllanguage

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
~~~

<hr>

## Ein Abnahmetest mit Selenide

In diesem Abschnitt wird die Umsetzung eines Abnahmetest für die Anwendung `TeDDjbrary` mit Selenide beschrieben.

### Use-Case Beschreibung

In diesem Use-Case (UC: Raum verlassen) soll es möglich sein, dass ein angemeldeter Nutzer aus einem Leseraum, in dem er momentan drin ist, wieder verlassen kann.
Dazu muss der Nutzer auf der Seite Übersicht der Räume sein und den entsprechenden Raum auswählen. Auf der Detailseite klickt er dann auf "Verlassen" und verlässt damit den Raum. Dadurch wird die Anzahl der Personen im Raum um eins reduziert und der Benutzer wird wieder auf die Übersichtsseite weitergeleitet.

### Konkreter Akzeptanztest

**Name:** Erfolgreiches verlassen eines Raumes, wenn Nutzer nicht angemeldet ist. \
**Beschreibung:** Der Nutzer kann einen Raum, in dem er ist wieder verlassen.
Wenn er nicht angemeldet ist, muss er sich anmelden und kann erst dann die Aktion durchführen. Das Verlassen des Raumes soll dabei gelingen \
**Vorbedingung:
** Der Benutzer ist nicht angemeldet. Es befindet sich in dem Raum, den er verlassen möchte. Der Nutzer befindet sich auf der Seite "Übersicht der Räume".
**Nachbedingung:
** Der Benutzer ist nicht mehr in dem Raum, den er verlassen hat. Er befindet sich auf der Seite "Übersicht der Räume" und die Anzahl der Personen im Raum ist um 1 verringert. \

Dies ist der Code des Tests, der den Akzeptanztest beschreibt. Er befindet sich in der Datei `src/test/java/de/olivergeisel/RaumUiTests.java`

> **Hinweis:
** Der Test wird fehlschlagen. Die Logik ist im Backend nicht implementiert. Das ist aber gewollt, da es hier nur um die Umsetzung eines Abnahmetests geht.

```jshelllanguage

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
```

### Aufgabe

Korrigieren bzw. ergänzen Sie die Logik, damit der oben stehende Test erfolgreich wird.
Der Test sollte nicht angefasst werden.

<hr>

## Zusammenfassung
