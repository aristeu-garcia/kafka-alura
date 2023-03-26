package br.com.kafka.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        String TOPIC_NAME = "ECOMMERCE_NEW_ORDER";
        try (var orderDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<String>()) {
                for (int i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);

                    Order order = new Order(userId, orderId, amount);

                    orderDispatcher.send(TOPIC_NAME, userId, order);

                    var email = "Thank you for your order! We are processing your order!";
                    emailDispatcher.send(TOPIC_NAME, userId, email);
                }
            }
        }
    }
}
