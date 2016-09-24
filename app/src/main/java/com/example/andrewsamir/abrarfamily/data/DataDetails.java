package com.example.andrewsamir.abrarfamily.data;

/**
 * Created by Andrew Samir on 1/28/2016.
 */
public class DataDetails {

    String Name;
    String RakmManzl;
    String Street;
    String Floor;
    String Homenumber;
    String Description;
    String AnotherAdd;
    String baba;
    String mama;
    String phone;
    String birthday;
    String serial;


    public DataDetails(String name, String rakmManzl, String street, String floor,
                       String homenumber, String description, String anotherAdd, String baba,
                       String mama, String phone, String birthday, String photo, String serial) {
        Name = name;
        RakmManzl = rakmManzl;
        Street = street;
        Floor = floor;
        Homenumber = homenumber;
        Description = description;
        AnotherAdd = anotherAdd;
        this.baba = baba;
        this.mama = mama;
        this.phone = phone;
        this.birthday = birthday;
        this.serial = serial;
        this.photo = photo;
    }

    String photo;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRakmManzl() {
        return RakmManzl;
    }

    public void setRakmManzl(String rakmManzl) {
        RakmManzl = rakmManzl;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getHomenumber() {
        return Homenumber;
    }

    public void setHomenumber(String homenumber) {
        Homenumber = homenumber;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAnotherAdd() {
        return AnotherAdd;
    }

    public void setAnotherAdd(String anotherAdd) {
        AnotherAdd = anotherAdd;
    }

    public String getBaba() {
        return baba;
    }

    public void setBaba(String baba) {
        this.baba = baba;
    }

    public String getMama() {
        return mama;
    }

    public void setMama(String mama) {
        this.mama = mama;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
