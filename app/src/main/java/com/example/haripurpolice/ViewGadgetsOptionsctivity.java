package com.example.haripurpolice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewGadgetsOptionsctivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<GdgetsList> vehiclesLists;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gadgets_optionsctivity);

        ListView listView = findViewById(R.id.ViewGadgetsInfoList);

        vehiclesLists = setIconAndName();
        GadgetsCustoAdapter customAdapter = new GadgetsCustoAdapter(ViewGadgetsOptionsctivity.this, vehiclesLists);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(ViewGadgetsOptionsctivity.this);

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
            Intent intent = new Intent(getApplicationContext(), UserPanelActivity.class);
            startActivity(intent);
        }

        if(position==1)
        {
           Intent intent = new Intent(getApplicationContext(), UserLaptopsActivity.class);
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

}