package com.example.haripurpolice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class VanButtonsActivity extends AppCompatActivity
{
    Animation Top;
    ImageView imageView;
    Button AddRecord,ShowRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_buttons);



        Top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        // Bottom= AnimationUtils.loadAnimation(this,R.anim.bot_animation);

        imageView=(ImageView)findViewById(R.id.imageViewVan);
        AddRecord=(Button)findViewById(R.id.AdminButtonVan);
        ShowRecord=(Button)findViewById(R.id.UserButtonVan);


        imageView.setAnimation(Top);


        AddRecord.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), VansActivity.class);
                startActivity(intent);
            }
        });


        ShowRecord.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), UserVanActivity.class);
                startActivity(intent);
            }

        });













    }
}