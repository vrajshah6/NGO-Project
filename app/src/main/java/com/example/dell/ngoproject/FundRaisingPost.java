package com.example.dell.ngoproject;

import com.google.gson.annotations.SerializedName;

public class FundRaisingPost {
    NGO ngo;

    ///For different keys on server-client
    @SerializedName("title")
    private String event_title;

    private String event_description;
    private long total_amount;
    private static long post_id;

    public FundRaisingPost(String event_title, String event_description, long total_amount) {
        this.event_title = event_title;
        this.event_description = event_description;
        this.total_amount = total_amount;
    }

    public NGO getNgo() {
        return ngo;
    }

    public void setNgo(NGO ngo) {
        this.ngo = ngo;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public long getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(long total_amount) {
        this.total_amount = total_amount;
    }

    public static long getPost_id() {
        return post_id;
    }

    public static void setPost_id(long post_id) {
        FundRaisingPost.post_id = post_id;
    }
}
