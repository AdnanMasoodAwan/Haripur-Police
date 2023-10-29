package com.example.haripurpolice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class VansActivity extends AppCompatActivity
{
    ImageView VanPhoto;
    EditText Vanmodel,VanName,VanNumber,VanOwner,VanFirstOwner,VanOwnerIdCard;

    Button SaveInfo;
    private  final  static int gallerypic=1;
    private Uri ImageUri;
    private StorageReference storageReference;
    private String pdescription,RandomKey;
    private String SaveCurrentDate,SaveCurrentTime;
    private  String pname,DownloadImageUri;
    private DatabaseReference databaseReference,SellersRef;
    String  vanName;
    String  vanmodel;
    String  vanNumber;
    String  vanOwner;
    String  vanFirstOwner;
    String  vanOwnerIdCard;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vans);




        storageReference= FirebaseStorage.getInstance().getReference().child("Van Images");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Van Information");


        VanPhoto=(ImageView) findViewById(R.id.Vanphoto);
        VanName=(EditText)findViewById(R.id.VanName);
        Vanmodel=(EditText) findViewById(R.id.Vanmodel);
        VanNumber=(EditText) findViewById(R.id.Vannumber);
        VanOwner=(EditText) findViewById(R.id.VanOwner);
        VanFirstOwner=(EditText) findViewById(R.id.VanFirstOwner);
        VanOwnerIdCard=(EditText) findViewById(R.id.VanOwnerIdCard);
        SaveInfo=(Button)findViewById(R.id.VanSaveButton);

        SaveInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ValidateInfo();

            }

        });
        VanPhoto.setOnClickListener(new View.OnClickListener()
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

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==gallerypic && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            VanPhoto.setImageURI(ImageUri);
            ValidateInfo();
        }


    }


    private void ValidateInfo()
    {
        vanName=VanName.getText().toString();
        vanmodel=Vanmodel.getText().toString();
        vanNumber=VanNumber.getText().toString();
        vanOwner=VanOwner.getText().toString();
        vanFirstOwner=VanFirstOwner.getText().toString();
        vanOwnerIdCard=VanOwnerIdCard.getText().toString();


        if(ImageUri==null)
        {
            Toast.makeText(getApplicationContext(), "Please Choose Van Image", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vanName))
        {
            Toast.makeText(getApplicationContext(), "Please give  Van Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vanmodel))
        {
            Toast.makeText(getApplicationContext(), "Please Set  Van model", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vanNumber))
        {
            Toast.makeText(getApplicationContext(), "Please give Van  Number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vanOwner))
        {
            Toast.makeText(getApplicationContext(), "Please give  Van Owner ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vanFirstOwner))
        {
            Toast.makeText(getApplicationContext(), "Please give  Van First Owner ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(vanOwnerIdCard))
        {
            Toast.makeText(getApplicationContext(), "Please give  Van Owner Id Card ", Toast.LENGTH_SHORT).show();
        }

        else {
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
                Toast.makeText(getApplicationContext(), "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

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
        phoneMap.put("vanid", RandomKey);
        //phoneMap.put("uploadedby", Prevelant.currentOnlineUser.getName());
        phoneMap.put("date", SaveCurrentDate);
        phoneMap.put("time", SaveCurrentTime);
        phoneMap.put("image", DownloadImageUri);
        phoneMap.put("vanname", VanName.getText().toString());
        phoneMap.put("vannumber", VanNumber.getText().toString());
        phoneMap.put("vanmodel",   Vanmodel.getText().toString());
        phoneMap.put("vanowner",  VanOwner.getText().toString());
        phoneMap.put("vanfirstowner",  VanFirstOwner.getText().toString());
        phoneMap.put("vanowneridcard",  VanOwnerIdCard.getText().toString());




        databaseReference
                .child(RandomKey)
                .updateChildren(phoneMap)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(getApplicationContext(), PhoneActivity.class);
                            startActivity(intent);

                            Toast.makeText(getApplicationContext(), "Van Info Is Added Successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }







}