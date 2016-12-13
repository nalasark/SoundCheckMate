package com.nalasark.a1dsoundchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Start = new Intent(MainActivity.this, Start.class);
                startActivity(Start);
            }
        });

        Button preset = (Button) findViewById(R.id.preset);
        preset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Preset = new Intent(MainActivity.this, Preset.class);
                startActivity(Preset);
            }
        });
        
    }

}
