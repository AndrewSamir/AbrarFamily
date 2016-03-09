package com.example.andrewsamir.abrarfamily.data;

/**
 * Created by Andrew Samir on 1/27/2016.
 */
public class Name {

    String name;
    String photo;

    public String getRakm() {
        return rakm;
    }

    public void setRakm(String rakm) {
        this.rakm = rakm;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    String rakm;
    String street;

    String number;

    public Name(String name, String photo, String rakm, String street, String number) {
        this.name = name;
        this.photo = photo;
        this.rakm = rakm;
        this.street = street;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
