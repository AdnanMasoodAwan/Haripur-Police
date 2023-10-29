package com.example.haripurpolice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class AdminProfileActivity extends AppCompatActivity
{
    TextView Name,Email,CNIC,Phone;
    CircleImageView ProfileImage;
    Button Save;
    private DatabaseReference AdminRef;
    String CurrentUserId;
    String SaveCurrentDate,SaveCurrentTime,RandomPostName,downLoadUri;

    CircleImageView Image;
    Button SaveInfo;
    private  final  static int gallerypic=1;
    private Uri ImageUri;
    private StorageReference storageReference;
    private String pdescription,RandomKey;
    private  String pname,DownloadImageUri;
    private DatabaseReference databaseReference,SellersRef;
    String name;
    String address;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

       // CurrentUserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        AdminRef= FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference= FirebaseStorage.getInstance().getReference().child("Admin Profile Images");





        Name=(TextView) findViewById(R.id.AdminName);
        Phone=(TextView) findViewById(R.id.AdminPhone);
        ProfileImage=(CircleImageView) findViewById(R.id.Admin_Profile_Image);
        Email=(TextView) findViewById(R.id.AdminEmail);
        CNIC=(TextView) findViewById(R.id.AdminNIC);
        Save=(Button) findViewById(R.id.SaveAdminProfileButton);



        Save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Paper.book().destroy();
            }
        });


        Phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PhAlertDialog();
            }

        });

        Name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               NameAlertDialog();
            }

        });

        Email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AboutAlertDialog();
            }

        });

        CNIC.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PhoneAlertDialog();
            }

        });

        ProfileImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent galleryintent=new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,gallerypic);


            }
        });




        RetrieveAdminInfo();

    }


    private void RetrieveAdminInfo()
    {

          AdminRef.child(Prevelant.currentOnlineUser.getPhoneno())

         .addValueEventListener(new ValueEventListener()
          {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot)

              {

                     if(snapshot.exists())
                     {
                         String name=snapshot.child("name").getValue().toString();
                         String email=snapshot.child("email").getValue().toString();
                         String cnic=snapshot.child("cnic").getValue().toString();
                         String phone=snapshot.child("phoneno").getValue().toString();
                         String Pic=snapshot.child("profileimage").getValue().toString();

                         Name.setText(name);
                         Email.setText(email);
                         CNIC.setText(cnic);
                         Phone.setText(phone);

                         Picasso.with(getApplicationContext()).load(Pic).into(ProfileImage);


                     }

              }

              @Override
              public void onCancelled(@NonNull DatabaseError error)
              {

              }

          });
    }




    private void NameAlertDialog()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(AdminProfileActivity.this);
        builder.setTitle("Update youy Name");

        final EditText Input1=new EditText(AdminProfileActivity.this);
        builder.setView(Input1);
        builder.setIcon(R.drawable.edit_profile);
        Input1.setHint("Enter Your Name");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {


                HashMap<String,Object> UsersMap=new HashMap<>();
                UsersMap.put("name",Input1.getText().toString());


                AdminRef.child(Prevelant.currentOnlineUser.getPhoneno()).updateChildren(UsersMap);



            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }

        });
        builder.show();


    }

    private void AboutAlertDialog()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(AdminProfileActivity.this);
        builder.setTitle("Update youy Email");

        final EditText Input2=new EditText(AdminProfileActivity.this);
        builder.setView(Input2);
        builder.setIcon(R.drawable.edit_status);
        Input2.setHint("Enter Your Email");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {


                HashMap<String,Object> UsersMap=new HashMap<>();
                UsersMap.put("email",Input2.getText().toString());

                AdminRef.child(Prevelant.currentOnlineUser.getPhoneno()).updateChildren(UsersMap);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }

        });
        builder.show();

    }


    private void PhoneAlertDialog()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(AdminProfileActivity.this);
        builder.setTitle("Update youy CNIC");

        final EditText Input=new EditText(AdminProfileActivity.this);
        builder.setView(Input);
        builder.setIcon(R.drawable.phone);
        Input.setHint("Enter Your CNIC");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {



                HashMap<String,Object> UsersMap=new HashMap<>();
                UsersMap.put("cnic",Input.getText().toString());


                AdminRef.child(Prevelant.currentOnlineUser.getPhoneno()).updateChildren(UsersMap);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }

        });
        builder.show();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==gallerypic && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            ProfileImage.setImageURI(ImageUri);
            ValidateInfo();
        }


    }


    private void ValidateInfo()
    {



        if(ImageUri==null)
        {
            Toast.makeText(getApplicationContext(), "Please Choose Image", Toast.LENGTH_SHORT).show();
        }
        else
        {
            SavePhotoToStorage();
        }



    }




    private void SavePhotoToStorage()
    {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        SaveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = currentTime.format(calendar.getTime());

        RandomKey=SaveCurrentDate+SaveCurrentTime;


        final StorageReference filePath = storageReference.child(ImageUri.getLastPathSegment() + RandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(getApplicationContext(), "Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {

                            Toast.makeText(getApplicationContext(), ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }


                        DownloadImageUri = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            DownloadImageUri = task.getResult().toString();

                            Toast.makeText(getApplicationContext(), "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();
                            SaveInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveInfoToDatabase()
    {

        HashMap<String, Object> phoneMap = new HashMap<>();

        phoneMap.put("profileimage", DownloadImageUri);
      ;


        AdminRef
                .child(Prevelant.currentOnlineUser.getPhoneno())
                .updateChildren(phoneMap)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

                            Toast.makeText(getApplicationContext(), "Bike Info is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



    private void PhAlertDialog()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(AdminProfileActivity.this);
        builder.setTitle("Update youy Phone No");

        final EditText Input=new EditText(AdminProfileActivity.this);
        builder.setView(Input);
        builder.setIcon(R.drawable.phone);
        Input.setHint("Enter Your Phone");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {



                HashMap<String,Object> UsersMap=new HashMap<>();
                UsersMap.put("phoneno",Input.getText().toString());


                AdminRef.child(Prevelant.currentOnlineUser.getPhoneno()).updateChildren(UsersMap);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }

        });
        builder.show();

    }








}