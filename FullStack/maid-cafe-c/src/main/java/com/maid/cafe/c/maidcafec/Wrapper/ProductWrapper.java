package com.maid.cafe.c.maidcafec.Wrapper;

import lombok.Data;

import java.util.stream.Stream;

@Data
public class ProductWrapper {

    Integer id;

    String name;

    String description;

    Integer price; //todo if price will be ever changed to double, it must be changed over here too

    Integer category_id;

    String categoryName;

    String status;

    public ProductWrapper() {
    }

    //Is used by NamedQuery, the order in both must be the same
    public ProductWrapper(Integer id, String name, String description, Integer price, String status, Integer categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.category_id = categoryId;
        this.categoryName = categoryName;
    }

    public ProductWrapper(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductWrapper(Integer id, String name, String description, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
