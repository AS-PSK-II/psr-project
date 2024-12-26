package pl.arusoftware.psrproject.edge.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.arusoftware.psrproject.edge.config.gson.InstantSerializer;

import java.time.Instant;

public record Telemetry(String deviceId, String property, double value, Instant timestamp) {

    public static Telemetry fromCSV(String line) {
        String[] split = line.split(";");
        return new Telemetry(split[0], split[1], Double.parseDouble(split[2]), Instant.ofEpochMilli(Long.parseLong(split[3])));
    }

    public String toJSON() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantSerializer())
                .create();
        return gson.toJson(this, Telemetry.class);
    }
}
