package com.imedical.mobiledoctor.entity;

/**
 * Created by Alessandro on 12/01/2016.
 */
public class Item {
    private int idImage;
    private String name;
    private int index;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item(int idImage, String name, int id) {
        this.idImage = idImage;
        this.name = name;
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public int getindex() {
        return index;
    }

    public void setindex(int index) {
        this.index = index;
    }



}
