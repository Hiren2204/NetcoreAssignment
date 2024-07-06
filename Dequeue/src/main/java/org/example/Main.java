package org.example;

import redis.clients.jedis.Jedis;

import java.util.*;


public class Main {
    public static volatile  boolean running = true;
    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = new Jedis();
                Scanner sc= new Scanner(System.in);
                System.out.println("Item in List are : ");
                while (running) {
                    List<String> li = jedis.lrange("Listt",0,-1);
                    System.out.println(li);
                    running = false;

                    System.out.println("To Update List ENter 1 \n");
                    int i = sc.nextInt();
                    if(i == 1){
                        running = true;
                    }

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