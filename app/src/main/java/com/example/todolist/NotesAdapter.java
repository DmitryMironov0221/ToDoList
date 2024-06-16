package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        viewHolder.textViewNote.setText(note.getText());
        int colorResID;
        switch (note.getPriority()){
            case 0:
                colorResID = android.R.color.holo_green_dark;
                break;
            case 1:
                colorResID = android.R.color.holo_orange_dark;
                break;
            default:
                colorResID = android.R.color.holo_red_dark;
        }
        int color = ContextCompat.getColor(viewHolder.itemView.getContext(),colorResID);
        viewHolder.textViewNote.setBackgroundColor(color);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoteClickListener != null){
                onNoteClickListener.OnNoteClick(note);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNote;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.textViewNote);
        }
    }
    interface OnNoteClickListener{
        void OnNoteClick(Note note);
    }
}

