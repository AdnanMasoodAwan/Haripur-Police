package com.example.haripurpolice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Animatable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity
{
    Animation Top,Bottom;
    ImageView imageView;
    TextView Text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        Bottom= AnimationUtils.loadAnimation(this,R.anim.bot_animation);

        imageView=(ImageView)findViewById(R.id.imageView);
        Text=(TextView)findViewById(R.id.TextPolice);

        imageView.setAnimation(Top);;
        Text.setAnimation(Bottom);



        Thread thread=new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }

        };
        thread.start();


    }



    }