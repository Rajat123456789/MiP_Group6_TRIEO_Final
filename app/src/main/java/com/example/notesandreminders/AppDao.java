package com.example.notesandreminders;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesandreminders.model.Note;
import com.example.notesandreminders.model.Reminder;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    void addNote(Note note);

    @Insert
    void addReminder(Reminder reminder);

    @Query("select * from notes")
    List<Note> getNotes();

    @Query("select * from reminders")
    List<Reminder> getReminders();

    @Update
    void updateNote(Note note);

    @Update
    void updateReminder(Reminder reminder);

    @Delete
    void deleteNote(Note note);

    @Delete
    void deleteReminder(Reminder reminder);


    @Query("DELETE FROM reminders")
    void nukeTable();

}
