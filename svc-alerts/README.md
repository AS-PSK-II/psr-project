# Technologie

- JAVA
- REST
- PostgreSQL

# Opis

- Aplikacja odbiera żądania o utworzenie alertu poprzez REST API
- Aplikacja tworzy alert w bazie danych

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

# Schemat danych wejściowych do REST API (JSON)

```json
{
    "id": "uuid",
    "message": "string",
    "data": "string",
    "createdAt": "Date ISO Format UTC"
}
```

Przykład

```json
{
    "id": "d26ccb5b-8d40-44ca-9e34-831a3470681e",
    "message": "test allert",
    "data": "{\"name\": \"test alert data\"",
    "timestamp": "2024-11-18T18:58:00.000Z"
}
```

## Schemat alertów w bazie danych

```sql
create table alert
(
    id         uuid not null primary key,
    created_at timestamp(6) with time zone,
    data       jsonb,
    message    varchar(255)
);
```