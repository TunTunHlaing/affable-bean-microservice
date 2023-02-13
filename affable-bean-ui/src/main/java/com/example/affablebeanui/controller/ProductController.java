package com.example.affablebeanui.controller;

import com.example.affablebeanui.ds.CartBean;
import com.example.affablebeanui.dto.ProductDto;
import com.example.affablebeanui.dto.ProductDto1;
import com.example.affablebeanui.service.ProductClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/webui")
public class ProductController {

    @Autowired
    private CartBean cartBean;

    private String categoryName;

    List<Integer> itemList = new ArrayList<>();

    @ModelAttribute("totalItem")
    public Integer totalItem(){
        return cartBean.getCart().stream().map(ProductDto::getQuantity)
                .mapToInt(Integer::intValue).sum();
    }

    @ModelAttribute("updateTotalPrice")
    public Double updateTotalPrice(){
        return cartBean.getCart().stream().map(p -> p.getPrice() * p.getQuantity())
                .mapToDouble(i -> i).sum();
    }

    @Autowired
    private ProductClientService productClientService;
    @GetMapping({"/","/home"})
    public String home(){
        return "home";
    }

    @GetMapping("/products")
    public String listProductByCategory(@RequestParam("category")String categoryName, Model model){
        this.categoryName = categoryName;
        model.addAttribute("products",productClientService.findProductsByCategoryname(categoryName));
        return "products";
    }

    @GetMapping("/product/add")
    public String addToCart(@RequestParam("id") int productId){

        cartBean.addToCart(productClientService.findProductById(productId));

        return "redirect:/webui/products?category="+ categoryName;
    }

    @GetMapping("/cart-view")
    public String cartView(Model model){
        model.addAttribute("productsInCart",cartBean.getCart());
        model.addAttribute("productDto",new ProductDto1());
        model.addAttribute("success",model.containsAttribute("success"));
        return "cart-view";
    }

    @PostMapping("/update-item")
    public String processItem(ProductDto1 productDto, RedirectAttributes attributes){
        itemList = productDto.getItemList();
        int counter = 0;
        double total = 0;

        for (ProductDto product : cartBean.getCart()){
            product.setQuantity(itemList.get(counter));
             //total += itemList.get(counter) * product.getPrice();
             counter++;
        }

        attributes.addFlashAttribute("success",true);
        //System.out.println("==================" + itemList);
        return "redirect:/webui/cart-view";
    }

    @ModelAttribute("cartSize")
    public Integer cartSize(){
        return cartBean.getCart().size();
    }

    @ModelAttribute("total")
    public Double totalPrice(){
        return cartBean.getCart()
                .stream().map(
                p -> p.getPrice() * 1
        ).mapToDouble(i -> i).sum();
    }

}
