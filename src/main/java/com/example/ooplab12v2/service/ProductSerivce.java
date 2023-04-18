package com.example.ooplab12v2.service;

import com.example.ooplab12v2.data.Product;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductSerivce {
    @Getter
    private List<Product> products;

    @PostConstruct
    public void init() {

//                products = new ArrayList<>(List.of(
//                new Product(1,"Оселедець", "Norven", 119.50, LocalDate.of(2022, 12, 25), 45),
//                new Product(2,"Шоколад", "Шоколад", 90.75, LocalDate.of(2022, 10, 15), 100),
//                new Product(3,"Нaпій", "Pepsi", 27.60, LocalDate.of(2021, 12, 25), 37),
//                new Product(4,"Йогурт", "Danone", 28.90, LocalDate.of(2023, 3, 15), 12),
//                new Product(5,"Йогурт", "Марійка", 23.60, LocalDate.of(2023, 2, 25), 30),
//                new Product(6,"Масло", "Ферма", 46.50, LocalDate.of(2023, 4, 25), 30),
//                new Product(7,"Ковбаса", "Добров", 83.90, LocalDate.of(2023, 4, 5), 30),
//                new Product(8,"Сік", "Sandora", 66.90, LocalDate.of(2022, 12, 15), 30),
//                new Product(9,"Молоко", "Ферма", 33.40, LocalDate.of(2023, 11, 25), 43)
//        ));

        try (BufferedReader reader = Files.newBufferedReader(Path.of("products.txt"))) {
            products = new ArrayList<>(reader.lines().map(line -> {
                String[] s = line.split(";");
                int id = Integer.parseInt(s[0]);
                String name = s[1];
                String manuf = s[2];
                double cost = Double.parseDouble(s[3]);
                LocalDate term = LocalDate.parse(s[4]);
                int count = Integer.parseInt(s[5]);
                return new Product(id, name, manuf, cost,term,count);
            }).toList());
        } catch (IOException e) {
            makeClear();
        }

    }

    public void add(Product product) {
        products.add(product);
    }
    public void remove(Product product) {
        products.remove(product);
    }
    public void removeById(int id) {
        products.removeIf(p->p.getId()==id);
    }

    public void writeToFile() {
        try (PrintWriter out = new PrintWriter("products.txt")){
                for(Product p: products) {
                 out.println(p.getId()+";"+p.getName()+";"+p.getManufacturer()+";"+p.getPrice()+";"+p.getTerm()+";"+p.getCount());
                }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void readFromFile() {
        init();
    }
    public void makeClear() {
        products = new ArrayList<>();
    }

    public void sortByName(String name) {
        List<Product> tempProducts = new ArrayList<>();
        for(Product p: products) {
            if(p.getName().equals(name)) {
               tempProducts.add(p);
            }
        }
        products = tempProducts;
        sortByCost(-1);
    }
    public void sortByCost(double price) {
        if(price==-1) {
            products.sort(Comparator.comparing(Product::getPrice));
        } else {
            List<Product> tempProducts = new ArrayList<>();
            for (Product product : products) {
                if (product.getPrice() <= price) {
                    tempProducts.add(product);
                }
            }
            products = tempProducts;
        }
    }
    public void sortByTerm(LocalDate term) {
        if(!(term == null)) {
            List<Product> tempProducts = new ArrayList<>();
            for (Product product : products) {
                if (product.getTerm().isAfter(term)) {
                    tempProducts.add(product);
                }
            }
            products = tempProducts;
        } else {
            products.sort(Comparator.comparing(Product::getTerm).reversed());
        }
    }
}
