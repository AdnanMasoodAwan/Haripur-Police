package com.example.haripurpolice;

import static com.example.haripurpolice.R.layout.all_criminals_layout;
import static com.example.haripurpolice.R.layout.user_bike;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHabitualCriminalActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_user_habitual_criminal);

        recyclerView = (RecyclerView) findViewById(R.id.CriminalInfoList);
        Search=(EditText)findViewById(R.id.Search_Criminal_Info);
        SearchButton=(ImageButton) findViewById(R.id.SearchIconCriminal);




        PhoneRef= FirebaseDatabase.getInstance().getReference().child("Criminal Information");

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserHabitualCriminalActivity.this);
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
                    Toast.makeText(getApplicationContext(),"Please Write  Name",Toast.LENGTH_LONG).show();
                }
                onStart();
            }
        });


    }


    @Override
    protected void onStart()
    {
        super.onStart();

        Query query = PhoneRef.orderByChild("name").startAt(input).endAt(input + "\uf8ff");

        FirebaseRecyclerOptions<Criminals> options =
                new FirebaseRecyclerOptions.Builder<Criminals>()
                        .setQuery(query,Criminals.class)
                        .build();

        FirebaseRecyclerAdapter recyclerAdapter=new FirebaseRecyclerAdapter<Criminals,CriViewHolder>(options)
        {

            public CriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(all_criminals_layout, parent, false);

                return new CriViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CriViewHolder holder, int position, @NonNull Criminals model)
            {

                holder.setName(model.getName());
                holder.setImage(model.getImage(),getApplicationContext());






            }
        };
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();




    }





    public static class CriViewHolder extends RecyclerView.ViewHolder
    {
        TextView Name;
        CircleImageView CriImage;

        View view;

        public CriViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
        }



        public void setName(String name)
        {
            Name=(TextView)view.findViewById(R.id.Criminal_name);
            Name.setText(name);
        }


        public void setImage(String image, Context ctx)
        {
            CriImage=(CircleImageView) view.findViewById(R.id.Criminal_profile_image);
            Picasso.with(ctx).load(image).into(CriImage);
        }


    }










}