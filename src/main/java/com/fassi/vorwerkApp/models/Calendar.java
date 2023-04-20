package com.fassi.vorwerkApp.models;

import java.time.ZonedDateTime;

public class Calendar{
    private ZonedDateTime date;
    private String clientFullName;
    private Integer serviceNo;

    public Calendar(ZonedDateTime date, String clientFullName, Integer serviceNo) {
        this.date = date;
        this.clientFullName = clientFullName;
        this.serviceNo = serviceNo;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public Integer getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(Integer serviceNo) {
        this.serviceNo = serviceNo;
    }

    @Override
    public String toString() {
        return "CalenderActivity{" +
                "date=" + date +
                ", clientFullName='" + clientFullName + '\'' +
                ", serviceNo=" + serviceNo +
                '}';
    }
}