package com.example.haripurpolice;

public class ViewPickups
{
    public String time,pickupowneridcard,pickupowner,pickupnumber,pickupname,pickupmodel,pickupid,pickupfirstowner,image,date;


    public ViewPickups()
    {

    }

    public ViewPickups(String time, String pickupowneridcard, String pickupowner, String pickupnumber, String pickupname, String pickupmodel, String pickupid, String pickupfirstowner, String image, String date)
    {
        this.time = time;
        this.pickupowneridcard = pickupowneridcard;
        this.pickupowner = pickupowner;
        this.pickupnumber = pickupnumber;
        this.pickupname = pickupname;
        this.pickupmodel = pickupmodel;
        this.pickupid = pickupid;
        this.pickupfirstowner = pickupfirstowner;
        this.image = image;
        this.date = date;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPickupowneridcard() {
        return pickupowneridcard;
    }

    public void setPickupowneridcard(String pickupowneridcard) {
        this.pickupowneridcard = pickupowneridcard;
    }

    public String getPickupowner() {
        return pickupowner;
    }

    public void setPickupowner(String pickupowner) {
        this.pickupowner = pickupowner;
    }

    public String getPickupnumber() {
        return pickupnumber;
    }

    public void setPickupnumber(String pickupnumber) {
        this.pickupnumber = pickupnumber;
    }

    public String getPickupname() {
        return pickupname;
    }

    public void setPickupname(String pickupname) {
        this.pickupname = pickupname;
    }

    public String getPickupmodel() {
        return pickupmodel;
    }

    public void setPickupmodel(String pickupmodel) {
        this.pickupmodel = pickupmodel;
    }

    public String getPickupid() {
        return pickupid;
    }

    public void setPickupid(String pickupid) {
        this.pickupid = pickupid;
    }

    public String getPickupfirstowner() {
        return pickupfirstowner;
    }

    public void setPickupfirstowner(String pickupfirstowner) {
        this.pickupfirstowner = pickupfirstowner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
