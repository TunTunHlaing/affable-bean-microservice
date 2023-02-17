package com.example.transportationservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/** FIELDS SHOULD INCLUDE ::
 * name
 * qty
 * price
 * total amount
 * userEmail
 * userName
 * userId
 * localDateTime
 * orderId
* **/

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double pirce;
    private int quantity;
    @ManyToOne
    private CustomerOrderProduct customerOrderProduct;

    public Product(){

    }

    public Product(String name, double pirce, int quantity) {
        this.name = name;
        this.pirce = pirce;
        this.quantity = quantity;
    }
}
