package com.example.thedoctor46.letschat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView t1;
    private EditText e1;
    private EditText e2;
    private Button b1;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

        }



        t1= (TextView)findViewById(R.id.SignInfo);
        e1 = (EditText)findViewById(R.id.editText2);
        e2 = (EditText)findViewById(R.id.editText3);
        b1 = (Button)findViewById(R.id.login);



    t1.setOnClickListener(this);
    b1.setOnClickListener(this);
    progressDialog = new ProgressDialog(this);

    }
    public void userLogin()
    {
        String email = e1.getText().toString().trim();
        String password= e2.getText().toString().trim();
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


        progressDialog.setMessage("Logging In User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            progressDialog.dismiss();
            if(task.isSuccessful())
            {
                finish();
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
            else {
                Toast.makeText(getApplicationContext(),"Invalid Details",Toast.LENGTH_LONG).show();

            }
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view == t1)
        {   finish();
            startActivity(new Intent(this,MainActivity.class));

        }
        if(view == b1)
        {
            userLogin();

        }

    }
}
