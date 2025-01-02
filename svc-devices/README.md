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

<details>
    <summary><b>Schemat danych informujących o danym stanie maszyny (JSON)</b></summary>

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

</details>

<details>
    <summary><b>Schemat danych konfiguracyjnych urządzenie (JSON)</b></summary>

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

</details>

<details>
    <summary><b>Schemat danych konfiguracyjnych w bazie danych</b></summary>

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

</details>

<details>
    <summary><b>Schemat danych konfiguracyjnych sensora w bazie danych</b></summary>

```sql
create table device_sensor_config
(
    id          uuid    not null primary key,
    device      uuid    not null,
    property    varchar(255),
    value       integer
);
```

</details>

# REST

<details>
    <summary><b>Pobieranie danych konfiguracyjnych dla wszystkich urządzeń</b></summary>

`GET` `/api/v1/devices`

### Parametry

> brak

### Odpowiedzi

> | HTTP CODE | CONTENT-TYPE       | RESPONSE      |
> |-----------|--------------------|---------------|
> | 200       | `application/json` | array of data |

### Przykład odpowiedzi

```json
[
    {
        "id": "uuid",
        "name": "simulator",
        "isActive": true,
        "isConnected": true,
        "timestamp": "2024-12-31T19:22:11.432Z"
    }
]
```

</details>

<details>
    <summary><b>Pobieranie konfiguracji sensorów dla urządzenia</b></summary>

`GET` `/api/v1/devices/{id}/config`

### Parametry

> | NAME    | TYPE        | DATA TYPE            | DESCRIPTION                                   |
> |---------|-------------|----------------------|-----------------------------------------------|
> | id | wymagany | UUID                 | id urządzeznia |

### Odpowiedzi

> | HTTP CODE | CONTENT-TYPE       | RESPONSE         |
> |-----------|--------------------|------------------|
> | 200       | `application/json` | array of data    |
> | 404       | `application/json` | device not found |

### Przykład odpowiedzi

```json
[
    {
        "id": "uuid",
        "device": "device-uuid",
        "property": "temperature",
        "value": 1000
    }
]
```

</details>

<details>
    <summary><b>Zapis nowej konfiguracji sensora dla urządzenia</b></summary>

`POST` `/api/v1/devices/{id}/config`

### Parametry

> | NAME    | TYPE        | DATA TYPE            | DESCRIPTION                                   |
> |---------|-------------|----------------------|-----------------------------------------------|
> | id | wymagany | UUID                 | id urządzeznia |
> | none | wymagany | obiekt JSON | N/A |

### Odpowiedzi

> | HTTP CODE | CONTENT-TYPE       | RESPONSE         |
> |-----------|--------------------|------------------|
> | 201       | `application/json` | created          |
> | 400       | `application/json` | bad request      |
> | 404       | `application/json` | device not found |
> | 500       | `application/json` | internal server error |

### Przykład odpowiedzi

```text
None
```

</details>
