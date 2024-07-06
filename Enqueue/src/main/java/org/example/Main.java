package org.example;

import redis.clients.jedis.Jedis;

import java.util.Scanner;


public class Main {
    public static volatile  boolean running = true;
    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = new Jedis();
                Scanner sc = new Scanner(System.in);
                while (running) {
                    System.out.println("Enter Item to enqueue : ");
                    String str = sc.next();
                    jedis.lpush("Listt", str);
                }
            }});
        t.setDaemon(true);
        t.start();
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            running = false;
            try {
                t.join();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }));

        try {
            Thread.sleep(Long.MAX_VALUE);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}