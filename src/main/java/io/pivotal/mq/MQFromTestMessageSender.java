package io.pivotal.mq;

import com.ibm.mq.*;
import com.ibm.mq.constants.MQConstants;
import io.pivotal.mq.samples.MQSampleMessageManager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Hashtable;

public class MQFromTestMessageSender implements MQSender {
    @Override
    public void send(String host, int port, String queueManagerName, String channel, String queueName, String user, String pwd) throws Exception {

        Hashtable properties = new Hashtable();
        properties.put("hostname", host);
        properties.put("port", port);
        properties.put("channel", channel);
//        properties.put("CCSID", new Integer(iCCSID)); not sure what it is
        properties.put(MQConstants.APPNAME_PROPERTY, "MQIVP (Java)");

        // Connect to MQ!!
        try {
            queueManager = new MQQueueManager(queueManagerName, properties);
        } catch (NoClassDefFoundError e) {
            writeln(messageMgr.getMessage(62));
            writeln(e.toString());
            return;
        } catch (MQException ex) {
            writeln(messageMgr.getMessage(9));
            switch (ex.reasonCode) {
                case 2009:
                    writeln(messageMgr.getMessage(10));
                    break;
                case 2059:
                    writeln(messageMgr.getMessage(11));
                    break;
                case 2058:
                    writeln(messageMgr.getMessage(50));
                    break;
                case 2063:
                    writeln(messageMgr.getMessage(13));
                    break;
                default:
                    writeln(messageMgr.getMessage(14, new Object[]{new Integer(ex.reasonCode)}));
            }
            return;
        }

        writeln(messageMgr.getMessage(15));

        // Open a queue...
        MQQueue system_default_local_queue;
        try {
            system_default_local_queue = queueManager.accessQueue("SYSTEM.DEFAULT.LOCAL.QUEUE",
                    MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_OUTPUT, // input, output, inq, set
                    null, null, null);
        } catch (MQException ex) {
            writeln(messageMgr.getMessage(16));
            switch (ex.reasonCode) {
                case 2009:
                    writeln(messageMgr.getMessage(10));
                    break;
                case 2085:
                    writeln(messageMgr.getMessage(17));
                    break;
                default:
                    writeln(messageMgr.getMessage(14, new Object[]{new Integer(ex.reasonCode)}));
            }
            try {
                queueManager.disconnect();
            } catch (MQException e) {
                // We'll silently ignore it
            }
            return;
        }

        writeln(messageMgr.getMessage(18));

        // Put a message!!!
        MQMessage hello_world = new MQMessage();
        hello_world.characterSet = 1208;
        try {
            hello_world.writeUTF("Hello World!");
        } catch (Exception ex) {
            writeln(messageMgr.getMessage(19));
        }
        try {
            system_default_local_queue.put(hello_world, new MQPutMessageOptions());
        } catch (MQException ex) {
            writeln(messageMgr.getMessage(20));
            switch (ex.reasonCode) {
                case 2009:
                    writeln(messageMgr.getMessage(10));
                    break;
                case 2030:
                    writeln(messageMgr.getMessage(21));
                    break;
                case 2053:
                    writeln(messageMgr.getMessage(22));
                    break;
                default:
                    writeln(messageMgr.getMessage(14, new Object[]{new Integer(ex.reasonCode)}));
            }
            try {
                system_default_local_queue.close();
            } catch (MQException e) {
                // We'll silently ignore it
            }
            try {
                queueManager.disconnect();
            } catch (MQException e) {
                // We'll silently ignore it
            }
            return;
        }

        writeln(messageMgr.getMessage(23));

        // Get a message!!!
        MQMessage hello_world_msg;
        try {
            hello_world_msg = new MQMessage();
            hello_world_msg.messageId = hello_world.messageId;
            hello_world_msg.characterSet = 1208;
            system_default_local_queue.get(hello_world_msg, new MQGetMessageOptions(), 30);
        } catch (MQException ex) {
            writeln(messageMgr.getMessage(24));
            switch (ex.reasonCode) {
                case 2009:
                    writeln(messageMgr.getMessage(10));
                    break;
                case 2033:
                    writeln(messageMgr.getMessage(25));
                    break;
                default:
                    writeln(messageMgr.getMessage(14, new Object[]{new Integer(ex.reasonCode)}));
            }
            try {
                system_default_local_queue.close();
            } catch (MQException e) {
                // We'll silently ignore it
            }
            try {
                queueManager.disconnect();
            } catch (MQException e) {
                // We'll silently ignore it
            }
            return;
        }

        String msgReceived;
        try {
            msgReceived = hello_world_msg.readUTF();
        } catch (Exception io_ex) {
            writeln(messageMgr.getMessage(26));
            try {
                system_default_local_queue.close();
            } catch (MQException e) {
                // We'll silently ignore it
            }
            try {
                queueManager.disconnect();
            } catch (MQException e) {
                // We'll silently ignore it
            }
            return;
        }

        if (!msgReceived.equals("Hello World!")) {
            writeln(messageMgr.getMessage(26));
            try {
                system_default_local_queue.close();
            } catch (MQException e) {
                // We'll silently ignore it
            }
            try {
                queueManager.disconnect();
            } catch (MQException e) {
                // We'll silently ignore it
            }
            return;
        }

        writeln(messageMgr.getMessage(27));

        // Close it again...
        try {
            system_default_local_queue.close();
        } catch (MQException ex) {
            writeln(messageMgr.getMessage(28));
            switch (ex.reasonCode) {
                case 2009:
                    writeln(messageMgr.getMessage(10));
                    break;
                default:
                    writeln(messageMgr.getMessage(14, new Object[]{new Integer(ex.reasonCode)}));
            }
            try {
                queueManager.disconnect();
            } catch (MQException e) {
                // We'll silently ignore it
            }
            return;
        }

        writeln(messageMgr.getMessage(29));

        // Disconnect from MQ!!
        try {
            queueManager.disconnect();
        } catch (MQException ex) {
            writeln(messageMgr.getMessage(30));
            switch (ex.reasonCode) {
                case 2009:
                    writeln(messageMgr.getMessage(10));
                    break;
                default:
                    writeln(messageMgr.getMessage(14, new Object[]{new Integer(ex.reasonCode)}));
            }
            return;
        }

        writeln(messageMgr.getMessage(31));

        writeln(messageMgr.getMessage(32));

        // Press Enter to continue ...
        writeln(messageMgr.getMessage(63));
        System.in.read();

        return;
    }

    private void finish() {
        try {
            if (os != null) {
                os.close();
                os = null;
            }
        } catch (Exception e) {
            // cannot really do anything
        }
    }

    private void writeln(String message) throws IOException {
        os.write(message + "\n");
        os.flush();
    }

    private void write(String message) throws IOException {
        os.write(message);
        os.flush();
    }

//    private Hashtable properties = null;
    private OutputStreamWriter os = null;
    private MQSampleMessageManager messageMgr;
    private String queueMgr;
    private MQQueueManager queueManager;
}
