package com.example.haripurpolice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.sql.Ref;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.text.Regex;

public class AdminSignupActivity extends AppCompatActivity
{

    Button SignUp,PhoneSignUp;
    TextView AlreadyHaveAccount;
    EditText Name,Email,Password,CNIC,Phone;
    FirebaseAuth Auth;
    String currentUserId,type;
    int CheckRadioButtonId;
    CircleImageView ProfileImage;
    private DatabaseReference AdminsRef,UsersRef;
    private DatabaseReference AdminRef;
    String Type="Admin";
    String Type1="User";


    String SaveCurrentDate,SaveCurrentTime,RandomPostNam;


    RadioGroup radioGroup;
    RadioButton Admin,User;


    CircleImageView Image;
    Button SaveInfo;
    private  final  static int gallerypic=1;
    private Uri ImageUri;
    private StorageReference storageReference;
    private String pdescription,RandomKey;
    private  String pname,DownloadImageUri;
    private DatabaseReference databaseReference,SellersRef;
    String address;






     @Override
    protected void onCreate(Bundle savedInstanceState)
     {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_admin_signup);

         Auth = FirebaseAuth.getInstance();
        // currentUserId=Auth.getCurrentUser().getUid();
         AdminsRef = FirebaseDatabase.getInstance().getReference().child("Admins");
         AdminRef = FirebaseDatabase.getInstance().getReference();
         UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
         storageReference = FirebaseStorage.getInstance().getReference().child("Admin Profile Images");


         Name = (EditText) findViewById(R.id.AdminName);
         Phone = (EditText) findViewById(R.id.AdminPhoneNo);
         Password = (EditText) findViewById(R.id.AdminPassword);
         Email = (EditText) findViewById(R.id.AdminEmail);
         CNIC = (EditText) findViewById(R.id.AdminCNIC);
         SignUp = (Button) findViewById(R.id.SignupButton);
         PhoneSignUp = (Button) findViewById(R.id.PhoneSignUpButton);
         AlreadyHaveAccount = (TextView) findViewById(R.id.AdminAlreadyhaveAccount);
         radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
         Admin = (RadioButton) findViewById(R.id.Admin);
         User = (RadioButton) findViewById(R.id.User);
         ProfileImage = (CircleImageView) findViewById(R.id.AdimageView);



         PhoneSignUp.setOnClickListener(new View.OnClickListener()
         {

             @Override
             public void onClick(View view)
             {
                 Intent intent = new Intent(getApplicationContext(), PhoneLoginActivity.class);
                 startActivity(intent);
             }

         });



         ProfileImage.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view) {
                 Intent galleryintent = new Intent();
                 galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                 galleryintent.setType("image/*");
                 startActivityForResult(galleryintent, gallerypic);


             }
         });


         SignUp.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view)
             {

                 CheckRadioButtonId = radioGroup.getCheckedRadioButtonId();
                 switch (CheckRadioButtonId)
                 {
                     case R.id.Admin:

                         ValidateInfo();
                         //ValidateInfoForAdmin();

                              /*
                         AdminsRef.addValueEventListener(new ValueEventListener()
                         {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot)
                             {

                                 if (snapshot.exists())
                                 {



                                     String name1 = Name.getText().toString();
                                     String email1 = Email.getText().toString();//
                                     // String password1 = Password.getText().toString();
                                     String cnic1 = CNIC.getText().toString();

                                     String nam = String.valueOf(snapshot.child(currentUserId).child("name").getValue());
                                     String emai = String.valueOf(snapshot.child(currentUserId).child("email").getValue());
                                     String cni = String.valueOf(snapshot.child(currentUserId).child("cnic").getValue());
                                     String typ = String.valueOf(snapshot.child(currentUserId).child("type").getValue());
                                     //String Pic = snapshot.child("profileimage").getValue().toString();


                                     Toast.makeText(getApplicationContext(), "" + currentUserId, Toast.LENGTH_SHORT).show();
                                     Toast.makeText(getApplicationContext(), "" + nam, Toast.LENGTH_SHORT).show();
                                     Toast.makeText(getApplicationContext(), "" + emai, Toast.LENGTH_SHORT).show();
                                     Toast.makeText(getApplicationContext(), "" + cni, Toast.LENGTH_SHORT).show();
                                     Toast.makeText(getApplicationContext(), "" + typ, Toast.LENGTH_SHORT).show();




                                     if (name1.equals(nam)   && cnic1.equals(cni) && typ.equals("Admin"))
                                     {
                                         Toast.makeText(getApplicationContext(), "You are Admin", Toast.LENGTH_SHORT).show();
                                         ValidateInfoForAdmin();
                                         ValidateInfo();

                                     }
                                     else {

                                         Toast.makeText(getApplicationContext(), "Sorry you are not autohrized to Use as Admin ", Toast.LENGTH_SHORT).show();

                                     }


                                 }
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {

                             }

                         } ); */







                         break;

                     case R.id.User:


                         SavePhotoToStorageForUser();
                         break;
                 }


             }

         });


         AlreadyHaveAccount.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view)
             {
                 Intent intent = new Intent(getApplicationContext(), AdminSignInActivity.class);
                 startActivity(intent);
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
            ProfileImage.setImageURI(ImageUri);
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


        final StorageReference filePath = storageReference.child(ImageUri.getLastPathSegment()+ ".jpg");

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
                            ValidateInfoForAdmin();
                        }
                    }
                });
            }
        });

    }










    private void ValidateInfoForAdmin()
    {
        String name=Name.getText().toString();
        String email=Email.getText().toString();
        String password=Password.getText().toString();
        String cnic=CNIC.getText().toString();
        String phone=Phone.getText().toString();


        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(cnic))
        {
            Toast.makeText(getApplicationContext(), "Please Enter CNIC", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Phone No", Toast.LENGTH_SHORT).show();
        }
        else
        {
            final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {


                      if((snapshot.child("Users").child(phone).exists()))
                      {
                          Users users=snapshot.child("Users").child(phone).getValue(Users.class);

                          if((users.getPhoneno().equals(phone) &&    (users.getPassword().equals(password))
                                  && (users.getCnic().equals(cnic))  &&     (users.getEmail().equals(email))
                                  &&  (users.getName().equals(name)) ) )
                          {



                                           HashMap<String,Object> Adminsmap=new HashMap<>();
                                           Adminsmap.put("currentuserid",phone);
                                           Adminsmap.put("name",name);
                                           Adminsmap.put("email",email);
                                           Adminsmap.put("phoneno",phone);
                                           Adminsmap.put("password",password);
                                           Adminsmap.put("cnic",cnic);
                                           Adminsmap.put("profileimage", DownloadImageUri);
                                           Adminsmap.put("type",Type);

                                           databaseReference.child("Users").child(phone).setValue(Adminsmap)
                                                   .addOnCompleteListener(new OnCompleteListener<Void>()
                                                   {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task)
                                                       {
                                                           if(task.isSuccessful())
                                                           {
                                                               Toast.makeText(getApplicationContext(), "Account Created SuccessFully", Toast.LENGTH_SHORT).show();
                                                               Toast.makeText(getApplicationContext(), "Welcome "+name, Toast.LENGTH_SHORT).show();
                                                               Intent intent=new Intent(getApplicationContext(),AdminSignInActivity.class);
                                                               startActivity(intent);

                                                           }
                                                           else
                                                           {
                                                               Toast.makeText(getApplicationContext(), "Sorry you are not Authorized", Toast.LENGTH_SHORT).show();
                                                           }

                                                       }

                                                   });


                                       }

                                     }




                      else
                      {
                          Toast.makeText(getApplicationContext(), "sorry ! This User "+phone+" not  Exsits you are not Admin ", Toast.LENGTH_SHORT).show();
                      }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }
            });



        }


    }




    private void SavePhotoToStorageForUser()
    {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        SaveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = currentTime.format(calendar.getTime());

        RandomKey=SaveCurrentDate+SaveCurrentTime;


        final StorageReference filePath = storageReference.child(ImageUri.getLastPathSegment()+ ".jpg");

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
                            ValidateInfoForUser();
                        }
                    }
                });
            }
        });

    }













    private void ValidateInfoForUser()
    {
        String name=Name.getText().toString();
        String email=Email.getText().toString();
        String password=Password.getText().toString();
        String cnic=CNIC.getText().toString();
        String phone= Phone.getText().toString();


        if(ImageUri==null)
        {
            Toast.makeText(getApplicationContext(), "Please Choose Image", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(), "Please Enter password", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(cnic))
        {
            Toast.makeText(getApplicationContext(), "Please Enter CNIC", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(getApplicationContext(), "Please Enter Phone No", Toast.LENGTH_SHORT).show();
        }
        else
        {

            final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if(!(snapshot.child("Users").child(phone).exists()))
                    {


                        HashMap<String,Object> Adminsmap=new HashMap<>();
                        Adminsmap.put("currentuserid",phone);
                        Adminsmap.put("name",name);
                        Adminsmap.put("email",email);
                        Adminsmap.put("phoneno",phone);
                        Adminsmap.put("password",password);
                        Adminsmap.put("cnic",cnic);
                        Adminsmap.put("profileimage", DownloadImageUri);
                        Adminsmap.put("type",Type1);

                        databaseReference.child("Users").child(phone).setValue(Adminsmap)
                                .addOnCompleteListener(new OnCompleteListener<Void>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(), "User Account Created SuccessFully", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getApplicationContext(), "Welcome "+name, Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(getApplicationContext(),AdminSignInActivity.class);
                                            startActivity(intent);

                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "Sorry you are not Authorized", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                });




                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(), "This User "+phone+" already  Exsits", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }

            });



        }


    }




}
















