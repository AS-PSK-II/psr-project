# Technologie

- JAVA
- REST
- PostgreSQL

# Opis

- Aplikacja odbiera żądania od użytkowników w celu pobrania danych historycznych z bazy
- Aplikacja pozwala na pobieranie danych wraz z odpowiednimi filtrami:
    - brak filtrów - zwraca wszystkie dane od początku działania aplikacji
    - urządzenia:
        - lista oddzielona przecinkami - zwraca dane dla wszystkich wymienionych urządzeń
        - `all` - zwraca dane dla wszystkich urządzeń
    - zakres dat:
        - `from` - początkowy zakres dat w formacie ISO UTC
        - `to` - końcowy zakres dat w formacie ISO UTC (jeśli bra, zwraca wszystko do aktualnego punktu w czasie)

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

## Pobranie wszystkich danych

`GET` `/api/v1/historical-data`

### Parametry

> | NAME    | TYPE        | DATA TYPE            | DESCRIPTION                                   |
> |---------|-------------|----------------------|-----------------------------------------------|
> | devices | optcjonalny | UUID                 | lista urządzeń dla których chcemy pobrać dane |
> | from    | opcjonalny  | timestamp ISO Format | data początkowa                               |
> | to      | opcjonalny  | timestamp ISO Format | data końcowa                                  |

### Odpowiedzi

> | HTTP CODE | CONTENT-TYPE       | RESPONSE      |
> |-----------|--------------------|---------------|
> | 200       | `application/json` | array of data |

### Przykład odpowiedzi

```JSON
[
  {
    "device": "uuid",
    "parameter": "temperature",
    "value": 22.43,
    "timestamp": "2024-12-31T19:10:00.000Z"
  },
  {
    "device": "uuid",
    "parameter": "temperature",
    "value": 22.43,
    "timestamp": "2024-12-31T19:09:00.000Z"
  }
]
```
