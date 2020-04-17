package com.example.notesandreminders.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "missed")
public class Missed implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private  String title;
    private  String description;
    private  Long createdOn;
    private  Long remindOn;
    private boolean MissedSuccess = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public Long getRemindOn() {
        return remindOn;
    }

    public void setRemindOn(Long remindOn) {
        this.remindOn = remindOn;
    }

    public boolean isMissedSuccess() {
        return MissedSuccess;
    }

    public void setMissedSuccess(boolean missedSuccess) {
        MissedSuccess = missedSuccess;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                ", remindOn=" + remindOn +
                ", isReminderSuccess=" + isMissedSuccess()+
                '}';
    }
}
