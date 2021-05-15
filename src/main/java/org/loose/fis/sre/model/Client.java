package org.loose.fis.sre.model;

public class Client {
    private String username;
    private  String name;
    private String email;
    private String address;
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Client(String username, String name, String email, String phone, String address) {
        this.username=username;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
    public Client()
    {

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
