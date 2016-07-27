package io.pivotal.mq;

public interface MQSender {
    void send(String host, int port, String queueManager
            , String channel, String queueName, String user, String pwd
    ) throws Exception;
}
