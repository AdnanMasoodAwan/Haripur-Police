package com.example.haripurpolice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class DesiredUserProfileActivity extends AppCompatActivity
{
    Toolbar toolbar;
    TextView Name,Email,CNIC;
    Button Save;
    private DatabaseReference AdminRef;
    String CurrentUserId,name,email,cnic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desired_user_profile);


        Name=(TextView) findViewById(R.id.DesiredUserName);
        Email=(TextView) findViewById(R.id.DesiredUsermail);
        CNIC=(TextView) findViewById(R.id.DesiredUserNIC);
        Save=(Button) findViewById(R.id.DesiredSaveUserProfileButton);

        name=(String)getIntent().getExtras().get("name");
        email=(String)getIntent().getExtras().get("email");
        cnic=(String)getIntent().getExtras().get("cnic");


        Name.setText(name);
        Email.setText(email);
        CNIC.setText(cnic);





        toolbar=(Toolbar)findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}