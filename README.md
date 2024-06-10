# General Info

- Nur den Branch "bad-example" benutzen, dieser Branch enthält den aktuellen Stand
- Die "guten Beispiele" (z.B. Benutzen von JWT für die Authentifizierung mit Bearer Token und JPA für Datenbankqueries zur Verhinderung von SQL-Injection) befinden sich ebenfalls auf diesen Branch, sind aber auskommentiert, da für die Demo die "schlechten Beispiele", die teilweise unsicher sind, gezeigt werden.

# How to use

1. Projekt klonen
2. Datenbank in Docker erstellen mit dem Befehl
   
   ```bash
   docker run --name m183 -e POSTGRES_PASSWORD=m183 -e POSTGRES_USER=postgres -p 5432:5432 -d postgres

4. Verbindung zur Datenbank in IDE konfigurieren
5. Die Tabellen und Rezeptdaten werden beim erstmaligen Starten der Applikation durch die FlyWay-Skripts erstellt   
