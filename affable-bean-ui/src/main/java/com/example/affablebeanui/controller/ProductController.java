package com.example.affablebeanui.controller;

import com.example.affablebeanui.service.ProductClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

@Controller
@RequestMapping("/webui")
public class ProductController {

    @Autowired
    private ProductClientService productClientService;
    @GetMapping({"/","/home"})
    public String home(){
        return "home";
    }

    @GetMapping("/products")
    public String listProductByCategory(@RequestParam("category")String categoryName, Model model){
        model.addAttribute("products",productClientService.findProductsByCategoryname(categoryName));
        return "products";
    }

}
