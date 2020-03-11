package ru.romanovAl.tochkatest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    @SerializedName("items")
    @Expose
    private List<User> items = null;

    @Override
    public String toString() {
        return items.toString();
    }

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }

}
