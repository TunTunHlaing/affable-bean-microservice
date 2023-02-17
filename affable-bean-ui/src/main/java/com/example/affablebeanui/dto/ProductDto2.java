package com.example.affablebeanui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProductDto2{
    private String orderId;
    private  String name;
    private double price;
    private String description;
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;
    private String categoryName;
    private int quantity;

    public ProductDto2(){

    }

    public ProductDto2(String id, String name, double price, String description, LocalDateTime lastUpdate, String categoryName, int quantity) {
        this.orderId = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.lastUpdate = lastUpdate;
        this.categoryName = categoryName;
        this.quantity = quantity;
    }
}