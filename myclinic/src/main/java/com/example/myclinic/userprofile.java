package com.example.myclinic;

public class userprofile {
    public String userPhone;
    public String userEmail;
    public String userName;

    public userprofile(){
    }

    public userprofile(String userPhone, String userEmail, String userName){
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {

        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

