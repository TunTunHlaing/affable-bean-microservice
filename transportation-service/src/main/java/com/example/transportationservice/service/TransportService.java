package com.example.transportationservice.service;

import com.example.transportationservice.dao.CustomerDao;
import com.example.transportationservice.dao.CustomerOrderProductDao;
import com.example.transportationservice.ds.CustomerOrder;
import com.example.transportationservice.ds.TransportFindRequest;
import com.example.transportationservice.ds.TransportInfoRequest;
import com.example.transportationservice.ds.TransportInfoResponse;
import com.example.transportationservice.entity.Customer;
import com.example.transportationservice.entity.CustomerOrderProduct;
import com.example.transportationservice.entity.Product;

import com.example.transportationservice.entity.ProductDto;
import com.example.transportationservice.error.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransportService {

    private RestTemplate template = new RestTemplate();

    @Autowired
    private CustomerOrderProductDao orderProductDao;
    @Autowired
    private CustomerDao customerDao;

    @Transactional
    public void saveTransPortService(TransportInfoRequest request) {
        Customer customer = new Customer(request.customerName(), request.email());

        List<Product> products = request.products()
                .stream()
                .map(p -> new Product(p.getName(), p.getPrice(), p.getQuantity()))
                .collect(Collectors.toList());

        CustomerOrderProduct orderProduct = new CustomerOrderProduct();
        orderProduct.setOrderId(generateOrderId());
        orderProduct.addCustomer(customer);
        orderProduct.setTransportTime(LocalDateTime.now());
        orderProduct.setTotalAmount(request.totalAmount());

        products.forEach(p -> orderProduct.addProduct(p));
        orderProductDao.save(orderProduct);

    }

    private String generateOrderId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    record Token(String token) {
    }

    public TransportInfoResponse findTransPortInfo(String email, String password) {

        try {
            var request = new TransportFindRequest(email, password);
            ResponseEntity<Token> response =
                    template.postForEntity("http://localhost:8080/security/login", request, Token.class);
            String token;

            if (response.getStatusCode().is2xxSuccessful()) {
                token = response.getBody().token();

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + token);

                HttpEntity<String> httpEntity = new HttpEntity<>(headers);
                ResponseEntity response1 = template.exchange("http://localhost:8080/security/user",
                        HttpMethod.GET,
                        httpEntity, String.class);

                if (response1.getStatusCode().is2xxSuccessful()) {

                    List<ProductDto> products = orderProductDao.findPRoductsByCustomerEmail(email);
                    Set<String> name = orderProductDao
                            .findCustomerNameByEmail(email);

                    List<CustomerOrder> customerOrders = orderProductDao.findTotalAmountByEmail(email);
                    System.out.println(customerOrders);
                    return new TransportInfoResponse(
                            name,
                            email,
                            products,
                            customerOrders
                    );
                }
                else {
                    throw new AuthorizationException();
                }
            } else {
                throw new AuthorizationException();
            }
        }

        catch (Exception e){
            throw new AuthorizationException();
        }

    }

}
