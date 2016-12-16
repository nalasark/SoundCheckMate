package com.nalasark.a1dsoundchecker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.jar.Attributes;


/**
 * Created by user on 14/12/2016.
 */

public class Create_preset extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_preset);

        final SharedPreferences sharedPref = getSharedPreferences("Data", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        final HashSet<String> empty = new HashSet<String>();
        final String emptyString = new String();
        final String Presets = sharedPref.getString("Presets", emptyString);

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

                editor.putString("Presets", editedPresets);
                editor.commit();

                Intent Start = new Intent(Create_preset.this, Preset.class);
                startActivity(Start);
                finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private BufferedReader readFile(File f) {
        try{
            return new BufferedReader(new FileReader(f));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Create_preset Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
