package com.example.ooplab12v2.controllers;

import com.example.ooplab12v2.data.Product;
import com.example.ooplab12v2.service.ProductSerivce;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
public class ProductController {

    private ProductSerivce productSerivce;

    @GetMapping("/products")
    public String showProducts(Model model) {
        model.addAttribute("products", productSerivce.getProducts());
        return "products";
    }

    @PostMapping("/add_product")
    public String addProduct(
            @RequestParam("product_id") int id,
            @RequestParam("product_name") String name,
            @RequestParam("product_manufacturer") String manufacturer,
            @RequestParam("product_price") int price,
            @RequestParam("product_term") LocalDate term,
            @RequestParam("product_count") int count
    ) {
        Product product = new Product(id, name, manufacturer, price, term, count);
        productSerivce.add(product);

        return "redirect:/products";
    }

    @GetMapping("/delete_product")
    public String deleteProduct(@RequestParam int id) {
        productSerivce.removeById(id);
        return "redirect:/products";
    }

    @GetMapping("/delete_product_v2")
    public String deleteProduct_v2(@RequestParam("product_id") int id,
                                   @RequestParam("product_name") String name,
                                   @RequestParam("product_manufacturer") String manufacturer,
                                   @RequestParam("product_price") double price,
                                   @RequestParam("product_term") LocalDate term,
                                   @RequestParam("product_count") int count) {
        productSerivce.remove(new Product(id, name, manufacturer, price, term, count));
        return "redirect:/products";
    }


    @GetMapping("save_products")
    public String saveProducts() {
        productSerivce.writeToFile();
        return "redirect:/products";
    }

    @GetMapping("clear_products")
    public String clearProducts() {
        productSerivce.makeClear();
        return "redirect:/products";
    }

    @GetMapping("read_products")
    public String readProducts() {
        productSerivce.refreshProductList();
        return "redirect:/products";
    }

    @PostMapping("name_sort_product")
    public String sortByName(@RequestParam("product_name") String name) {
        productSerivce.sortByName(name);
        return "redirect:/products";
    }

    @PostMapping("price_sort_product")
    public String sortByPrice() {
        productSerivce.sortByCost(-1);
        return "redirect:/products";
    }

    @PostMapping("term_sort_product")
    public String sortByTerm(@RequestParam("product_term") LocalDate term) {
        productSerivce.sortByTerm(term);
        return "redirect:/products";
    }

    @PostMapping("name_price_sort_product")
    public String sortByNameAndPrice(@RequestParam("product_name") String name,
                                     @RequestParam("product_price") double price) {
        productSerivce.sortByName(name);
        productSerivce.sortByCost(price);
        return "redirect:/products";
    }
}
