package com.tebyan.nehadclassroom.data;

import androidx.annotation.NonNull;

public class User {
    public User() {
    }

    public User(String name, int age,int id, UserType userType) {
        this.name = name;
        this.age = age;
        this.userType = userType;
        this.selfID =id;
    }



    int teacherID;
    String name;
    int age;
    UserType userType;
    int selfID;

    //region Get Set Functions
    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getSelfID() {
        return selfID;
    }

    public void setSelfID(int selfID) {
        this.selfID = selfID;
    }
    public boolean isTeacher(){
        return userType==UserType.TEACHER;
    }
    //endregion




    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", userType=" + userType +
                ", id=" + selfID +
                '}';
    }

    public enum UserType{
        TEACHER,STUDENT
    }



}

