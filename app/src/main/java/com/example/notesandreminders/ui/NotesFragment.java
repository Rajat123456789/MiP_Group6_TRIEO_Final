package com.example.notesandreminders.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesandreminders.R;
import com.example.notesandreminders.adapter.NotesAdapter;
import com.example.notesandreminders.model.DbManager;
import com.example.notesandreminders.model.Note;
import com.example.notesandreminders.model.Reminder;

public class NotesFragment extends Fragment {

    private RecyclerView mNoteRecyclerView;
    private ConstraintLayout emptyNotesContainer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes,container,false);
         mNoteRecyclerView = view.findViewById(R.id.notesRecyclerView);
        emptyNotesContainer = view.findViewById(R.id.emptyNotesContainer);
         populateData();
        return view;
    }


    private void populateData()
    {
        if(DbManager.getInstance().appDao().getNotes().size()!=0) {
            mNoteRecyclerView.setVisibility(View.VISIBLE);
            NotesAdapter mAdapter = new NotesAdapter(DbManager.getInstance().appDao().getNotes(), new NotesClickListener() {
                @Override
                public void onNotesClick(Note note) {
                    Intent intent = AddActivity.getStartIntent(getContext(),2,note);
                    startActivity(intent);
                }

                @Override
                public void onReminderClick(Reminder reminder) {

                }
            });
            mNoteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mNoteRecyclerView.setAdapter(mAdapter);
        }else
        {
            emptyNotesContainer.setVisibility(View.VISIBLE);
            mNoteRecyclerView.setVisibility(View.GONE);
        }
    }

}
