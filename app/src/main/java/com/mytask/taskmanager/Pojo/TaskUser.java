package com.mytask.taskmanager.Pojo;

/**
 * Created by GhanaShyam on 7/16/2016.
 */
public class TaskUser {

    String FirstName;
    private String Uid;
    private String UserLevel;
    private String UserRole;
    private String Image;
    private String Emailid;
    private String Phone;
    private String City;


    public String getEmailid() {
        return Emailid;
    }

    public void setEmailid(String emailid) {
        Emailid = emailid;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }




    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(String userLevel) {
        UserLevel = userLevel;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }
}
