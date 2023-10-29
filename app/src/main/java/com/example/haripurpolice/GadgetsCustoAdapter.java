package com.example.haripurpolice;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GadgetsCustoAdapter extends BaseAdapter
{
    private  final Context context;
    private final ArrayList<GdgetsList> vehiclesLists;


    public GadgetsCustoAdapter(Context context, ArrayList<GdgetsList> vehiclesLists)
    {
        this.context = context;
        this.vehiclesLists = vehiclesLists;
    }



    @Override
    public int getCount()
    {
        return vehiclesLists.size();
    }


    @Override
    public Object getItem(int i)
    {
        return vehiclesLists.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int Position, View convertView, ViewGroup Parent)
    {

        ViewHodler holderView;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.all_vehicle_list,
                    Parent,false);
            holderView = new ViewHodler(convertView);
            convertView.setTag(holderView);
        }

        else {
            holderView = (ViewHodler) convertView.getTag();
        }
        GdgetsList list = vehiclesLists.get(Position);
        holderView.VehicleIconList.setImageResource(list.getGadgetIcon());
        holderView.VehicleNameList.setText(list.getGadgetame());

        return convertView;

    }


    private  static  class  ViewHodler
    {
        private  final TextView VehicleNameList;
        private  final ImageView VehicleIconList;


        public ViewHodler(View view)
        {
            VehicleNameList=view.findViewById(R.id.Vehicle_name);
            VehicleIconList=view.findViewById(R.id.Vehicle_image);

        }
    }





}








