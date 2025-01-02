package pl.kielce.tu.apigateway.devices;

import java.util.UUID;

public record DeviceSensorConfig(UUID device, String property, int value) {
}
