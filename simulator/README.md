# Technologie

- NodeJS
- UDP (Klient)
- TCP (Serwer)

# Opis

- Aplikacja wysyła dane o sobie przy podłączeniu do aplikacji brzegowej (EDGE)
- Po podłączeniu zaczyna wysyłać dane w sposób losowy:
  - Wartości właściwości są losowane w określonych wcześniej przedziałach
  - Czas wysyłania właściwości jest stały i może być konfigurowany
- Aplikacja wysyła 3 właściwości:
  - temperatura
  - ciśnienie
  - wilgotność
- Jeśli zadana wartość odświeżania jest równa -1, dana wartość nie jest wysyłana dopóty, dopóki nie nastąpi jej
  zmiana na wartość powyżej zera
- Symulator nasłuchuje na zdarzenia przychodzące (Gniazdo TCP) z aplikacji brzegowej, aby reagować na zmianę
  konfiguracji
- Symulator wysyła wszystkie swoje dane poprzez gniazdo UDP

# Schemat danych

## Schemat opisu urządzenia (JSON)

```JSON
{
  "id": "uuid",
  "name": "string",
  "isActive": "boolean",
  "isConnected": "boolean",
  "timestamp": "Date ISO Format UTC"
}
```

Przykład:

```JSON
{
  "id": "d26ccb5b-8d40-44ca-9e34-831a3470681e",
  "name": "sim_1",
  "isActive": true,
  "isConnected": true,
  "timestamp": "2024-10-21T14:15:39.231Z"
}
```

## Schemat wysyłanych danych (String w formacie CSV)

```CSV
device;property;value;timestamp
```

Przykład:

```CSV
d26ccb5b-8d40-44ca-9e34-831a3470681e;temperature;18.23;2024-10-21T10:23:38.031Z
```

## Schemat przychodzącej konfiguracji (String w formacie CSV)

```CSV
property;value(ms)
```

Przykład:

```CSV
humidity;60000
```