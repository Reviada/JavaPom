package org.example;

import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Task2 {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Пожалуйста, укажите путь к файлу с сообщениями в качестве аргумента.");
            return;
        }
        String filePath = args[0];
        List<String> messages;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            messages = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        try (Connection connection = new ConnectionImpl()) {
            connection.start();
            Session session = connection.createSession(true);
            Destination destination = session.createDestination("myQueue");
            Producer producer = session.createProducer(destination);
            System.out.println("Создан продюсер для очереди myQueue!");

            while (true) {
                for (String message : messages) {
                    producer.send(message);
                    System.out.println(java.time.LocalDateTime.now() + " - Отправляю сообщение: " + message);
                    Thread.sleep(2000);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Молодец, что закрыл соединение! Не забывай закрывать ресурсы (Closeable и AutoCloseable объекты)!");
    }
}
