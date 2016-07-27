package io.pivotal.mq;


import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

public class MQQueueManagerSender implements MQSender {
    public void send(String host, int port, String queueManager
            , String channel, String queueName, String user, String pwd) throws Exception {
        MQEnvironment.hostname = host;
        MQEnvironment.port = port;
        MQEnvironment.channel = channel;
        MQEnvironment.properties.put(CMQC.TRANSPORT_PROPERTY, CMQC.TRANSPORT_MQSERIES_CLIENT);

        MQQueueManager qMgr;
        //call patch() to skip SSL
        patch();

        qMgr = new MQQueueManager(queueManager);
        log.info(qMgr.toString());

        int openOptions = CMQC.MQOO_BROWSE | CMQC.MQOO_INQUIRE | CMQC.MQOO_OUTPUT | CMQC.MQOO_INPUT_AS_Q_DEF;

        MQQueue destQueue = qMgr.accessQueue(queueName, openOptions);
        log.info("Queue size:" + destQueue.getCurrentDepth());
        MQMessage hello_world = new MQMessage();
        log.info("MQMessage message created");
        hello_world.writeUTF("Sending Sample message");
        MQPutMessageOptions pmo = new MQPutMessageOptions();
        try {
            destQueue.put(hello_world, pmo);
            destQueue.get(hello_world);
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        int len = hello_world.getDataLength();
        log.info("Length : " + len);
        log.info("GET: " + hello_world.readString(len - 1));
        destQueue.close();
        qMgr.disconnect();

    }

    public static void patch() throws KeyManagementException, NoSuchAlgorithmException {

        log.info("Calling SSL patch");

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }

                }
        };

        log.info("trustAllCerts = " + trustAllCerts);

        SSLContext sc = SSLContext.getInstance("SSL");

        log.info("sc before init = " + sc);

        sc.init(
                null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                return true;
            }
        };

        log.info("sc after init= " + sc);
        log.info("allHostsValid= " + allHostsValid);
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    private static final Logger log = Logger.getLogger(MQQueueManagerSender.class.getName());

}
