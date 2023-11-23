# Testbibliothek TeDDjbrary

Die TeDDjbrary ist eine Webanwendung, die in Java geschrieben ist und das Spring-Framework nutzt.
Ziel ist es eine Anwendung zu haben, die als Grundlage für verschiedene Arten von Tests dienen soll.

Im Normalfall läuft die Anwendung auf https://localhost:8443.

> **Hinweis:** Das Projekt ist noch in der Entwicklung. Einige Tests fehlen. Diese werden über die Zeit ergänzt.
> Bei Wünschen oder Fehlern sollte ein Issue erstellt werden.
## Aufbau

Die TeDDjbrary hat verschiedene Bereiche, die sich in Packages wiederfinden.
Jedes Package hat eine bestimmte Art von Tests zum Ziel. Die Packages sind:

* `auxiliary` - Sicherheitsrelevante Tests
* `core` - Unittests
* `inventory` - Unit/Integrationstests
* `rooms` - UI-Tests
* `structure` - TODO bisher leer
* `user` - Systemtests / API-Tests

<hr>

## Konfiguration für verschiedene Situationen

Die Bibliothek ist gesichert. Jede Kommunikation muss standardmäßig über HTTPS erfolgen und über den Port <span style="color:red"> 8443</span>. Wenn das deaktiviert werden soll, muss die Datei `application.properties` angepasst werden. Dazu muss die Zeile `server.ssl.enabled=true` und die Zeilen darunter auskommentiert werden. Zusätzlich muss die Zeile `server.port=8443` auskommentiert werden.

## Aufgaben der TeDDjbrary

Die TeDDjbrary ist eine öffentliche Bibliothek. Sie bietet eine Vielzahl von Büchern an, die von den Besuchern ausgeliehen werden können.
Es gibt zwei Arten von Besuchern: Studenten und Dozenten.

### Mitarbeiter

Todo

### Funktionalität

Todo

### Räume

Eine Übersicht von Leseräumen, die man betreten bzw. wieder verlassen kann.
Die Verwaltung hat noch mehr Räume. Es gibt Büros für die Mitarbeiter, einen Raum für die Buchhaltung und einen Raum für die IT-Abteilung. Auch gibt es eine Restauration für die Bücher.


<hr>

## Features/Besonderheiten

Die Bibliothek soll nicht nur als Beispiel für Tests dienen, sondern auch als Beispiel für die Verwendung von bestimmten Features von Spring und Java und als "Spielplatz" für Experimente.
So wird z.B. die Webseite mit TLS verschlüsselt. \
Hier werden alle Features aufgelistet, die in der TeDDjbrary verwendet werden.
Außerdem wird auch der "Ort" angegeben, an dem das Feature verwendet wird.

### Allgemeine Features

* CSV-Dateien werden als Datenquelle verwendet ➡️ `inventory/BuchInitializer.java`

### Spring-Features

Folgende Features von Spring werden in der TeDDjbrary verwendet:

* Sichere Verbindung über HTTPS ➡️ application.properties mit `server.ssl.enabled=true`
* Page-Repository-Methoden ➡️ `core/BuchRepository.java` mit `inventory/BestandsController.java`
* Lokalisierung ➡️ 'LocaleConfig.java' und pom.xml (Branch `feature/localization`)
* Scheduled Tasks ➡️ `auxiliary/PageRequest.java`
* Properties nutzen ➡️ `application.properties` mit `TeDDjbraryApplication.java` (@Value)
* Formatter ➡️ in TDD
* Converter ➡️ `user/staff/AngestelltenConverter.java`

### Java-Features

In der TeDDjbrary werden folgende "moderne" Java-Features verwendet:

* Text Blocks ➡️ `Book.java`
* Records ➡️ `ISBN.java`
* Pattern Matching for instanceof
* Switch Expressions 

