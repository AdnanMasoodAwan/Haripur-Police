package com.example.haripurpolice;

import static com.example.haripurpolice.R.layout.user_truck;
import static com.example.haripurpolice.R.layout.user_van;

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

public class UserVanActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_user_van);



        recyclerView = (RecyclerView) findViewById(R.id.VanInfoList);
        Search = (EditText) findViewById(R.id.Search_Van_Info);
        SearchButton = (ImageButton) findViewById(R.id.SearchIconVan);


        PhoneRef = FirebaseDatabase.getInstance().getReference().child("Van Information");


        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserVanActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        SearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                input = Search.getText().toString();

                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(getApplicationContext(), "Please Van Truck Name", Toast.LENGTH_LONG).show();
                }
                onStart();
            }
        });

    }


    @Override
    protected void onStart()
    {
        super.onStart();


        Query query = PhoneRef.orderByChild("vanname").startAt(input).endAt(input + "\uf8ff");

        FirebaseRecyclerOptions<ViewVans> options=
                new FirebaseRecyclerOptions.Builder<ViewVans>()
                        .setQuery(query,ViewVans.class)
                        .build();


        FirebaseRecyclerAdapter firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ViewVans,VansViewHolder>(options)
        {


            @NonNull
            @Override
            public VansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(user_van, parent, false);

                return new VansViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull VansViewHolder holder, int position, @NonNull ViewVans model)
            {
                String PostKey = getRef(position).getKey();

                holder.setVanname(model.getVanname());
                holder.setVanmodel(model.getVanmodel());
                holder.setVanowner(model.getVanowner());
                holder.setVanfirstowner(model.getVanfirstowner());
                holder.setVanowneridcard(model.getVanowneridcard());
                holder.setVannumber(model.getVannumber());
                holder.setImage(model.getImage(),getApplicationContext());



            }


        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();







    }



    public static class VansViewHolder extends RecyclerView.ViewHolder
    {
        ImageView VansPhoto;
        TextView VansModel,VansName,VansNumber,VansOwner,VansFirstOwner,VansOwnerIdCard;

        View view;

        public VansViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
        }



        public void setVanname(String vanname)
        {

              VansName= (TextView) view.findViewById(R.id.UserVanName);
              VansName.setText("VanName : "+vanname);

        }


        public void setVanmodel(String vanmodel)
        {
            VansModel = (TextView) view.findViewById(R.id.UserVanModel);
            VansModel.setText("VanModel : "+vanmodel);
        }

        public void setVannumber(String vannumber)
        {
            VansNumber = (TextView) view.findViewById(R.id.UserVanRegNo);
            VansNumber.setText("VanNumber "+vannumber);

        }

        public void setImage(String image, Context ctx)
        {
            VansPhoto = (ImageView) view.findViewById(R.id.UserVanImage);
            Picasso.with(ctx).load(image).into(VansPhoto);
        }

        public void setVanowner(String vanowner)
        {
            VansOwner = (TextView) view.findViewById(R.id.UserVanOwner);
            VansOwner.setText("VanOwner : "+vanowner);
        }

        public void setVanfirstowner(String vanfirstowner)
        {
            VansFirstOwner = (TextView) view.findViewById(R.id.UserVanFirstOwner);
            VansFirstOwner.setText("VanFirstOwner : "+vanfirstowner);
        }

           public void setVanowneridcard(String vanowneridcard)
           {
            VansOwnerIdCard= (TextView) view.findViewById(R.id.UserVanOwnerNIC);
            VansOwnerIdCard.setText("VanOwnerNic : "+ vanowneridcard);
           }

    }



}