package com.example.haripurpolice;

public class ViewTrucks
{

    public String truckowneridcard,truckowner,trucknumber,truckname,truckmodel,truckid,truckfirstowner,time,image,date;

    public ViewTrucks()
    {

    }


    public ViewTrucks(String truckowneridcard, String truckowner, String trucknumber, String truckname, String truckmodel, String truckid, String truckfirstowner, String time, String image, String date) {
        this.truckowneridcard = truckowneridcard;
        this.truckowner = truckowner;
        this.trucknumber = trucknumber;
        this.truckname = truckname;
        this.truckmodel = truckmodel;
        this.truckid = truckid;
        this.truckfirstowner = truckfirstowner;
        this.time = time;
        this.image = image;
        this.date = date;
    }

    public String getTruckowneridcard() {
        return truckowneridcard;
    }

    public void setTruckowneridcard(String truckowneridcard)
    {
        this.truckowneridcard = truckowneridcard;
    }

    public String getTruckowner() {
        return truckowner;
    }

    public void setTruckowner(String truckowner) {
        this.truckowner = truckowner;
    }

    public String getTrucknumber() {
        return trucknumber;
    }

    public void setTrucknumber(String trucknumber) {
        this.trucknumber = trucknumber;
    }

    public String getTruckname() {
        return truckname;
    }

    public void setTruckname(String truckname) {
        this.truckname = truckname;
    }

    public String getTruckmodel() {
        return truckmodel;
    }

    public void setTruckmodel(String truckmodel) {
        this.truckmodel = truckmodel;
    }

    public String getTruckid() {
        return truckid;
    }

    public void setTruckid(String truckid) {
        this.truckid = truckid;
    }

    public String getTruckfirstowner() {
        return truckfirstowner;
    }

    public void setTruckfirstowner(String truckfirstowner) {
        this.truckfirstowner = truckfirstowner;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
