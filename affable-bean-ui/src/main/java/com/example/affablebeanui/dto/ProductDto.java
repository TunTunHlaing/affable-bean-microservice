package com.example.affablebeanui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductDto{
    private int id;
    private  String name;
    private double price;
    private String description;
    @JsonProperty("last_update")
    private  LocalDateTime lastUpdate;
    private String categoryName;
    private int quantity;

    public ProductDto(){

    }

    public ProductDto(int id, String name, double price, String description, LocalDateTime lastUpdate, String categoryName, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.lastUpdate = lastUpdate;
        this.categoryName = categoryName;
        this.quantity = quantity;
    }
}
