# Technologie

- JAVA
- REST API
- Kafka
- PostgreSQL

# Opis

- Aplikacja łączy się z Kafką
    -  `device` - topic Kafki, z którego pobierane są informacje o podłączonym lub odłączonym urządzeniu
    -  `device_config` - topic Kafki, na który wysyłana jest zaktualizowana konfiguracja urządzenia
- Przychodzące dane z urządzenia są zapisywane w bazie danych
- Na podstawie przychodzących danych z urządzenia jest tworzony alert dla użytkownika
    - Wysyłane jest żądanie do `svc-alerts` z informacją o zdarzeniu
- Odbieranie żądania rekonfiguracyjnego od użytkownika i wysłanie go do urządzenia poprzez Kafkę

W celu ułatwienia pracy z kodem, wykorzystywana jest biblioteka `Lombok` do generowania setterów, getterów, konstruktorów w wielu miejscach aplikacji oraz biblioteka `GSON` do manipulacji obiektami `JSON`.

# Uruchomienie aplikacji

Aby uruchomić aplikację, należy wejść do głównego folderu mikroserwisu i wykonać poniższą komendę

## Unix

```shell
./gradlew run
```

## Windows

```shell
gradlew.bat run
```

# Schemat danych

# Schemat danych informujących o danym stanie maszyny (JSON)

```json
{
  "id": "uuid",
  "name": "string",
  "isActive": "boolean",
  "isConnected": "boolean",
  "timestamp": "Date ISO Format UTC"
}
```

Przykład:

```json
{
  "id": "d26ccb5b-8d40-44ca-9e34-831a3470681e",
  "name": "sim_1",
  "isActive": true,
  "isConnected": true,
  "timestamp": "2024-10-21T14:15:39.231Z"
}
```

# Schemat danych konfiguracyjnych urządzenie (JSON)

```json
{
    "device": "uuid",
    "property": "string",
    "value": "integer (ms)"
}
```

Przykład:

```json
{
    "device": "d26ccb5b-8d40-44ca-9e34-831a3470681e",
    "property": "temperature",
    "value": 2000
}
```

# Schemat danych konfiguracyjnych w bazie danych

```sql
create table device_config
(
    id           uuid    not null primary key,
    is_active    boolean not null,
    is_connected boolean not null,
    name         varchar(255),
    created_at   timestamp(6) with time zone
);
```