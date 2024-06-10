# M183-Projekt Backend
Folgend wird das Backend unseres Projekts beschrieben, wobei der Fokus auf den sicherheitsrelevanten Aspekten liegt.

### Software-Architektur
Die Architektur unseres Backends folgt dem bewährten dreischichtigen Architekturmuster, das eine klare Trennung der Verantwortlichkeiten und eine organisierte Struktur des Codes ermöglicht.

- In der Präsentationsschicht (Controller) liegt der Fokus auf der Handhabung von HTTP-Anfragen und -Antworten. Diese Schicht nimmt Anfragen von externen Clients wie Webbrowsern oder mobilen Apps entgegen und leitet sie an die entsprechenden Service-Schichten weiter. Die Controller sind auch für die Rückgabe von Antworten an den Client in Form von JSON- oder XML-Daten verantwortlich.
- Die Geschäftslogikschicht (Services) implementiert die Geschäftslogik der Anwendung. Hier werden Anfragen von den Controllern verarbeitet und die erforderlichen Aktionen durchgeführt. Diese Schicht ist unabhängig von der Darstellungsschicht und den Datenzugriffsschichten und kann von verschiedenen Controllern wiederverwendet werden.
- Die Datenzugriffschicht (Repository) ist für den Datenzugriff verantwortlich und ermöglicht den Zugriff auf die Datenbank oder andere externe Datenquellen. Sie implementiert Methoden zum Speichern, Abrufen, Aktualisieren und Löschen von Daten. Durch die Kapselung der Datenbankzugriffslogik erleichtert sie die Verwaltung und Wartung der Datenbankzugriffsdetails.

Diese dreischichtige Architektur ermöglicht eine klare Trennung der Verantwortlichkeiten, verbessert die Wartbarkeit und Skalierbarkeit der Anwendung und erhöht die Flexibilität und Agilität der Entwicklung. Jede Schicht hat spezifische Aufgaben, die es ermöglichen, Änderungen in einer Schicht unabhängig von den anderen vorzunehmen.

## Generelle Informationen zum Projekt

- Nur den Branch "bad-example" benutzen, dieser Branch enthält den aktuellen Stand
- Die "guten Beispiele" (z.B. Benutzen von JWT für die Authentifizierung mit Bearer Token und JPA für Datenbankqueries zur Verhinderung von SQL-Injection) befinden sich ebenfalls auf diesen Branch, sind aber auskommentiert, da für die Demo die "schlechten Beispiele", die teilweise unsicher sind, gezeigt werden.

## Projekt-Setup
sciherheitsrelevante aspekte

1. Projekt klonen
2. Datenbank in Docker erstellen mit dem Befehl
   
   ```bash
   docker run --name m183 -e POSTGRES_PASSWORD=m183 -e POSTGRES_USER=postgres -p 5432:5432 -d postgres

4. Verbindung zur Datenbank in IDE konfigurieren
5. Die Tabellen und Rezeptdaten werden beim erstmaligen Starten der Applikation durch die FlyWay-Skripts erstellt   

## Anleitung
Folgend werden die für die Bewertung relevanten Punkte bezüglich Implementation beschrieben. Die entsprechenden GUI-Anleitungen sind im README.md des Frontend-Projekts (https://git.gibb.ch/gma140704/m183-project-frontend) ersichtlich.

### Authentifikation
Im Backend wurde für die Authentifikation im _AuthenticationController_ Endpunkte definiert, über welche sich Benutzer registrieren und einloggen können. 
- Registration: Über den Endpunkt '/register' kann sich der Benutze registrieren. Es werden die Credentials (Benutzername und Passwort) entgegengenommen und geprüft, ob der Benutzername bereits vorhanden ist. Das Passwort wird dem PasswordEncoder der BCrypt-Library verschlüsselt und der Benutzer wird in der Datenbank gespeichert. Ebenfalls wird für den Benutzer ein Bearer-Token generiert und ihm zugewiesen. Als Antwort auf den Request werden die Benutzerdetails und das generierte Token zurückgeschickt.
- Login: Über den Endpunkt '/login' kann sich der Benutzer einloggen. Es werden die Credentials (Benutzername und Passwort) entgegen genommen und geprüft, ob der Benutzer existiert. Ebenfalls wird das Passwort durch Vergleich mit dem in der Datenbank gespeicherten verschlüsselten Passwort überprüft. Bei einer erfolgreichen Überprüfung wird ein Bearer-Token generiert un dem Benutzer zugewiesen. Als Antwort auf den Request werden die Benutzerdetails und das generierte Token zurückgeschickt.

### Injektion
Durch die Methode _findByKeyword_ im _RecipeService_ wird eine potenzielle SQL-Injektion durch die direkte Konkatenation von Benutzereingaben in die SQL-Abfrage ermöglicht. Die Methode nimmt einen String-Parameter entgegen, der in die SQL-Abfrage eingefügt wird, um nach passenden Rezepten zu suchen. Das Keyword wird direkt in die SQL-Abfrage interpoliert, indem es zwischen % Zeichen für den LIKE Operator eingefügt wird. Ein Angreifer könnte eine bösartige Zeichenfolge als Keyword übergeben, die die SQL-Abfrage manipuliert und unerwünschte Operationen ausführt. Zum Beispiel könnte ein Angreifer das keyword so ändern, dass es eine gültige SQL-Abfrage ergibt, die zusätzliche SQL-Befehle enthält, wie z.B. das Löschen von Daten oder das Ausführen von nicht autorisierten Abfragen. Um SQL-Injektionen zu verhindern, sollten Parameterisierung oder vorbereitete Anweisungen verwendet werden, um Benutzereingaben sicher in SQL-Abfragen einzufügen. Das stellt sicher, dass Benutzereingaben als Daten behandelt werden, und nicht als Teil der SQL-Abfragen interpretiert werden.

Die auskommentierte sichere Variante mit JPA wird die Möglichkeit einer SQL-Injektion durch die Verwendung von bennanten Paramter in der JPQL (Java Persistence Query Language) Abfrage eliminiert. JPA ist eine Java-Spezifikation, die eine Schnittstelle zur Verwaltung von relationalen Datenbanken in Java-Applikationen bereitstellt. In der JPQL-Abfrage wird das Parameter 'keyword' als benanntes Parameter mit dem Prefix ':' verwendet. Das bedeuetet, dass der Wert des Parameters separat vom Query-String behandelt wird und nicht direkt in die Abfrage eingefügt wird. Anders als bei der unsicheren Implementierung, wo das Keyword direkt in die SQL-Abfrage eingefügt wird, wird bei der sicheren Variante der LIKE-Operator verwendet, in dem das Keyword-Parameter als Platzhalter innerhalb der Abfrage verwendet wird. Wenn ein Angreifer versucht, durch seine Benutzereingabe das Keyword so zu manipulieren, dass es zusätzliche SQL-Befehle enthält, werden diese nicht als Teil der SQL-Abfrage interpretiert. 

### Insecure Sessions
In der _createToken_ Methode in der _UserAuthProvider_ Klasse wird ein unsicheres Bearer-Token generiert, das dem Benutzer zugewiesen wird und das er für Authentifikation bei Requests benötigt. Das Token besteht aus dem Präfix 'token-' gefolgt vom Benutzernamen. Dies wird dann als Base64-kodierter String zurückgegeben. Ein solches Token macht die Session unsicher. Das Token ist vorhersehbar und kann leicht erraten werden, wenn der Benutzername bekannt ist. Es werden keine kryptografischen Sicherheitsmassnahmen verwendet, um das Token zu schützen, sondern lediglich der Benutzername kodiert, was keine ausreichende Sicherheit bietet. Das Token hat keine Ablaufzeit oder Gültigkeitsdauer, sondern bleibt dauerhaft gültig. Das ist ein Sicherheitsrisiko, da ein Angreifer so das Token jederzeit verwenden kann, um sich als Benutzer auszugeben.

Die auskommentierte sichere Variante verwendet ein JSON Web Token für die Token-Erstellung und Validierung. Die Variante ist sicherer, weil einmit dem HMAC256-Algorithmus ein geheimer Schlüssel verwendet wird, um das Token zu signieren und sicherzustellen, dass es nicht manipuliert wwurde. Das JWT hat eine begrenzte Gültigkeitsdaue, wodurch das Risiko von Missbrauch oder unbefugtem Zugriff vermindert werden kann, da es nach Ablauf der Gültigkeitsdauer nicht mehr verwendet werden kann. Bei der Valdierung des JWT wird auch überprüft, ob das Token gültig ist und nicht manipuliert wurde. Dazu wird der geheime Schlüssel verwendet, um die Signatur des Tokens zu überprüfen. Diese Variante bietet eine robuste und zuverlässige Methode zur Erstellung und Validierung von JWTs, die die Vertrautlichkeit, Integriätt und Sicherheit der Tokens gewährleistet

### Unsafe Consumption of APIs
Es liegt eine unsichere Verwendung von APIs vor, weil die Implementierung Schwachstellen aufweist, die potenzielle Sicherheitsrisiken darstellen. Die Verwendung von schlecht generierten Bearer-Token kann zu Sicherheitsproblemen führen. Diese Tokens sind vorhersehbar und könnten von Angreifern abgefangen und missbraucht werden, um sich als Benutzer auszugeben oder auf geschützte Ressourcen zuzugreifen. Die Implementierung enthält potenzielle SQL-Injection-Schwachstellen, da Benutzereingaben direkt in SQL-Abfragen eingefügt werden. Ein Angreifer könnte bösartige Zeichenfolgen übergeben, die die SQL-Abfrage manipulieren und unerwünschte Operationen wie Datenlöschung oder Datenmanipulation ausführen könnten.
