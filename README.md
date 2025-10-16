# ProgettoPiattaformeWeb

Progetto di un'app web per l'esame di Piattaforme software per applicazioni sul web.

## Configurazione del database

Le proprietà principali sono definite in `src/main/resources/application.yml`. L'applicazione attiva di default il profilo `dev-h2`, che utilizza un database H2 in memoria compatibile con PostgreSQL, così da poter avviare il progetto senza dipendenze esterne.

```bash
./mvnw spring-boot:run
```

L'H2 console è disponibile all'indirizzo <http://localhost:8080/h2-console>.

### Profili Spring disponibili

| Profilo                | Descrizione                                                                 | Come attivarlo                                                |
|------------------------|-----------------------------------------------------------------------------|----------------------------------------------------------------|
| *(default)* `dev-h2`   | Usa un database H2 in-memory compatibile con PostgreSQL per lo sviluppo.    | Nessuna azione necessaria: è attivo in automatico.             |
| `postgres`             | Usa PostgreSQL reale con le credenziali/URL configurati.                    | `SPRING_PROFILES_ACTIVE=postgres` oppure `--spring.profiles.active=postgres` |

### Utilizzare PostgreSQL

Per evitare l'errore `FATALE: autenticazione con password fallita per l'utente "postgres"` assicurati di:

1. Avviare un database PostgreSQL raggiungibile all'indirizzo `jdbc:postgresql://localhost:5432/progettopiattaforme` (ad esempio con `docker compose up -d postgres`).
2. Attivare il profilo `postgres` (es. `SPRING_PROFILES_ACTIVE=postgres`).
3. Impostare le variabili `DB_USERNAME`/`DB_PASSWORD` se il database usa credenziali diverse da `postgres/postgres`.
4. Avviare l'applicazione Spring Boot.

Esempio di avvio:

```bash
SPRING_PROFILES_ACTIVE=postgres DB_PASSWORD=mia-password ./mvnw spring-boot:run
```

Le stesse variabili possono essere configurate anche in IntelliJ IDEA (Run/Debug configuration) o come variabili d'ambiente di sistema.
