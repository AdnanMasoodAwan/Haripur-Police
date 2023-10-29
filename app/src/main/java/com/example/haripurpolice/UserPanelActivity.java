package com.example.haripurpolice;

import static com.example.haripurpolice.R.layout.user_phone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.ColorSpace;
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


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class UserPanelActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference PhoneRef;
    EditText Search;
    ImageButton SearchButton;
    String input;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        recyclerView = (RecyclerView) findViewById(R.id.InfoList);
        Search=(EditText)findViewById(R.id.Search_Info);
        SearchButton=(ImageButton) findViewById(R.id.SearchIcon);



        PhoneRef= FirebaseDatabase.getInstance().getReference().child("Phone Information");



         recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserPanelActivity.this);
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


        Query query = PhoneRef.orderByChild("pname").startAt(input).endAt(input + "\uf8ff");

        FirebaseRecyclerOptions<ViewPhone> options =
                new FirebaseRecyclerOptions.Builder<ViewPhone>()
                        .setQuery(query, ViewPhone.class)
                        .build();


        FirebaseRecyclerAdapter recyclerAdapter =
                new FirebaseRecyclerAdapter<ViewPhone,PhonesViewHolder>
                        (options)
                {

                    @NonNull
                    @Override
                    public PhonesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(user_phone, parent, false);

                        return new PhonesViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull PhonesViewHolder holder, int position, @NonNull ViewPhone model)
                    {
                        String PostKey = getRef(position).getKey();
                        holder.setPname(model.getPname());
                        holder.setPmodel(model.getPmodel());
                        holder.setPemei(model.getPemei());
                        holder.setPcreator(model.getPcreator());
                        holder.setImage(model.getImage(),getApplicationContext());
                    }
                };
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();

    }







    public static class PhonesViewHolder extends RecyclerView.ViewHolder
    {
        TextView PhoneName, Phonemodel, PhoneEmi, PhoneCreator;
        ImageView PhoneImage;

        View view;

        public PhonesViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
        }


        public void setPname(String pname)
        {
            PhoneName=(TextView)view.findViewById(R.id.PhoneName);
            PhoneName.setText(" PhoneName : "+pname);

        }

        public void setPemei(String pemei)
        {
            PhoneEmi=(TextView)view.findViewById(R.id.PhoneEmi);
            PhoneEmi.setText("PhoneImei : "+pemei);
        }

        public void setPcreator(String pcreator)
        {
            PhoneCreator=(TextView)view.findViewById(R.id.PhoneManufacturer);
            PhoneCreator.setText("PhoneCreator : "+pcreator);

        }

        public void setImage(String image,Context ctx)
        {
            PhoneImage=(ImageView) view.findViewById(R.id.UserPhoneImage);
            Picasso.with(ctx).load(image).into(PhoneImage);
        }

        public void setPmodel(String pmodel)
        {
            Phonemodel=(TextView)view.findViewById(R.id.Phonemodel);
            Phonemodel.setText(" Phonemodel : "+pmodel);
        }

    }




}


