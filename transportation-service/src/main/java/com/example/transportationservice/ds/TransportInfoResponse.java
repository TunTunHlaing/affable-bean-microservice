package com.example.transportationservice.ds;

import com.example.transportationservice.entity.Product;
import com.example.transportationservice.entity.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public  class TransportInfoResponse{

        @JsonProperty("customer_name")
       private Set<String> customerName;
       private String email;
       private List<ProductDto> products = new ArrayList<>();
      // @JsonProperty("customer_order")
       private List<CustomerOrder> customerOrder = new ArrayList<>();

       public TransportInfoResponse(){}

    public TransportInfoResponse(Set<String> customerName, String email, List<ProductDto> products, List<CustomerOrder> customerOrder) {
        this.customerName = customerName;
        this.email = email;
        this.products = products;
        this.customerOrder = customerOrder;
    }
}