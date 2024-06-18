package org.example;

import redis.clients.jedis.Jedis;
import com.google.common.base.Strings;
import java.util.Scanner;

public class Main {
    public static Jedis jedis = new Jedis();

    private static void storeUserInfo(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(email)) {
            System.out.println("Username and email cannot be empty!");
            return;
        }

        jedis.hset("user:" + username, "username", username);
        jedis.hset("user:" + username, "email", email);
        System.out.println("User info stored successfully.");
    }

    private static void retrieveUserInfo(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (Strings.isNullOrEmpty(username)) {
            System.out.println("Username cannot be empty!");
            return;
        }

        if (jedis.exists("user:" + username)) {
            String retrievedUsername = jedis.hget("user:" + username, "username");
            String retrievedEmail = jedis.hget("user:" + username, "email");
            System.out.println("Retrieved info:");
            System.out.println("Username: " + retrievedUsername);
            System.out.println("Email: " + retrievedEmail);
        } else {
            System.out.println("No user found with username: " + username);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("\n1.Store info");
            System.out.println("\n2. retrive info");
            System.out.println("\n2. close");
            int n = sc.nextInt();
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    storeUserInfo(sc);
                    break;
                case 2:
                    retrieveUserInfo(sc);
                    break;
                case 3:
                    jedis.close();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}