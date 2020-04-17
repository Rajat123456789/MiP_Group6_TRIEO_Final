package com.example.notesandreminders.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesandreminders.R;
import com.example.notesandreminders.model.Reminder;
import com.example.notesandreminders.ui.NotesClickListener;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {


    private List<Reminder> mReminder;
    private NotesClickListener mListener;
    private DateFormat ds = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private int mode;
    public ReminderAdapter(List<Reminder> mReminder, NotesClickListener mListener, int mode) {
        this.mListener=mListener;
        this.mReminder = mReminder;
        this.mode=mode;
    }


    @NonNull
    @Override
    public ReminderAdapter.ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_reminder, parent, false);
        return new ReminderAdapter.ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ReminderViewHolder holder, final int position) {
        holder.reminderSetON.setVisibility(View.VISIBLE);
        holder.mNoteTitle.setText(mReminder.get(position).getTitle());
        holder.mNoteDescription.setText(mReminder.get(position).getDescription());
        Timestamp ts = new Timestamp(mReminder.get(position).getCreatedOn());
        holder.mNoteDate.setText(String.valueOf(ts));
        if(mode==1) {
            holder.reminderSetON.setText(String.format("Reminder Set For %s",String.valueOf(ds.format(mReminder.get(position).getRemindOn()))));
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onReminderClick(mReminder.get(position));
                }
            }
            );
        }
        else if (mode ==2)
        {
            holder.reminderSetON.setText(String.format("Missed Reminder On %s",String.valueOf(ds.format(mReminder.get(position).getRemindOn()))));
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onReminderClick(mReminder.get(position));

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mReminder.size();
    }

    static class ReminderViewHolder extends RecyclerView.ViewHolder {
        private TextView mNoteTitle, mNoteDescription, mNoteDate,reminderSetON;
        private ConstraintLayout container;

        public ReminderViewHolder(View view) {
            super(view);
            mNoteTitle = view.findViewById(R.id.noteTitle);
            mNoteDescription = view.findViewById(R.id.noteDescription);
            mNoteDate = view.findViewById(R.id.noteDate);
            container = view.findViewById(R.id.container);
            reminderSetON =view.findViewById(R.id.reminderSetON);

        }
    }
}
