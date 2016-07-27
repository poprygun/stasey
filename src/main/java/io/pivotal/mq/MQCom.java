package io.pivotal.mq;


import org.apache.commons.cli.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MQCom {
    public MQCom(String[] args) {
        this.args = args;

        options.addOption("h", "help", false, "show help.");
        options.addOption("ho", "host", true, "MQ Host");
        options.addOption("p", "port", true, "Port");
        options.addOption("qm", "queue-manager-name", true, "Queue Manage Namer");
        options.addOption("c", "channel", true, "Channel");
        options.addOption("q", "queue", true, "Queue");
        options.addOption("u", "user", true, "User");
        options.addOption("p", "password", true, "Password");
        options.addOption("i", "sender-implementation", true, "Use MQQueueManager implementation to send message");
    }

    public void parse() {
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h"))
                help();

            if (cmd.hasOption("ho")) {
                host = cmd.getOptionValue("ho");
                log.info("Host: " + host);
            } else {
                log.log(Level.SEVERE, "MIssing host option");
                help();
            }

            if (cmd.hasOption("p")) {
                port = Integer.parseInt(cmd.getOptionValue("p"));
                log.info("Port: " + port);
            } else {
                log.log(Level.SEVERE, "MIssing port option");
                help();
            }

            if (cmd.hasOption("qm")) {
                queueManager = cmd.getOptionValue("qm");
                log.info("Queue Manager: " + queueManager);
            } else {
                log.log(Level.SEVERE, "MIssing queue manager option");
                help();
            }

            if (cmd.hasOption("c")) {
                channel = cmd.getOptionValue("c");
                log.info("Channel: " + channel);
            }

            if (cmd.hasOption("q")) {
                queueName = cmd.getOptionValue("q");
                log.info("Channel: " + queueName);

            } else {
                log.log(Level.SEVERE, "MIssing queue option");
                help();
            }

            if (cmd.hasOption("u")) {
                user = cmd.getOptionValue("u");
                log.info("User: " + user);

                pwd = cmd.hasOption("p")?cmd.getOptionValue("p"):null;
            }

            MQSender mqSender = null;
            if (cmd.hasOption("i")) {
                mqSender = new MQQueueManagerSender();
            } else {
               mqSender = new MQFactoryManagerSender();
            }
            mqSender.send(host, port, queueManager, channel, queueName, user, pwd);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to parse comand line properties", e);
            help();
        }
    }

    private void help() {
        // This prints out some help
        HelpFormatter formater = new HelpFormatter();

        formater.printHelp("Main", options);
        System.exit(0);
    }

    private static final Logger log = Logger.getLogger(MQCom.class.getName());
    private String[] args = null;
    private Options options = new Options();

    private String host;
    private int port;
    private String queueManager;
    private String channel;
    private String queueName;
    private String user;
    private String pwd;
}
