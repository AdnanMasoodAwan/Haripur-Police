package com.example.haripurpolice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class VehicleOptionsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ArrayList<VehiclesList> vehiclesLists;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_options);

        ListView listView = findViewById(R.id.VehicleInfoList);
        toolbar=(Toolbar)findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Vehicles List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        vehiclesLists = setIconAndName();
        CustomAdapter customAdapter = new CustomAdapter(VehicleOptionsActivity.this, vehiclesLists);
        listView.setAdapter(customAdapter);


        listView.setOnItemClickListener(VehicleOptionsActivity.this);


    }


    private ArrayList<VehiclesList> setIconAndName()
    {
        vehiclesLists = new ArrayList<>();

        vehiclesLists.add(new VehiclesList(R.drawable.bus, "Bus"));
        vehiclesLists.add(new VehiclesList(R.drawable.carr, "Car"));
        vehiclesLists.add(new VehiclesList(R.drawable.pickup, "Pickup"));
        vehiclesLists.add(new VehiclesList(R.drawable.bik, "Bike"));
        vehiclesLists.add(new VehiclesList(R.drawable.container, "Trucks"));
        vehiclesLists.add(new VehiclesList(R.drawable.van, "Vans"));

        return vehiclesLists;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        if(position==0)
        {
            Intent intent = new Intent(getApplicationContext(), BusButtonsActivity.class);
            startActivity(intent);
        }

        if(position==1)
        {
            Intent intent = new Intent(getApplicationContext(), CarButtonsActivity.class);
            startActivity(intent);
        }
        if(position==2)
        {
            Intent intent = new Intent(getApplicationContext(), PickupButtonsActivity.class);
            startActivity(intent);
        }


        if(position==3)
        {
            Intent intent = new Intent(getApplicationContext(), BikeButtonsActivity.class);
            startActivity(intent);
        }
        if(position==4)
        {
            Intent intent = new Intent(getApplicationContext(), TruckButtonsActivity.class);
            startActivity(intent);
        }

        if(position==5)
        {
            Intent intent = new Intent(getApplicationContext(), VanButtonsActivity.class);
            startActivity(intent);
        }

        if(position==6)
        {

        }





    }
}














