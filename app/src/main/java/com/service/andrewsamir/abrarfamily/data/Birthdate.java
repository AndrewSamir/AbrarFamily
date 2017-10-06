package com.example.andrewsamir.abrarfamily.data;

/**
 * Created by andre on 20-Oct-16.
 */

public class Birthdate {

    String name,birthdate;
    int id;

    public Birthdate(String name, String birthdate ,int id) {
        this.name = name;
        this.birthdate = birthdate;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }
}


