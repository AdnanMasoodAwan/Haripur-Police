package com.example.haripurpolice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;

public class UserDashboardActivity extends AppCompatActivity
{
    CardView cardCar;
    CardView cardCycle;
    CardView cardProfile;
    CardView cardLogout;
    CardView cardPhone;
    CardView cardBike;

    FirebaseAuth Auth;
    ProgressDialog progressDialog;
    FirebaseUser CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);


        Auth = FirebaseAuth.getInstance();
        //CurrentUser = Auth.getCurrentUser();
        // CurrentUser= Auth.getCurrentUser();



        cardCar = (CardView) findViewById(R.id.Usercar);
        cardCycle = (CardView) findViewById(R.id.Usercycle);
        cardProfile = (CardView) findViewById(R.id.Userprofile);
        cardLogout = (CardView) findViewById(R.id.Userlogout);
        cardPhone = (CardView) findViewById(R.id.Userphone);
        cardBike = (CardView) findViewById(R.id.Userbike);

        progressDialog = new ProgressDialog(UserDashboardActivity.this);


        cardCycle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), UserHabitualCriminalActivity.class);
                startActivity(intent);
            }

        });



        cardCar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(getApplicationContext(), ViewVehicleOptionsActivity.class);
                startActivity(intent);
            }

        });



        cardBike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }

        });

        cardProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(intent);
            }
        });

        cardPhone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ViewGadgetsOptionsctivity.class);
                startActivity(intent);

            }

        });


        cardLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                LogoutConformation();
            }

        });


    }

/*
    @Override
    protected void onStart()
    {
        super.onStart();

        if (CurrentUser == null)
        {
            Intent intent = new Intent(getApplicationContext(),UserSignInActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }
*/



    private void LogoutConformation()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserDashboardActivity.this);
        builder.setTitle("Are you sure to Logout from App?");
        builder.setIcon(R.drawable.logout);


        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                progressDialog.setTitle("Logout");
                progressDialog.setMessage("Logging Out ...");
                progressDialog.setProgress(10);
                progressDialog.show();

                Paper.book().destroy();
                Intent intent = new Intent(getApplicationContext(), AdminSignInActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();

    }

}

