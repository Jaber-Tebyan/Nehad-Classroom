package com.tebyan.nehadclassroom.data;

import android.util.Patterns;

import com.tebyan.nehadclassroom.Listeners.AccountValidate;

public class AccountData {

    public AccountData() {
        email = "";
        pass = "";
        teacherID=0;
    }

    public AccountValidate accountValidate;
    String email;
    String pass;



    int teacherID;
    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        email = value;
    }

    public void setPass(String value) {
        pass = value;
    }

    public boolean CheckEmailForPattern() {
        String email=getEmail();
        if(email.startsWith("teacher123:")){
            email=email.split(":")[1];
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean CheckPasswordForPattern() {
        return getPass().length() > 6;
    }

    public boolean validate() {
        boolean valid = true;
        if (accountValidate != null) {
            valid = accountValidate.onValidate(this);
        }
        return valid;
    }

    public void setAccountValidate(AccountValidate accountValidate) {
        this.accountValidate = accountValidate;
    }
}
