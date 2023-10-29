package com.example.haripurpolice;

public class Users
{
    public String name, email, phoneno, password, cnic, type,profileimage;

    public Users()
    {

    }

    public Users(String name, String email, String phoneno, String password, String cnic, String type, String profileimage) {
        this.name = name;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;
        this.cnic = cnic;
        this.type = type;
        this.profileimage = profileimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }
}
