package com.example.affablebeanui.dto;

import lombok.Data;

import java.util.List;

@Data
public class Products {

    private List<ProductDto> productDtos;

    public Products(){

    }

    public Products(List<ProductDto> productDtos){
        this.productDtos = productDtos;
    }
}
