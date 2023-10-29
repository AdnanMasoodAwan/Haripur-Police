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

public class LaptopActivity extends AppCompatActivity
{

    ImageView LaptopPhoto;
    EditText LaptopModel,LaptopRam,LaptopHardisk,LaptopProcessor,LaptopCompany,LaptopGeneration,LaptopOwnerNIC;
    Button SaveInfo;
    private  final  static int gallerypic=1;
    private Uri ImageUri;
    private StorageReference storageReference;
    private String pdescription,RandomKey;
    private String SaveCurrentDate,SaveCurrentTime;
    private  String pname,DownloadImageUri;
    private DatabaseReference databaseReference,SellersRef;
    String  laptopModel,laptopRam,laptopHardisk,laptopProcessor,laptopCompany,laptopGeneration,laptopOwnerNIC;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);


        storageReference= FirebaseStorage.getInstance().getReference().child("Laptop Images");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Laptop Information");


        LaptopPhoto=(ImageView) findViewById(R.id.Laptopphoto);
        LaptopCompany=(EditText)findViewById(R.id.LaptopCompany);
        LaptopModel=(EditText) findViewById(R.id.LaptopModel);
        LaptopGeneration=(EditText) findViewById(R.id.LaptopGeneration);
        LaptopProcessor=(EditText) findViewById(R.id.LaptopProcessor);
        LaptopRam=(EditText) findViewById(R.id.LaptopRam);
        LaptopHardisk=(EditText) findViewById(R.id.LaptopHarddisk);
        LaptopOwnerNIC=(EditText) findViewById(R.id.LaptopOwnerNic);
        SaveInfo=(Button)findViewById(R.id.LapotopSaveButton);

        SaveInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ValidateInfo();

            }

        });

        LaptopPhoto.setOnClickListener(new View.OnClickListener()
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
            LaptopPhoto.setImageURI(ImageUri);
            ValidateInfo();
        }


    }


    private void ValidateInfo()
    {
        laptopCompany=LaptopCompany.getText().toString();
        laptopGeneration=LaptopGeneration.getText().toString();
        laptopModel=LaptopModel.getText().toString();
        laptopHardisk=LaptopHardisk.getText().toString();
        laptopRam=LaptopRam.getText().toString();
        laptopProcessor=LaptopProcessor.getText().toString();
        laptopOwnerNIC=LaptopOwnerNIC.getText().toString();



        if(ImageUri==null)
        {
            Toast.makeText(getApplicationContext(), "Please Choose Laptop Image", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(laptopCompany))
        {
            Toast.makeText(getApplicationContext(), "Please give  Laptop Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(laptopModel))
        {
            Toast.makeText(getApplicationContext(), "Please Set  Laptop model", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(laptopGeneration))
        {
            Toast.makeText(getApplicationContext(), "Please give  Laptop Generation", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(laptopHardisk))
        {
            Toast.makeText(getApplicationContext(), "Please give  Laptop Harddisk ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(laptopRam))
        {
            Toast.makeText(getApplicationContext(), "Please give  Laptop Ra ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(laptopProcessor))
        {
            Toast.makeText(getApplicationContext(), "Please give  Laptop Id Processor ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(laptopOwnerNIC))
        {
            Toast.makeText(getApplicationContext(), "Please give  Laptop Owner Id Card ", Toast.LENGTH_SHORT).show();
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
        phoneMap.put("laptopid", RandomKey);
        phoneMap.put("date", SaveCurrentDate);
        phoneMap.put("time", SaveCurrentTime);
        phoneMap.put("image", DownloadImageUri);
        phoneMap.put("laptopname",LaptopCompany.getText().toString());
        phoneMap.put("laptopgeneration",LaptopGeneration.getText().toString());
        phoneMap.put("laptopram", LaptopRam.getText().toString());
        phoneMap.put("laptophardsik", LaptopHardisk.getText().toString());
        phoneMap.put("laptopprocessor", LaptopProcessor.getText().toString());
        phoneMap.put("laptopmodel", LaptopModel.getText().toString());
        phoneMap.put("laptopownernic", LaptopOwnerNIC.getText().toString());




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

                            Toast.makeText(getApplicationContext(), "Laptop Info Is Added Successfully..", Toast.LENGTH_SHORT).show();
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