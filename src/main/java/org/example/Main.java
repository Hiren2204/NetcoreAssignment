package org.example;
import java.util.*;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class Main {
    public static void main(String[] args) {
        Jedis jedis = new Jedis();

        jedis.set("City","Mumbai");
        String str = jedis.get("City");
        System.out.println(str);


        jedis.lpush("Q","Abc");
        jedis.lpush("Q","Pqr");
        String rpop = jedis.rpop("Q");
        String lpop = jedis.lpop("Q");
        System.out.println("Rpop :"+rpop);
        System.out.println("Lpop :"+lpop);

        jedis.sadd("List","ICIC");
        jedis.sadd("List","ICIC");
        jedis.sadd("List","HDFC");
        Set<String> set = jedis.smembers("List");
        System.out.println(set);

        //Jedis HashMap
        System.out.println("Hash Map JEdis");
        jedis.hset("User1","Name","Abc");
        jedis.hset("User1","Dept","Engg");
        Map<String,String> map = jedis.hgetAll("User1");
        System.out.println(map);




    }
}