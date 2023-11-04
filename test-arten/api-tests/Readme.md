# API-Tests

## Was sind API-Tests?

Sicher hat jeder schon mal den Begriff API gehört und weiß, dass es für *Application Programming Interface* steht.
Es sind also Schnittstellen, die es ermöglichen, dass verschiedene Anwendungen miteinander kommunizieren können bzw.
für eigene Anwendungen nutzbar sind.
API-Tests sind also Tests, die die Bindeglieder zwischen Anwendungen testen.

Nehmen wir als Beispiel die TeDDjbrary. Es handelt sich um eine Web-Anwendung, die über HTTP-Requests angesprochen werden
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
Das sind zum Beispiel [RestAssured](http://rest-assured.io/) oder
[Spring RestTemplate](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html).
Diese erlauben es zusammen mit JUnit HTTP-Requests zu schicken und die HTTP-Responses zu testen.

Es gibt aber auch andere Möglichkeiten die API zu testen. Es gibt Anwendungen, die es ermöglichen, HTTP-Requests zu senden und die HTTP-Responses zu testen. Eine solche Anwendung ist [Postman](https://www.postman.com/). Eine schnelle Variante sind HTTP-Requests mit IntelliJ.
Dafür gibt es ein Plugin, das sich [HTTP Client](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html) nennt.
Es ist allerdings nur für IntelliJ Ultimate verfügbar. Dafür gibt es aber auch eine kostenlose [CLI Variante](https://www.jetbrains.com/help/idea/http-client-cli.html), welche jeder nutzen kann.

In diesem Projekt wird das mit dem HTTP Client von IntelliJ gemacht.

### HTTP Client

Der HTTP Client kann HTTP-Requests senden an eine Webadresse senden und die HTTP-Responses entgegennehmen. Zusätzlich können auch Tests zu den einzelnen Requests geschrieben werden.

#### Grundlagen

Damit der HTTP Client Anfragen stellen kann, muss die Anfrage in einer Datei gespeichert werden. Diese Datei muss die Endung `.http` haben.

Jede Anfrage besteht aus einem Request und einer Response. Der Request wird an eine URL gesendet und die Response wird von der URL zurückgesendet. Dafür gibt es eine eigene Syntax, die sich an die Syntax von HTTP-Requests anlehnt.
Die Syntax einer Anfrage ist wie folgt aufgebaut:

```
<HTTP-Methode> <URL>
<Header>

<Body>
###
```

Die `<HTTP-Methode>` ist die Methode, die der HTTP-Client verwenden soll. Das kann unter anderem `GET`, `POST`, `PUT` oder `DELETE` sein. \
Die `<URL>` ist die URL, die der HTTP-Client aufrufen soll. Das kann zum Beispiel `http://localhost:8080/` sein. \
Im `<Header>` können zusätzliche Informationen angegeben werden. Das können zum Beispiel Informationen über den Content-Type, dem Browser oder Cookies sein. \
Im `<Body> können Daten` angegeben werden, die mit der Anfrage gesendet werden sollen. Das können zum Beispiel JSON-Objekte oder Form-Parameter sein.
`###` Ist ein Trennzeichen. Eigentlich ist es der Anfang einer neuen Anfrage. Nach den 3 `#` kann ein Text geschrieben werden. Dieser ist der Name der Anfrage.

In der Datei `basic.http` ist ein Beispiel für eine Anfrage zu finden. Diese Anfrage ist ein `GET`-Request an die URL `http://localhost:8080/`. Die Anfrage hat keine Header und keinen Body.

#### Umgebungen

#### Tests schreiben

#### Beispiel: TeDDjbrary-Login

#### Weitere Informationen

Eine Einführung für den HTTP Client in Form eines Videos gibt es [hier](https://www.youtube.com/watch?v=VMUaOZ6kvJ0).
Das CLI-Tool ist [hier](https://www.youtube.com/watch?v=mwiHAukbWjM) zu finden.

## Extra Notizen/Texte

In der Regel ist das eine REST-Schnittstelle, die HTTP-Requests entgegennimmt und HTTP-Responses zurückgibt.
Die Requests und Responses sind in der Regel JSON-Objekte (REST charakterisiert sich noch durch weitere Eigenschaften,
diese werden aber oft vergessen und es handelt sich gar nicht um eine REST-Schnittstelle).