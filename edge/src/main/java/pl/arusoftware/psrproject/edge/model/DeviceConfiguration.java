package pl.arusoftware.psrproject.edge.model;

import com.google.gson.Gson;

public record DeviceConfiguration(String device, String property, int value) {
    private static final Gson GSON = new Gson();

    public static DeviceConfiguration fromJSON(String json) {
        return GSON.fromJson(json, DeviceConfiguration.class);
    }

    public String toJSON() {
        return GSON.toJson(this);
    }
}
