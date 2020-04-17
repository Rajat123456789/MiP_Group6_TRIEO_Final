package com.example.notesandreminders.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.notesandreminders.R;
import com.example.notesandreminders.model.Note;
import com.example.notesandreminders.ui.NotesClickListener;

import java.sql.Timestamp;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {


    private List<Note> mNotes;
    private NotesClickListener mListener;
    public NotesAdapter(List<Note> mNotes, NotesClickListener mListener) {
        this.mListener=mListener;
        this.mNotes = mNotes;
    }


    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  =LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_reminder, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, final int position) {
        holder.mNoteTitle.setText(mNotes.get(position).getTitle());
        holder.mNoteDescription.setText(mNotes.get(position).getDescription());
        Timestamp ts = new Timestamp(mNotes.get(position).getCreatedOn());
        holder.mNoteDate.setText(String.valueOf(ts));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNotesClick(mNotes.get(position));


            }
        });

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView mNoteTitle, mNoteDescription, mNoteDate;
        private ConstraintLayout container;

        public NotesViewHolder(View view) {
            super(view);
            mNoteTitle = view.findViewById(R.id.noteTitle);
            mNoteDescription = view.findViewById(R.id.noteDescription);
            mNoteDate = view.findViewById(R.id.noteDate);
            container = view.findViewById(R.id.container);
        }
    }
}
