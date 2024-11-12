package pl.arusoftware.psrproject.edge.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class KafkaProducer {
    private final String kafkaUrl;
    private Producer<String, String> producer;

    public KafkaProducer(String kafkaUrl) {
        this.kafkaUrl = kafkaUrl;
    }

    public void connect() {
        final Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        props.put(KEY_SERIALIZER_CLASS_CONFIG,   StringSerializer.class.getCanonicalName());
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getCanonicalName());
        props.put(ACKS_CONFIG,                   "all");

        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
    }

    public void send(String topic, String key, String message) {
        producer.send(new ProducerRecord<>(topic, key, message));
    }
}
