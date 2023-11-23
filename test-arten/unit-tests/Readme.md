# Unittests

Dieses Projekt behandelt die Unittests.
Es wird erklärt, was Unittests sind und wie sie geschrieben werden.
Zusätzlich wird erklärt, wie Unittests mit Spring Boot geschrieben werden.
Auch das Mocking wird behandelt.

## Was sind Unittests?

Ein Unittest ist ein Test, der eine Komponente des Programms testet. Dabei wird die Komponente isoliert von anderen Komponenten getestet. Das bedeutet, dass die Komponente keine Abhängigkeiten zu anderen Komponenten hat. Sollte die Komponente Abhängigkeiten haben, dann werden diese gemockt. Das bedeutet, dass die Abhängigkeiten simuliert werden.
> Für das Thema Mocking gibt es ein eigenes Projekt.
> Siehe dazu [tdd und mocking](../../tdd und mocking/Readme.md). Es wird empfohlen den Theorieteil dieses Projektes vorher zu lesen. Es wird auch noch eine Stelle wo es ohne die Grundlagen nicht weiter gehen kann. Diese ist entsprechend markiert.

Komponenten sind dabei verschiedene Dinge.
Komponenten können von Funktionen/Methoden über Klassen, Paketen bis hin zu ganzen Teilsystemen sein.
Es sollten auch alle Komponenten getestet werden.
Nur weil alle Klassen eines Paketes einzeln getestet wurden, heißt das nicht, dass das Paket als Komponente getestet wurde.
Wir werden uns hier auf die Klassen bzw. Methoden als Komponenten konzentrieren.

<hr>

## Unittests mit JUnit

Damit das automatisierte Ausführen von Tests nicht immer von 0 beginnt, gibt es ein Framework, welches das Erstellen von Tests vereinfacht. Dieses Framework heißt JUnit und ist in der Lage, die Tests automatisiert auszuführen und die Ergebnisse zu präsentieren.

Die offizielle [Website](https://junit.org) enthält die vollständige Dokumentation dieses Frameworks.
Dennoch seinen hier die wichtigsten Punkte aufgeführt.
JUnit befindet sich aktuell in der Version 5.X und besteht eigentlich aus mehreren Teilen. Es sind 3 Teile, die für uns wichtig sind:

* **JUnit Platform** - Die Plattform, die die Tests ausführt
* **JUnit Jupiter** - Die Bibliothek, die die Tests enthält; die modernen Methoden und Annotationen
* **JUnit Vintage** - Die Bibliothek, die die Tests aus JUnit 4.X ausführt

Hier soll sich ausschließlich mit JUnit Jupiter beschäftigt werden, da es die modernste Version ist und die anderen beiden Teile für uns nicht relevant sind.

### Importieren der Bibliothek mit Maven

Dank Maven ist es sehr einfach, die Bibliothek in das Projekt zu importieren. Dazu muss lediglich folgender Eintrag in die pom.xml eingefügt werden:

```xml

<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>
```

Da dieses Projekt ebenfalls Maven verwendet, ist das auch so in der `pom.xml` zu finden.

### Testklassen/Unittests schreiben

Folgender Quellcode kann als Template-Klasse für Unittests genommen werden.

```Java 
...

public class MeineKlasseTest {

	// def der privaten attribute die zum test gebraucht werden.
	private MeineKlasse getestetsObjekt;

	// Mock-Objekte
	@Mock
	private AndereKlasse mockObjekt;

	@BeforeEach
	void setup () {
		// init der privaten Attribute
		MockitoAnnotations.openMocks(this);
		getestetsObjekt = new MeineKlasse(mockObjekt);

		// eventuelles Stubbing
		when(getestetsObjekt.isX()).then(true);
	}

	@AfterEach
	void tearDown () {
		// Optinaler Reset von Objekten. 
	}

	@Test
	void meineTestMethode1 () {
		getestetsObjekt.m1();
		assertTrue(getestetsObjekt.isGut(), "Nach m1 muss das Objekt als gut gelten!")
	}

	@Test
	void meineTestMethode2 () {
		assertEquals(mockObjekt, getestetsObjekt.getAndereKlasse(), "Der Getter muss das passende Objekt zurückgeben");
	}

	@Test
	void meineTestMethode3 () {
		try {
			getestetsObjekt.methodeMitException();
			fail("Die Exception muss ausgelöst werden");
		} catch (NoSuchElementException ne) {
			// Alles Okay
		}
	}

}
```

#### Struktur der Testklasse

Die obige Klasse kann drei Bereiche aufgeteilt werden.
Die genutzten "Testobjekte als Attribute", der "Initialisierung bzw. das Aufräumen" und die "Testmethoden" (Es sind keine offiziellen Bezeichnungen)

In dem Template wird `MeineKlasse` getestet. Diese Klasse benötigt ein Objekt des Types `AndereKlasse`.
Damit dieses Objekt keinen Einfluss auf die Tests hat, wird dieses Objekt gemockt und mit der entsprechenden Annotation gekennzeichnet. Weitere Informationen zu Mock kommen in den nächsten Kapiteln. \
All benötige Objekte werden im ersten Bereich als private Attribute niedergeschrieben.

Im zweiten Bereich werden Vor- bzw. Nachbereitungen für jeden Test, innerhalb der Klasse, abgehandelt. Die Methoden, die mit `@BeforeEach` gekennzeichnet sind, laufen vor jedem Test.
In dieser/n Methode/n werden die Objekte initialisiert. Die Mock-Objekte werden dabei entweder, wie in diesem Beispiel, alle zusammen durch ` MockitoAnnotations.openMocks(this);` oder einzeln durch `mock(...)` initialisiert.
Das zu testende Objekt bzw. Klasse wird ganz normal durch den Aufruf des Konstruktors initialisiert.\
Sollte das zu testende Objekt Methoden oder Attribute, der Mock-Objekte, nutzen so werden die entsprechenden Teile "simuliert". Beispielsweise kann ein boolean-Getter des Mock-Objektes mit `when(mockObjekt.isX()).thenretrun(true);` simuliert werden.
Damit bekommt das testObjekt jedes Mal, wenn es diese Methode aufruft, `true` zurückgegeben.
Ziel ist es dabei die möglichen Fehler, aus der anderen Klasse, nicht zu haben und damit die Fehler nur innerhalb der zu testenden Klasse sind.

Der dritte und größte Abschnitt enthält alle Testmethoden, die die Methoden oder Attribute der Klasse `MeineKlasse` testen.
Dabei sollte ein gutes Namensschema genommen werden. Jede Methode, die mit `@Test` gekennzeichnet ist, ist ein Test-Case.
Die Methoden dürfen **nicht** private sein, da sie sonst nicht als Test-Case ausgeführt werden.
Da hier Unittests behandelt werden, müssen die Tests atomar sein. Das bedeutet, sie beeinflussen keine anderen Tests.
Das bedeutet auch, dass die Reihenfolge, der ausgeführten Test, nicht wichtig ist.\
Innerhalb der Test werden hauptsächlich die `asserX(...)`-Methoden aufgerufen. Wie diese Methoden genutzt werden sollten, ist im nächsten Kapitel erklärt.

#### assertX

`asserX(...)`-Methoden, aus JUnit, sicheren etwas zu. Sie können Vor-, Zwischen- und Nachbedingungen realisieren.\
Es sei angenommen, dass für eine Methode, die getestet werden soll, die Vorbedingung: "der Parameter x muss größer 3 sein" gilt.
Dann kann dies durch die Codezeile `assertTrue(x > 3, "Der Parameter x muss größer 3 sein!");` umgesetzt werden.
In diesem Fall wurde die `asssertTrue(...)`-Methode genommen. Diese nimmt den Booleschen-Ausdruck (Expression) `x > 3` und wertet ihn zur Laufzeit aus.
Wenn der Ausdruck zu `true` evaluiert wird, dann ist alles okay und die Vorbedingung gilt als erfüllt.
Wenn der Ausdruck hingegen zu `false` ausgewertet wird, dann wirft die `assertTrue(...)`-Methode eine Exception und das JUnit-Framework bricht den Test-Case ab und markiert ihn als Fehlschlag und gibt den angegeben String "Der Parameter x muss größer 3 sein!" im Testergebnisbericht ausgegeben.

Es gibt noch mehr `asserX(...)`-Methoden. So gibt es auch noch `asserEquals` mit zwei oder drei Parametern.
Dabei wird ein Sollzustand, mit einem Istzustand verglichen und optional (3. Parameter) die Fehler-Nachricht beim Fehlschlag ausgegeben.
Die Wahl der richtigen assert-Methode kann die Lesbarkeit des Codes erheblich verbessern.
So könnte auch das vorherige Beispiel mit `asserEquals(true, x > 3, "Der Parameter x muss größer 3 sein!");` realisiert werden.
Jedoch ist diese Umsetzung etwas komplizierter als die Vorherige.

Eine Übersicht über alle assert-Methoden kann bei [JUnit-Javadoc](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html) nachgelesen werden.

### JUnit-Annotationen

In JUnit 5.X gibt es viele Annotationen, die das Testen erleichtern.
Viele sind schon in JUnit 4 vorhanden, jedoch gibt es auch neue.
In der Datei
*src/test/java/structure/BesucherComputerTest.java* werden einige der neuen Annotationen genutzt und kurz erklärt.
Annotationen, die in dieser Datei vorkommen sind:

* `@Test` - kennzeichnet eine Methode als Test-Case (Testfall)
* `@BeforeEach` - kennzeichnet eine Methode, die vor jedem Test-Case ausgeführt wird
* `@AfterEach` - kennzeichnet eine Methode, die nach jedem Test-Case ausgeführt wird
* `@DisplayName` - gibt dem Test-Case einen Namen, der im Testergebnis-Bericht angezeigt wird
* `@RepeatedTest` - kennzeichnet eine Methode als wiederholbaren Test-Case
* `@TestMethodOrder` - gibt die Reihenfolge der Test-Case-Methoden an
* `@ParameterizedTest` - kennzeichnet eine Methode als parametrisierten Test-Case
* `@TestInstance` - gibt an, wie oft die Test-Case-Klasse instanziiert wird
* `@CsvSource` - gibt eine Liste von Parametern an, die für einen parametrisierten Test-Case genutzt werden

### Tests in IDEs

Die meisten IDEs haben eine Testumgebung bereits installiert, oder können über ein Plug-in installiert werden.
Oft ist JUnit mit integriert.

#### Übersicht der Tests

Nachdem Test ausgeführt wurden, werden sie oft in einem eigenen Fenster angezeigt.
Oft gibt es noch ein Gesamtergebnis, das anzeigt wie viele Tests fehlschlugen und wie viele erfolgreich waren.
Die ausgeführten Tests werden dabei auch entsprechend ihres Ergebnisses mit einem Symbol markiert.
In JUnit gibt es vier mögliche Testausgänge:

* success - Test läuft erfolgreich durch
* fail - ein assert stimmt nicht
* abort - eine unerwartete Exception trat auf
* skipped - Test wurde übersprungen

Je nach IDE werden dabei unterschiedliche Symbole genommen.
Manchmal werden sogar zwei Arten mit dem gleichen Symbol gekennzeichnet.

**Aufgabe:** In *src/test/java/TestErgebnisse.java* sind verschiedene Testausgänge vorhanden.
Führen Sie die Tests aus und stellen Sie fest, welcher Test zu welchem Ausgang gehört und welches Symbol die IDE dazu
anzeigt.

#### Übersicht filtern

Wenn alle Tests des Projektes ausgeführt wurden, dann ist im Normalfall ein großer Teil erfolgreich und ein kleiner Teil fehlgeschlagen.
Oft interessiert einen aber nur die fehlgeschlagenen Tests. Dafür gibt es Filter.
Diese blenden alle erfolgreichen Tests aus.

<hr>

## Unittests mit Spring Boot

Unittests mit Spring Boot müssen etwas besonders behandelt werden.
Viele Funktionalitäten von Spring Boot sind in Unittests nicht vorhanden.
So sollte der Webserver nicht gestartet werden, da dieser nicht benötigt wird.
Auch die Datenbank sollte nicht gestartet werden, da diese auch nicht benötigt wird. \
Hier ist Mocking sehr wichtig. Es sollte alles gemockt werden, was nicht getestet werden soll. Für eine genaue Behandlung von Mocking siehe den `tdd und mocking` Teil dieses Projektes. \
> Hinweis! <span style="color: #ff4040;">Ab hier ist Mocking unbedingt notwendig! Bitte das erwähnte Projekt lesen!</span>

### Repository-Tests

Fangen wir mit der untersten Ebene an - der Datenebene.
Die Daten stehen in einer Datenbank und müssen geladen werden, damit sie Entitäten erzeugen können.
Dafür gibt es die Repositories von Spring.
Es gibt aber ein "Problem" mit den Repositories.
Im Normalfall ist das Repository ein Interface und es werden nur die Methodenköpfe definiert.
Anhand der Methodennamen oder der Query-Annotationen werden die Methoden von Spring implementiert.  
Das ist zwar ziemlich bequem, aber die Methoden werden erst mit der Laufzeitumgebung umgesetzt.
Und trotzdem müssen die Methoden getestet werden. Sicher ein 'save()' oder 'findById()' muss nicht getestet werden, wenn sie nicht überschrieben wurden.
Das kann man auch bei den einfachen Methoden, die nur nach einem Attribut filtern so machen.
Komplexere Methoden sollten aber unbedingt getestet werden.
Nicht nur die mit Methodennamen, sondern auch die mit Query-Annotationen.
> Es gibt diskussionen, ob die Tests jetzt Unit- oder Integrationstests sind. Im Projekt wurden sie als Unit-Tests behandelt. Es wird aber niemanden stören, wenn sie als Integrationstests behandelt werden.
#### Annotationen für Repository-Tests

Damit die Repositories getestet werden können, müssen sie als Testklassen gekennzeichnet werden.
Damit kann Spring die Methoden implementieren und die Repositories können getestet werden.
Die Annotation dafür ist `@DataJpaTest`.
Zusätzlich wird noch `@AutoConfigureTestDatabase` benötigt.
> Hinweis! \
> Wenn Spring Security genutzt wird, dann muss ein UserDetailsService vorhanden sein. Wir können das hier "umgehen" indem wir `@AutoConfigureMockMvc` nutzen. Das ist aber nur eine Notlösung. Eine bessere Lösung wird gerade gesucht bzw. getestet.

#### Beispiele für Repository-Tests

In der TeDDjbrary gibt es das `Buch`- und `RegalRepository`.
Dazu gibt es die Testklassen `BuchRepositoryTest` und `RegalRepositoryTest` im `core` bzw. `inventory`-Package.
Es gibt komplexe Methoden in den Repositories.
Diese sollen verdeutlichen, dass bereits eine Logik in den Repositories vorhanden ist.
Diese Logik muss getestet werden. Deshalb gibt es die Tests. \
Die Tests sind sehr einfach gehalten.
Es wird ein Repository-Objekt erstellt und mit der Annotation `@Autowired` wird das Repository-Objekt injiziert. Dann wird die Methode aufgerufen und das Ergebnis überprüft.

#### Aufgabe zum Üben

TODO: Aufgabe zu Repository-Tests ergänzen

### Entity-Tests

Die Entitäten haben die einfachsten Unittests.
Sie sind reine Java-Klassen und haben keine Abhängigkeiten von Spring.
Sie können also direkt getestet werden.
Es sollten nur Mocks von Klassen geben, die selbst Entity-Klassen sind.
Value-Objekte werden nicht gemockt.

Einige Dinge können in Unittest nicht getestet werden. Sollte es z.B. so sein, dass die Attribute der Entity mit Validation-Constraints versehen sind, dann kann das nicht getestet werden. Das liegt daran, dass die Validation-Constraints erst zur Laufzeit überprüft werden. Deshalb sind das eher (Komponenten-)Integrationstests.

#### Besonderheiten bei Entity-Tests

#### Beispiele für Entity-Tests

#### Aufgabe zum Üben

TODO: Aufgabe zu Entity-Tests ergänzen

### Service-Tests

Nun sind wir in der Logik-Ebene. In dieser werden die Daten verarbeitet.
Hier gibt es die meisten Abhängigkeiten.
Im Allgemeinen werden die Komponenten, die die Logik maßgeblich verwalten und an die nächste Ebene weiter geben,
*Service* genannt.
Beim Sping-Framework sind das Klassen mit der `@Service` Annotation.
Ein Service sollte nur einen großen Aufgabenbereich haben.
In einem Service sollten folgende Abhängigkeiten vorhanden sein:

* Repositories
* Entitäten
* Value-Objekte
* andere Services
* Logger
* Model-Objekt

In einem Service sollten keine Controller oder andere Web-Komponenten vorhanden sein. Diese gehören in die nächste Ebene.

Durch diese Abhängigkeiten ist es nicht möglich, den Service direkt zu testen. Es müssen also Mocks erstellt werden. Es sollten (fast) alle Abhängigkeiten gemockt werden. \
Das Schema ist dabei immer das gleiche. Es wird ein Mock-Objekt erstellt und in den Service injiziert. Dann wird die Methode aufgerufen und das Ergebnis überprüft. Dabei werden die Mock-Objekte genutzt, um die Ergebnisse zu prüfen.

#### Annotationen für Service-Tests

#### Beispiele für Service-Tests

#### Aufgabe zum Üben

TODO: Aufgabe zu Service-Tests ergänzen


### Controller-Tests

Die letzte Ebene sind die Controller [^1].
Diese sind für die Kommunikation mit dem Client zuständig. Sie nehmen die Eingaben des Clients entgegen und geben die Ergebnisse an jenen zurück.

[^1]: Je nach Modell gibt noch eine Ebene. In einer Spring Boot-Anwendung ist es aber im Normalfall die letzte auf seitens des Servers.
Auf der Seite des Clients gibt es noch die UI-Ebene. Diese wird hier nicht behandelt.

#### Besonderes zu Controller-Tests

Der Controller ist die Schnittstelle zum Client. Schnittstellen werden aber eher als Integrationstests getestet.
Deshalb ist das Testen der Controller bei Unittest schnell erledigt. Bei den Integrationstests wird es aber interessant. Siehe dazu das Projekt [integrations-tests](../integrations-tests/Readme.md).

#### Beispiele für Controller-Tests

Das Beispiel für Controller-Unittests sind die Tests zum `BestandsController` der TeDDjbrary.

#### Aufgabe zum Üben

TODO: Aufgabe zu Controller-Tests ergänzen

## Texte für später

die Abhängigkeiten automatisch injizieren. Dafür gibt es die `@Autowired`-Annotation. Jedoch ist das wieder die Laufzeitumgebung. Deshalb muss das Mocking manuell gemacht werden.