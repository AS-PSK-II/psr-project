package pl.kielce.tu.svcservice.controllers;

import com.google.gson.Gson;

import java.util.UUID;

public record DeviceSensorConfigurationDTO(UUID device, String property, int value) {
    private final static Gson GSON = new Gson();

    public String toJson() {
        return GSON.toJson(this);
    }
}
