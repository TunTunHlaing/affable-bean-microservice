package com.example.affablebeanbackend.service;

import com.example.affablebeanbackend.dao.ProductDao;
import com.example.affablebeanbackend.dto.ProductDto;
import com.example.affablebeanbackend.dto.Products;
import com.example.affablebeanbackend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductDao productDao;

    public ProductService (DataSource dataSource){
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }

    public void saveProduct(int id, String name, double price, String description, String lastUpdate,int categoryid){

        jdbcTemplate.update(
    "insert into product(id,description,last_update,name,price,category_id)"+
    "values (?,?,?,?,?,?)",
                id,description,convertToDateTime(lastUpdate),name,price,categoryid
);

    }

    public Products listAllProducts(){

        return new Products
                (StreamSupport
                        .stream(productDao.findAll().spliterator(),false)
                .map(p -> toDto(p)).collect(Collectors.toList()));
    }

    public LocalDateTime convertToDateTime(String date){
        String[] sdate = date.split(" ");
        StringBuilder sb = new StringBuilder();
        sb.append(sdate[1]).append("T").append(sdate[2]);
        return LocalDateTime.parse(sb.toString());
    }

    private ProductDto toDto(Product product){

        return new ProductDto(product.getId(),
                product.getName(),
                product.getPrice(), product.getDescription(),
                product.getLastUpdate(),
                product.getCategory().getName());
    }
}
