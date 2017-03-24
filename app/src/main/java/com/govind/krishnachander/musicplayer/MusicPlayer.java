package com.govind.krishnachander.musicplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MusicPlayer extends AppCompatActivity implements  View.OnClickListener,SeekBar.OnSeekBarChangeListener {
    MediaPlayer mp;
    SeekBar sb;
    TextView tdu,rtdu,np,artists;
    Thread progress,rem;
    ImageButton play;
    MediaMetadataRetriever mr;
    ImageView iv;
    @Override
    protected void onPause() {
        super.onPause();
        mp.reset();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_music_player);
        sb = (SeekBar) findViewById(R.id.seekBar);
        tdu = (TextView) findViewById(R.id.tdu);
        rtdu=(TextView) findViewById(R.id.rtdu) ;
        play = (ImageButton) findViewById(R.id.play);
        iv=(ImageView) findViewById(R.id.imageView);
        artists= (TextView) findViewById(R.id.artists);
        np=(TextView) findViewById(R.id.mywidget);

        Bundle b=getIntent().getExtras();
        Uri myuri=Uri.parse((String) b.get("pos"));
        mp=MediaPlayer.create(getApplicationContext(),myuri);
        mp.start();
        play.setImageResource(android.R.drawable.ic_media_pause);

        tdu.setText(ConvertToDuration(mp.getDuration()));

        sb.setMax(mp.getDuration());
        sb.setOnSeekBarChangeListener(this);
        play.setOnClickListener(this);
        tdu.setText(ConvertToDuration(mp.getDuration()));
        mr= new MediaMetadataRetriever();
        mr.setDataSource(this,myuri);
        np.setText(""+mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        artists.setText(""+mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        np.setSelected(true);
        artists.setSelected(true);
        byte[] art=mr.getEmbeddedPicture();
        if(art!=null) {

            Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
            iv.setImageBitmap(songImage);
        }

        progress = new Thread() {
            @Override
            public void run() {
                while(mp.getDuration()!=mp.getCurrentPosition())
                {
                    sb.setProgress(mp.getCurrentPosition());

                }
            }
        };progress.start();

    }


    String ConvertToDuration(long l)
    {   String duration="";
        long l1=l/1000;
        duration+=l1/60+":"+l1%60;
        return duration;
    }




    @Override
    public void onClick(View view) {
        if(!mp.isPlaying())
        {
            mp.start();
            play.setImageResource(android.R.drawable.ic_media_pause);
        }
        else
        {
            mp.pause();
            play.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if(b)
        {
            mp.seekTo(i);

        }
        long a=mp.getDuration()-mp.getCurrentPosition();

        rtdu.setText(ConvertToDuration(a));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
