package com.example.haripurpolice;

public class ViewVans
{
    public String vanowneridcard,vanowner,vannumber,vanname,vanmodel,vanid,vanfirstowner,time,image,date;

    public ViewVans()
    {

    }

    public ViewVans(String vanowneridcard, String vanowner, String vannumber, String vanname, String vanmodel, String vanid, String vanfirstowner, String time, String image, String date) {
        this.vanowneridcard = vanowneridcard;
        this.vanowner = vanowner;
        this.vannumber = vannumber;
        this.vanname = vanname;
        this.vanmodel = vanmodel;
        this.vanid = vanid;
        this.vanfirstowner = vanfirstowner;
        this.time = time;
        this.image = image;
        this.date = date;
    }


    public String getVanowneridcard() {
        return vanowneridcard;
    }

    public void setVanowneridcard(String vanowneridcard) {
        this.vanowneridcard = vanowneridcard;
    }

    public String getVanowner() {
        return vanowner;
    }

    public void setVanowner(String vanowner) {
        this.vanowner = vanowner;
    }

    public String getVannumber() {
        return vannumber;
    }

    public void setVannumber(String vannumber) {
        this.vannumber = vannumber;
    }

    public String getVanname() {
        return vanname;
    }

    public void setVanname(String vanname) {
        this.vanname = vanname;
    }

    public String getVanmodel() {
        return vanmodel;
    }

    public void setVanmodel(String vanmodel) {
        this.vanmodel = vanmodel;
    }

    public String getVanid() {
        return vanid;
    }

    public void setVanid(String vanid) {
        this.vanid = vanid;
    }

    public String getVanfirstowner() {
        return vanfirstowner;
    }

    public void setVanfirstowner(String vanfirstowner) {
        this.vanfirstowner = vanfirstowner;
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
