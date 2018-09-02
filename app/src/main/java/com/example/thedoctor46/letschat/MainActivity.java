package com.example.thedoctor46.letschat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public  class MainActivity extends AppCompatActivity implements View.OnClickListener {
private     EditText e1;
private     EditText e2;
private     Button b1;
private     TextView t1;
private ProgressDialog progressDialog;
private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = (EditText) findViewById(R.id.editText2);
        e2 = (EditText) findViewById(R.id.editText3);
        b1 = (Button) findViewById(R.id.register);
        t1 = (TextView) findViewById(R.id.LoginInfo);
    firebaseAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        if(firebaseAuth.getCurrentUser() !=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }
        b1.setOnClickListener(this);
        t1.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }
        private void registerUser()
    {

        String email = e1.getText().toString().trim();
        String password = e2.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(getApplicationContext(),"Enter your email",Toast.LENGTH_LONG).show();
        return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(getApplicationContext(),"Enter your password",Toast.LENGTH_LONG).show();
        return;
        }


        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                    Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_LONG).show();


                }
                else{
                    Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void loginUser()
    {

    }

    @Override
    public void onClick(View view)
    {
        if(view == b1)
        {
            registerUser();
        }
        if(view == t1)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }


    }


}