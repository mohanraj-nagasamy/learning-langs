
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import io.iron.ironmq.Client;
import io.iron.ironmq.Cloud;
import io.iron.ironmq.Queue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TestRabbitMq {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        System.out.println("TestRabbitMq.main");
        rabbitMQTest();

//        ironMq();
    }

    private static void ironMq() throws IOException {
        int apiVersion = 3;
        Client client = new Client("56b132b5f254f200060001e1", "1lQeph6nvqs7OEml2RLP",
                new Cloud("http", "mq-aws-eu-west-1-1.iron.io", 8080), apiVersion);

        // Get a queue (if it doesn't exist, it will be created when you first post a message)
        Queue queue = client.queue("my_queue");

// Post a message
        queue.push("hello world!");
    }

    private static void rabbitMQTest() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUri("amqp://5hxD-NmF:2Zs9avuAU4ZiqzH5T7iTiR_5q09gCnEL@spotted-silver-20.bigwig.lshift.net:11042/F2SYeeilCGCn");
        factory.setUri("amqp://jmmfjvdb:GuTbMiTIkAArz20dNIIiD2OCUiM1EL_e@jaguar.rmq.cloudamqp.com/jmmfjvdb");
//        factory.setUri("amqp://ookrqnjf:mKt1A5San2O95nWYGJ4usJ6HwFLhVvSS@moose.rmq.cloudamqp.com/ookrqnjf");
//        amqp://5hxD-NmF:2Zs9avuAU4ZiqzH5T7iTiR_5q09gCnEL@spotted-silver-20.bigwig.lshift.net:11043/F2SYeeilCGCn
//        factory.setHost("jaguar.rmq.cloudamqp.com");
//        factory.setPort(11043);
//        factory.setPassword("2Zs9avuAU4ZiqzH5T7iTiR_5q09gCnEL");
//        factory.setUsername("5hxD-NmF");
//        factory.setVirtualHost("F2SYeeilCGCn");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        String queue = "hello";     //queue name
        boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
        boolean exclusive = false;  //exclusive - if queue only will be used by one connection
        boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes

        channel.queueDeclare(queue, durable, exclusive, autoDelete, null);

        String message = "Hello world123";

        String exchangeName = "";
        String routingKey = "hello";
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

//        QueueingConsumer consumer = new QueueingConsumer(channel);
//        channel.basicConsume(queue, true, consumer);
//        while (true) {
//            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
//            message = new String(delivery.getBody());
//            System.out.println(" [x] Received '" + message + "'");
//        }

        channel.close();
        conn.close();
    }
}
