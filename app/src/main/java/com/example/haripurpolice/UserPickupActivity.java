package com.example.haripurpolice;

import static com.example.haripurpolice.R.layout.user_bus;
import static com.example.haripurpolice.R.layout.user_pickup;

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

public class UserPickupActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_user_pickup);



        recyclerView = (RecyclerView) findViewById(R.id.PickupInfoList);
        Search = (EditText) findViewById(R.id.Search_Pickup_Info);
        SearchButton = (ImageButton) findViewById(R.id.SearchIconPickup);


        PhoneRef = FirebaseDatabase.getInstance().getReference().child("Pickup Information");


        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserPickupActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        SearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                input = Search.getText().toString();

                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(getApplicationContext(), "Please Write Pickup Name", Toast.LENGTH_LONG).show();
                }
                onStart();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        Query query = PhoneRef.orderByChild("pickupname").startAt(input).endAt(input + "\uf8ff");

        FirebaseRecyclerOptions<ViewPickups> options =
                new FirebaseRecyclerOptions.Builder<ViewPickups>()
                        .setQuery(query, ViewPickups.class)
                        .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ViewPickups,PickupViewHolder>(options)
        {


            @NonNull
            @Override
            public PickupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(user_pickup, parent, false);

                return new PickupViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PickupViewHolder holder, int position, @NonNull ViewPickups model)
            {
                String PostKey = getRef(position).getKey();

                holder.setPickupname(model.getPickupname());
                holder.setPickupmodel(model.getPickupmodel());
                holder.setPickupowner(model.getPickupowner());
                holder.setPickupfirstowner(model.getPickupfirstowner());
                holder.setPickupowneridcard(model.getPickupowneridcard());
                holder.setPickupnumber(model.getPickupnumber());
                holder.setImage(model.getImage(),getApplicationContext());



            }

        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();



    }





    public static class PickupViewHolder extends RecyclerView.ViewHolder
    {
        ImageView PickupPhoto;
        TextView PickupModel,PickupName,PickupNumber,PickupOwner,PickupFirstOwner,PickupOwnerIdCard;

        View view;

        public PickupViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
        }


        public void setPickupname(String pickupname)
        {
            PickupName = (TextView) view.findViewById(R.id.UserPickupName);
            PickupName.setText("PickupName : "+pickupname);

        }


        public void setPickupmodel(String pickupmodel)
        {
            PickupModel = (TextView) view.findViewById(R.id.UserPickupModel);
            PickupModel.setText("PickupModel : "+pickupmodel);
        }

        public void setPickupnumber(String pickupnumber)
        {
            PickupNumber = (TextView) view.findViewById(R.id.UserPickupRegNo);
            PickupNumber.setText("PickupNumber "+pickupnumber);

        }

        public void setImage(String image, Context ctx)
        {
            PickupPhoto = (ImageView) view.findViewById(R.id.UserPickupImage);
            Picasso.with(ctx).load(image).into(PickupPhoto);
        }



        public void setPickupowner(String pickupowner)
        {
            PickupOwner = (TextView) view.findViewById(R.id.UserPickupOwner);
            PickupOwner.setText("PickupOwner : "+pickupowner);
        }

        public void setPickupfirstowner(String pickupfirstowner)
        {
            PickupFirstOwner = (TextView) view.findViewById(R.id.UserPickupFirstOwner);
            PickupFirstOwner.setText("PickupFirstOwner : "+pickupfirstowner);
        }

        public void setPickupowneridcard(String pickupowneridcard)
        {
            PickupOwnerIdCard = (TextView) view.findViewById(R.id.UserPickupOwnerNIC);
            PickupOwnerIdCard.setText("PickupOwnerIdCard : "+ pickupowneridcard);
        }

    }













}