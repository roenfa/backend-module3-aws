package org.example.models;

public class Products {
    private String id;
    private String name;
    private int stock;

    public String getId() {
        return this.id;
    }

    public String getname() {
        return this.name;
    }

    public int getstock() {
        return this.stock;
    }

    public void setId(String transactionId) {
        this.id = transactionId;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setstock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + " name: " + this.name + " stock: " + this.stock;
    }
}
