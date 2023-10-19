# Testarten-Bibliothek

Dieses Projekt ist im Rahmen des Moduls *Softwaretechnologie
Praktikum* an der Technischen Universität Dresden entstanden. Es ist ein Projekt von Oliver Geisel. Ziel ist es die Grundlagen von Tests zu erlernen und zu verstehen. Neben den Grundlagen soll auch das Thema Test Driven Development (TDD) und Mocking behandelt werden. Damit im Praktikum auch gute Tests geschrieben werden, sind auch Tests mit dem [Spring Framework](https://spring.io/) enthalten.

Dieses gesamte Projekt ist eine Ansammlung vieler Teilprojekte, die alle mit dem Thema Tests zu tun haben. Dazu gehören 3 Teilbereiche:

* **Testarten** - Teilprojekte zu verschiedenen Testarten
* **TDD und Mocking** - Teilprojekte zu TDD und Mocking
* **TeDDjbrary** - Eine Webanwendung mit Spring Boot geschrieben, um beispiele für Tests zu geben.

Zusätzlich ist **dieses Dokument
** der Leitfaden für das gesamte Projekt. Es wird empfohlen dieses Dokument zu lesen und die Teilprojekte entsprechende der Reihenfolge in diesem Dokument zu bearbeiten.


<hr>

## Allgemeines zu den Teilprojekten

Jedes Teilprojekt ist ein eigenes Maven-Projekt. Im Wurzelverzeichnis jedes Projektes gibt es eine eigene `Readme.md` Datei. Diese Datei ist der entsprechende Leitfaden für das Teilprojekt.
In dieser sind immer eine Erklärung der entsprechenden Thematik mit Beispielen und wenn verfügbar auch Übungsaufgaben enthalten.
Jedes Teilprojekt steht (bis auf eine Ausnahme) für sich alleine. Es ist nicht notwendig alle Teilprojekte zu bearbeiten.

### Ablauf des gesamten Projektes

Zuerst möchten wir uns mit der Definition bzw. allgemeinen Begriffen und Dingen von Tests beschäftigen. Dies wird in diesem Dokument in den ersten Kapiteln behandelt.
Danach soll es mit Unittests weitergehen. Hierzu gibt es ein eigenes Teilprojekt. Es ist unter `testarten/unittests` zu finden. Während der Erklärung von Unittests wird auch das Thema Mocking behandelt.

### Was davor sein sollte

Dieses Projekt ist für Studenten im 3. Semester des Studienganges Informatik an der TU Dresden gedacht. Es wird davon ausgegangen, dass die Studenten bereits die Module
*Programmierung* und
*Softwaretechnologie* an der TU Dresden bzw. ähnliche Module an einer anderen Hochschule absolviert haben. Das bedeutet, dass bereits Erfahrung mit der Programmiersprache Java, Algorithmen, Grundlagen des Spring-Frameworks, Maven und Debugging vorhanden seien sollten.
Sollte diese Grundlagen nicht vorhanden sein, so wird empfohlen diese zuerst zu erlernen.
Für das Thema Debugging gibt es bereits ein anderes Projekt, das eine Einführung in das Thema gibt. Dieses ist bei GitHub unter [OliverGeisel/Debug-Tutorial](https://github.com/OliverGeisel/Debug-Tutorial) zu finden.

Eine Hilfe kann die [Checkliste](#checkliste---vorbereitung) unten sein. Diese enthält alle Themen, die für dieses Projekt wichtig sind. Sollte ein Thema nicht bekannt sein, so wird empfohlen dieses zuerst zu erlernen und dann den Haken zu setzen.


<hr>

## Was ist ein Test und wenn ja wie viele?

### Was ist ein Test

Es gibt viele Definitionen für das Wort Test.
Im [Duden](https://www.duden.de/rechtschreibung/Test) wird ein Test wie folgt definiert:
"nach einer genau durchdachten Methode vorgenommener Versuch, Prüfung zur Feststellung der Eignung, der Eigenschaften, der Leistung o. Ä. einer Person oder Sache" - Duden.de, 19.10.2023 \
In [Wikipedia](https://de.wikipedia.org/wiki/Test_(Informatik)) wird ein Softwaretest wie folgt definiert:
"Ein Softwaretest prüft und bewertet Software auf Erfüllung der für ihren Einsatz definierten Anforderungen und misst ihre Qualität." - de.Wikipedia.org, 19.10.2023 \
Ein (Software)Test ist nach diesen Definitionen eine Prüfung, ob Software die bestimmte Anforderungen erfüllt. Aus der Definition des Dudens geht hervor, dass diese Prüfung nach einer genau durchdachten Methode durchgeführt wird.

Wikipedia und der Duden sind jedoch nicht Institutionen, die besten Definitionen für Softwaretests festlegen.
Es gibt eine Organisation, welche sich mit allem befasst, was mit dem Thema "Tests für Software" beschäftigt. Diese Organisation ist das [International Software Testing Qualifications Board](https://www.istqb.org/) (ISTQB).
Es gibt auch einen deutschen Ableger, das [German Testing Board](https://www.german-testing-board.info/).
Auf der Webseite des German Testing Boards gibt es eine [Seite](https://www.german-testing-board.info/wp-content/uploads/2018/09/ISTQB®_GTB_Standardglossar_der_Testbegriffe_Deutsch_Englisch_V3_2.pdf) die unter anderem den Bergriff Test definieren. Darin heist es, dass ein Test: "Eine Menge von einem oder mehreren Testfällen" ist. \
Ein Testfall ist laut dieser Seite: "Eine Menge von Vorbedingungen, Eingaben, Aktionen (falls anwendbar), erwarteten Ergebnissen und Nachbedingungen, welche auf Basis von Testbedingungen entwickelt wurden. [Nach ISO 29119]" \
Der darin erwähnte ISO Standard kann [hier](https://standards.iso.org/ittf/PubliclyAvailableStandards/index.html) nachgelesen werden.

Wenn wir also von Tests in der Softwareentwicklung sprechen, dann meinen wir eine Menge von Testfällen.
Testfälle haben, wie oben beschrieben gewisse Mengen. Daraus lässt sich auch eine allgemeine Abfolge von Testfällen ableiten.
Jeder Testfall hat Vorbedingungen und Eingaben. Mit diesen werden Aktionen ausgeführt. Die Aktionen führen dann zu erwarteten Ergebnissen und Nachbedingungen. \
Ein Testfall hat also immer drei Teile:

* Eingabe/Vorbereitung
* Verarbeitung
* Ausgabe/Auswertung

Diese 3 Teile bzw. dieser Ablauf wird auch als *Arrange-Act-Assert* bezeichnet.

### Weitere Begriffe und Definitionen

Neben dem Begriff Test(fall) gibt es noch weitere Begriffe, die in der Softwareentwicklung häufig verwendet werden.
Validierung und Verifikation sind zwei Begriffe, die häufig in der Softwareentwicklung verwendet werden.
Oft kommt es auch zu Verwechslungen dieser beiden Begriffe.
Das ISTQB bzw German Testing Board definiert diese beiden Begriffe [hier](https://www.german-testing-board.info/wp-content/uploads/2018/09/ISTQB®_GTB_Standardglossar_der_Testbegriffe_Deutsch_Englisch_V3_2.pdf) wie folgt:

* Validierung - "Bestätigung durch Bereitstellung eines objektiven Nachweises, dass die Anforderungen für einen spezifischen beabsichtigten Gebrauch oder eine spezifische beabsichtigte Anwendung erfüllt worden sind. [ISO 9000]"
* Verifikation - "Bestätigung durch Bereitstellung eines objektiven Nachweises, dass festgelegte Anforderungen erfüllt worden sind. [ISO 9000]"

Vereinfacht gesagt ist Validierung die Prüfung, ob die Software die ist, die man haben wollte und Verifikation ist die Prüfung, ob die Software das Richtige tut. \

<hr>

## Unittests

Dieses Kapitel beschäftigt sich mit Unittests. Es wird erklärt, was Unittests sind und wie sie geschrieben werden.
Zusätzlich wird auch das Thema Mocking behandelt. Das Teilprojekt zu Unittests ist unter `testarten/unittests` zu finden.
**Achtung!
** Zwischendurch wird auch das Teilprojekt zu TDD und Mocking benötigt. Dieses ist unter `tdd und mocking` zu finden.

<hr>

## Integrationstests

<hr>

## Systemtests

<hr>

## Sicherheitstests

<hr>

## UI-Tests

<hr>

## TDD und Mocking

<hr>

## TeDDjbrary

<hr>

## Zusammenfassung

### Checkliste - Vorbereitung

- [ ] Algorithmen Grundlagen
- [ ] Java Grundlagen
- [ ] Maven Grundlagen
- [ ] Spring Grundlagen
- [ ] Debugging Grundlagen

### Checkliste - Tests

- [ ] Was ist ein Test?
- [ ] Was ist ein Testfall?
- [ ] Was ist Validierung?
- [ ] Was ist Verifikation?
- [ ] Was ist ein Unittest?

### Checkliste - Mocking

#### Allgemeines zu Mocking

- [ ] Was ist Mocking?
- [ ] Was ist ein Dummy?
- [ ] Was ist ein Stub?
- [ ] Was ist ein Spy?
- [ ] Was ist ein Mock?
- [ ] Was ist ein Fake?

#### Mockito Grundlagen

- [ ] mock()
- [ ] spy()
- [ ] when()
- [ ] thenReturn()
- [ ] verify()
- [ ] ArgumentMatchers
- [ ] ArgumentCaptor
- [ ] @RunWith(MockitoJUnitRunner.class)
- [ ] @Mock
- [ ] @Spy
- [ ] @InjectMocks

### Checkliste - Unittests

### Checkliste - Integrationstests

### Checkliste - TDD 

