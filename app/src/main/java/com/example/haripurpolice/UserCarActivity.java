package com.example.haripurpolice;

import static com.example.haripurpolice.R.layout.user_car;
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

import java.util.concurrent.Callable;

public class UserCarActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference PhoneRef;
    EditText Search;
    ImageButton SearchButton;
    String input;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_car);


        recyclerView = (RecyclerView) findViewById(R.id.CarInfoList);
        Search = (EditText) findViewById(R.id.Search_Car_Info);
        SearchButton = (ImageButton) findViewById(R.id.SearchIconCar);


        PhoneRef = FirebaseDatabase.getInstance().getReference().child("Car Information");


        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserCarActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = Search.getText().toString();

                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(getApplicationContext(), "Please Write Car Name", Toast.LENGTH_LONG).show();
                }
                onStart();
            }
        });


    }



    @Override
    protected void onStart()
    {
        super.onStart();

        Query query = PhoneRef.orderByChild("carname").startAt(input).endAt(input + "\uf8ff");

        FirebaseRecyclerOptions<ViewCar> options=
                new FirebaseRecyclerOptions.Builder<ViewCar>()
                        .setQuery(query,ViewCar.class)
                        .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ViewCar,CarsViewHolder>(options)
        {

            public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(user_car, parent, false);

                return new CarsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CarsViewHolder holder, int position, @NonNull ViewCar model)
            {

                String PostKey = getRef(position).getKey();

                holder.setCarname(model.getCarname());
                holder.setCarmodel(model.getCarmodel());
                holder.setCarnumber(model.getCarnumber());
                holder.setCarowner(model.getCarowner());
                holder.setImage(model.getImage(),getApplicationContext());





            }

        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();




    }











    public static class CarsViewHolder extends RecyclerView.ViewHolder {
        TextView CarName, Carmodel, CarRegNo, CarOwner;
        ImageView CarImage;

        View view;

        public CarsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }


        public void setCarname(String carname) {
            CarName = (TextView) view.findViewById(R.id.CarName);
            CarName.setText(" CarName : "+carname);

        }

        public void setCarmodel(String carmodel) {
            Carmodel = (TextView) view.findViewById(R.id.CarModel);
            Carmodel.setText(" Carmodel : "+carmodel);
        }

        public void setCarnumber(String carnumber) {
            CarRegNo = (TextView) view.findViewById(R.id.CarRegNo);
            CarRegNo.setText("CarRegNo : "+ carnumber);

        }

        public void setImage(String image, Context ctx) {
            CarImage = (ImageView) view.findViewById(R.id.UserCarImage);
            Picasso.with(ctx).load(image).into(CarImage);
        }


        public void setCarowner(String carowner) {
            CarOwner = (TextView) view.findViewById(R.id.CarOwner);
            CarOwner.setText("CarOwner : "+carowner);
        }

    }

}