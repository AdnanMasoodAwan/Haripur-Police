package com.example.haripurpolice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
{
    Button Admin,User;
    ImageView imageView;
    Animation Top,Bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Admin = (Button) findViewById(R.id.AdminButton);
        User = (Button) findViewById(R.id.UserButton);
        imageView = (ImageView) findViewById(R.id.imageViewA);

        Top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Paper.init(this);


        imageView.setAnimation(Top);


        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminSignupActivity.class);
                startActivity(intent);

            }
        });

        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminSignInActivity.class);
                startActivity(intent);
            }
        });


        String UserPhoneKey = Paper.book().read(Prevelant.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevelant.UserPasswordKey);
        String UserTypeKey=Paper.book().read(Prevelant.UserTypeKey);


        if (UserPhoneKey != "" && UserPasswordKey != "" && UserTypeKey !="")
        {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPhoneKey, UserPasswordKey,UserTypeKey);

            }

        }


    }











    private void AllowAccess(String userPhoneKey, String userPasswordKey,String userTypeKey)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                String tpye= snapshot.child("Users").child(userPhoneKey).child("type").getValue().toString();


                if (snapshot.child("Users").child(userPhoneKey).exists() &&  tpye.equals("Admin") )
                {
                    Users users = snapshot.child("Users").child(userPhoneKey).getValue(Users.class);

                    if (users.getType().equals(userTypeKey))
                    {

                    if (users.getPhoneno().equals(userPhoneKey))
                    {

                        if (users.getPassword().equals(userPasswordKey))
                        {

                                Toast.makeText(MainActivity.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, AdminPanelActivity.class);
                                Prevelant.currentOnlineUser = users;
                                startActivity(intent);
                            }
                        }
                    }
                }
                else
                {

                    if(snapshot.child("Users").child(userPhoneKey).exists()  &&  tpye.equals("User"))
                    {
                        Users users = snapshot.child("Users").child(userPhoneKey).getValue(Users.class);

                        if(users.getType().equals(userTypeKey))
                        {
                        if(users.getPhoneno().equals(userPhoneKey))
                        {


                            if(users.getPassword().equals(userPasswordKey))
                            {

                                    Toast.makeText( MainActivity.this, "Please wait User, you are already logged in...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent( MainActivity.this, UserDashboardActivity.class);
                                    Prevelant.currentOnlineUser = users;
                                    startActivity(intent);

                                }

                            }

                        }




                    }



                }








            }
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


    }







}










