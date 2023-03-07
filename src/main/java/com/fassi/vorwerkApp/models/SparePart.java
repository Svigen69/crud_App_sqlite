package com.fassi.vorwerkApp.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Esatzteile")
public class SparePart {
    @DatabaseField(generatedId = true, columnName = "ID")
    private int sparePartId;
    @DatabaseField(columnName = "Name", canBeNull = false)
    private String sparePartName;
    @DatabaseField(columnName = "Preis", canBeNull = false)
    private double sparePartPrice;
    @DatabaseField(columnName = "Verfügbarkeit", canBeNull = false)
    private int sparePartAvailability;
    @DatabaseField(columnName = "ErsatzteilKategorie", canBeNull = false)
    private String sparePartType;

    public SparePart(int sparePartId, String sparePartName, double sparePartPrice, int sparePartAvailability, String sparePartType) {
        this.sparePartId = sparePartId;
        this.sparePartName = sparePartName;
        this.sparePartPrice = sparePartPrice;
        this.sparePartAvailability = sparePartAvailability;
        this.sparePartType = sparePartType;
    }

    public String getSparePartType() {
        return sparePartType;
    }

    public void setSparePartType(String sparePartType) {
        this.sparePartType = sparePartType;
    }

    public SparePart(String sparePartName, double sparePartPrice, int sparePartAvailability, String sparePartType) {
        this.sparePartName = sparePartName;
        this.sparePartPrice = sparePartPrice;
        this.sparePartAvailability = sparePartAvailability;
        this.sparePartType = sparePartType;
    }

    public SparePart() {
    }

    public int getSparePartId() {
        return sparePartId;
    }

    public void setSparePartId(int sparePartId) {
        this.sparePartId = sparePartId;
    }

    public String getSparePartName() {
        return sparePartName;
    }

    public void setSparePartName(String sparePartName) {
        this.sparePartName = sparePartName;
    }

    public double getSparePartPrice() {
        return sparePartPrice;
    }

    public void setSparePartPrice(double sparePartPrice) {
        this.sparePartPrice = sparePartPrice;
    }

    public int getSparePartAvailability() {
        return sparePartAvailability;
    }

    public void setSparePartAvailability(int sparePartAvailability) {
        this.sparePartAvailability = sparePartAvailability;
    }

}

