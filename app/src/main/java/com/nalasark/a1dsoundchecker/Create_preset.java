package com.nalasark.a1dsoundchecker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by user on 14/12/2016.
 */

public class Create_preset extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_preset);

        final SharedPreferences sharedPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        final HashSet<String> empty = new HashSet<String>();
        final String emptyString = new String();
        final String Presets = sharedPref.getString("Presets",emptyString);

        final EditText name_in = (EditText) findViewById(R.id.name);
        final EditText width_in = (EditText) findViewById(R.id.width);
        final EditText breadth_in = (EditText) findViewById(R.id.breadth);

        Button generate = (Button) findViewById(R.id.generate);
            generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = name_in.getText().toString();
                String width = width_in.getText().toString();
                String breadth = breadth_in.getText().toString();

                String generated_val = "test";

                String input = name + " " + width + " " + breadth + " " + generated_val;
                String editedPresets;

                if (Presets.isEmpty()) {
                    editedPresets = input;
                } else {
                    editedPresets = Presets + "," + input;
                }

                editor.putString("Presets",editedPresets);
                editor.commit();

                Intent Start = new Intent(Create_preset.this, Preset.class);
                startActivity(Start);
                finish();
            }
        });
    }
}
