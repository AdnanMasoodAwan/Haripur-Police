package com.example.haripurpolice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class CriButtonsActivity extends AppCompatActivity
{

    Animation Top;
    ImageView imageView;
    Button AddRecord,ShowRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cri_buttons);

        Top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        // Bottom= AnimationUtils.loadAnimation(this,R.anim.bot_animation);

        imageView=(ImageView)findViewById(R.id.imageViewCri);
        AddRecord=(Button)findViewById(R.id.AdminButtonCri);
        ShowRecord=(Button)findViewById(R.id.UserButtonCri);


        imageView.setAnimation(Top);


        AddRecord.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), HabitualCriminalActivity.class);
                startActivity(intent);
            }
        });


        ShowRecord.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), UserHabitualCriminalActivity.class);
                startActivity(intent);
            }

        });




    }
}