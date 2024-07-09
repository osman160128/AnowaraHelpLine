package com.example.anowarahelpline.blood;

import java.util.HashMap;

public class BloodGroupModel {

      String name;
      String mobileNumber;
      String email;
      String downloadImgUri;
      String bloodGroup;

    public BloodGroupModel() {
    }

    public BloodGroupModel(String name, String mobileNumber, String email, String downloadImgUri, String bloodGroup) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.downloadImgUri = downloadImgUri;
        this.bloodGroup = bloodGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDownloadImgUri() {
        return downloadImgUri;
    }

    public void setDownloadImgUri(String downloadImgUri) {
        this.downloadImgUri = downloadImgUri;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
