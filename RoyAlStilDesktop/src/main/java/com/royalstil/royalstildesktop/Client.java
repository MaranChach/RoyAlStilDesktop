package com.royalstil.royalstildesktop;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Client {
    public SimpleIntegerProperty clid;
    public SimpleStringProperty firstName;
    private SimpleStringProperty secondName;
    private SimpleStringProperty login;
    private SimpleStringProperty password;
    private SimpleStringProperty phoneNumber;
    private SimpleStringProperty email;
    private Date birthDate;

    public Client(int id, String firstName, String secondName, String login, String password, String phoneNumber, String email, Date birthDate) {
        this.clid = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.secondName = new SimpleStringProperty(secondName);
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
        this.birthDate = birthDate;
    }

    public void setClid(int clid) {
        this.clid.set(clid);
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public int getClid() {
        return clid.get();
    }
    public String getFirstName(){
        return firstName.get();
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public String getLogin() {
        return login.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getSecondName() {
        return secondName.get();
    }
}
