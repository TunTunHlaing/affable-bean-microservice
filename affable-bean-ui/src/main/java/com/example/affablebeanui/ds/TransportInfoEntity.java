package com.example.affablebeanui.ds;

import com.example.affablebeanui.dto.ProductDto2;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
public  class TransportInfoEntity {

        @JsonProperty("customer_name")
       private List<String> customerName;
       private String email;
       private List<ProductDto2> products = new ArrayList<>();

       private List<CustomerOrder> customerOrder = new ArrayList<>();

       public TransportInfoEntity(){}

    public TransportInfoEntity(List<String> customerName, String email, List<ProductDto2> products, List<CustomerOrder> customerOrderList) {
        this.customerName = customerName;
        this.email = email;
        this.products = products;
        this.customerOrder = customerOrderList;
    }
}