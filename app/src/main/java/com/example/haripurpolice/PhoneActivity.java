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

public class PhoneActivity extends AppCompatActivity
{
    ImageView  PhonePhoto;
    EditText PhoneModel,PhoneName,PhoneEmei,PhnoeManufact,PhoneOwner;
    Button SaveInfo;
    private  final  static int gallerypic=1;
    private Uri ImageUri;
    private StorageReference storageReference;
    private String pdescription,RandomKey;
    private String SaveCurrentDate,SaveCurrentTime;
    private  String pname,DownloadImageUri;
    private DatabaseReference databaseReference,SellersRef;
    String phoneName;
    String phonemodel;
    String phoneEmi;
    String phoneManufactuer;
    String phoneOwnerNic;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        storageReference= FirebaseStorage.getInstance().getReference().child("Phone Images");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Phone Information");


        PhonePhoto=(ImageView) findViewById(R.id.smartphonephoto);
        PhoneModel=(EditText) findViewById(R.id.Phonemodel);
        PhoneName=(EditText) findViewById(R.id.PhoneInfoname);
        PhoneEmei=(EditText) findViewById(R.id.PhoneInfoImei);
        PhnoeManufact=(EditText) findViewById(R.id.PhoneInfoManufacturer);
        PhoneOwner=(EditText) findViewById(R.id.PhoneOwnerId);
        SaveInfo=(Button)findViewById(R.id.PhoneSaveButton);

        SaveInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                 ValidateInfo();

            }

        });

        PhonePhoto.setOnClickListener(new View.OnClickListener()
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
            PhonePhoto.setImageURI(ImageUri);
            ValidateInfo();
        }


    }


    private void ValidateInfo()
    {
        phoneName=PhoneName.getText().toString();
        phonemodel=PhoneModel.getText().toString();
        phoneEmi=PhoneEmei.getText().toString();
        phoneManufactuer=PhnoeManufact.getText().toString();
        phoneOwnerNic=PhoneOwner.getText().toString();



        if(ImageUri==null)
        {
            Toast.makeText(getApplicationContext(), "Please Choose Phone Image", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneName))
        {
            Toast.makeText(getApplicationContext(), "Please give Phone Name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phonemodel))
        {
            Toast.makeText(getApplicationContext(), "Please Set Phone odel", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEmi))
        {
            Toast.makeText(getApplicationContext(), "Please give Phone Emi", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneManufactuer))
        {
            Toast.makeText(getApplicationContext(), "Please give Phone Manufactuer ", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phoneOwnerNic))
        {
            Toast.makeText(getApplicationContext(), "Please give Phone Owner NIC", Toast.LENGTH_SHORT).show();
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
        phoneMap.put("phoneid", RandomKey);
        phoneMap.put("date", SaveCurrentDate);
        phoneMap.put("time", SaveCurrentTime);
        phoneMap.put("image", DownloadImageUri);
        phoneMap.put("pemei",PhoneEmei.getText().toString());
        phoneMap.put("pmodel", PhoneModel.getText().toString());
        phoneMap.put("ponic", PhoneOwner.getText().toString());
        phoneMap.put("pname", PhoneName.getText().toString());
        phoneMap.put("pcreator", PhnoeManufact.getText().toString());

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

                            Toast.makeText(getApplicationContext(), "PHONE Info is added successfully..", Toast.LENGTH_SHORT).show();
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











