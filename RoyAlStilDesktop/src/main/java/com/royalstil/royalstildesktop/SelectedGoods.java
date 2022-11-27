package com.royalstil.royalstildesktop;

import javafx.beans.property.SimpleStringProperty;

public class SelectedGoods {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty cost;
    private SimpleStringProperty number;
    private SimpleStringProperty sum;

    public SelectedGoods(Integer id, String name, Integer number, Double cost) {
        this.id = new SimpleStringProperty(id.toString());
        this.name = new SimpleStringProperty(name);
        this.number = new SimpleStringProperty(number.toString());
        this.cost = new SimpleStringProperty(cost.toString());
        Double sumResult = number * cost;
        this.sum = new SimpleStringProperty(sumResult.toString());
    }

    /**public SelectedGoods(String id, String name, String number, String cost, String sum) {
        this.idItem = new SimpleStringProperty(id);
        this.nameItem = new SimpleStringProperty(name);
        this.number = new SimpleStringProperty(number);
        this.cost = new SimpleStringProperty(cost);
        this.sum = new SimpleStringProperty(sum);
    }**/


    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCost() {
        return cost.get();
    }

    public void setCost(String cost) {
        this.cost.set(cost);
    }

    public String getNumber() {
        return number.get();
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getSum() {
        return sum.get();
    }

    public void setSum(String sum) {
        this.sum.set(sum);
    }
}
