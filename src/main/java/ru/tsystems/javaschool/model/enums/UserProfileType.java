package ru.tsystems.javaschool.model.enums;

public enum UserProfileType {
    USER("USER"),
    DBA("DRIVER"),
    ADMIN("ADMIN");

    String type;

    UserProfileType(String type){
        this.type = type;
    }

    public String getUserProfileType(){
        return type;
    }

}