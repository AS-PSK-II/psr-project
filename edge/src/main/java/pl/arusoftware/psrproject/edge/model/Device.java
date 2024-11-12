package pl.arusoftware.psrproject.edge.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.arusoftware.psrproject.edge.config.gson.InstantDeserializer;

import java.time.Instant;

public record Device(String id, String name, boolean isActive, boolean isConnected, Instant timestamp) {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantDeserializer())
            .create();

    public static Device fromJSON(String json) {
        return gson.fromJson(json, Device.class);
    }

    public String toJSON() {
        return gson.toJson(this, Device.class);
    }
}
