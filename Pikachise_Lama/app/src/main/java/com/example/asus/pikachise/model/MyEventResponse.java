package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 13/12/2017.
 */

public class MyEventResponse {
    @SerializedName("events")
    @Expose
    private List<MyEvent> events = null;

    public List<MyEvent> getEvents() {
        return events;
    }

    public void setEvents(List<MyEvent> events) {
        this.events = events;
    }
}
