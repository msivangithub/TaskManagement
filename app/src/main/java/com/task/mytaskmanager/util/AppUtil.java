package com.task.mytaskmanager.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GhanaShyam on 7/9/2016.
 */
public class AppUtil {
    public static String ImeId = "";
    public static String TaskFromId="";
    public static String TaskToId="";
    public static String ExpStartDate="";
    public static String ExpEndDate="";
    public static String ActStartDate="";
    public static String ActEndDate="";
    public static String TaskStatus="";
    public  static String TaskHeading="";
    public static String TaskDes="";
    public  static String priority="";

    public static String getImeId() {
        return ImeId;
    }

    public static void setImeId(String imeId) {
        ImeId = imeId;
    }

    public static String getTaskFromId() {
        return TaskFromId;
    }

    public static void setTaskFromId(String taskFromId) {
        TaskFromId = taskFromId;
    }

    public static String getTaskToId() {
        return TaskToId;
    }

    public static void setTaskToId(String taskToId) {
        TaskToId = taskToId;
    }

    public static String getExpStartDate() {
        return ExpStartDate;
    }

    public static void setExpStartDate(String expStartDate) {
        ExpStartDate = expStartDate;
    }

    public static String getExpEndDate() {
        return ExpEndDate;
    }

    public static void setExpEndDate(String expEndDate) {
        ExpEndDate = expEndDate;
    }

    public static String getActStartDate() {
        return ActStartDate;
    }

    public static void setActStartDate(String actStartDate) {
        ActStartDate = actStartDate;
    }

    public static String getActEndDate() {
        return ActEndDate;
    }

    public static void setActEndDate(String actEndDate) {
        ActEndDate = actEndDate;
    }

    public static String getTaskStatus() {
        return TaskStatus;
    }

    public static void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public static String getTaskHeading() {
        return TaskHeading;
    }

    public static void setTaskHeading(String taskHeading) {
        TaskHeading = taskHeading;
    }

    public static String getTaskDes() {
        return TaskDes;
    }

    public static void setTaskDes(String taskDes) {
        TaskDes = taskDes;
    }

    public static String getPriority() {
        return priority;
    }

    public static void setPriority(String priority) {
        AppUtil.priority = priority;
    }

    public static String getCurrentTimeStamp() {

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}
