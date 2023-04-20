package com.fassi.vorwerkApp.models;

import com.fassi.vorwerkApp.enumerations.EMeetingType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Termine")
public class Meeting {
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @DatabaseField(columnName = "date", canBeNull = false)
    private long date;
    @DatabaseField(columnName = "note", canBeNull = false)
    private String note;
    @DatabaseField(columnName = "client_id", foreign = true, foreignAutoRefresh = true)
    private Client client;
    @DatabaseField(columnName = "product_id", foreign = true, foreignAutoRefresh = true)
    private Product product;
    @DatabaseField(columnName = "type", canBeNull = false)
    private EMeetingType type;

    public Meeting() {
    }

    public String getProductName() {
        return this.product.getName();
    }

    public String getStringDate() {
        return new Date(this.date).toString();
    }

    public String getClientFullName() {
        return this.client.getFullName();
    }

    public Meeting(long date, Client client, Product product, EMeetingType type, String note) {
        this.date = date;
        this.client = client;
        this.product = product;
        this.type = type;
        this.note = note;
    }

    public Meeting(int id, long date, Client client, Product product, EMeetingType type, String note) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.product = product;
        this.type = type;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public EMeetingType getType() {
        return type;
    }

    public void setType(EMeetingType type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
