# Technologie

- JAVA
- Kafka
- PostgreSQL

# Opis

- Aplikacja odbiera dane z Kafki i zapisuje je w bazie danych
    - `data` - topic Kafki, z którego są odczytywane dane
- Dane z Kafki są odczytywane w postaci JSON
- Aplikacja wykorzystuje do pracy Spring Framework
    - Spring JPA - do zapisu danych oraz do tworzenia tabel w bazie danych
    - Spring Kafka - do odczytu danych z Kafki

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

## Schemat danych telemetrycznych pobieranych z Kafki (JSON)

```json
{
    "device": "uuid",
    "property": "string",
    "value": "float",
    "timestamp": "Date ISO Format UTC"
}
```

Przykład:

```json
{
    "device": "d26ccb5b-8d40-44ca-9e34-831a3470681e",
    "property": "temperature",
    "value": 20.45,
    "timestamp": "2024-10-23T19:13:45.987Z"
}
```

## Schemat danych telemetrychnych w bazie danych

```sql
create table telemetry_data
(
    id             uuid not null primary key,
    device_id      uuid,
    property       varchar(255),
    created_at     timestamp(6) with time zone,
    property_value double precision
);
```