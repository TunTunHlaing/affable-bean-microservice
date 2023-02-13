package com.example.affablebeanui.ds;

import com.example.affablebeanui.dto.ProductDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Getter
public class CartBean {

    private Set<ProductDto> cart = new HashSet<>();

    public void addToCart(ProductDto productDto){
        cart.add(productDto);
    }

    public void removeFromCart(ProductDto productDto){
        cart.remove(productDto);
    }

    public void clearCart(){
        cart.clear();
    }
}
