package com.example.hariprasad.assignment6;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by hari on 12/3/2015.
 */
public class GameView extends SurfaceView {
    SurfaceHolder holder;

    Bitmap mainCat;
    private int score = 0;
    private long timer = 0;
    private Paint scorePaint;
    // horizontal position (graphic is 205 pixels wide thus initialize right edge of graphic fall to left screen edge)
    private float catX = 205.0f;
    private float catY = 100.0f;
    private GameThread gthread = null;
    int min = 100;
    int max = 300;
    int min1 = 100;
    int max2 = 500;
    int startTime = 20000;
    int intervalTime = 1000;
    CountDownTimer maintimer;

    public int getScore()
    {
        return score;
    }

    public void setScore(int scoreIn)
    {
        score = scoreIn;
    }

   public  void  GameView ()
    {}

    public GameView(final Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mainCat = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
                mainCat=Bitmap.createScaledBitmap(mainCat, 100, 100, false);
                scorePaint = new Paint();
                scorePaint.setColor(Color.BLACK);
                scorePaint.setTextSize(50.0f);
                makeThread();
                gthread.setRunning(true);
                gthread.start();

                maintimer = new CountDownTimer(startTime, intervalTime) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = millisUntilFinished / 1000;

                    }

                    @Override
                    public void onFinish() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Game over!!!!");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                score = 0;
                                makeThread();
                                maintimer.start();
                                gthread.start();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                killThread();
                                ((Activity) context).finish();
                            }
                        });
                        builder.show();
                    }
                };
                maintimer.start();

                gthread.setRunning(true);
                gthread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }


        });
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //  catX = catX + 4.0f;


        if (catX > getWidth()) {
            catX = -205.0f;
        }


        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(mainCat, catX, catY, null);
        canvas.drawText("Score: " + String.valueOf(score), 10.0f, 50.0f, scorePaint);
        canvas.drawText("TIME:" + timer, getWidth() - 200.0f, 50.0f, scorePaint);

    }

    public void makeThread() {
        gthread = new GameThread(this);

    }

    public void killThread() {
        boolean retry = true;
        gthread.setRunning(false);
        while (retry) {
            try {
                gthread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public void onDestroy() {
        mainCat.recycle();
        mainCat = null;
        System.gc();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        Random r = new Random();
        float i1 = r.nextInt(max - min + 1) + min;
        float i2 = r.nextInt(max - min + 1) + min;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (x > catX && x < catX + mainCat.getWidth() && y > catY && y < catY + mainCat.getHeight()) {
                Log.e("Coordinate", i1 + " " + i2);
                catX = i1;
                catY = i2;

                score++;
                return true;
            }
        }
        return false;
    }



    protected void onResume()
    {
        makeThread();

    }


    protected void onPause()
    {
        killThread();

    }


}
