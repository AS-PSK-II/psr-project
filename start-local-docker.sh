#!/bin/bash

cd simulator
docker build -t distributed-iot/simulator:latest .

cd ../edge
docker build -t distributed-iot/edge:latest .

cd ../collector
docker build -t distributed-iot/collector:latest .

cd ../svc-devices
docker build -t distributed-iot/svc-devices:latest .

cd ../svc-alerts
docker build -t distributed-iot/svc-alerts:latest .

cd ../svc-historical-data
docker build -t distributed-iot/svc-historical-data .

cd ../
docker-compose -f local-services-compose.yml up -d

