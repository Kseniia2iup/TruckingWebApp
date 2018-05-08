package ru.tsystems.javaschool.model.enums;

public enum UserProfileType {
    USER("USER"),
    DBA("DRIVER"),
    ADMIN("ADMIN");

    String userProfileType;

    UserProfileType(String userProfileType){
        this.userProfileType = userProfileType;
    }

    public String getUserProfileType(){
        return userProfileType;
    }

}