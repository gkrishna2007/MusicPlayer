package com.govind.krishnachander.musicplayer;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
Cursor cursor;
static int type=0;
TextView tv;
    ArrayAdapter<String> adapter;

    ListView lv;
    private List<String> name = new ArrayList<String>();
    private List<String> title = new ArrayList<String>();
    private List<String> artist = new ArrayList<String>();
    private List<String> duration = new ArrayList<String>();
    private List<String> location = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION
        };
        tv=(TextView) findViewById(R.id.search);
        cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null);

        while(cursor.moveToNext()) {
            if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)))>4500)
            {
                        artist.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                        title.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                        name.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
                        duration.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                        location.add(cursor.getString((cursor.getColumnIndex(MediaStore.MediaColumns.DATA))));

            }
        }
        lv=(ListView) findViewById(R.id.listView);
       adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,title);
        lv.setAdapter(adapter);

        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.this.adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                MediaPlayer mp = null;
                Intent intent=new Intent(MainActivity.this,MusicPlayer.class);

                Toast.makeText(getApplicationContext(), ""+l, Toast.LENGTH_SHORT).show();
                    intent.putExtra("pos", location.get(i));
                    intent.putExtra("i",i);
                    startActivity(intent);
            }

        });
            }

    }
