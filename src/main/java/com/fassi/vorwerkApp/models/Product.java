package com.fassi.vorwerkApp.models;

import com.fassi.vorwerkApp.enumerations.EProductCategory;
import com.fassi.vorwerkApp.enumerations.EProductType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Produkte")
public class Product {
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @DatabaseField(columnName = "Name", canBeNull = false)
    private String name;
    @DatabaseField(columnName = "price", canBeNull = false)
    private double price;
    @DatabaseField(columnName = "quantity", canBeNull = false)
    private int quantity;
    @DatabaseField(columnName = "category", canBeNull = false)
    private EProductCategory category;
    @DatabaseField(columnName = "type", canBeNull = false)
    private EProductType type;

    public Product( String name, double price, int quantity, EProductCategory category, EProductType type) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.type = type;
    }


    public Product(int id, String name, double price, int quantity, EProductCategory category, EProductType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.type = type;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public EProductCategory getCategory() {
        return category;
    }

    public void setCategory(EProductCategory category) {
        this.category = category;
    }

    public EProductType getType() {
        return type;
    }

    public void setType(EProductType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category=" + category +
                ", type=" + type +
                '}';
    }
}
