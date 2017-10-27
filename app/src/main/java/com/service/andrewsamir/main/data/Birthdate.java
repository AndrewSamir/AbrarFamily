package com.service.andrewsamir.abrarfamily.data;

/**
 * Created by andre on 20-Oct-16.
 */

public class Birthdate {

    String name,birthdate;
    String id;

    public Birthdate(String name, String birthdate ,String id) {
        this.name = name;
        this.birthdate = birthdate;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }
}


