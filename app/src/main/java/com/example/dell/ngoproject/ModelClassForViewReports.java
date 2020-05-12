package com.example.dell.ngoproject;



public class ModelClassForViewReports {

    private String heading;
    private String description;

    public ModelClassForViewReports(String heading, String description) {
        this.heading = heading;
        this.description = description;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }
}