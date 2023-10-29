package com.example.haripurpolice;

public class ViewCar
{

    public String careid,carmodel,carname,carnumber,carowner,date,image,time;

    public ViewCar()
    {

    }


    public ViewCar(String careid, String carmodel, String carname, String carnumber, String carowner, String date, String image, String time)
    {
        this.careid = careid;
        this.carmodel = carmodel;
        this.carname = carname;
        this.carnumber = carnumber;
        this.carowner = carowner;
        this.date = date;
        this.image = image;
        this.time = time;
    }

    public String getCareid() {
        return careid;
    }

    public void setCareid(String careid) {
        this.careid = careid;
    }

    public String getCarmodel() {
        return carmodel;
    }

    public void setCarmodel(String carmodel) {
        this.carmodel = carmodel;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getCarowner() {
        return carowner;
    }

    public void setCarowner(String carowner) {
        this.carowner = carowner;
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
