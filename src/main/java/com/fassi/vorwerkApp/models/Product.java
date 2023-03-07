package com.fassi.vorwerkApp.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Produkte")
public class Product {
    @DatabaseField(generatedId = true, columnName = "ID")
    private int productId;
    @DatabaseField(columnName = "Name", canBeNull = false)
    private String productName;
    @DatabaseField(columnName = "Preis", canBeNull = false)
    private double productPrice;
    @DatabaseField(columnName = "Verfügbarkeit", canBeNull = false)
    private int productAvailability;
    @DatabaseField(columnName = "ProduktKategorie", canBeNull = false)
    private String productType;

    public Product(int productId, String productName, double productPrice, int productAvailability, String productType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productAvailability = productAvailability;
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Product(String productName, double productPrice, int productAvailability, String productType) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productAvailability = productAvailability;
        this.productType = productType;
    }

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductAvailability() {
        return productAvailability;
    }

    public void setProductAvailability(int productAvailability) {
        this.productAvailability = productAvailability;
    }
}
