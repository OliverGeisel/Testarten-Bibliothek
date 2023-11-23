# Sicherheitstests

**Hinweis! Sicherheitstests dürfen nur auf Systemen angewendet werden, zu denen es eine Genehmigung gibt.
Diese Beispiele dienen nur zu Test- und Lehrzwecken. Sie dürfen nicht für unethische oder illegale Zwecke verwendet
werden.**

## Verschlüsselung - HTTPS und SSL
Ein Schutzziel ist die Vertraulichkeit. Das bedeutet nur der Nutzer und der Server dürfen die Daten lesen. Kein anderer 
darf die Daten lesen.
Diese kann durch Verschlüsselung erreicht werden.
Im Internet wird dazu HTTPS verwendet. Dieses Protokoll verwendet SSL (Secure Sockets Layer) oder TLS (Transport Layer 
Security) um die Daten zu verschlüsseln.

### HTTPS
HTTPS ist eine verschlüsselte Verbindung zwischen einem Client und einem Server. 
Es basiert auf dem HTTP-Protokoll. 
Es wird aber eine zusätzliche Verschlüsselungsschicht verwendet, um die Daten zu verschlüsseln.
Diese Verschlüsselungsschicht ist SSL oder TLS.
Diese Schicht wird zwischen dem HTTP-Protokoll und dem TCP-Protokoll eingefügt.
Für die Verbindung ist ein Zertifikat erforderlich.
Dieses wird vom Server bereitgestellt und vom Client überprüft.

#### Funktionsweise
Das sind die Schritte, die bei einer HTTPS-Verbindung durchgeführt werden:
1. Der Client sendet eine Anfrage an den Server.
2. Der Server sendet ein Zertifikat an den Client.
3. Der Client prüft das Zertifikat und entscheidet, ob er dem Server vertraut.
4. Wenn der Client dem Server vertraut, wird die Verbindung verschlüsselt.
5. Der Client sendet die Anfrage erneut an den Server. Diesmal verschlüsselt.
6. Der Server entschlüsselt die Anfrage und sendet eine Antwort.
7. Der Client entschlüsselt die Antwort.
8. Der Client und der Server kommunizieren verschlüsselt.
9. Die Verbindung wird geschlossen.


#### Zertifikat erstellen
Das Zertifikat kann mit dem folgenden Befehl erstellt werden:
```bash
keytool -genkeypair -alias library -keyalg RSA -keysize 2048 -keystore library.p12 -validity 1825
```
Dies erstellt ein selbst signiertes Zertifikat mit dem Alias `library`, das 1825 Tage gültig ist.
Das Zertifikat wird in der Datei `library.p12` gespeichert.
Bei der Erstellung wird nach einem Passwort gefragt. Dieses Passwort muss in der Datei `application.properties`.
Das Passwort für das Zertifikat ist `library`.

##### Aufgabe
Aktiviere HTTPS in der TeDDjbrary.
Öffne die Seite im Browser.
Was ist das Problem?
Was steht im Zertifikat? Welche Organisation und welche Person sind die Besitzer des Zertifikates?
Wer ist der Aussteller?

#### Https in Spring
In Spring kann HTTPS über die Datei `application.properties` aktiviert werden.
Dazu muss die Zeile `server.ssl.enabled=true` hinzugefügt werden.
Außerdem muss ein Zertifikat erstellt werden.
Dieses Zertifikat muss in der Datei `application.properties` angegeben werden.
Dazu muss die Eigenschaft `server.ssl.key-store` den Pfad des Zertifikates enthalten.
In der TeDDjbrary ist HTTPS deaktiviert.
Es sind aber alle Voraussetzungen für HTTPS vorhanden.
Zuerst müssen die auskommentierten Zeilen in der Datei `application.properties` aktiviert werden.
Das Zertifikat ist der Einfachheit halber relativ zum Classpath angegeben.
Das Zertifikat ist in der Datei `src/main/resources/keystore/library.p12` zu finden.

##### Test in Spring
In Spring kann getestet werden, ob HTTPS funktioniert.
Das ist ein Beispiel für einen Test:
```java
@Test
void testHttps() throws Exception {
    SSLContext sslContext = SSLContextBuilder.create()
            .loadTrustMaterial(new ClassPathResource("keystore/library.p12").getFile(), "library".toCharArray())
            .build();

    CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLContext(sslContext)
            .build();

    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);

    ResponseEntity<String> response = new RestTemplate(requestFactory).getForEntity("https://localhost:8080", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
}
```


#### Übung

TODO



## Brute-force - Hydra

> Um diese Aufgaben machen zu können, sollte https deaktiviert werden und port 8080 oder 80 verwendet werden.

Hydra ist ein Kommandozeilentool, das Zugangsdaten für verschiedene Dienste testet.
Es kann für Brute-Force-Angriffe verwendet werden, um Benutzernamen und Passwörter zu erraten und zu überprüfen, ob ein Benutzername und ein Passwort gültig sind.

### Installation

In Linux reicht es aus, das Paket zu installieren.

```bash
sudo apt install hydra
```

Für Windows gibt es eine exe. Jedoch geht das WSL (Windows Subsystem for Linux) schneller.
In diesem Fall ist der Befehl dann der gleiche.

### Nutzung

Hydra benötigt mindestens drei Parameter:

1. Das Protokoll
2. Die IP-Adresse/Servername
3. Zugangsdaten
   1. Nutzername
   2. Passwort

so sieht ein Beispiel aus:

```bash
hydra -l Oliver -p 12345 http://localhost
```

Dieser Befehl testet den Nutzer Oliver (-l) mit dem Passwort 12345 (-p) auf dem lokalen Webserver.
Sollen mehrere Passwörter oder Nutzer getestet werden, muss ein Wörterbuch verwendet werden.
Dazu muss der entsprechen Parameter großgeschrieben werden.

```bash 
hydra -l Oliver -P pass.txt http://localhost
```

In diesem Fall werden nun alle Passwörter aus der Datei `pass.txt` getestet mit dem Nutzer *Oliver*.

### HTTP-Formular

Hydra kann auch Formulare ausfüllen und absenden. Dazu muss das Protokoll *http-post-form* (für Post-Requests) verwendet
werden.
Jedoch muss dazu noch etwas mehr angegeben werden.
Das Protokoll benötigt drei zusätzliche Parameter, die mit einem Doppelpunkt (:) getrennt werden.

1. Die URL des Formulars
2. Der Name des Nutzername-Feldes sowie der Name des Passwort-Feldes
3. Die Fehlermeldung, die angezeigt wird, wenn die Anmeldung fehlschlägt.

So sind die `"/login:username=^USER^&password=^PASS^:Da hat etwas nicht geklappt!"`-Optionen:

1. Der Pfad an den das Formular geht (/login).
2. Die Parameter, die an das Formular gesendet werden.
   1. Der Nutzername wird mit ^USER^ ersetzt.
   2. Das Passwort wird mit ^PASS^ ersetzt.
3. Die Fehlermeldung, die angezeigt wird, wenn die Anmeldung fehlschlägt.
   Wenn dieser String nicht in der Antwort auftaucht, ist die Anmeldung erfolgreich.

### Beispiel für die Teddjbary

Das Beispiel unten ist für die Teddjbary. Es gibt Zugang zu den Nutzer *admin*.
Wichtig. Der Port muss auf 80 gesetzt werden, da es Probleme mit Port 8080 gibt.

```bash
hydra -l admin -P pass.txt -w 2 <IP_DES_COMPUTERS> http-post-form "/login:username=^USER^&password=^PASS^:Einloggen"
```

### Übung

> Nicht fertig; wird ergänzt

Es gibt einen weiteren Nutzer, der ein schlechtes Passwort hat. Finde ihn heraus.

#### Tipps

* Nicht jeder Nutzername ist möglich.
* Registriere ein paar Nutzer und schau dir an, was im Konto steht bzw. was nicht geht.
* Das Passwort ist in der pass.txt enthalten.
* Generiere eigene Nutzernamen. Entweder selbst oder mit Hydra direkt.

## SQL-Injection

Bei einer SQL-Injection wird versucht, eine SQL-Abfrage zu manipulieren, um Daten aus der Datenbank zu erhalten, die
nicht für den Nutzer bestimmt sind.
In Spring wird die Datenbank über JPA angesprochen.
Spring nutzt zudem Hibernate, um die Daten aus Datenbank zu Objekten umzuwandeln.
Hibernate ist ein ORM (Object-Relational-Mapping). Das bedeutet, dass die Datenbank als Objekte dargestellt wird. 
Diese Objekte werden dann in der Datenbank gespeichert.
Spring ist somit nicht direkt von einer SQL-Injection betroffen.
Jedoch kann Hibernate so konfiguriert werden, dass es SQL-Abfragen ausführt.
Diese Abfragen können dann manipuliert werden.

## Cross-Site-Scripting (XSS)

Bei einem Cross-Site-Scripting (XSS) wird versucht, JavaScript-Code in eine Webseite einzuschleusen, um Daten von
anderen Nutzern zu erhalten.
Ein Beispiel ist, wenn ein Nutzer einen Kommentar schreibt und dieser Kommentar dann auf der Webseite angezeigt wird.
Wenn der Kommentar nicht richtig gefiltert wird, kann ein Nutzer JavaScript-Code in den Kommentar schreiben, der dann
von anderen Nutzern ausgeführt wird.
Auch wenn der Nutzer nicht auf den Kommentar klickt, wird der Code ausgeführt.
Im Normalfall wird der Code in einem `<script>`-Tag geschrieben. Jedoch kann auch ein Bild oder ein Link verwendet
werden.

Eine Springanwendung mit Thymeleaf filtert die Eingaben im Normalfall nicht.
Jedoch kann durch die Nutzung von `th:text` ein XSS-Angriff verhindert werden.
In diesem Fall werden Sonderzeichen wie `<` und `>` in HTML-Entities umgewandelt.
Ein Beispiel ist `<` wird zu `&lt;` und `>` wird zu `&gt;`.\
Es gibt aber auch `th:utext`, das die Eingabe nicht umwandelt.
Hier können dann auch HTML-Tags verwendet werden. Somit ist auch `<script> alert('Hallo') </script>` möglich.

Die TeDDjbrary hat eine XSS-Lücke. Diese ist im Gästebuch der Bibliothek.

### Übung

Finde die XSS-Lücke und erstelle einen Kommentar, der JavaScript-Code ausführt.
Das Script soll eine Alert-Box mit dem Text "Hallo" anzeigen.
Die Anwendung soll nicht untersucht werden.
Nur durch die Eingabe bzw. durch die Untersuchung des HTML-Codes, den der Client bekommt, soll die Lücke gefunden werden.

#### Tipps

* SCHAU DIR DEN HTML-CODE AN!
* Prüfe, welche Eingabe gefiltert wird.
* Nutze das Script aus der Erklärung.

### Bonus - Stiehl den Session-Cookie

Session-hijacken ist ein Angriff, bei dem der Angreifer die Session eines Nutzers übernimmt.
Dazu muss der Angreifer die Session-ID des Nutzers kennen. In Spring ist diese Session-ID in einem Cookie gespeichert.
Der Cookie hat den Namen `JSESSIONID`.
Spring schützt seine Session-Cookies mit dem HttpOnly-Flag. Dieses Flag verhindert, dass JavaScript auf den Cookie
zugreifen kann.
Wir können aber einen anderen Cookie auslesen. Dieser ist nicht geschützt.
Um das Prinzip zu verstehen, stehlen wir den ungeschützten Cookie und schreiben ihn in das Gästebuch.
Der Zugriff auf den Cookie erfolgt über `document.cookie`.

#### Aufgabe

Schreibe ein Script, das die Cookies des Nutzers ausliest und direkt im Gästebuch anzeigt.

#### Tipps

* Nutze das Script aus der Erklärung.
* Schau dir die Cookies an, die der Nutzer bekommt.

#### Die SessionId wirklich stehlen

Um die SessionId wirklich zu stehlen, muss der Cookie bearbeitet werden.
Dazu muss das Attribut `httpOnly` auf `false` setzen. Wenn der Cookie dann gesetzt wird, kann JavaScript auf den Cookie
zugreifen.
Dafür gibt es die Seite `/insecure` auf der TeDDjbrary. Diese setzt den Cookie mit dem Attribut `httpOnly` auf `false`.
Keine Sorge es gibt auch eine Seite, die den Cookie wieder richtig setzt. `/secure` setzt den Cookie wieder richtig.

Es kann ein Geheimer Nutzer aktiviert werden.
In `resources/application.properties` muss die Zeile `app.geheimeruser` auf `true` gesetzt werden. Alternativ kann
auch `/start` aufgerufen werden.
Damit läuft im Hintergrund ein Scheduled-Task, der ein Selenide ausführt und einen Nutzer simuliert.  
Dieser ruft alle 2 Minuten die Seite `/testimonials` auf. Die SESSIONID ist nicht httpOnly und kann somit ausgelesen
werden.
Wenn das Script richtig ist, steht die SessionId im Gästebuch.
Dadurch kannst du die Session des Nutzers übernehmen. Kopiere die SessionId und füge sie in deinen Cookie ein.
Du müsstest jetzt als der entsprechende Nutzer angemeldet sein.
Damit hast du die Session eines Nutzers übernommen.

#### Lösung

Die Lösung ist in der Datei `xss.js` zu finden.

## Cross-Site-Request-Forgery (CSRF)

Bei einem Cross-Site-Request-Forgery (CSRF) wird versucht, eine Aktion auf einer Webseite auszuführen, ohne dass der
Nutzer es merkt.
Dazu wird ein Link oder ein Bild verwendet, das auf eine Seite verweist, die eine Aktion ausführt.
Ein Beispiel ist, wenn ein Nutzer auf einer Webseite eingeloggt ist und ein Bild von einer anderen Webseite lädt.
Wenn der Nutzer auf der anderen Webseite eingeloggt ist, kann die andere Webseite eine Aktion auf der ersten Webseite
ausführen.


CSRF ist in Spring standardmäßig aktiviert. Es gibt aber Möglichkeiten, um CSRF zu deaktivieren.
In Spring wird CSRF über ein Token geschützt. Dieses Token wird in einem Cookie gespeichert und muss bei jedem Request 
mitgeschickt werden. Wenn das Token nicht mitgeschickt wird, wird der Request abgelehnt. Für Tests kann das Token 
deaktiviert werden.

In der TeDDjbrary ist CSRF deaktiviert. Somit kann ein CSRF-Angriff durchgeführt werden.
