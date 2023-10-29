package com.example.haripurpolice;

import static com.example.haripurpolice.R.layout.user_laptop;
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

public class UserLaptopsActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_user_laptops);



        recyclerView = (RecyclerView) findViewById(R.id.LaptopsInfoList);
        Search = (EditText) findViewById(R.id.Search_Laptops_Info);
        SearchButton = (ImageButton) findViewById(R.id.SearchIconLaptops);


        PhoneRef = FirebaseDatabase.getInstance().getReference().child("Laptop Information");


        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserLaptopsActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        SearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                input = Search.getText().toString();

                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(getApplicationContext(), "Please Write Laptop Name", Toast.LENGTH_LONG).show();
                }
                onStart();
            }
        });

    }


    @Override
    protected void onStart()
    {
        super.onStart();

        Query query = PhoneRef.orderByChild("laptopname").startAt(input).endAt(input + "\uf8ff");

        FirebaseRecyclerOptions<ViewLaptops> options=
                new FirebaseRecyclerOptions.Builder<ViewLaptops>()
                        .setQuery(query,ViewLaptops.class)
                        .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ViewLaptops,LaptopViewHolder>(options)
        {

            @NonNull
            @Override
            public LaptopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(user_laptop, parent, false);

                return new LaptopViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull LaptopViewHolder holder, int position, @NonNull ViewLaptops model)
            {
                String PostKey = getRef(position).getKey();

                holder.setLaptopname(model.getLaptopname());
                holder.setLaptopmodel(model.getLaptopmodel());
                holder.setLaptopgeneration(model.getLaptopgeneration());
                holder.setLaptopprocessor(model.getLaptopprocessor());
                holder.setLaptopownernic(model.getLaptopownernic());
                holder.setLaptopram(model.getLaptopram());
                holder.setLaptophardsik(model.getLaptophardsik());
                holder.setImage(model.getImage(),getApplicationContext());



            }
        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();




    }



    public static class LaptopViewHolder extends RecyclerView.ViewHolder
    {
        ImageView LaptopPhoto;
        TextView LaptopModel,LaptopName,LaptopRam,LaptopHardisk,LaptopOwner,LaptopProcessor,LaptopGeneration,LaptopOwnerNic;

        View view;

        public LaptopViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
        }



        public void setLaptopname(String laptopname)
        {

            LaptopName= (TextView) view.findViewById(R.id.UserLaptopName);
            LaptopName.setText("LaptopName : "+laptopname);

        }


        public void setLaptopmodel(String laptopmodel)
        {
            LaptopModel = (TextView) view.findViewById(R.id.UserLaptopModel);
            LaptopModel.setText("LaptopModel : "+laptopmodel);
        }

        public void setLaptophardsik(String laptophardsik)
        {
            LaptopHardisk = (TextView) view.findViewById(R.id.UserLaptopHardisk);
            LaptopHardisk.setText("LaptopNumber "+laptophardsik);

        }

        public void setImage(String image, Context ctx)
        {
            LaptopPhoto = (ImageView) view.findViewById(R.id.UserLaptopImage);
            Picasso.with(ctx).load(image).into(LaptopPhoto);
        }

        public void setLaptopram(String laptopram)
        {
            LaptopRam = (TextView) view.findViewById(R.id.UserLaptopRam);
            LaptopRam.setText("LaptopRam: "+laptopram);
        }

        public void setLaptopprocessor(String laptopprocessor)
        {
            LaptopProcessor = (TextView) view.findViewById(R.id.UserLaptopProcessor);
            LaptopProcessor.setText("LaptopProcessor : "+laptopprocessor);
        }


        public void setLaptopgeneration(String laptopgeneration)
        {
            LaptopGeneration= (TextView) view.findViewById(R.id.UserLaptopGeneration);
            LaptopGeneration.setText("LaptopGeneration : "+ laptopgeneration);
        }

        public void setLaptopownernic(String laptopownernic)
        {
            LaptopOwnerNic= (TextView) view.findViewById(R.id.UserLaptopOwnerNic);
            LaptopOwnerNic.setText("LaptopOwnerNic : "+ laptopownernic);
        }

    }







}