package com.example.hariprasad.assignment6;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnClickListener {
    GameView gameView;
    private Button flowerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
////
        flowerButton = new Button(this);
        flowerButton.setWidth(350);
        flowerButton.setHeight(100);
        flowerButton.setBackgroundColor(Color.LTGRAY);
        flowerButton.setTextColor(Color.RED);
        flowerButton.setTextSize(20);
        flowerButton.setText("Give Flower");
        flowerButton.setOnClickListener(this);
        flowerButton.setGravity(Gravity.CENTER);
        FrameLayout GameLayout = new FrameLayout(this);
        LinearLayout buttonlayout = new LinearLayout(this);

        gameView = new GameView(this);

        // buttonlayout.addView(flowerButton);
        //    buttonlayout.addView(image);
        // buttonlayout.setBackgroundResource(R.drawable.background);

        GameLayout.addView(gameView);
        GameLayout.addView(buttonlayout);
        setContentView(GameLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("GopherPokeData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        String tempscore=Integer.toString( gameView.getScore() );
        editor.putString("GopherPokeScore", tempscore);

        editor.commit();

        super.onPause();

        gameView.killThread();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameView.onDestroy();
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    protected void onResume()
    {
        SharedPreferences prefs = getSharedPreferences("GopherPokeData",MODE_PRIVATE);
        String retrievedHighScore = prefs.getString("GopherPokeScore", "1");

        gameView.setScore( Integer.valueOf(retrievedHighScore) );

        super.onResume();
    }
}
