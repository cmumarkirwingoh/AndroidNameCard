package com.example.navadon.androidnamecard;

import android.arch.lifecycle.ViewModel;
import android.graphics.drawable.Drawable;

public class MainViewModel extends ViewModel {

    private String name;
    private String studentid;
    private String citizenid;
    private String faculty;
    private Drawable image;

    public MainViewModel(){

    }

    public String getName(){
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String string) {
        this.studentid = string;
    }

    public String getCitizenid() {
        return citizenid;
    }

    public void setCitizenid(String string) {
        this.citizenid = string;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String string) {
        this.faculty = string;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}