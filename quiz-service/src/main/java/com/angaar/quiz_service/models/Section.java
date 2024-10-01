package com.angaar.quiz_service.models;

import java.util.List;

import com.angaar.quiz_service.models.item.Item;

public class Section {
    private String title;
    private List<Item> items;

    public Section() {}

    public Section(String title, List<Item> items) {
        this.title = title;
        this.items = items;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
