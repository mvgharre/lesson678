package com.example.hariprasad.assignment6;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Created by Hariprasad on 12/17/2015.
 */
public class SoundActivity extends Activity{

    GameView gv;
    private MediaPlayer song2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        gv = new GameView(this);

        FrameLayout outerLayout = new FrameLayout(this);
        outerLayout.addView(gv);
        setContentView(outerLayout);
    }


    public void allocateSounds()
    {
        if(song2 == null)
            song2 = MediaPlayer.create(this.getBaseContext(), R.raw.lordofrings);
      //  song2.setVolume(0.2f, 0.2f);
        song2.setOnPreparedListener(song2PListener);
        song2.setOnCompletionListener(song2CListener);
    }

    private MediaPlayer.OnPreparedListener song2PListener = new MediaPlayer.OnPreparedListener()
    {
        @Override
        public void onPrepared(MediaPlayer mp)
        {
            playSong2();
        }
    };

    private MediaPlayer.OnCompletionListener song2CListener = new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mp)
        {
            playSong2();
        }
    };

    public void playSong2()
    {
        if (song2.isPlaying())
        {
            song2.pause();
        }
        if(song2.getCurrentPosition() != 1)
        {
            song2.seekTo(1);
        }
        song2.start();
    }

    public void deallocateSounds()
    {

        if (song2.isPlaying())
        {
            song2.stop();
        }

        if (!(song2 == null))
        {
            song2.release();
            song2 = null;
        }
        song2CListener = null;
        song2PListener = null;
    }

    protected void onResume()
    {
        super.onResume();
        allocateSounds();
        gv.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        deallocateSounds();
        gv.onPause();
    }
}
