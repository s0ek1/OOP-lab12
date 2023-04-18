package com.example.ooplab12v2.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id; //id
    private String name; //Найменування
    private String manufacturer; //Виробник
    private double price; //Ціна
    private LocalDate term; //Термін зберігання
    private int count; //Кількість
}
