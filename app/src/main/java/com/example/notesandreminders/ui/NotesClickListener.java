package com.example.notesandreminders.ui;

import com.example.notesandreminders.model.Note;
import com.example.notesandreminders.model.Reminder;

public interface NotesClickListener  {


    void onNotesClick(Note note);


    void onReminderClick(Reminder reminder);
}
