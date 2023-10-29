package com.example.haripurpolice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity
{
    Animation Top,Bottom;
    ImageView Photo;
    EditText InputPhoneField,InputCodeField;
    Button SendCode,VerifyCode;
    FirebaseAuth Auth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private  PhoneAuthProvider.ForceResendingToken token;
    private  String mVerificationId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        Auth=FirebaseAuth.getInstance();

        Top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        Bottom= AnimationUtils.loadAnimation(this,R.anim.bot_animation);

        Photo=(ImageView)findViewById(R.id.sart);
        InputPhoneField=(EditText)findViewById(R.id.Phone_Input);
        InputCodeField=(EditText)findViewById(R.id.Code_Input);
        SendCode=(Button)findViewById(R.id.verify_verification_code);
        VerifyCode=(Button)findViewById(R.id.verify_button);
        Photo.setAnimation(Top);



       SendCode.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View view)
           {
            String  PhoneNumber= InputPhoneField.getText().toString();

            if(TextUtils.isEmpty(PhoneNumber))
            {
                Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
            }
             else
            {
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                                .setPhoneNumber(PhoneNumber)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setActivity(PhoneLoginActivity.this)
                                .setCallbacks(mCallBacks)
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);

            }



           }

       });

       VerifyCode.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View view)
           {
               String Code=InputCodeField.getText().toString();

               if(TextUtils.isEmpty(Code))
               {
                   Toast.makeText(getApplicationContext(), "Please Enter Verification Code", Toast.LENGTH_SHORT).show();
               }
               else
               {
                       PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(mVerificationId,Code);
                      signInWithPhoneAuthCredential(phoneAuthCredential);
               }

           }

      });



        mCallBacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
            {

                 signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {
                Toast.makeText(getApplicationContext(),"Please Enter Correct Phone Number",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
            {
               // super.onCodeSent(s, forceResendingToken);

                mVerificationId=s;
                token=forceResendingToken;

                Toast.makeText(getApplicationContext(),"Code has been Sent,Check and Verify",Toast.LENGTH_LONG).show();

                InputPhoneField.setVisibility(View.INVISIBLE);
                SendCode.setVisibility(View.INVISIBLE);

                InputCodeField.setVisibility(View.VISIBLE);
                VerifyCode.setVisibility(View.VISIBLE);


            }


        };


    }





    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        Auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {

                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"User Verified",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(getApplicationContext(),AdminProfileActivity.class);
                                startActivity(intent);
                            }
                            else
                            {

                            }
                        }
                        else
                        {

                        }
                    }

                });
    }











}