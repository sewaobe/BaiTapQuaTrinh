package com.example.demo.repositorys;

import com.example.demo.entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryIdOrderByPriceAsc(Long categoryId);


    List<Product> findAllByOrderByPriceAsc();

}
