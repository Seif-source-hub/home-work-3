package ru.topacademy;

import ru.topacademy.config.KafkaConfig;
import ru.topacademy.domain.Symbol;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;

public class ConsumerApp {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerApp.class);
    private static final Duration TEN_MILLISECONDS_INTERVAL = Duration.ofMillis(10);

    public static void main(String[] args) {
        /***
         * Создание и запуск консюмеров в трех потоках
         */
        for (int i = 0; i < KafkaConfig.NUM_CONSUMERS; i++) {

            new Thread(() -> {
                KafkaConsumer<Long, Symbol> consumer = new KafkaConsumer<>(KafkaConfig.getConsumerConfig());
                consumer.subscribe(Collections.singletonList(KafkaConfig.VOWELS));

                /** Возможно использовать try-with-resources */
                try {
                    while (true) {
                        ConsumerRecords<Long, Symbol> records = consumer.poll(TEN_MILLISECONDS_INTERVAL);
                        for (ConsumerRecord<Long, Symbol> cr : records) {
                            logger.info("Consumer {}. Received message: key={}, value={}, partition={}, offset={}",
                                    Thread.currentThread().getName(), cr.key(), cr.value(), cr.partition(), cr.offset());
                        }
                    }
                } catch (Exception e) {
                    logger.error("Exception occurred in consumer thread", e);
                } finally {
                    consumer.close();
                }
            }).start();

            new Thread(() -> {
                KafkaConsumer<Long, Symbol> consumer = new KafkaConsumer<>(KafkaConfig.getConsumerConfig());
                consumer.subscribe(Collections.singletonList(KafkaConfig.CONSONANTS));

                /** Возможно использовать try-with-resources */
                try {
                    while (true) {
                        ConsumerRecords<Long, Symbol> records = consumer.poll(TEN_MILLISECONDS_INTERVAL);
                        for (ConsumerRecord<Long, Symbol> cr : records) {
                            logger.info("Consumer {}. Received message: key={}, value={}, partition={}, offset={}",
                                    Thread.currentThread().getName(), cr.key(), cr.value(), cr.partition(), cr.offset());
                        }
                    }
                } catch (Exception e) {
                    logger.error("Exception occurred in consumer thread", e);
                } finally {
                    consumer.close();
                }
            }).start();

        }
    }
}
