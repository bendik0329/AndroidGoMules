package com.amits.gomules.Entity;

public class UserEntity {

    private int ID;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private boolean Active;
    private boolean userExists;


    public UserEntity(int ID, String firstName, String lastName, String email, String password, boolean active) {
        this.ID = ID;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Password = password;
        Active = active;
    }

    public UserEntity() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    public boolean isUserExists() {
        return userExists;
    }

    public void setUserExists(boolean userExists) {
        this.userExists = userExists;
    }
}
