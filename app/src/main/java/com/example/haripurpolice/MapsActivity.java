package com.example.haripurpolice;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.haripurpolice.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    ImageButton Search;
    SearchView SearchField;
    double CurrentLat = 0, CurrentLong = 0;
    SupportMapFragment mapFragment;
    ImageButton Police,Hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        SearchField = (SearchView) findViewById(R.id.Search_Info_maps);
        Search = (ImageButton) findViewById(R.id.SearchIconmaps);
        Police = (ImageButton) findViewById(R.id.PoliceButton);
        Hospital = (ImageButton) findViewById(R.id.HospitalButton);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

       // String PlaceTypeList[] = {"atm", "bank", "hospital", "restrauant", "police_station"};
        //String PlaceNameList[] = {"Atm", "Bank", "Hospital", "Restrauant", "Police_Station"};

       // SearchField.setAdapter(new ArrayAdapter<>(this,
         //       com.google.android.material.R.layout.support_simple_spinner_dropdown_item, PlaceNameList));


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


            getCurrentLocation();


            SearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextSubmit(String s)
                {
                    String location=SearchField.getQuery().toString();
                    List<Address> addressList=null;

                    if(location!=null || !location.equals(""))
                    {
                        Geocoder geocoder=new Geocoder(MapsActivity.this);
                        try
                        {
                            addressList=geocoder.getFromLocationName(location,1);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        Address address=addressList.get(0);
                        LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                        MarkerOptions markerOptions=new MarkerOptions();
                        markerOptions.title(location);
                        markerOptions.position(latLng);
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.locat));
                        mMap.addMarker(markerOptions);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));


                    }


                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s)
                {
                    return false;
                }

            });


            Police.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    Toast.makeText(MapsActivity.this,"Clicked",Toast.LENGTH_LONG).show();
                 StringBuilder stringBuilder=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?&key=AIzaSyABaVvK1Ien3_3tdrpu5G1u7OQ6d_Jk6ho");
                 stringBuilder.append("location="+CurrentLat+CurrentLong);
                 stringBuilder.append("&radius=1000");
                 stringBuilder.append("&type=hospital");
                 stringBuilder.append("&sensor=true");
                 stringBuilder.append("&key=AIzaSyABaVvK1Ien3_3tdrpu5G1u7OQ6d_Jk6ho");

                 String url=stringBuilder.toString();
                 Object dataFetch[]=new Object[2];
                 dataFetch[0]=mMap;
                 dataFetch[1]=url;

                 FetchData fetchData=new FetchData();
                 fetchData.execute(fetchData);



                }
            });





    }

    private void getCurrentLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>()
        {
            @Override
            public void onSuccess(Location location)
            {
               if(location!=null)
               {
                   CurrentLat=location.getLatitude();
                   CurrentLong=location.getLongitude();

                   mapFragment.getMapAsync(new OnMapReadyCallback()
                   {
                       @Override
                       public void onMapReady(@NonNull GoogleMap googleMap)
                       {
                           mMap=googleMap;
                           mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(CurrentLat,CurrentLong),17));
                           MarkerOptions markerOptions=new MarkerOptions().position(new LatLng(CurrentLat,CurrentLong)).title("Your Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.locat));
                           mMap.addMarker(markerOptions);
                           /*
                           LatLng latLng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                           MarkerOptions markerOptions=new MarkerOptions().position(latLng).title("Your Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.locat));
                           googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                           googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                           googleMap.addMarker(markerOptions);*/


                       }
                   });
               }
            }

        });


    }


}







