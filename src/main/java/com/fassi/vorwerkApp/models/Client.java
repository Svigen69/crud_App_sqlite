package com.fassi.vorwerkApp.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Kunden")
public class Client {
    @DatabaseField(generatedId = true, columnName = "ID")
    private int clienId;
    @DatabaseField(columnName = "Vorname", canBeNull = false)
    private String firstName;
    @DatabaseField(columnName = "Nachname", canBeNull = false)
    private String lastName;
    @DatabaseField(columnName = "Adresse", canBeNull = false)
    private String address;
    @DatabaseField(columnName = "TelefonNummer", canBeNull = false)
    private String phoneNumber;
    @DatabaseField(columnName = "Email", canBeNull = false)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Client(int clientId, String firstName, String lastName, String address, String phoneNumber, String email) {
        this.clienId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Client(String firstName, String lastName, String address, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getClienId() {
        return clienId;
    }

    public void setClienId(int clienId) {
        this.clienId = clienId;
    }

    public Client() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
