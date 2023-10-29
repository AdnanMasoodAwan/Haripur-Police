package com.example.haripurpolice;

public class ViewLaptops
{
    public String  time,laptopram,laptopprocessor,laptopownernic,laptopname,laptopmodel,laptopid,laptophardsik,
            laptopgeneration,image,date;


    public ViewLaptops()
    {

    }

    public ViewLaptops(String time, String laptopram, String laptopprocessor, String laptopownernic, String laptopname, String laptopmodel, String laptopid, String laptophardsik, String laptopgeneration, String image, String date)
    {
        this.time = time;
        this.laptopram = laptopram;
        this.laptopprocessor = laptopprocessor;
        this.laptopownernic = laptopownernic;
        this.laptopname = laptopname;
        this.laptopmodel = laptopmodel;
        this.laptopid = laptopid;
        this.laptophardsik = laptophardsik;
        this.laptopgeneration = laptopgeneration;
        this.image = image;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLaptopram() {
        return laptopram;
    }

    public void setLaptopram(String laptopram) {
        this.laptopram = laptopram;
    }

    public String getLaptopprocessor() {
        return laptopprocessor;
    }

    public void setLaptopprocessor(String laptopprocessor) {
        this.laptopprocessor = laptopprocessor;
    }

    public String getLaptopownernic() {
        return laptopownernic;
    }

    public void setLaptopownernic(String laptopownernic) {
        this.laptopownernic = laptopownernic;
    }

    public String getLaptopname() {
        return laptopname;
    }

    public void setLaptopname(String laptopname) {
        this.laptopname = laptopname;
    }

    public String getLaptopmodel() {
        return laptopmodel;
    }

    public void setLaptopmodel(String laptopmodel) {
        this.laptopmodel = laptopmodel;
    }

    public String getLaptopid() {
        return laptopid;
    }

    public void setLaptopid(String laptopid) {
        this.laptopid = laptopid;
    }

    public String getLaptophardsik() {
        return laptophardsik;
    }

    public void setLaptophardsik(String laptophardsik) {
        this.laptophardsik = laptophardsik;
    }

    public String getLaptopgeneration() {
        return laptopgeneration;
    }

    public void setLaptopgeneration(String laptopgeneration) {
        this.laptopgeneration = laptopgeneration;
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
