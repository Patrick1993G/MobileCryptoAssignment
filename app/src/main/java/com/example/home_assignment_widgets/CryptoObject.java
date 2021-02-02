package com.example.home_assignment_widgets;

public class CryptoObject {
    public CryptoObject(String title, String price) {
        this.title = title;
        this.price = price;
    }
    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    private String title,price;

}
