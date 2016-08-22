package com.task.mytaskmanager.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.task.mytaskmanager.Pojo.Comments;
import com.task.mytaskmanager.Pojo.Task;
import com.task.mytaskmanager.Pojo.TaskBranches;
import com.task.mytaskmanager.Pojo.TaskUser;
import com.task.mytaskmanager.Pojo.User;
import com.task.mytaskmanager.Pojo.UserRoles;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by GhanaShyam on 7/9/2016.
 */
public class AppUtil {

    public static ArrayList<TaskBranches> branchesInfo = new ArrayList<>();

    public static ArrayList<TaskBranches> getBranchesInfo() {
        return branchesInfo;
    }

    public static void setBranchesInfo(ArrayList<TaskBranches> branchesInfo) {
        AppUtil.branchesInfo = branchesInfo;
    }

    /*Get Current POjo*/
    public static ArrayList<Comments> currentPojo = new ArrayList<>();
    public static ArrayList<Comments> getCurrentPojo() {
        return currentPojo;
    }
    public static void setCurrentPojo(ArrayList<Comments> currentPojo) {
        AppUtil.currentPojo = currentPojo;
    }

    /*Comments list*/
    public static ArrayList<String> currentComments = new ArrayList<>();

    public static ArrayList<String> getCurrentComments() {
        return currentComments;
    }

    public static void setCurrentComments(ArrayList<String> currentComments) {
        AppUtil.currentComments = currentComments;
    }

    /*UserRoles Info*/
    public static ArrayList<UserRoles> userRolesInfo = new ArrayList<>();

    public static ArrayList<UserRoles> getUserRolesInfo() {
        return userRolesInfo;
    }

    public static void setUserRolesInfo(ArrayList<UserRoles> userRolesInfo) {
        AppUtil.userRolesInfo = userRolesInfo;
    }


    public static ArrayList<Task> userArrayList = new ArrayList<>();
    public static ArrayList<TaskUser> seekUsers = new ArrayList<>();

    public static ArrayList<TaskUser> getSeekUsers() {
        return seekUsers;
    }

    public static void setSeekUsers(ArrayList<TaskUser> seekUsers) {
        AppUtil.seekUsers = seekUsers;
    }

    public static ArrayList<Task> deletedUserList = new ArrayList<>();

    public static ArrayList<Task> getDeletedUserList() {
        return deletedUserList;
    }

    public static void setDeletedUserList(ArrayList<Task> deletedUserList) {
        AppUtil.deletedUserList = deletedUserList;
    }

    public static ArrayList<Task> getUserArrayList() {
        return userArrayList;
    }

    public static void setUserArrayList(ArrayList<Task> userArrayList) {
        AppUtil.userArrayList = userArrayList;
    }

    public static String ImeId = "";
    public static String TaskFromId = "";
    public static String TaskToId = "";
    public static String ExpStartDate = "";
    public static String ExpEndDate = "";
    public static String ActStartDate = "";
    public static String ActEndDate = "";
    public static String TaskStatus = "";
    public static String TaskHeading = "";
    public static String TaskDes = "";
    public static String priority = "";
    public static String StartFromTime = "";
    public static String StartToTime = "";



    public static String getStartToTime() {
        return StartToTime;
    }

    public static void setStartToTime(String startToTime) {
        StartToTime = startToTime;
    }

    public static String getStartFromTime() {
        return StartFromTime;
    }

    public static void setStartFromTime(String startFromTime) {
        StartFromTime = startFromTime;
    }
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

    public static ArrayList<Task> removedTasks(ArrayList<Task> tasks, int id) {
        ArrayList<Task> returnedTasks = new ArrayList<>();
        for (int task = 0; task < tasks.size(); task++) {
            if (tasks.get(task).getTaskId() == id) {
                break;
            }
            returnedTasks.add(tasks.get(task));
        }
        return returnedTasks;
    }

    public static ArrayList<Task> removedTasksByTwoLists(ArrayList<Task> tasks, ArrayList<Task> tasks1) {


        ArrayList<Task> returnedTasks = new ArrayList<>();

        for (int i = 0; i < tasks1.size(); i++) {
            for (int task = 0; task < tasks.size(); task++) {
                if (tasks.get(task).getTaskId() == tasks1.get(i).getTaskId()) {
                    tasks.remove(task);
                }


            }

        }
        return tasks;
    }

    public static ArrayList<TaskUser> removedseekUsers(ArrayList<TaskUser> users, String taskId) {
        ArrayList<TaskUser> returnedTasks = new ArrayList<>();
        for (int task = 0; task < users.size(); task++) {
            if (users.get(task).getUid().equalsIgnoreCase(taskId)) {
                break;
            }
            returnedTasks.add(users.get(task));
        }
        return returnedTasks;

    }


}
