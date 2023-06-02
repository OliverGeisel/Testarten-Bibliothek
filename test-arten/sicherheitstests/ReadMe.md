# Sicherheitstests

Hinweis! Sicherheitstests dürfen nur auf Systemen angewendet werden, zu denen es eine Genehmigung gibt.
Diese Beispiele dienen nur zu Test- und Lehrzwecken. Sie dürfen nicht für unethische oder illegale Zwecke verwendet
werden.

## Brute-force - Hydra

Hydra ist ein Kommandozeilentool, das Zugangsdaten für verschiedene Dienste testet.
Es kann für Brute-Force-Angriffe verwendet werden, um Benutzernamen und Passwörter zu erraten und zu überprüfen, ob ein
Benutzername und ein Passwort gültig sind.

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
Sollen mehrere Passwörter oder Nutzer getestet werden muss ein Wortbuch verwendet werden.
Dazu muss der entsprechen Parameter großgeschrieben werden.

```bash 
hydra -l Oliver -P pass.txt http://localhost
```

In diesem Fall werden nun alle Passwörter aus der Datei pass.txt getestet mit dem Nutzer *Oliver*.

### HTTP-Formular

Hydra kann auch Formulare ausfüllen und absenden. Dazu muss das Protokoll *http-post-form* (für Post-Requests) verwendet
werden.
Jedoch muss dazu noch etwas mehr angegeben werden.
Das Protokoll benötigt drei zusätzliche Parameter, die mit einem Doppelpunkt (:) getrennt werden.

1. Die URL des Formulars
2. Der Name des Nutzername-Feldes
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
hydra -l admin -P pass.txt -w 2 192.168.178.32 http-post-form "/login:username=^USER^&password=^PASS^:Einloggen"
```

### Übung

Es gibt einen weiteren Nutzer, der ein schlechtes Passwort hat. Finde ihn heraus.

#### Tipps

* Nicht jeder Nutzername ist möglich.
* Registriere ein paar Nutzer und schau dir an, was im Konto steht bzw. was nicht geht.
* Das Passwort ist in der pass.txt enthalten.
* Generiere eigene Nutzernamen. Entweder selbst oder mit Hydra direkt. 

