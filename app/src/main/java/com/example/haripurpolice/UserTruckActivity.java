package com.example.haripurpolice;

import static com.example.haripurpolice.R.layout.user_bus;
import static com.example.haripurpolice.R.layout.user_truck;

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

public class UserTruckActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_user_truck);



        recyclerView = (RecyclerView) findViewById(R.id.TruckInfoList);
        Search = (EditText) findViewById(R.id.Search_Truck_Info);
        SearchButton = (ImageButton) findViewById(R.id.SearchIconTruck);


        PhoneRef = FirebaseDatabase.getInstance().getReference().child("Truck Information");


        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserTruckActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        SearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                input = Search.getText().toString();

                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(getApplicationContext(), "Please Write Truck Name", Toast.LENGTH_LONG).show();
                }
                onStart();
            }
        });


    }


    @Override
    protected void onStart()
    {
        super.onStart();

        Query query = PhoneRef.orderByChild("truckname").startAt(input).endAt(input + "\uf8ff");

        FirebaseRecyclerOptions<ViewTrucks> options=
                new FirebaseRecyclerOptions.Builder<ViewTrucks>()
                        .setQuery(query,ViewTrucks.class)
                        .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ViewTrucks,TrucksViewHolder>(options)
        {


            @NonNull
            @Override
            public TrucksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(user_truck, parent, false);

                return new TrucksViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TrucksViewHolder holder, int position, @NonNull ViewTrucks model)
            {
                String PostKey = getRef(position).getKey();

                holder.setTruckname(model.getTruckname());
                holder.setTruckmodel(model.getTruckmodel());
                holder.setTruckowner(model.getTruckowner());
                holder.setTruckfirstowner(model.getTruckfirstowner());
                holder.setTruckowneridcard(model.getTruckowneridcard());
                holder.setTrucknumber(model.getTrucknumber());
                holder.setImage(model.getImage(),getApplicationContext());


            }

        };



        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();


    }









    public static class TrucksViewHolder extends RecyclerView.ViewHolder
    {
        ImageView TurcksPhoto;
        TextView TurcksModel,TurcksName,TurcksNumber,TurcksOwner,TurcksFirstOwner,TurcksOwnerIdCard;

        View view;

        public TrucksViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
        }


        public void setTruckname(String truckname)
        {

             TurcksName= (TextView) view.findViewById(R.id.UserTruckName);
             TurcksName.setText("TruckName : "+truckname);

        }


        public void setTruckmodel(String truckmodel)
        {
            TurcksModel = (TextView) view.findViewById(R.id.UserTruckModel);
            TurcksModel.setText("TruckModel : "+truckmodel);
        }

        public void setTrucknumber(String trucknumber)
        {
            TurcksNumber = (TextView) view.findViewById(R.id.UserTruckRegNo);
            TurcksNumber.setText("TruckNumber "+trucknumber);

        }

        public void setImage(String image, Context ctx)
        {
            TurcksPhoto = (ImageView) view.findViewById(R.id.UserTruckImage);
            Picasso.with(ctx).load(image).into(TurcksPhoto);
        }

        public void setTruckowner(String truckowner)
        {
            TurcksOwner = (TextView) view.findViewById(R.id.UserTruckOwner);
            TurcksOwner.setText("TruckOwner : "+truckowner);
        }

        public void setTruckfirstowner(String truckfirstowner)
        {
            TurcksFirstOwner = (TextView) view.findViewById(R.id.UserTruckFirstOwner);
            TurcksFirstOwner.setText(" TruckFirstOwner : "+truckfirstowner);
        }

        public void setTruckowneridcard(String truckowneridcard)
        {
            TurcksOwnerIdCard= (TextView) view.findViewById(R.id.UserTruckOwnerNIC);
            TurcksOwnerIdCard.setText("TruckOwnerIdCard : "+ truckowneridcard);
        }

    }






}