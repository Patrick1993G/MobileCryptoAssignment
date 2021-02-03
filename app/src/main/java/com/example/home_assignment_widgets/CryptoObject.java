package com.example.home_assignment_widgets;

import com.google.gson.GsonBuilder;

import java.util.Objects;

public class CryptoObject {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CryptoObject(String title, String price, String id) {
        this.title = title;
        this.price = price;
        this.id = id;
    }

    private String title,price, id;

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this,CryptoObject.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CryptoObject that = (CryptoObject) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(price, that.price) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, id);
    }
}
