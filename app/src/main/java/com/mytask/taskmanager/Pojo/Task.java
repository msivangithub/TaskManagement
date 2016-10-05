package com.mytask.taskmanager.Pojo;

/**
 * Created by GhanaShyam on 7/20/2016.
 */
public class Task {
    private int taskId;
    private String taskComment;
    private String video;
    private String ImeId;
    private String TaskFromId;
    private String TaskToId;
    private String ExpStartDate;
    private String ExpEndDate;
    private String ActStartDate;
    private String ActEndDate;
    private String TaskStatus;
    private String TaskHeading;
    private String TaskDes;
    private String priority;
    private String StartTime;
    private String EndTime;
    private String Uname;
    private String profile;



    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }



    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(String taskComment) {
        this.taskComment = taskComment;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }


    public String getImeId() {
        return ImeId;
    }

    public void setImeId(String imeId) {
        ImeId = imeId;
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

    public String getExpStartDate() {
        return ExpStartDate;
    }

    public void setExpStartDate(String expStartDate) {
        ExpStartDate = expStartDate;
    }

    public String getExpEndDate() {
        return ExpEndDate;
    }

    public void setExpEndDate(String expEndDate) {
        ExpEndDate = expEndDate;
    }

    public String getActStartDate() {
        return ActStartDate;
    }

    public void setActStartDate(String actStartDate) {
        ActStartDate = actStartDate;
    }

    public String getActEndDate() {
        return ActEndDate;
    }

    public void setActEndDate(String actEndDate) {
        ActEndDate = actEndDate;
    }

    public String getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public String getTaskHeading() {
        return TaskHeading;
    }

    public void setTaskHeading(String taskHeading) {
        TaskHeading = taskHeading;
    }

    public String getTaskDes() {
        return TaskDes;
    }

    public void setTaskDes(String taskDes) {
        TaskDes = taskDes;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
