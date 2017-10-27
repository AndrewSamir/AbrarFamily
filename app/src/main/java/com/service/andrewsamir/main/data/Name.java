package com.service.andrewsamir.abrarfamily.data;

/**
 * Created by Andrew Samir on 1/27/2016.
 */
public class Name {

    String anotherAdd;
    String attendance;
    String birthdate;
    String description;
    String flat;
    String floor;
    String homeNo;
    String image;
    String lastEftkad;
    String mama;
    String name;
    String papa;
    String phone;
    String place;
    String street;
    String key;


    public Name() {
    }

    public Name(String homeNo, String image, String lastEftkad, String name, String street, String key,
                String lastAttendance) {
        this.homeNo = homeNo;
        this.image = image;
        this.lastEftkad = lastEftkad;
        this.name = name;
        this.street = street;
        this.key = key;
        this.attendance = lastAttendance;
    }


    public String getAnotherAdd() {
        return anotherAdd;
    }

    public void setAnotherAdd(String anotherAdd) {
        this.anotherAdd = anotherAdd;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getHomeNo() {
        return homeNo;
    }

    public void setHomeNo(String homeNo) {
        this.homeNo = homeNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastEftkad() {
        return lastEftkad;
    }

    public void setLastEftkad(String lastEftkad) {
        this.lastEftkad = lastEftkad;
    }

    public String getMama() {
        return mama;
    }

    public void setMama(String mama) {
        this.mama = mama;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPapa() {
        return papa;
    }

    public void setPapa(String papa) {
        this.papa = papa;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
