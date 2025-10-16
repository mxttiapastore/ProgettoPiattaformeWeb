# ProgettoPiattaformeWeb

Progetto di un'app web per l'esame di Piattaforme software per applicazioni sul web.

## Configurazione del database

L'applicazione è configurata per utilizzare PostgreSQL in ambiente di produzione. Per evitare l'errore di connessione `Connection to localhost:5432 refused` è necessario avviare un database PostgreSQL in ascolto sulla porta indicata oppure aggiornare le variabili d'ambiente con i dati corretti.

Puoi avviare rapidamente un'istanza PostgreSQL locale con Docker Compose:

```bash
docker compose up -d postgres
```

Le credenziali di default sono:

- **Database**: `progettopiattaforme`
- **Utente**: `postgres`
- **Password**: `postgres`

In alternativa, per lo sviluppo locale puoi attivare il profilo `dev-h2`, che utilizza un database H2 in memoria e non richiede PostgreSQL avviato:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev-h2
```

Le proprietà di configurazione si trovano in `src/main/resources/application.yml`.
