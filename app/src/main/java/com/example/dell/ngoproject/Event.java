package com.example.dell.ngoproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Event implements Parcelable {

    private long id = 0;
    private String name = "",title = "",date = "",venue = "", description = "";

    ArrayList<String> imageUrlList = new ArrayList<>();

    public Event(String title, String venue, String description,ArrayList<String> imageUrlList) {
        this.title = title;
        this.venue = venue;
        this.description = description;
        this.imageUrlList=imageUrlList;
    }

    //
//    public Event(long id, String name, String title, String date, String venue, String description, String imageUrl) {
//        this.id = id;
//        this.name = name;
//        this.title = title;
//        this.date = date;
//        this.venue = venue;
//        this.description = description;
//        this.imageUrl = imageUrl;
//    }

    //    public Event(String name, String title, String date,String venue,String description) {
//        this.id = id;
//        this.name = name;
//        this.title = title;
//        this.date = date;
//        this.venue = venue;
//        this.description = description;
//    }

    protected Event(Parcel in) {
        id = in.readLong();
        name = in.readString();
        title = in.readString();
        date = in.readString();
        venue = in.readString();
        description = in.readString();
        imageUrlList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(venue);
        dest.writeString(description);
        dest.writeStringList(imageUrlList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Creator<Event> getCREATOR() {
        return CREATOR;
    }



    public ArrayList<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(ArrayList<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }
}
