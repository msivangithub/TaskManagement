package com.task.mytaskmanager.Pojo;

/**
 * Created by GhanaShyam on 9/12/2016.
 */
public class TaskReport {

    private String ActEndDate;
    private String ActStartDate;
    private String TaskHeading;
    private String TaskStatus;
    private String cid;

    public String getActEndDate() {
        return ActEndDate;
    }

    public void setActEndDate(String actEndDate) {
        ActEndDate = actEndDate;
    }

    public String getActStartDate() {
        return ActStartDate;
    }

    public void setActStartDate(String actStartDate) {
        ActStartDate = actStartDate;
    }

    public String getTaskHeading() {
        return TaskHeading;
    }

    public void setTaskHeading(String taskHeading) {
        TaskHeading = taskHeading;
    }

    public String getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
