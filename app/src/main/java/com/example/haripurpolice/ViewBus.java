package com.example.haripurpolice;

public class ViewBus
{
   public String  busfirstowner,busid,busmodel,busname,busnumber,busowner,busowneridcard,date,image,time;

    public ViewBus()
    {

    }

    public ViewBus(String busfirstowner, String busid, String busmodel, String busname, String busnumber, String busowne, String busowneridcard, String date, String image, String time)
    {
        this.busfirstowner = busfirstowner;
        this.busid = busid;
        this.busmodel = busmodel;
        this.busname = busname;
        this.busnumber = busnumber;
        this.busowner = busowne;
        this.busowneridcard = busowneridcard;
        this.date = date;
        this.image = image;
        this.time = time;
    }

    public String getBusfirstowner() {
        return busfirstowner;
    }

    public void setBusfirstowner(String busfirstowner) {
        this.busfirstowner = busfirstowner;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public String getBusmodel() {
        return busmodel;
    }

    public void setBusmodel(String busmodel) {
        this.busmodel = busmodel;
    }

    public String getBusname() {
        return busname;
    }

    public void setBusname(String busname) {
        this.busname = busname;
    }

    public String getBusnumber() {
        return busnumber;
    }

    public void setBusnumber(String busnumber) {
        this.busnumber = busnumber;
    }

    public String getBusowner() {
        return busowner;
    }

    public void setBusowner(String busowne) {
        this.busowner = busowne;
    }

    public String getBusowneridcard() {
        return busowneridcard;
    }

    public void setBusowneridcard(String busowneridcard) {
        this.busowneridcard = busowneridcard;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
