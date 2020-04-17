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
import com.example.notesandreminders.adapter.ReminderAdapter;
import com.example.notesandreminders.model.DbManager;
import com.example.notesandreminders.model.Note;
import com.example.notesandreminders.model.Reminder;

import java.util.ArrayList;
import java.util.List;

public class MissedFragment extends Fragment {

    private RecyclerView missedRecyclerView;
    private ConstraintLayout emptyNotesContainer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_missed,container,false);
        missedRecyclerView = view.findViewById(R.id.missedRecyclerView);
        emptyNotesContainer =view.findViewById(R.id.emptyNotesContainer);
        populateData();
        return view;
    }

    private void populateData()
    {
        List<Reminder> missedReminders = new ArrayList<>();
        if(DbManager.getInstance().appDao().getReminders().size()!=0) {

            for (int i = 0; i < DbManager.getInstance().appDao().getReminders().size(); i++) {
                if ((System.currentTimeMillis() > DbManager.getInstance().appDao().getReminders().get(i).getRemindOn()) &&
                        (!DbManager.getInstance().appDao().getReminders().get(i).isReminderSuccess())) {
                    missedReminders.add(DbManager.getInstance().appDao().getReminders().get(i));
                }
            }


        }
        else
        {
            emptyNotesContainer.setVisibility(View.VISIBLE);
            missedRecyclerView.setVisibility(View.GONE);
        }
        System.out.println("MISSED LIST"+missedReminders);
        if(missedReminders.size()!=0)
        {
            missedRecyclerView.setVisibility(View.VISIBLE);
            ReminderAdapter mAdapter = new ReminderAdapter(missedReminders, new NotesClickListener() {
                @Override
                public void onNotesClick(Note note) {
                }
                @Override
                public void onReminderClick(Reminder reminder) {
                    Intent intent = AddActivity.getStartIntent(getContext(),4,reminder);
                    startActivity(intent);
                }
            },2);
            missedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            missedRecyclerView.setAdapter(mAdapter);
        }    else
        {
            emptyNotesContainer.setVisibility(View.VISIBLE);
            missedRecyclerView.setVisibility(View.GONE);
        }
    }

}
