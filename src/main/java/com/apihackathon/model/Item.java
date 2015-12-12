package com.apihackathon.model;

/**
 * Created by prasanna on 12/12/15.
 */
public class Item {

    private String code;
    private Integer quantity;
    private String name;
    private Double price;

    public Item(String code, String qty){
        Integer quantity = Integer.parseInt(qty);
        this.quantity = quantity;
        this.code = code;
        this.name = ItemUtil.getName(code);
        this.price = ItemUtil.getPrice(code);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString(){
        return name + " - " + price;
    }
}
