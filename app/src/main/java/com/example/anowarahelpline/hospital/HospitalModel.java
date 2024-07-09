package com.example.anowarahelpline.hospital;

public class HospitalModel {

   String address,imgUrl,name,phone;
    public HospitalModel(){};

    public HospitalModel(String address, String imgUrl, String name, String phone) {
        this.address = address;
        this.imgUrl = imgUrl;
        this.name = name;
        this.phone = phone;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
