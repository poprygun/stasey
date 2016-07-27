package io.pivotal.mq;

import com.ibm.mq.jms.*;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.logging.Logger;

public class MQFactoryManagerSender implements MQSender {
    @Override
    public void send(String host, int port, String queueManager, String channel, String queueName
            , String user, String pwd) throws Exception {
        MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
        MQQueueConnection connection = null;
        MQQueueSession session = null;
        MQQueueSender sender = null;

        try {
            cf.setHostName(host);
            cf.setPort(port);
            cf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setChannel(channel);
            cf.setQueueManager(queueManager);

            if (null != user) {
                String password = pwd != null ? pwd : null;
                connection = (MQQueueConnection) cf.createQueueConnection(user, password);
            } else {
                connection = (MQQueueConnection) cf.createQueueConnection();
            }

            connection.start();

            log.info("****Created connection");
            session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            log.info("****Created session");
            MQQueue queue = (MQQueue) session.createQueue("queue:///" + queueName);
            log.info("****Created queue");
            sender = (MQQueueSender) session.createSender(queue);
            log.info("****Created sender");
            TextMessage message = (TextMessage) session.createTextMessage("Hello");


            log.info("****Stated connection");
            sender.send(message);
            log.info("****Message sent");

        } finally {

            if (null != sender) sender.close();
            if (null != session) session.close();
            if (null != connection) connection.close();
        }
    }

    private static final Logger log = Logger.getLogger(MQFactoryManagerSender.class.getName());

}
