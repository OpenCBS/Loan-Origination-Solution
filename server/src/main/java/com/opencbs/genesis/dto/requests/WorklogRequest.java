package com.opencbs.genesis.dto.requests;

import java.util.Date;

/**
 * Created by alopatin on 16-May-17.
 */
public class WorklogRequest {
    private String note;
    private double spentHours;
    private Date enteredDate;

    public String getNote() {return  this.note;}
    public void setNote(String note) {this.note = note;}

    public double getSpentHours() {return  this.spentHours;}
    public void setSpentHours(double spentHours) { this.spentHours = spentHours;}

    public Date getEnteredDate(){ return  this.enteredDate;}
    public void setEnteredDate(Date enteredDate) { this.enteredDate = enteredDate;}
}
