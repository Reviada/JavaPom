package org.example;

import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

import java.util.Arrays;
import java.util.List;

public class Task1 {
    public static void main(String[] args) {
        List<String> messages = Arrays.asList("Четыре", "Пять", "Шесть");
        try (Connection connection = new ConnectionImpl()) {
            connection.start();
            Session session = connection.createSession(true);
            Destination destination = session.createDestination("myQueue");
            Producer producer = session.createProducer(destination);
            for (String message : messages) {
                producer.send(message);
                //System.out.println(java.time.LocalDateTime.now() + " - Отправляю сообщение: " + message);
                Thread.sleep(2000);
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}