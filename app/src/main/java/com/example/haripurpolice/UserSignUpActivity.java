package com.example.haripurpolice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserSignUpActivity extends AppCompatActivity
{
    TextView AlreadyHaveAccount;
    Button SignUp;
    EditText Name,Email,Password,CNIC;
    FirebaseAuth Auth;
    String currentUserId,type;

    private DatabaseReference UsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);


        Auth=FirebaseAuth.getInstance();
       // currentUserId=Auth.getCurrentUser().getUid();
        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users");


        AlreadyHaveAccount=(TextView)findViewById(R.id.UserAlreadyhaveAccount);
        Name=(EditText)findViewById(R.id.UserName);
        Password=(EditText)findViewById(R.id.UserPassword);
        Email=(EditText)findViewById(R.id.UserEmail);
        CNIC=(EditText)findViewById(R.id.UserCNIC);
        SignUp=(Button)findViewById(R.id.UserSignupButton);


        SignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ValidateInfo();
            }

        });




        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(),UserSignInActivity.class);
                startActivity(intent);
            }
        });
    }


    private void ValidateInfo()
    {
        String name=Name.getText().toString();
        String email=Email.getText().toString();
        String password=Password.getText().toString();
        String cnic=CNIC.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(cnic))
        {
            Toast.makeText(getApplicationContext(), "Please Enter CNIC", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                currentUserId=Auth.getCurrentUser().getUid();

                                HashMap<String,Object> Adminsmap=new HashMap<>();
                                Adminsmap.put("currentuserid",currentUserId);
                                Adminsmap.put("name",name);
                                Adminsmap.put("email",email);
                                Adminsmap.put("password",password);
                                Adminsmap.put("cnic",cnic);
                                //Adminsmap.put(type,"User");

                                UsersRef.child(currentUserId)
                                        .updateChildren(Adminsmap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                Toast.makeText(getApplicationContext(), "Data Uploaded SuccessFully", Toast.LENGTH_SHORT).show();
                                            }
                                        });





                                Toast.makeText(getApplicationContext(), "Account Created SuccessFully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),UserDashboardActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                String MESSAGE=task.getException().toString();
                                Toast.makeText(getApplicationContext(), ""+MESSAGE, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }



    }
}