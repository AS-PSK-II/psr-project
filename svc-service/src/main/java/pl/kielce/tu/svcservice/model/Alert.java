package pl.kielce.tu.svcservice.model;

import com.google.gson.Gson;

public record Alert(String message, String data) {

    public static String toJSON(Alert alert) {
        return new Gson().toJson(alert);
    }
}
