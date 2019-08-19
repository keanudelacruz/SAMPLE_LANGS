package com.example.sample_sqlite.Class;

public class Student {

    private int id;
    private String lname;
    private String fname;
    private String mname;

    public Student(int id, String lname, String fname, String mname) {
        this.id = id;
        this.lname  = lname;
        this.fname  = fname;
        this.mname = mname;
    }

//    public void Student (int id,String lname, String fname,String mname){
//
//    }

    public Student(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }
}
