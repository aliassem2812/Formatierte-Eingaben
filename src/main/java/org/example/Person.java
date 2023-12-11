package org.example;

import java.util.Date;

/**
 1.	Diese Klasse definiert das Datenmodell für eine Person.
 2.	Sie enthält Attribute wie id, fname, lname, gender, region, AHV, dateofbirth, und kinder, sowie entsprechende Getter und Setter-Methoden.
 */
public class Person {
    private int id;
    private String fname;
    private String lname;
    private String gender;
    private String region;
    private String AHV;
    private Date dateofbirth;
    private int kinder;

    public Person(int id,String fname, String lname, String gender, String region, String AHV, Date dateofbirth, int kinder) {
        this.id=id;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.region = region;
        this.AHV = AHV;
        this.dateofbirth = dateofbirth;
        this.kinder = kinder;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getGender() {
        return gender;
    }

    public String getRegion() {
        return region;
    }

    public String getAHV() {
        return AHV;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public int getKinder() {
        return kinder;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setAHV(String AHV) {
        this.AHV = AHV;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public void setKinder(int kinder) {
        this.kinder = kinder;
    }
}
