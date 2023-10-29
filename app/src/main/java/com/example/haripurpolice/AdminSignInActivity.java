package com.example.haripurpolice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.rpc.context.AttributeContext;

import io.paperdb.Paper;

public class AdminSignInActivity extends AppCompatActivity
{

    Button SignIn;
    EditText Phone,Password;
    FirebaseAuth Auth,Auth1;
    String currentUserId;
    int CheckRadioButtonId;
    String CheckRadioButton;
    DatabaseReference databaseReference;

    TextView donthaveaccount;
    String Tpye="Users";


    RadioGroup radioGroup;
    RadioButton Admin,User;
    CheckBox chkBoxRememberMe;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);



        Auth=FirebaseAuth.getInstance();
        Auth1=FirebaseAuth.getInstance();
        //currentUserId=Auth.getCurrentUser().getUid();


        donthaveaccount=(TextView) findViewById(R.id.DonthaveAccount);
        Password=(EditText)findViewById(R.id.AdminSignInPassword);
        Phone=(EditText)findViewById(R.id.AdminSignInEmail);
        SignIn=(Button)findViewById(R.id.AdminSignInButton);
        radioGroup=(RadioGroup)findViewById(R.id.SignInRadioGroup);
        Admin=(RadioButton)findViewById(R.id.SignInAdmin);
        User=(RadioButton)findViewById(R.id.SignInUser);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);






        SignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CheckRadioButtonId=radioGroup.getCheckedRadioButtonId();
                switch (CheckRadioButtonId)
                {
                    case  R.id.SignInAdmin:

                        AdminValidateInfo();

                        break;

                    case R.id.SignInUser:

                        UserValidateInfo();

                        break;
                }


            }
        });


        donthaveaccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AdminSignupActivity.class);
                startActivity(intent);
            }
        });





    }





    private void AdminValidateInfo()
    {

        String phone=Phone.getText().toString();
        String password=Password.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Phone", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_SHORT).show();
        }

        else
        {


            if(chkBoxRememberMe.isChecked())
            {
                Paper.book().write(Prevelant.UserPhoneKey,phone);
                Paper.book().write(Prevelant.UserPasswordKey,password);
                Paper.book().write(Prevelant.UserTypeKey,"Admin");
            }

            final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {

                     if(snapshot.child(Tpye).child(phone).exists())
                     {
                         Users users=snapshot.child(Tpye).child(phone).getValue(Users.class);

                         if(users.getPhoneno().equals(phone))
                         {
                            if(users.getPassword().equals(password))
                            {
                                Toast.makeText(getApplicationContext(), "You are Logged In Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),AdminPanelActivity.class);
                                startActivity(intent);

                            }

                         }


                     }
                     else
                     {
                         Toast.makeText(getApplicationContext(), "This User "+phone+" not Exsits", Toast.LENGTH_SHORT).show();
                     }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }

            });




        }



    }





    private void UserValidateInfo()
    {

        String phone=Phone.getText().toString();
        String password=Password.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Phone", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_SHORT).show();
        }

        else
        {

            if(chkBoxRememberMe.isChecked())
            {
                Paper.book().write(Prevelant.UserPhoneKey,phone);
                Paper.book().write(Prevelant.UserPasswordKey,password);
                Paper.book().write(Prevelant.UserTypeKey,"User");
            }


            final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                  if(snapshot.child(Tpye).child(phone).exists())
                  {
                      Users users=snapshot.child(Tpye).child(phone).getValue(Users.class);

                      if(users.getPhoneno().equals(phone))
                      {
                          if(users.getPassword().equals(password))
                          {
                              if(users.getType().equals("User"))
                              {

                                  Toast.makeText(getApplicationContext(), "You are Logged In Successfully", Toast.LENGTH_SHORT).show();
                                  Intent intent=new Intent(getApplicationContext(),UserDashboardActivity.class);
                                  startActivity(intent);

                              }


                          }
                      }

                  }
                  else
                  {
                      Toast.makeText(getApplicationContext(), "This User "+phone+" not Exsits", Toast.LENGTH_SHORT).show();
                  }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }

            });






        }



    }


















    @Override
    protected void onStop()
    {
        super.onStop();

        Toast.makeText(getApplicationContext(), "Activity Stopped", Toast.LENGTH_SHORT).show();
    }





}




