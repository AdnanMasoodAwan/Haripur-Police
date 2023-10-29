package com.example.haripurpolice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import io.paperdb.Paper;

public class AdminPanelActivity extends AppCompatActivity
{
    CardView cardCar;
    CardView cardCycle;
    CardView cardProfile;
    CardView cardLogout;
    CardView cardPhone;
    CardView cardBike;
    CardView cardTheft;

    FirebaseAuth AAuth;
    ProgressDialog progressDialog;
    FirebaseUser CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        AAuth=FirebaseAuth.getInstance();
       // CurrentUser= AAuth.getCurrentUser();



        cardCar=(CardView)findViewById(R.id.car);
        cardCycle=(CardView)findViewById(R.id.cycle);
        cardProfile=(CardView)findViewById(R.id.profile);
        cardLogout=(CardView)findViewById(R.id.logout);
        cardPhone=(CardView)findViewById(R.id.phone);
        cardBike=(CardView)findViewById(R.id.bike);
        cardTheft=(CardView)findViewById(R.id.Chor);

        Paper.init(this);



        progressDialog=new ProgressDialog(AdminPanelActivity.this);


        cardTheft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), CriButtonsActivity.class);
                startActivity(intent);
            }
        });




        cardCycle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ManageUsersActivity.class);
                startActivity(intent);
            }

        });

        cardCar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), VehicleOptionsActivity.class);
                startActivity(intent);
            }

        });


        cardBike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent=new Intent(getApplicationContext(),AdminMapsActivity.class);
                startActivity(intent);
            }

        });

        cardPhone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(),GadgetsOptionsActivity.class);
                startActivity(intent);
            }

        });

        cardProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(),AdminProfileActivity.class);
                startActivity(intent);
            }
        });



        cardLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
              LogoutConformation();
            }
        });



    }


    private void LogoutConformation()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(AdminPanelActivity.this);
        builder.setTitle("Are you sure to Logout from App?");
        builder.setIcon(R.drawable.logout);


        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

                progressDialog.setTitle("Logout");
                progressDialog.setMessage("Logging Out ...");
                progressDialog.setProgress(10);
                progressDialog.show();

                Paper.book().destroy();
                Intent intent=new Intent(getApplicationContext(),AdminSignInActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });

        builder.show();

    }





}
