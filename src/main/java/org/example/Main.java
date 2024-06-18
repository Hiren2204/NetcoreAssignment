package org.example;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        String jdbc = "jdbc:postgresql://localhost:5432/mydatabase";
        String username = "postgres";
        String password = "1234";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbc,username,password);
            Statement statement  = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("Select * from EMP");
            long s = System.nanoTime();
            String sql = "insert into jd(id,name,email) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
            for (int i = 1; i <= 10000000; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2,"Abc");
                preparedStatement.setString(3,"abc@mail.com");
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            resultSet.close();
            statement.close();
            connection.close();
            long e = System.nanoTime();
            System.out.println((e-s)/1000000);
        }
        catch (Exception e){
            e.printStackTrace();
        }




    }
}