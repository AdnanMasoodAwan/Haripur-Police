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

public class BikeActivity extends AppCompatActivity
{
    ImageView BikePhoto;
    EditText BikeModel,BikeName,BikeNumber,BikeOwner;
    Button SaveInfo;
    private  final  static int gallerypic=1;
    private Uri ImageUri;
    private StorageReference storageReference;
    private String pdescription,RandomKey;
    private String SaveCurrentDate,SaveCurrentTime;
    private  String pname,DownloadImageUri;
    private DatabaseReference databaseReference,SellersRef;
    String Bikename;
    String Bikemodel;
    String Bikenumber;
    String Bikeowner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);

        BikePhoto=(ImageView) findViewById(R.id.BikeImage);
        BikeModel=(EditText) findViewById(R.id.Bikemodel);
        BikeName=(EditText) findViewById(R.id.BikeCompany);
        BikeNumber=(EditText) findViewById(R.id.BikeNumber);
        BikeOwner=(EditText) findViewById(R.id.BikeOwner);
        SaveInfo=(Button)findViewById(R.id.BikeSaveButton);

        storageReference= FirebaseStorage.getInstance().getReference().child("Bike Images");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Bike Information");


        SaveInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ValidateInfo();

            }

        });

        BikePhoto.setOnClickListener(new View.OnClickListener()
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
            BikePhoto.setImageURI(ImageUri);
            ValidateInfo();
        }


    }


    private void ValidateInfo()
    {
        Bikename=BikeName.getText().toString();
        Bikemodel=BikeModel.getText().toString();
        Bikenumber=BikeNumber.getText().toString();
        Bikeowner=BikeOwner.getText().toString();



        if(ImageUri==null)
        {
            Toast.makeText(getApplicationContext(), "Please Choose Bike Image", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Bikename))
        {
            Toast.makeText(getApplicationContext(), "Please give Bike Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Bikemodel))
        {
            Toast.makeText(getApplicationContext(), "Please Set Bike odel", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Bikenumber))
        {
            Toast.makeText(getApplicationContext(), "Please give Bike number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Bikeowner))
        {
            Toast.makeText(getApplicationContext(), "Please give Bike Owner nae ", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Bike Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

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
        phoneMap.put("bikeid", RandomKey);
        phoneMap.put("date", SaveCurrentDate);
        phoneMap.put("time", SaveCurrentTime);
        phoneMap.put("image", DownloadImageUri);
       // phoneMap.put("uploadedby", Prevelant.currentOnlineUser.getName());
        phoneMap.put("bname",BikeName.getText().toString());
        phoneMap.put("bmodel", BikeModel.getText().toString());
        phoneMap.put("bnumber", BikeNumber.getText().toString());
        phoneMap.put("bowner", BikeOwner.getText().toString());

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






}