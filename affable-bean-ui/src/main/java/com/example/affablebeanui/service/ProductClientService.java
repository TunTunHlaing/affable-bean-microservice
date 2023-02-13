package com.example.affablebeanui.service;

import com.example.affablebeanui.dto.ProductDto;
import com.example.affablebeanui.dto.Products;
import com.example.affablebeanui.exception.ProductNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductClientService {

    private RestTemplate template = new RestTemplate();
    private List<ProductDto> productDtos;

    @PostConstruct
    public void init(){
       ResponseEntity<Products> response = template.getForEntity("http://localhost:8080/backend/products", Products.class);

       if (response.getStatusCode().is2xxSuccessful()){
           this.productDtos = response.getBody().getProductDtos();
           System.out.println(productDtos);
       }
       else {
           throw new RuntimeException("Error!!!");
       }
    }

    public List<ProductDto> findAllProductDtos(){
        return this.productDtos;
    }

    public List<ProductDto> findProductsByCategoryname(String categoryName){

        return productDtos.stream().filter(p -> p.getCategoryName().equals(categoryName))
                .collect(Collectors.toList());
    }

    public ProductDto findProductById(int id) {

        return productDtos.stream().filter(p -> p.getId() == id).findAny()
                .orElseThrow(() -> new ProductNotFoundException(HttpStatus.NOT_FOUND,id + "Not Found"));
    }
}
