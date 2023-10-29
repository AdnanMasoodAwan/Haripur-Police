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
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.context.AttributeContext;

import org.w3c.dom.Text;

public class UserSignInActivity extends AppCompatActivity {

    TextView DontHaveAccount;
    Button SignIn;
    EditText Email,Password;
    FirebaseAuth UserAuth;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);


        UserAuth=FirebaseAuth.getInstance();
        // currentUserId=Auth.getCurrentUser().getUid();


        DontHaveAccount=(TextView) findViewById(R.id.DonthaveAccount);
        Password=(EditText)findViewById(R.id.UserSignInPassword);
        Email=(EditText)findViewById(R.id.UserSignInEmail);
        SignIn=(Button)findViewById(R.id.UserSignInButton);




        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                UserValidateInfo();
            }
        });



        DontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(),UserSignUpActivity.class);
                startActivity(intent);

            }
        });
    }


    private void UserValidateInfo()
    {

        String email=Email.getText().toString();
        String password=Password.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_SHORT).show();
        }

        else
        {
            UserAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(), "User login  SuccessFully", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
       // currentUserId=FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(currentUser!=null)
        {

            Intent intent=new Intent(getApplicationContext(),UserDashboardActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else
        {

        }




    }


}