# Distribiuted IoT Platform

## Opis działania

Urządzenia wysyłają dane poprzez urządzenie brzegowe(EDGE) na kafkę  (jeden topic na wszystkie urządzenia). Urządzenie
brzegowe przekształca dane z urządzeń na format akceptowany przez pozostałe części aplikacji. Dane z kafki są odbierane
przez kolektor(Collector) i wrzucane do bazy danych, którą będzie PostgreSQL.

Urządzenia oprócz danych będą wysyłać informacje o sobie (id, czas podłączenia, czy jest aktywne) przy każdej próbie
połączenia do systemu oraz periodycznie co określony czas, tak, aby system mógł określić, czy dane urządzenie działa,
czy z jakiegoś powodu przestało. Komunikacja ta będzie się odbywać poprzez oddzielny topic kafki, również wspólny dla
wszystkich urządzeń.

System będzie udostępniać proste REST API do podglądu takich danych jak:

- ostatnie wartości wysłane z urządzenia
- prosta konfiguracja urządzenia (zmiana częstotliwości odświeżania)
- wartości historyczne z urządzenia
- podgląd alertów wygenerowanych przez system (urządzenie odłączone lub podłączone)

## Technologie

### UI

UI będzie prostą aplikacją napisaną w języku JavaScript z wykorzystaniem biblioteki ReactJS. Aplikacja UI będzie
komunikować się z backendem za pomocą REST API.

### Serwis

Każdy z serwisów będzie napisany w języku JAVA z wykorzystaniem Spring Framework do utworzenia komunikacji REST oraz (
jeśli będzie taka potrzeba) z Kafką oraz Bazą Danych.

### Edge

### Symulator

### Baza danych

Bazą danych do przechowywania danych telemetrycznych oraz pozostałych danych związanych z działaniem aplikacji (takich
jak alerty, zarejestrowane urządzenia, etc.) będzie PostgreSQL.

### Infrastruktura

Każdy z serwisów będzie osobnym kontenerem Dockerowym. Cała struktura będzie utworzona w stosie docker-compose, będzie
pracować w jednej wewnętrznej sieci, za wyjątkiem serwisów, do których musimy mieć dostęp z zewnątrz jak UI oraz Kafka.

## Schemat aplikacji

![project schema](docs/PSR-project-schema.svg)

## Repozytorium Git

https://github.com/AS-PSK-II/psr-project