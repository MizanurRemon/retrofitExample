package com.example.retrofitexample;

public class FetchData {

    private String userid;
  public String name;
    private String email;
    private String contact;

    public FetchData(String userid, String name, String email, String contact) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.contact = contact;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
