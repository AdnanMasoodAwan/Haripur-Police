package com.example.haripurpolice;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.haripurpolice.databinding.ActivityAdminMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class AdminMapsActivity extends FragmentActivity
{

    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private ActivityAdminMapsBinding binding;
    private Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    ImageButton Search;
    SearchView SearchField;
    double CurrentLat = 0, CurrentLong = 0;
    ImageButton Police,Hospital;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maps);


        SearchField = (SearchView) findViewById(R.id.Search_Info_maps_Ad);
        Search = (ImageButton) findViewById(R.id.SearchIconmapsAd);
        Police = (ImageButton) findViewById(R.id.AdPoliceButton);
        Hospital = (ImageButton) findViewById(R.id.AdHospitalButton);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.Admap);

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
                    Geocoder geocoder=new Geocoder(AdminMapsActivity.this);
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










