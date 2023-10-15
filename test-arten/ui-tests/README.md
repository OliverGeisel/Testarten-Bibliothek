# UI- und Abnahme-Tests

E

## Selenium und Selenide

Selenium ist ein Framework für UI-Tests. Es ermöglicht das Testen von Webanwendungen in verschiedenen Browsern.
Selenide ist eine Erweiterung von Selenium, die das Schreiben von Tests vereinfacht.
Selenide kann unter anderem Berichte über die Testergebnisse erstellen und Screenshots von den Tests machen.

## Beispiel in TeDDjbrary

In der TeDDjbrary gibt es einen ganzen Bereich für UI-Tests. Unter "/rooms" ist die Übersicht der Räume zu finden.
In diesem Teilprojekt gibt es einige UI-Tests, die mit Selenium bzw. Selenide geschrieben wurden.
Hier ein Beispiel für einen Test mit Selenide:

```java

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoomDetailsPageTest {

	@Test
	public void shouldShowRoomDetails() {
		open("/rooms/1");
		$("#room-title").shouldHave(text("Room 1"));
	}
}
```

## Beispiel für einen UI-Test

```java
package de.otto.myapp.ui;

import de.otto.myapp.domain.Product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductDetailsPageTest {

	@Test
	public void shouldShowProductDetails() {
		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/products/1");
		WebElement title = driver.findElement(By.className("product-title"));
		assertThat(title.getText(), is("Product 1"));
	}
}
```
