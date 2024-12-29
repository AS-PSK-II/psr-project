package pl.arusoftware.psrproject.edge.config;

public class AppConfig {
    public static final String KAFKA_DATA_TOPIC = getEnvOrDefault("APP_KAFKA_DATA_TOPIC", "data");
    public static final String KAFKA_DEVICE_TOPIC = getEnvOrDefault("APP_KAFKA_DEVICE_TOPIC", "device");
    public static final String KAFKA_DEVICE_CONFIG_TOPIC = getEnvOrDefault("APP_KAFKA_DEVICE_CONFIG_TOPIC", "device-config");
    public static final String KAFKA_CONSUMER_GROUP = getEnvOrDefault("APP_KAFKA_CONSUMER_GROUP", "edge-local");
    public static final String KAFKA_HOST = getEnvOrDefault("APP_KAFKA_HOST", "localhost:9092");
    public static final int APP_PORT = Integer.parseInt(getEnvOrDefault("APP_PORT", "12345"));

    private static String getEnvOrDefault(String key, String defaultValue) {
        String envValue = System.getenv(key);

        return envValue == null || envValue.isBlank() ? defaultValue : envValue;
    }
}