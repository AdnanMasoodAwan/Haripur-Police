package com.example.haripurpolice;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchData  extends AsyncTask<Object,String,String>
{
    String googleNearByPlacesData;
    GoogleMap googleMap;
    String url;



    @Override
    protected void onPostExecute(String s)
    {
       try
       {
           JSONObject jsonObject=new JSONObject(s);
           JSONArray jsonArray=jsonObject.getJSONArray("results");

           for(int i=0;i<jsonArray.length();i++)
           {
              JSONObject jsonObject1=jsonArray.getJSONObject(i);
              JSONObject getLocation=jsonObject1.getJSONObject("geometry").getJSONObject("location");

              String Lat=getLocation.getString("lat");
              String Lng=getLocation.getString("lng");

              JSONObject getName=jsonArray.getJSONObject(i);
              String  name=getName.getString(" name");

               LatLng latLng=new LatLng(Double.parseDouble(Lat),Double.parseDouble(Lng));
               MarkerOptions markerOptions=new MarkerOptions();
               markerOptions.title(name);
               markerOptions.position(latLng);
               markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.locat));
               googleMap.addMarker(markerOptions);
               googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));

           }







       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

    }




    @Override
    protected String doInBackground(Object... objects)
    {
       try
       {
             googleMap=(GoogleMap)objects[0];
             url=(String) objects[1];
             DownLoadUrl downLoadUrl=new DownLoadUrl();
             googleNearByPlacesData=downLoadUrl.retrieveUrl(url);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       return  googleNearByPlacesData;


    }


}
