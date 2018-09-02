package com.example.thedoctor46.letschat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    ListView listViewNotes;


    private Button b2;
    private EditText e1;
    private EditText e2;

    List<Notes> notesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mDatabase = FirebaseDatabase.getInstance().getReference("data");

        e1 = (EditText)findViewById(R.id.title1);
        e2 = (EditText)findViewById(R.id.main_note);

        b2 = (Button)findViewById(R.id.save_data);

        listViewNotes =(ListView)findViewById(R.id.listViewNote);

        notesList = new ArrayList<>();
        b2.setOnClickListener(this);


        firebaseAuth =  FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null)
        {

            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

    }
/*    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onvalue","failed");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("oncreate","failed");
                notesList.clear();
                for(DataSnapshot notesSnapshot: dataSnapshot.getChildren())
                {

                    Notes notes = notesSnapshot.getValue(Notes.class);

                    notesList.add(notes);
                }

                Log.e("onchange","failed");
                NotesList adapter = new NotesList(ProfileActivity.this, notesList);

                listViewNotes.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.main_logout)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }

        else if(item.getItemId() == R.id.view_notes)
        {


            startActivity(new Intent(this,NotesDisplay.class));

        }
        return true;
    }


    @Override
    public void onClick(View view) {



            submitPost();

    }

    private void submitPost()
    {
        String send_title = e1.getText().toString().trim();
        String send_note = e2.getText().toString().trim();

        if(TextUtils.isEmpty(send_title))
        {
            Toast.makeText(getApplicationContext(),"Enter a title",Toast.LENGTH_LONG).show();

        }
       

      else  if(TextUtils.isEmpty(send_note))

        {
            Toast.makeText(getApplicationContext(),"Enter a note",Toast.LENGTH_LONG).show();

        }
        else {
            String id = mDatabase.push().getKey();

            Notes notes = new Notes(id, send_title, send_note);

            mDatabase.child(id).setValue(notes);
            Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
            e1.setText("");
            e2.setText("");
        }
    }




}
