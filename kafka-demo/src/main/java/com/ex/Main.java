package com.ex;

import com.ex.config.ConsumerCreator;
import com.ex.config.ProducerCreator;
import com.ex.constants.KafkaConstants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        //runProducer();
        runConsumer();
    }

    static void runConsumer() {
        Consumer<Long, String> consumer = ConsumerCreator.createConsumer();

        int noMessagesFound = 0;

        while(true) {
            //poll and wait at most 1000ms if no record is found
            ConsumerRecords<Long, String> records = consumer.poll(Duration.ofSeconds(1));
            if(records.count() == 0) {
                noMessagesFound++;
                if(noMessagesFound > KafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT) {
                    //if we hit the max threshold break
                    break;
                } else {
                    continue;
                }
            }

            //print each record
            records.forEach(record -> {
                System.out.println(String.format("Record key %d", record.key()));
                System.out.println(String.format("Record value %s", record.value()));
                System.out.println(String.format("Record partition %d", record.partition()));
                System.out.println(String.format("Record offset %d", record.offset()));
            });

            //commit the offset
            consumer.commitAsync();
        }
        consumer.close();

    }

    static void runProducer() {
        Producer<Long, String> producer = ProducerCreator.createProducer();

        for(int index = 0; index < KafkaConstants.MESSAGE_COUNT; index++) {
            ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(KafkaConstants.TOPIC_NAME,
                    String.format("This is record %d", index));

            try {
                RecordMetadata metadata = producer.send(record).get();
                System.out.println(String.format("Record sent with key %d to partition %d with offset %d",
                        index, metadata.partition(), metadata.offset()));
            } catch (InterruptedException e) {
                System.out.println("Error sending message");
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println("Error sending message");
                e.printStackTrace();
            }
        }

        producer.close();

    }
}
