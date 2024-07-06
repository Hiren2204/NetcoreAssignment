package com.spring.PostgressConnection.Controller;

import com.spring.PostgressConnection.Model.Product;
import com.spring.PostgressConnection.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) Integer id) {
        try {
            List<Product> list = new ArrayList<>();
            if (id == null) {
                productRepository.findAll().forEach(list::add);
            } else {
                Product product = productRepository.findById(id);
                if (product != null) {
                    list.add(product);
                }
            }
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
