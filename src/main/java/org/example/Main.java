package  org.example;
import redis.clients.jedis.Jedis;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    private static volatile boolean running = true;

    public static void main(String[] args) {
        Thread daemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (Jedis jedis = new Jedis(); // Connect to Redis
                     FileWriter writer = new FileWriter("output.txt", true)) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    while (running) {
                        List<String> data = jedis.lrange("city",0,1);
                        if (data != null) {
                            String timestamp = dtf.format(LocalDateTime.now());
                            writer.write(timestamp + " - " + data + "\n");
                            writer.flush();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        daemonThread.setDaemon(true);
        daemonThread.start();


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            running = false;
            try {
                daemonThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Daemon process terminated.");
        }));

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
