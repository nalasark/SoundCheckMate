package com.nalasark.a1dsoundchecker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by user on 14/12/2016.
 */

public class Preset extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presets);

        final SharedPreferences sharedPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        final HashSet<String> empty = new HashSet<String>();
        final String emptyString = new String();
        final String Presets = sharedPref.getString("Presets",emptyString);


        final ListView listView = (ListView) findViewById(R.id.preset_list);

        ArrayList<String> list_final = new ArrayList<String>();
        final ArrayAdapter<String> listArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_final);
        listView.setAdapter(listArrayAdapter);

        if (Presets.isEmpty()) {
            listArrayAdapter.addAll("no presets.");
        } else {
            final String[] presets = Presets.split(",");
            listArrayAdapter.addAll(presets);
        }

        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Start = new Intent(Preset.this, Create_preset.class);
                startActivity(Start);
                finish();
            }
        });


    }

}
