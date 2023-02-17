package com.example.affablebeanui.ds;

import lombok.Data;

@Data
public class CustomerOrder {

    private double totalAmount;

    public CustomerOrder() {
    }

    public CustomerOrder(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
