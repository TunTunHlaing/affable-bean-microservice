package com.example.transportationservice.controller;

import com.example.transportationservice.ds.TransportFindRequest;
import com.example.transportationservice.ds.TransportInfoRequest;
import com.example.transportationservice.ds.TransportInfoResponse;
import com.example.transportationservice.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * customer Name
 * customer Email
 * product name
 * product price
 * product quantity
 * customerOrderProduct transportTime
 * customerOrderProduct orderId
 * customerOrderProduct totalAmount
 * **/


@RestController
@RequestMapping("/transport")
public class TranspotationController {
    @Autowired
    private TransportService transportService;

    record TransportInfoResponseSuccess(String name){}

    @PostMapping("/save-transport-info")
    public TransportInfoResponseSuccess transportInfo(@RequestBody TransportInfoRequest request){

        transportService.saveTransPortService(request);
        return new TransportInfoResponseSuccess("success");
    }



    @PostMapping("/find-transport-info")
    public TransportInfoResponse findTransportInfo(@RequestBody TransportFindRequest request){
      //  System.out.println(transportService.findTransPortInfo(request.email).getCustomerOrder());
        return transportService.findTransPortInfo(request.email(),request.password());
    }

}
