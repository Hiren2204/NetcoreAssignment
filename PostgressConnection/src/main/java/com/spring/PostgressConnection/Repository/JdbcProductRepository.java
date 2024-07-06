package com.spring.PostgressConnection.Repository;

import com.spring.PostgressConnection.Model.Product;

import java.util.List;

public interface JdbcProductRepository {
    int save(Product product);
    int update(Product product);
    int  delete(Product product);
    List<Product> findAll();
    Product findById(int id);
}
