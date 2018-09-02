package com.example.thedoctor46.letschat;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NotesList extends ArrayAdapter<Notes>
{
    private Activity context;
    private List<Notes> notesList1;

    public NotesList(  Activity context, List<Notes> notesList1) {
        super(context, R.layout.list_layout,notesList1);
        this.context = context;
        this.notesList1 = notesList1;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.recieve_title);
        TextView textViewNote = (TextView) listViewItem.findViewById(R.id.receive_note);

        Notes notes = notesList1.get(position);

        textViewTitle.setText(notes.getTitle());
        textViewNote.setText(notes.getNote());
        return listViewItem;

    }
}
