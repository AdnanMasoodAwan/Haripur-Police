package com.example.haripurpolice;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadUrl
{

    public  String retrieveUrl(String Url) throws  Exception
    {
        String UrlData = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            URL getUrl = new URL(Url);
            httpURLConnection = (HttpURLConnection) getUrl.openConnection();
            httpURLConnection.connect();


            inputStream = httpURLConnection.getInputStream();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = bufferedReader.readLine()) != null)
            {
                sb.append(line);

            }
            UrlData = sb.toString();
            bufferedReader.close();


        }

        catch (Exception e)
        {
            Log.d("Exception", e.toString());
        }

        finally
        {

             inputStream.close();
             httpURLConnection.disconnect();
        }

           return  UrlData;
    }




}
