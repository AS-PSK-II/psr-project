package pl.kielce.tu.apigateway.devices;

import java.time.Instant;
import java.util.UUID;

public record DeviceConfig(UUID id, String name, boolean isActive, boolean isConnected, Instant timestamp) {
}
