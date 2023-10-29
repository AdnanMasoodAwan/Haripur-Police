package com.example.haripurpolice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewVehicleOptionsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ArrayList<VehiclesList> vehiclesLists;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle_options);

//             getSupportActionBar().setTitle("View Vehicle Info");

        ListView listView = findViewById(R.id.ViewVehicleInfoList);

        vehiclesLists = setIconAndName();
        CustomAdapter customAdapter = new CustomAdapter(ViewVehicleOptionsActivity.this, vehiclesLists);
        listView.setAdapter(customAdapter);


        listView.setOnItemClickListener(ViewVehicleOptionsActivity.this);

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
            Intent intent = new Intent(getApplicationContext(), UserBusActivity.class);
            startActivity(intent);
        }

        if(position==1)
        {
              Intent intent = new Intent(getApplicationContext(), UserCarActivity.class);
              startActivity(intent);
        }
        if(position==2)
        {
              Intent intent = new Intent(getApplicationContext(), UserPickupActivity.class);
              startActivity(intent);
        }


        if(position==3)
        {
            Intent intent = new Intent(getApplicationContext(), UserBikeActivity.class);
            startActivity(intent);
        }
        if(position==4)
        {
            Intent intent = new Intent(getApplicationContext(), UserTruckActivity.class);
            startActivity(intent);
        }

        if(position==5)
        {
            Intent intent = new Intent(getApplicationContext(), UserVanActivity.class);
           startActivity(intent);
        }

        if(position==6)
        {

        }





    }





}