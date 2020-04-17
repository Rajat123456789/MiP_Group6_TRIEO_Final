package com.example.notesandreminders.model;

import android.content.Context;

import androidx.room.Room;

import com.example.notesandreminders.AppDao;
import com.example.notesandreminders.AppDatabase;

public class DbManager {
    private static DbManager instance;

    private AppDatabase database;

    private DbManager() {

    }

    public static DbManager getInstance() {
        if (instance == null)
            instance = new DbManager();
        return instance;
    }

    public void initializeDatabase(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, "app_database")
                .allowMainThreadQueries()
                .build();
    }

    public AppDao appDao() {
        return database.appDao();
    }
}
