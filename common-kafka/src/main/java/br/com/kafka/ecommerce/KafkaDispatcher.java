package br.com.kafka.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaDispatcher<T> implements Closeable {
    private final KafkaProducer<String, String> producer;

    KafkaDispatcher() {
        this.producer = new KafkaProducer<>(properties());
    }

    private static Properties properties() {
        // TODO Auto-generated method stub
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        return properties;
    }

    public void send(String topicName, String key, T value) throws ExecutionException, InterruptedException {
        KafkaProducer<String, T> producer = new KafkaProducer<String, T>(properties());
        ProducerRecord<String, T> record = new ProducerRecord<String, T>(topicName, key, value);

        producer.send(record, callback()).get();
    }

    private static Callback callback() {
        return (data, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                return;
            }
            System.out.println("Send sucess " + data.topic() + ":::partition" + data.partition() + "/ offset "
                    + data.offset() + "/ timestamp" + data.timestamp());
        };
    }

    @Override
    public void close() {
        producer.close();
    }
}
