package com.example.affablebeanui.service;

import com.example.affablebeanui.ds.CartBean;
import com.example.affablebeanui.ds.CustomerOrder;
import com.example.affablebeanui.ds.TransportInfoEntity;
import com.example.affablebeanui.dto.ProductDto;
import com.example.affablebeanui.dto.Products;
import com.example.affablebeanui.exception.AuthenticationException;
import com.example.affablebeanui.exception.ProductNotFoundException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductClientService {

    @Autowired
    private CartBean cartBean;
    private RestTemplate template = new RestTemplate();
    private List<ProductDto> productDtos;

    @PostConstruct
    public void init() {
        ResponseEntity<Products> response = template.getForEntity("http://localhost:8080/backend/products", Products.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            this.productDtos = response.getBody().getProductDtos();
            System.out.println(productDtos);
        } else {
            throw new RuntimeException("Error!!!");
        }
    }

    public List<ProductDto> findAllProductDtos() {
        return this.productDtos;
    }

    public List<ProductDto> findProductsByCategoryname(String categoryName) {

        return productDtos.stream().filter(p -> p.getCategoryName().equals(categoryName))
                .collect(Collectors.toList());
    }

    public ProductDto findProductById(int id) {

        return productDtos.stream().filter(p -> p.getId() == id).findAny()
                .orElseThrow(() -> new ProductNotFoundException(HttpStatus.NOT_FOUND, id + "Not Found"));
    }

    record TransportInfo(String email,String password) {
    }


    public TransportInfoEntity findTransportInfo(String email,String password) {


        try {
            var transportInfo = new TransportInfo(email, password);

            ResponseEntity<TransportInfoEntity> response =
                    template.postForEntity("http://localhost:8080/transport/find-transport-info",
                            transportInfo, TransportInfoEntity.class);

            System.out.println(response.getBody());

            TransportInfoEntity entity = new TransportInfoEntity();

            if (response.getStatusCode().is2xxSuccessful()) {

                entity = response.getBody();

                List<CustomerOrder> customerOrders = response.getBody().getCustomerOrder();

                System.out.println(response.getBody().getCustomerOrder());

                System.out.println(entity.getProducts());


            } else if (response.getStatusCode().is4xxClientError()) {
                throw new AuthenticationException("Login Error!!!");
            }
            return entity;
        }
        catch (Exception e){
            throw new AuthenticationException("Login Error!!!!");
        }

    }

    record TransferRequest(@JsonProperty("from_name") String fromName,
                           @JsonProperty("from_email") String fromEmail,
                           @JsonProperty("to_name") String toName,
                           @JsonProperty("to_email") String toEmail,
                           double amount) {
    }

    record TransportInfoRequest(
            @JsonProperty("customer_name")
            String customerName,
            String email,
            Set<ProductDto> products,
            @JsonProperty("total_amount")
            double totalAmount
    ) {

    }

    public void checkout(String name, String email, double total) {

        var request = new TransferRequest(name, email,
                "william", "william@gmail.com", total);

        try {
            ResponseEntity<String> response =
                    template.postForEntity("http://localhost:8080/payment/transfer",
                            request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {

                var transportInfo = new TransportInfoRequest(
                        name,
                        email,
                        cartBean.getCart(),
                        total
                );
                template.postForEntity("http://localhost:8080/transport/save-transport-info",
                        transportInfo,
                        String.class);

                cartBean.clearCart();
            }
        } catch (HttpClientErrorException e) {
            String msg = String.format("%s and %s is not found in payment account!",
                    email, name);
            throw new IllegalArgumentException(msg);
        }
    }
}
