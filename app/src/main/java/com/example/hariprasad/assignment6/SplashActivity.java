package com.example.hariprasad.assignment6;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout innerLayout = new LinearLayout(this);
       // innerLayout.setBackgroundResource(R.drawable.lovebirds);

        Button launchButton = new Button(this);
        launchButton.setBackgroundColor(Color.WHITE);
        launchButton.setTextColor(Color.parseColor("#f23262"));
        launchButton.setText(" Play");
        launchButton.setTextSize(40.0f);
        launchButton.setPadding(0, 0, 0, 50);
        launchButton.setOnClickListener(buttonListener);

        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        buttonLayout.addView(launchButton);

        FrameLayout outerLayout = new FrameLayout(this);
        outerLayout.addView(innerLayout);
        outerLayout.addView(buttonLayout);

        setContentView(outerLayout);


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    public View.OnClickListener buttonListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            Intent launchIntent = new Intent(getApplicationContext(),SoundActivity.class);
            startActivity(launchIntent);
        }
    };
}
