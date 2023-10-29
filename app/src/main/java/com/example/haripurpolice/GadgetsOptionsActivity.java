package com.example.haripurpolice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class GadgetsOptionsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ArrayList<GdgetsList> vehiclesLists;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gadgets_options);

        ListView listView = findViewById(R.id.GadgetsInfoList);
        toolbar=(Toolbar)findViewById(R.id.include);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gadgets List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        vehiclesLists = setIconAndName();
        GadgetsCustoAdapter customAdapter = new GadgetsCustoAdapter(GadgetsOptionsActivity.this, vehiclesLists);
        listView.setAdapter(customAdapter);


        listView.setOnItemClickListener(GadgetsOptionsActivity.this);



    }





    private ArrayList<GdgetsList> setIconAndName()
    {
        vehiclesLists = new ArrayList<>();

        vehiclesLists.add(new GdgetsList("Phone", R.drawable.smartphon));
        vehiclesLists.add(new GdgetsList("Laptops", R.drawable.laptop));
       // vehiclesLists.add(new GdgetsList("Tablets", R.drawable.tablet));
        //vehiclesLists.add(new GdgetsList("Lcds", R.drawable.led));


        return vehiclesLists;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        if(position==0)
        {
            Intent intent = new Intent(getApplicationContext(), PhoneButonsActivity.class);
            startActivity(intent);
        }

        if(position==1)
        {
            Intent intent = new Intent(getApplicationContext(), LaptopButtonsActivity.class);
            startActivity(intent);
        }
        if(position==2)
        {
            //Intent intent = new Intent(getApplicationContext(), PickupActivity.class);
            //startActivity(intent);
        }
        if(position==3)
        {
            //Intent intent = new Intent(getApplicationContext(), PickupActivity.class);
            //startActivity(intent);
        }








    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {
        super.onPointerCaptureChanged(hasCapture);
    }

}