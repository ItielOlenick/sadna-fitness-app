package com.example.androidfitnessapp.data;

import java.io.Serializable;

public class CategoryData implements Serializable
{
    int id;
    String name;

    public CategoryData(CategoryData category)
    {
        if(category == null)
        {
            return;
        }
        this.id = category.id;
        this.name = category.name;
    }

    public CategoryData(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryData(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryData()
    {

    }
}
