package com.example.affablebeanui.controller;

import com.example.affablebeanui.ds.CartBean;
import com.example.affablebeanui.ds.CustomerOrder;
import com.example.affablebeanui.ds.TransportInfoEntity;
import com.example.affablebeanui.dto.ProductDto;
import com.example.affablebeanui.dto.ProductDto1;
import com.example.affablebeanui.service.ProductClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping ("/checkout")
    public String checkout(){
        return "checkout";
    }
    @PostMapping("/checkout")
    public String checkoutProcess(@RequestParam("name") String name,
                                  @RequestParam("email") String email,
                                  @ModelAttribute("updateTotalPrice") double total){

       productClientService.checkout(name,email,total);
        return "redirect:/webui/";
    }


    @GetMapping("/find-transport-info")
    public String findTransportForm(){

        return "findTransportForm";
    }

    @PostMapping("/find-transport-info")
    public String processFinedTransport(@RequestParam("email") String email,
                                        @RequestParam("password") String password){

        this.entity = productClientService.findTransportInfo(email,password);
        return "redirect:/webui/trans-port-entity/view";
    }
    TransportInfoEntity entity;
    @GetMapping("/trans-port-entity/view")
    public String transportInfoPageView(Model model){

        model.addAttribute("entity",entity);
        model.addAttribute("customerName",entity.getCustomerName().get(0));
        model.addAttribute("product",entity.getProducts());
        model.addAttribute("totalAmount",entity.getCustomerOrder()
                .stream().map(CustomerOrder::getTotalAmount).mapToDouble(d -> d).sum());

        return "transport-view";
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
