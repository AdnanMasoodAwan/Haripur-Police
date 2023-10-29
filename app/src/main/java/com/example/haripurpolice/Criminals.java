package com.example.haripurpolice;

public class Criminals
{

    public String name,image,address;

    public  Criminals()
    {

    }


    public Criminals(String name, String image, String address)
    {
        this.name = name;
        this.image = image;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
