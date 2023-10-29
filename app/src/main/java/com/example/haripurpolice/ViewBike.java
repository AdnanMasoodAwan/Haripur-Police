package com.example.haripurpolice;

public class ViewBike
{

    public String bikeid,bmodel,bname,bnumber,bowner,date,image,time;

    public ViewBike()
    {

    }

    public ViewBike(String bikeid, String bmodel, String bname, String bnumber, String bowner, String date, String image, String time)
    {
        this.bikeid = bikeid;
        this.bmodel = bmodel;
        this.bname = bname;
        this.bnumber = bnumber;
        this.bowner = bowner;
        this.date = date;
        this.image = image;
        this.time = time;
    }

    public String getBikeid()
    {
        return bikeid;
    }

    public void setBikeid(String bikeid)
    {
        this.bikeid = bikeid;
    }

    public String getBmodel()
    {
        return bmodel;
    }

    public void setBmodel(String bmodel)
    {
        this.bmodel = bmodel;
    }

    public String getBname()
    {
        return bname;
    }

    public void setBname(String bname)
    {
        this.bname = bname;
    }

    public String getBnumber()
    {
        return bnumber;
    }

    public void setBnumber(String bnumber)
    {
        this.bnumber = bnumber;
    }

    public String getBowner()
    {
        return bowner;
    }

    public void setBowner(String bowner)
    {
        this.bowner = bowner;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }


}
