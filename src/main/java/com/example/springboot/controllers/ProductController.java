package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductsModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @PostMapping ("/products")
        public ResponseEntity<ProductsModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
        var productModel = new ProductsModel();
        BeanUtils.copyProperties(productRecordDto,productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }
    @GetMapping("/products")
    public ResponseEntity<List<ProductsModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id")UUID id){
        Optional<ProductsModel> product0 = productRepository.findById(id);
            if (product0.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
            }
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

}
