package pl.arusoftware.psrproject.edge.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class KafkaConsumer {
    private final String kafkaUrl;
    private Consumer<String, String> consumer;

    public KafkaConsumer(String kafkaUrl) {
        this.kafkaUrl = kafkaUrl;
    }

    public void connect() {
        final Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        props.put(GROUP_ID_CONFIG, "edge-app");
        props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");

        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        consumer.subscribe(List.of("device-config"));
    }

    public void receive(java.util.function.Consumer<String> configReceiver) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
        for (ConsumerRecord<String, String> record : records) {
            configReceiver.accept(record.value());
        }
    }
}
