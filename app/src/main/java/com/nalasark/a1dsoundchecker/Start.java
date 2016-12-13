package com.nalasark.a1dsoundchecker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 6/12/2016.
 */

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        final ArrayList<String> instruments = new ArrayList<String>();

        final CheckBox vocals = (CheckBox)findViewById(R.id.checkBoxVocals);
        final CheckBox guitar = (CheckBox)findViewById(R.id.checkBoxGuitar);
        final CheckBox drums = (CheckBox)findViewById(R.id.checkBoxDrums);
        final CheckBox keyboard = (CheckBox)findViewById(R.id.checkBoxKeyBoard);

        vocals.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    instruments.add("vocals");
                } else {
                    instruments.remove("vocals");
                }
            }
        });

        guitar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    instruments.add("guitar");
                } else {
                    instruments.remove("guitar");
                }
            }
        });

        drums.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    instruments.add("drums");
                } else {
                    instruments.remove("drums");
                }
            }
        });

        keyboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    instruments.add("keyboard");
                } else {
                    instruments.remove("keyboard");
                }
            }
        });

        SharedPreferences sharedPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        final HashSet<String> empty = new HashSet<String>();
        final Set<String> instrumentData = sharedPref.getStringSet("Instruments",empty);

        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"Ayy lmao",Toast.LENGTH_SHORT).show();

                if (instruments.size()==0){
                    Toast.makeText(getBaseContext(),"Nothing has been selected!",Toast.LENGTH_SHORT).show();
                }
                else{
                    for (String instrument:instruments){
                        instrumentData.add(instrument);
                        editor.putStringSet("Instruments",instrumentData);
                        editor.commit();
                    }

                    Intent Start = new Intent(Start.this, volumechecker.class);
                    startActivity(Start);
                }

            }
        });

    }

}