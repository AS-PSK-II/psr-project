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

<details>
    <summary><b>Schemat danych wejściowych do REST API (JSON)</b></summary>

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

</details>

<details>
    <summary><b>Schemat alertów w bazie danych</b></summary>

```sql
create table alert
(
    id         uuid not null primary key,
    created_at timestamp(6) with time zone,
    data       jsonb,
    message    varchar(255)
);
```

</details>

# REST

<details>
    <summary><b>Pobieranie alertów</b></summary>

`GET` `/api/v1/alerts`

### Parametry

> | NAME    | TYPE        | DATA TYPE            | DESCRIPTION                                   |
> |---------|-------------|----------------------|-----------------------------------------------|
> | acknowledged | opcjonalny | boolean                 | informacja czy pobieramy również alerty, z którymi użytkownik się już zapoznał - domyślnie `false` |

### Odpowiedzi

> | HTTP CODE | CONTENT-TYPE       | RESPONSE         |
> |-----------|--------------------|------------------|
> | 200       | `application/json` | array of data    |

### Przykład odpowiedzi

```json
[
    {
        "id": "uuid",
        "message": "Device Connected",
        "data": "stringified JSON object",
        "acknowledged": false,
        "createdAt": "2024-12-13T12:34:42.371Z"
    }
]
```

</details>

<details>
    <summary><b>Tworzenie alertu</b></summary>

`POST` `/api/v1/alerts`

### Parametry

> | NAME    | TYPE        | DATA TYPE            | DESCRIPTION                                   |
> |---------|-------------|----------------------|-----------------------------------------------|
> | none | wymagany | obiekt JSON                 | N/A |

### Odpowiedzi

> | HTTP CODE | CONTENT-TYPE       | RESPONSE         |
> |-----------|--------------------|------------------|
> | 201       | `application/json` | created   |
> | 500       | `application/json` | internal server error |

### Przykład odpowiedzi

```text
None
```

</details>

<detailsa>
    <summary><b>Aktualizacja alertu</b></summary>

`PUT` `/api/v1/alerts/{id}`

### Parametry

> | NAME    | TYPE        | DATA TYPE            | DESCRIPTION                                   |
> |---------|-------------|----------------------|-----------------------------------------------|
> | id | wymagany | UUID                 | id alertu |

### Odpowiedzi

> | HTTP CODE | CONTENT-TYPE       | RESPONSE        |
> |-----------|--------------------|-----------------|
> | 204       | `application/json` | no content   |
> | 404       | `application/json` | alert not found |
> | 500       | `application/json` | internal server error |

### Przykład odpowiedzi

```text
None
```

</details>