package com.example.haripurpolice;

import static com.example.haripurpolice.R.layout.activity_manage_users;
import static com.example.haripurpolice.R.layout.all_users_layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ManageUsersActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    private DatabaseReference PhoneRef;
    EditText Search;
    ImageButton SearchButton;
    String input;
    String userCnic;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        recyclerView = (RecyclerView) findViewById(R.id.UsersList);
        Search=(EditText)findViewById(R.id.Search_Users);
        SearchButton=(ImageButton) findViewById(R.id.SearchIconManageUsers);


        PhoneRef= FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ManageUsersActivity.this);
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

        Query query = PhoneRef.orderByChild("name").startAt(input).endAt(input + "\uf8ff");

        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(query,Users.class)
                .build();

        FirebaseRecyclerAdapter recyclerAdapter=new FirebaseRecyclerAdapter<Users,UsersViewHolder>(options)
        {

            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(all_users_layout, parent, false);

                return new UsersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model)
            {
                String Key = getRef(position).getKey();



                PhoneRef.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        String type= snapshot.child(model.getPhoneno()).child("type").getValue().toString();

                        if(type.equals("User"))
                        {

                            holder.setName(model.getName());
                            holder.setEmail(model.getEmail());
                            holder.setProfileimage(model.getProfileimage(), ManageUsersActivity .this);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });










                holder.view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                           CharSequence Options[]=new CharSequence[]
                                   {

                                      "Delete User",
                                      "View Profile",
                                      "Exit"

                                   };

                        AlertDialog.Builder builder=new AlertDialog.Builder(ManageUsersActivity.this);
                        builder.setTitle("Select Options");

                        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dialogInterface.cancel();
                            }

                        });


                      builder.setItems(Options, new DialogInterface.OnClickListener()
                      {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i)
                          {

                              if(i==0)
                              {
                                  PhoneRef.child(Key).removeValue();
                              }
                              if(i==1)
                              {
                                  Intent intent = new Intent(getApplicationContext(), DesiredUserProfileActivity.class);
                                  intent.putExtra("key",Key);
                                  intent.putExtra("name",model.getName());
                                  intent.putExtra("email",model.getEmail());
                                  intent.putExtra("cnic",model.getCnic());
                                  startActivity(intent);

                              }
                              if(i==2)
                              {
                                  dialogInterface.cancel();
                              }





                          }


                      });

                       builder.show();




                    }


                });



            }


        };
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();






    }






    public static class UsersViewHolder extends RecyclerView.ViewHolder

    {
        TextView UserName, UserEmail;
        ImageView Image;

        View view;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }


        public void setName(String name) {

            UserName = (TextView) view.findViewById(R.id.user_name);
            UserName.setText(name);

        }

        public void setEmail(String email)
            {
                UserEmail = (TextView) view.findViewById(R.id.Firend_user_Date);
                UserEmail.setText(email);
            }



        public void setProfileimage(String profileimage, Context context)
        {
            Image = (ImageView) view.findViewById(R.id.profile_image);
            Picasso.with(context).load(profileimage).into(Image);

        }




    }


}