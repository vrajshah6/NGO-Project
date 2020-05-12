package com.example.dell.ngoproject;

public class NGO {
    private long id;
    private String name, about;

    public NGO(long id, String name, String about) {

        this.id = id;
        this.name = name;
        this.about = about;
    }

    public NGO(long id) {

        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
