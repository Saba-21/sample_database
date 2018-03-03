package com.example.pc.sqliteexample;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PersonModel extends RealmObject {

    private String firstName;

    private String lastName;

    private String birthDate;

    private String gender;

    @PrimaryKey
    private int idNumber;

    String getFirstName() {
        return firstName;
    }

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String getLastName() {
        return lastName;
    }

    void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String getBirthDate() {
        return birthDate;
    }

    void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    String getGender() {
        return gender;
    }

    void setGender(String gender) {
        this.gender = gender;
    }

    int getIdNumber() {
        return idNumber;
    }

    void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }
}
