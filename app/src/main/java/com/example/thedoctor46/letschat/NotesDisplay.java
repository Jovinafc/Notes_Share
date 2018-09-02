package com.example.thedoctor46.letschat;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotesDisplay extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    ListView listViewNotes;
    List<Notes> notesList;

    private  Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_display);


        mDatabase = FirebaseDatabase.getInstance().getReference("data");
        listViewNotes =(ListView)findViewById(R.id.listViewNote);
        Button b1 = (Button)findViewById(R.id.update_button);
        Button b2 =(Button)findViewById(R.id.delete_button) ;



        notesList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                notesList.clear();
                for (DataSnapshot notesSnapshot : dataSnapshot.getChildren()) {

                    Notes notes = notesSnapshot.getValue(Notes.class);

                    notesList.add(notes);
                }


                NotesList adapter = new NotesList(NotesDisplay.this, notesList);

                listViewNotes.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Notes notes = notesList.get(i);
                showUpdateDialog(notes.getUserId(),notes.getTitle(),notes.getNote());

                return false;
            }
        });

    }


private void showUpdateDialog(final String userId, final String title , final String note)
{
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    LayoutInflater inflater = getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.update_dialog,null);
    dialogBuilder.setView(dialogView);

    final EditText editTextTitle = (EditText)dialogView.findViewById(R.id.updateTitle);
    final EditText editTextNote = (EditText)dialogView.findViewById(R.id.updateNote);
    final Button buttonUpdate = (Button)dialogView.findViewById(R.id.update_button);
    final Button buttonDelete = (Button)dialogView.findViewById(R.id.delete_button);

    buttonUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title = editTextTitle.getText().toString().trim();
            String note = editTextNote.getText().toString().trim();

            if(TextUtils.isEmpty(title) && TextUtils.isEmpty(note))
            {
                editTextTitle.setError("Title Required");
                editTextNote.setError("Note Required");

                return;
            }
            else
            {
                editTextTitle.setText("");
                editTextNote.setText("");
                updateNote(userId, title, note);
            }

        }
    });

    dialogBuilder.setTitle( "Updating Note :"+ " "+ title);

    AlertDialog alertDialog = dialogBuilder.create();
    alertDialog.show();



    buttonDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            deleteNotes(userId);
        }
    });
}

public void deleteNotes(String userId)
{
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data").child(userId);

    databaseReference.removeValue();

    Toast.makeText(this,"Note Deleted Successfully",Toast.LENGTH_LONG).show();

}

private boolean updateNote(String id, String title, String note)
{

DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("data").child(id);

Notes notes = new Notes(id,title,note);
databaseReference.setValue(notes);

    Toast.makeText(this,"Note Updated Successfully",Toast.LENGTH_LONG).show();

return true;
}




}
