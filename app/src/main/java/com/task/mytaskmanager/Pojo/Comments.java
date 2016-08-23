package com.task.mytaskmanager.Pojo;

/**
 * Created by GhanaShyam on 8/16/2016.
 */
public class Comments {

    private String Cid;
    private String Comments;
    private String ComtId;
    private String ExpEndDate;
    private String ExpStartDate;
    private String TaskFromId;
    private String TaskToId;
    private String UserRole;
    private String video;
private String Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getComtId() {
        return ComtId;
    }

    public void setComtId(String comtId) {
        ComtId = comtId;
    }

    public String getExpEndDate() {
        return ExpEndDate;
    }

    public void setExpEndDate(String expEndDate) {
        ExpEndDate = expEndDate;
    }

    public String getExpStartDate() {
        return ExpStartDate;
    }

    public void setExpStartDate(String expStartDate) {
        ExpStartDate = expStartDate;
    }

    public String getTaskFromId() {
        return TaskFromId;
    }

    public void setTaskFromId(String taskFromId) {
        TaskFromId = taskFromId;
    }

    public String getTaskToId() {
        return TaskToId;
    }

    public void setTaskToId(String taskToId) {
        TaskToId = taskToId;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
