package pl.kielce.tu.apigateway.historicaldata;

import java.time.Instant;
import java.util.UUID;

public record TelemetryData(UUID id, UUID deviceId, String property, double value, Instant timestamp) {
}
