# Integrationstests

## Was sind Integrationstests?

Integrationstests sind Tests, die die einzelnen Komponenten eines Systems testen.
Dabei wird das System als Ganzes betrachtet. Es wird nicht nur eine Komponente getestet, sondern auch die Interaktion
zwischen den Komponenten.
Dabei wird das System als Black-Box betrachtet. Das heißt, dass die Tests nicht wissen, wie das System funktioniert. Es
wird nur getestet, ob das System die richtigen Ergebnisse liefert.
Nach

### Unterschied zu Unit-Tests

Im Gegensatz zu Unit-Tests, die nur eine Komponente testen, testen Integrationstests das Zusammenwirken von mehreren
Komponenten.
Dadurch sind Integrationstests komplexer als Unit-Tests.

### Arten von Integrationstests

Es gibt verschiedene Arten von Integrationstests.
Diese unterscheiden sich in der Anzahl der Komponenten, die getestet werden.

### Spring Boot Integrationstests

Spring Boot bietet die Möglichkeit, Integrationstests zu schreiben.
Dies wird durch die Annotation `@SpringBootTest` ermöglicht.
Jede Testklasse, die diese Annotation besitzt, wird als Integrationstest ausgeführt.
Weil die gesamte Spring Boot Umgebung gestartet wird, dauert das Ausführen der Tests länger als bei Unit-Tests.
Deswegen sollte die Anzahl der Integrationstests nicht übertrieben groß sein.
Ein weiterer Nachteil ist, dass die Spring Boot Umgebung für jeden Test neu gestartet wird.
Dadurch dauert das Ausführen der Tests noch länger. Hier kann aber die Annotation konfiguriert werden, sodass die
Spring Boot Umgebung nur einmal pro Testklasse gestartet wird.
[Hier](https://docs.spring.io/spring-framework/reference/testing/integration.html) ist die Dokumentation zu den Spring
Boot Integrationstests zu finden.
Ein einfaches Beispiel für einen Spring Boot Integrationstest ist folgender:

```java

@SpringBootTest
class SpringBootIntegrationTest {

	@Test
	void contextLoads() {
	}

}
```

Dieser Test lädt die Spring Boot Umgebung und prüft, ob diese geladen werden kann.
Wenn die Spring Boot Umgebung nicht geladen werden kann, schlägt der Test fehl.




