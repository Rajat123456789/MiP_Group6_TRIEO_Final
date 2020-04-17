package com.example.notesandreminders;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.notesandreminders.model.Note;
import com.example.notesandreminders.model.Reminder;

@Database(entities = {Note.class, Reminder.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();
}
