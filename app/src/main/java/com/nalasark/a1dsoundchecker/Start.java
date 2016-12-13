package com.nalasark.a1dsoundchecker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
        final String emptyString = new String();
        final Set<String> instrumentData = sharedPref.getStringSet("Instruments",empty);

        final String presetData = sharedPref.getString("Presets",emptyString);

        List<String> PresetArraySpinner = new ArrayList<String>();
        if (presetData.isEmpty()) {
            PresetArraySpinner.add("No presets");
        } else {
            final List<String> presets = Arrays.asList(presetData.split(","));
            PresetArraySpinner.add("Choose a preset:");
            for (int i = 0; i < presets.size(); i++) {
                String[] values = presets.get(i).split(" ");
                PresetArraySpinner.add(values[0]);
            }
        }

        final Spinner Presets = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> spinner1ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PresetArraySpinner);
        spinner1ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Presets.setAdapter(spinner1ArrayAdapter);

        final String[] genres = {"acoustic","band"};
        List<String> GenreArraySpinner = new ArrayList<String>();
        GenreArraySpinner.add("Choose a genre:");
        GenreArraySpinner.addAll(Arrays.asList(genres));

        final Spinner Genres = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> spinner2ArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GenreArraySpinner);
        spinner2ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Genres.setAdapter(spinner2ArrayAdapter);

        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"Ayy lmao",Toast.LENGTH_SHORT).show();

                String genre = Genres.getSelectedItem().toString();
                String preset = Presets.getSelectedItem().toString();

                if (instruments.size()==0){
                    Toast.makeText(getBaseContext(),"No instrument selected!",Toast.LENGTH_SHORT).show();
                }
                else if (genre == "Choose a genre:") {
                    Toast.makeText(getBaseContext(),"No genre chosen!",Toast.LENGTH_SHORT).show();
                }
                else if (preset == "Choose a preset:" || preset == "No presets") {
                    Toast.makeText(getBaseContext(),"No preset chosen!",Toast.LENGTH_SHORT).show();
                }
                else{
                    for (String instrument:instruments){
                        instrumentData.add(instrument);
                        editor.putStringSet("Instruments",instrumentData);
                        editor.commit();
                    }
                    final List<String> presets = Arrays.asList(presetData.split(","));
                    for (int i = 0; i < presets.size(); i++) {
                        String[] values = presets.get(i).split(" ");
                        if (values[0] == preset) {
                            editor.putString("Preset",presets.get(i));
                            editor.commit();
                            break;
                        }
                    }
                    editor.putString("Genre",genre);
                    editor.commit();

                    Intent Start = new Intent(Start.this, volumechecker.class);
                    startActivity(Start);
                }

            }
        });

    }

}