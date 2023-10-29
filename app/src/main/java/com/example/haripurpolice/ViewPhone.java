package com.example.haripurpolice;

import android.view.View;

public class ViewPhone
{
    public String date,image,pcreator,pemei,phoneid,pname,time,pmodel;

    public ViewPhone()
    {

    }


    public ViewPhone(String date, String image, String pcreator, String pemei, String phoneid, String pname, String time,String pmodel) {
        this.date = date;
        this.image = image;
        this.pcreator = pcreator;
        this.pemei = pemei;
        this.phoneid = phoneid;
        this.pname = pname;
        this.time = time;
        this.pmodel = pmodel;
    }



    public String getPmodel() {
        return pmodel;
    }

    public void setPmodel(String pmodel) {
        this.pmodel = pmodel;
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

    public String getPcreator() {
        return pcreator;
    }

    public void setPcreator(String pcreator) {
        this.pcreator = pcreator;
    }

    public String getPemei() {
        return pemei;
    }

    public void setPemei(String pemei) {
        this.pemei = pemei;
    }

    public String getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(String phoneid) {
        this.phoneid = phoneid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
