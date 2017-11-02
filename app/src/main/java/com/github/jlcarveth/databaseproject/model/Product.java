package com.github.jlcarveth.databaseproject.model;

/**
 * Created by John on 11/2/2017.
 */

public class Product {
    private String name;

    private int sku;

    private int cost;

    private int id;

    public Product(String name, int sku, int cost, int id) {
        this.name = name;
        this.sku = sku;
        this.cost = cost;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
