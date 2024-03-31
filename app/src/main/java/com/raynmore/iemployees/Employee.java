package com.raynmore.iemployees;

public class Employee {

    private int id;
    private String name;
    private String phoneNumber;

    private String email;
    private int imageId;

    public Employee(int id, String name, String phoneNumber, String email, int imageId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imageId = imageId;
    }

    public Employee(String name, String phoneNumber, String email, int imageId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
