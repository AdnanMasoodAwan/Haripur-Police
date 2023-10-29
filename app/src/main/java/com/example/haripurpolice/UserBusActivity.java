package com.example.haripurpolice;

import static com.example.haripurpolice.R.layout.user_bike;
import static com.example.haripurpolice.R.layout.user_bus;
import static com.example.haripurpolice.R.layout.user_car;

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

public class UserBusActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_user_bus);


        recyclerView = (RecyclerView) findViewById(R.id.BusInfoList);
        Search = (EditText) findViewById(R.id.Search_Bus_Info);
        SearchButton = (ImageButton) findViewById(R.id.SearchIconBus);


        PhoneRef = FirebaseDatabase.getInstance().getReference().child("Bus Information");


        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserBusActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        SearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                input = Search.getText().toString();

                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(getApplicationContext(), "Please Write Bus Name", Toast.LENGTH_LONG).show();
                }
                onStart();
            }
        });


    }


    @Override
    protected void onStart()
    {
        super.onStart();

        Query query = PhoneRef.orderByChild("busname").startAt(input).endAt(input + "\uf8ff");

        FirebaseRecyclerOptions<ViewBus> options=
                new FirebaseRecyclerOptions.Builder<ViewBus>()
                        .setQuery(query,ViewBus.class)
                        .build();


        FirebaseRecyclerAdapter firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ViewBus,BusViewHolder>(options)
        {

            @NonNull
            @Override
            public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(user_bus, parent, false);

                return new BusViewHolder(view) ;
            }

            @Override
            protected void onBindViewHolder(@NonNull BusViewHolder holder, int position, @NonNull ViewBus model)
            {
                String PostKey = getRef(position).getKey();

                holder.setBusname(model.getBusname());
                holder.setBusmodel(model.getBusmodel());
                holder.setBusowner(model.getBusowner());
                holder.setBusfirstowner(model.getBusfirstowner());
                holder.setBusowneridcard(model.getBusowneridcard());
                holder.setBusnumber(model.getBusnumber());
                holder.setImage(model.getImage(),getApplicationContext());


            }


        } ;
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();


    }







    public static class BusViewHolder extends RecyclerView.ViewHolder
    {
        ImageView BusPhoto;
        TextView BusModel,BusName,BusNumber,BusOwner,BusFirstOwner,BusOwnerIdCard;

        View view;

        public BusViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
        }


        public void setBusname(String busname)
        {
            BusName = (TextView) view.findViewById(R.id.BusName);
            BusName.setText("BusName : "+busname);

        }


        public void setBusmodel(String busmodel)
        {
            BusModel = (TextView) view.findViewById(R.id.BusModel);
            BusModel.setText("BusModel : "+busmodel);
        }

        public void setBusnumber(String busnumber)
        {
            BusNumber = (TextView) view.findViewById(R.id.BusRegNo);
            BusNumber.setText("BusNumber "+busnumber);

        }

        public void setImage(String image, Context ctx)
        {
            BusPhoto = (ImageView) view.findViewById(R.id.UserBusImage);
            Picasso.with(ctx).load(image).into(BusPhoto);
        }


        public void setBusowner(String busowne)
        {
            BusOwner = (TextView) view.findViewById(R.id.BusOwner);
            BusOwner.setText("BusOwner : "+busowne);
        }

        public void setBusfirstowner(String busfirstowner)
        {
            BusFirstOwner = (TextView) view.findViewById(R.id.BusFirstOwner);
            BusFirstOwner.setText(" BusFirstOwner : "+busfirstowner);
        }

        public void setBusowneridcard(String busowneridcard)
        {
            BusOwnerIdCard = (TextView) view.findViewById(R.id.BusOwnerNIC);
            BusOwnerIdCard.setText("BusOwnerIdCard : "+ busowneridcard);
        }

    }







}