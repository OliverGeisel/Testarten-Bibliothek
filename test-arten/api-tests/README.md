# API-Tests

## Was sind API-Tests?

Sicher hat jeder schon mal den Begriff API gehört und weiß, das es für *Application Programming Interface* steht.
Es sind also Schnittstellen, die es ermöglichen, dass verschiedene Anwendungen miteinander kommunizieren können bzw.
für eigene Anwendungen nutzbar sind.
API-Tests sind also Tests, die die Bindeglieder zwischen Anwendungen testen.

Nehmen wir als Beispiel die TeDDjbrary. Es handelt sich um eine Web-Anwendung, die über HTTP-Requets angesprochen werden
kann. Bedeutet die API ist eine HTTP-Schnittstelle.
Das bedeutet, dass jedes Programm, das mit der TeDDjbrary kommunizieren möchte, HTTP-Requests ausführen muss.
Glücklicherweise gibt es eine weitverbreitete Art von Programm, die genau das kann: **Webbrowser**.

## Warum API-Tests?

Warum sollte man in unserem Beispiel überhaupt API-Tests schreiben? Es gibt doch schon UI-Tests, die die Webanwendung
testen. Nun die Antwort ist einfach: UI-Tests sind langsam und instabil.
API-Tests sind in der Regel schneller als UI-Tests, da sie nicht auf die Ladezeiten der Webanwendung warten müssen.
Außerdem sind sie stabiler, da sie nicht von der UI abhängig sind. Wenn sich die UI ändert, müssen die API-Tests nicht
angepasst werden. Das ist ein großer Vorteil, da UI-Tests oft angepasst werden müssen, wenn sich die UI ändert.

## Wie kann man API-Tests schreiben?

In diesem Projekt konzentrieren wir uns auf API-Tests für Webanwendungen. Das bedeutet, dass wir HTTP-Requests an die
Webanwendung schicken und die HTTP-Responses testen.
Dafür gibt es verschiedene Frameworks, die das Testen von HTTP-Requests und -Responses ermöglichen.
Das sind zum Beispiel [RestAssured](http://rest-assured.io/), [Postman](https://www.getpostman.com/) oder
[Spring RestTemplate](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html).
Eine schnelle Variante sind HTTP-Requests mit IntelliJ. Dafür gibt es ein Plugin, das sich [REST Client]() nennt.

### Extra Notizen/Texte

In der Regel ist das eine REST-Schnittstelle, die HTTP-Requests entgegennimmt und HTTP-Responses zurückgibt.
Die Requests und Responses sind in der Regel JSON-Objekte (REST charakterisiert sich noch durch weitere Eigenschaften,
diese werden aber oft vergessen und es handelt sich gar nicht um eine REST-Schnittstelle).