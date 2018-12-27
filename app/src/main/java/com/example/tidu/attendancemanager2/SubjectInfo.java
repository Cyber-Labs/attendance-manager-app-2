package com.example.tidu.attendancemanager2;

public class SubjectInfo {
    int id;
    String name,minimum,pres,abs,current;

    public SubjectInfo(int id, String name, String minimum, String pres, String abs, String current) {
        this.id = id;
        this.name = name;
        this.minimum = minimum;
        this.pres = pres;
        this.abs = abs;
        this.current = current;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }
}