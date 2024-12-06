package pl.arusoftware.psrproject.edge.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import pl.arusoftware.psrproject.edge.config.gson.InstantSerializer;

import java.time.Instant;
import java.util.List;

public record Device(String id, String name, boolean isActive, boolean isConnected, Instant timestamp,
                     @SerializedName("tcpServer") List<DeviceTCPServer> deviceTCPServers) {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Instant.class, new InstantSerializer())
            .create();

    public static Device fromJSON(String json) {
        return gson.fromJson(json, Device.class);
    }

    public String toJSON() {
        return gson.toJson(this, Device.class);
    }
}
