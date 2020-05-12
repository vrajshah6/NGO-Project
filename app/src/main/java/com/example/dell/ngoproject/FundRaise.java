package com.example.dell.ngoproject;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FundRaise implements Parcelable {

    private int id = 0;
    private String name = "";
    private  String descripition= "";
    private long amount;
    private String date;
    NGO ngo;
    ArrayList<String> imageUrlList = new ArrayList<>();


    public FundRaise(int id, String name, String descripition, long amount, NGO ngo,String date,ArrayList<String> imageUrlList) {
        this.id = id;
        this.name = name;
        this.descripition = descripition;
        this.amount = amount;
        this.ngo = ngo;
        this.date = date;
        this.imageUrlList = imageUrlList;
    }

    protected FundRaise(Parcel in) {
        id = in.readInt();
        name = in.readString();
        descripition = in.readString();
        amount = in.readLong();
        date = in.readString();
        imageUrlList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(descripition);
        dest.writeLong(amount);
        dest.writeString(date);
        dest.writeStringList(imageUrlList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FundRaise> CREATOR = new Creator<FundRaise>() {
        @Override
        public FundRaise createFromParcel(Parcel in) {
            return new FundRaise(in);
        }

        @Override
        public FundRaise[] newArray(int size) {
            return new FundRaise[size];
        }
    };

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

    public String getDescripition() {
        return descripition;
    }

    public void setDescripition(String descripition) {
        this.descripition = descripition;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NGO getNgo() {
        return ngo;
    }

    public void setNgo(NGO ngo) {
        this.ngo = ngo;
    }

    public static Creator<FundRaise> getCREATOR() {
        return CREATOR;
    }

    public ArrayList<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(ArrayList<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }
}
