package com.example.haripurpolice;

import static com.example.haripurpolice.R.layout.user_bike;
import static com.example.haripurpolice.R.layout.user_phone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class UserBikeActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    private DatabaseReference PhoneRef;
    EditText Search;
    ImageButton SearchButton;
    String input;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bike);

        recyclerView = (RecyclerView) findViewById(R.id.BikeInfoList);
        Search=(EditText)findViewById(R.id.Search_Bike_Info);
        SearchButton=(ImageButton) findViewById(R.id.SearchIconBike);



        PhoneRef= FirebaseDatabase.getInstance().getReference().child("Bike Information");

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserBikeActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);




        SearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                input=Search.getText().toString();

                if(TextUtils.isEmpty(input))
                {
                    Toast.makeText(getApplicationContext(),"Please Write Product Name",Toast.LENGTH_LONG).show();
                }
                onStart();
            }
        });


}


    @Override
    protected void onStart()
    {
        super.onStart();

        Query query = PhoneRef.orderByChild("bname").startAt(input).endAt(input + "\uf8ff");


        FirebaseRecyclerOptions<ViewBike> options =
                new FirebaseRecyclerOptions.Builder<ViewBike>()
                        .setQuery(query,ViewBike.class)
                        .build();

        FirebaseRecyclerAdapter recyclerAdapter =
                new FirebaseRecyclerAdapter<ViewBike, BikesViewHolder>
                        (options)
                {

                    public BikesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(user_bike, parent, false);

                        return new BikesViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull BikesViewHolder holder, int position, @NonNull ViewBike model)
                    {

                        String PostKey = getRef(position).getKey();
                        holder.setBname(model.getBname());
                        holder.setBmodel(model.getBmodel());
                        holder.setBnumber(model.getBnumber());
                        holder.setBowner(model.getBowner());
                        holder.setImage(model.getImage(),getApplicationContext());

                    }

                };
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();








    }




    public static class BikesViewHolder extends RecyclerView.ViewHolder
    {
        TextView BikeName, Bikemodel, BikeNumber, BikeOwner;
        ImageView BikeImage;

        View view;

        public BikesViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
        }


        public void setBname(String bname)
        {
            BikeName=(TextView)view.findViewById(R.id.UserBikeName);
            BikeName.setText("BikeName : "+  bname);

        }

        public void setBnumber(String bnumber)
        {
            BikeNumber=(TextView)view.findViewById(R.id.UserBikeNumber);
            BikeNumber.setText("BikeNumber : "+bnumber);
        }

        public void setBowner(String bowner)
        {
            BikeOwner=(TextView)view.findViewById(R.id.UserBikeOwner);
            BikeOwner.setText(" BikeOwner : "+bowner);

        }

        public void setImage(String image, Context ctx)
        {
            BikeImage=(ImageView) view.findViewById(R.id.UserBikeImage);
            Picasso.with(ctx).load(image).into(BikeImage);
        }


        public void setBmodel(String bmodel)
        {
            Bikemodel=(TextView)view.findViewById(R.id.UserBikeModel);
            Bikemodel.setText(" Bikemodel : "+bmodel);
        }

    }







}