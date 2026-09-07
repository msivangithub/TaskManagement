package com.office.taskmanager.Pojo;

/**
 * Created by vbsystem on 1/19/2017.
 */

public class Inward {

    private String name = "";
    private int id_ = 0;
    private String BF = "0";
    private String RCD = "0";
    private String OPEN = "0";
    private String BALANCE = "0";
    private String TotalBalance = "0";

/*    public Inward(String name, int id_) {
        this.name = name;
        this.id_ = id_;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
    }

    public String getBF() {
        return BF;
    }

    public void setBF(String BF) {
        this.BF = BF;
    }

    public String getRCD() {
        return RCD;
    }

    public void setRCD(String RCD) {
        this.RCD = RCD;
    }

    public String getOPEN() {
        return OPEN;
    }

    public void setOPEN(String OPEN) {
        this.OPEN = OPEN;
    }

    public String getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(String BALANCE) {
        this.BALANCE = BALANCE;
    }

    public String getTotalBalance() {
        return TotalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        TotalBalance = totalBalance;
    }
}
