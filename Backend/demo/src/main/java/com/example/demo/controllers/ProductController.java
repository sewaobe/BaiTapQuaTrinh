package com.example.demo.controllers;

import com.example.demo.entitys.Product;
import com.example.demo.repositorys.CategoryRepository;
import com.example.demo.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    // API 2: Hiển thị sản phẩm theo danh mục
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
        return ResponseEntity.ok(productRepository.findByCategoryIdOrderByPriceAsc(categoryId));
    }

    // API 3: Top 10 sản phẩm bán chạy
    @GetMapping("/top-sold")
    public ResponseEntity<List<Product>> getTopSoldProducts() {
        return ResponseEntity.ok(productRepository.findAllByOrderByPriceAsc());
    }

//    // API 4: 10 sản phẩm mới nhất trong 7 ngày
//    @GetMapping("/recent")
//    public ResponseEntity<List<Product>> getRecentProducts() {
//        LocalDateTime date = LocalDateTime.now().minusDays(7);
//        return ResponseEntity.ok(productRepository.findRecentProducts(
//                date,
//                PageRequest.of(0, 10, Sort.by("createdDate").descending())
//        ));
//    }
}
