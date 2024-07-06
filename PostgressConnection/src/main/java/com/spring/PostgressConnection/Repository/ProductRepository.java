package com.spring.PostgressConnection.Repository;

import com.spring.PostgressConnection.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository implements JdbcProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Product product) {
        return jdbcTemplate.update("INSERT INTO Product (id, name, qty) VALUES (?, ?, ?)",
                product.getId(), product.getName(), product.getQty());
    }

    @Override
    public int update(Product product) {
        return jdbcTemplate.update("UPDATE Product SET name=?, qty=? WHERE id=?",
                product.getName(), product.getQty(), product.getId());
    }

    @Override
    public int delete(Product product) {
        return jdbcTemplate.update("DELETE FROM Product WHERE id=?", product.getId());
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM Product", BeanPropertyRowMapper.newInstance(Product.class));
    }

    @Override
    public Product findById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Product WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Product.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
