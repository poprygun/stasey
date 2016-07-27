package io.pivotal.mq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

public class MQDefaultMessageSender implements MQSender{
    @Override
    public void send(String host, int port, String queueManager, String channel, String queueName, String user, String pwd) throws Exception {
        connect(host, port, queueManager, channel, queueName, user, pwd);

        for (int i = 0; i <= 5; i++) {
            sendTextMessage("Hello World!" + i);
            String message = receiveTextMessage();
            System.out.println("Received message: " + message);
        }
        close();
    }

    public void connect(String host, int port, String queueManager, String channel, String queueName, String userName, String password) throws JMSException {

        JmsFactoryFactory factoryFactory = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
        JmsConnectionFactory connectionFactory = factoryFactory.createConnectionFactory();
        // Set the properties
        connectionFactory.setStringProperty(WMQConstants.WMQ_HOST_NAME, host);
        connectionFactory.setIntProperty(WMQConstants.WMQ_PORT, port);
        connectionFactory.setStringProperty(WMQConstants.WMQ_CHANNEL, channel);
        connectionFactory.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
        connectionFactory.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, queueManager);

        // Create JMS objects
        connection = connectionFactory.createConnection(userName, password);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(queueName);

        //create producer and consumers
        producer = session.createProducer(destination);
        consumer = session.createConsumer(destination);

        // start receiving incomming messages in the connection
        connection.start();
    }

    private void close() {

        // stop receiving incomming messages in the connection
        try {
            connection.stop();
        } catch (JMSException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            if (session != null) {
                session.close();
            }
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String receiveTextMessage() throws JMSException {
        String text = null;

        Message message = consumer.receive(RECEIVE_WAIT_PERIOD);
        if (message != null && message instanceof TextMessage) {
            TextMessage tm = (TextMessage) message;
            text = tm.getText();
            // System.out.println("Message: " + text);
        } else {
            // System.out.println("Unknown message: " + message);
        }
        return text;
    }

    private void sendTextMessage(String text) throws JMSException {
        Message message = session.createTextMessage(text);
        producer.send(message);

//		message.getJMSMessageID();
        System.out.println("Sent message <" + text + ">");
    }

    private Connection connection = null;
    private Session session = null;
    private Queue destination = null;

    private MessageProducer producer = null;
    private MessageConsumer consumer = null;

    private static final String DEFAULT_IBM_MQ_CHANNEL_NAME = "SYSTEM.DEF.SVRCONN";
    private static final int RECEIVE_WAIT_PERIOD = 1;
}
