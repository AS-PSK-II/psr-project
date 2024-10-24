# Technologie

- JAVA

# Opis

- Aplikacja odbiera sygnały od urządzeń/symulatorów i przesyła je na Kafkę
- Aplikacja rozsyła przychodzące dane na 2 oddzielne topici:
    - `device` - informacje o podłączonym urządzeniu
    - `data` - dane telemetryczne z urządzeń
- Aplikacja odbiera dane z Kafki z konfiguracją urządzeń i przesyła je dalej do docelowego urządzenia
    - `device_config` - topic do przesyłania zaktualizowanej konfiguracji urządzenia
- Dane na kafkę są wysyłane i odbierane w postaci JSON
- Aplikacja przechowuje mapę urządzeń (podłączonych) wraz z ich adresami w pamięci

# Schemat danych

## Schemat danych telemetrycznych wysyłanych na Kafkę (JSON)

```JSON
{
    "device": "uuid",
    "property": "string",
    "value": "float",
    "timestamp": "Date ISO Format UTC"
}
```

Przykład:

```JSON
{
    "device": "d26ccb5b-8d40-44ca-9e34-831a3470681e",
    "property": "temperature",
    "value": 20.45,
    "timestamp": "2024-10-23T19:13:45.987Z"
}
```

## Schemat danych konfiguracji urządzenia (JSON)

```JSON
{
    "device": "uuid",
    "property": "string",
    "value": "integer (ms)"
}
```

Przykład:

```JSON
{
    "device": "d26ccb5b-8d40-44ca-9e34-831a3470681e",
    "property": "temperature",
    "value": 2000
}
```

