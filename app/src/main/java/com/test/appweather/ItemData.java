package com.test.appweather;

public class ItemData {

    private String week;
    private String mintemp;
    private String maxtemp;
    private String description;


    public ItemData() {
    }

    public ItemData(String week_, String mintemp_, String maxtemp_, String description_) {

        this.week = week_;
        this.mintemp = mintemp_;
        this.maxtemp = maxtemp_;
        this.description = description_;
    }


    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMintemp() {
        return mintemp;
    }

    public void setMintemp(String mintemp) {
        this.mintemp = mintemp;
    }

    public String getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(String maxtemp) {
        this.maxtemp = maxtemp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
