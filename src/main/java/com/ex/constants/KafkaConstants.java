package com.ex.constants;

public interface KafkaConstants {
    //list of broker addresses
    public static String KAFKA_BROKERS="localhost:9092";

    public static Integer MESSAGE_COUNT=1000;

    //id of the producer so the broker can determine the source
    public static String CLIENT_ID="client1";
    public static String TOPIC_NAME="kafka-training";

    //group id to id which group the consumer belongs to
    public static String GROUP_ID_CONFIG="consumerGroup1";

    public static Integer MAX_NO_MESSAGE_FOUND_COUNT=100;

    //if no offset is committed fetch newest record
    public static String OFFSET_RESET_LATEST="latest";

    //if no offset is committed fetch from 0
    public static String OFFSET_RESET_EARLIEST="earliest";

    //the max number of records the consumer will fetch in one iteration
    public static Integer MAX_POLL_RECORDS=1;
}
