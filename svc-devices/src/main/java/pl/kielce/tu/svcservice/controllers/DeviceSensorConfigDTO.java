package pl.kielce.tu.svcservice.controllers;

import com.google.gson.Gson;
import pl.kielce.tu.svcservice.model.DeviceSensorConfig;

import java.util.UUID;

public record DeviceSensorConfigDTO(UUID device, String property, int value) {
    private final static Gson GSON = new Gson();

    public static DeviceSensorConfigDTO fromDeviceSensorConfiguration(DeviceSensorConfig deviceSensorConfig) {
        return new DeviceSensorConfigDTO(
                deviceSensorConfig.getDevice(),
                deviceSensorConfig.getProperty(),
                deviceSensorConfig.getValue()
        );
    }

    public String toJson() {
        return GSON.toJson(this);
    }

    public DeviceSensorConfig toDeviceSensorConfiguration() {
        return new DeviceSensorConfig(null, device, property, value);
    }
}
