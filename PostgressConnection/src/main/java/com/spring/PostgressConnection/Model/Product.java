package com.spring.PostgressConnection.Model;


import org.springframework.boot.autoconfigure.domain.EntityScan;


public class Product {

    private  int id;
    private  String name;
    private  int qty;

    Product(){

    }

    Product(int id,String name,int qty){
         this.id = id;
        this.name = name;
        this.qty= qty;
    }

    public void setId(int id){
        this.id = id;
    }
    public  int getId(){
        return  id;
    }

    public void setName(String name){
        this.name = name;
    }
    public  String getName(){
        return  name;
    }

    public void setQty(int qty){
        this.qty = qty;
    }
    public  int getQty(){
        return  qty;
    }


}
