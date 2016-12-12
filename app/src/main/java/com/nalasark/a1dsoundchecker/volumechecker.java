package com.nalasark.a1dsoundchecker;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class volumechecker extends AppCompatActivity {

    double global_min = 25;
    double global_max = 500;
    boolean mic1 = false;
    boolean mic2 = false;
    double[] mic_min = {0,0};
    double[] mic_max = {0,0};

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://10.13.38.141:33");
        } catch (URISyntaxException e) {
            System.out.println("socket connection error");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volumechecker);

        //!! SET INSTRUMENT NAME FROM SELECTED LIST

        /////BAR 1 MIN & MAX lines
        mic_min[0] = 60; //!! SET VALUE FROM PRESET
        mic_max[0] = 300; //!! SET VALUE FROM PRESET

        int percent1_min = (int) ((mic_min[0]-global_min)/(global_max-global_min)*100);
        int percent1_max = (int) ((mic_max[0]-global_min)/(global_max-global_min)*100);

        final View min1 = findViewById(R.id.lowerbound1); //translate lower bound line
        ObjectAnimator translatemin1 = ObjectAnimator.ofFloat(min1, "TranslationY", dpToPx(-1*percent1_min)).setDuration(200);
        translatemin1.start();
        final View max1 = findViewById(R.id.upperbound1); //translate upper bound line
        ObjectAnimator translatemax1 = ObjectAnimator.ofFloat(max1, "TranslationY", dpToPx(-1*percent1_max)).setDuration(200);
        translatemax1.start();

        /////BAR 2 MIN & MAX lines
        mic_min[1] = 100; //!! SET VALUE FROM PRESET
        mic_max[1] = 200; //!! SET VALUE FROM PRESET

        int percent2_min = (int) ((mic_min[1]-global_min)/(global_max-global_min)*100);
        int percent2_max = (int) ((mic_max[1]-global_min)/(global_max-global_min)*100);

        final View min2 = findViewById(R.id.lowerbound2); //translate lower bound line
        ObjectAnimator translatemin2 = ObjectAnimator.ofFloat(min2, "TranslationY", dpToPx(-1*percent2_min)).setDuration(200);
        translatemin2.start();
        final View max2 = findViewById(R.id.upperbound2); //translate upper bound line
        ObjectAnimator translatemax2 = ObjectAnimator.ofFloat(max2, "TranslationY", dpToPx(-1*percent2_max)).setDuration(200);
        translatemax2.start();

        //socket updates
        mSocket.on("newData", updateMic);
        mSocket.connect();
        System.out.println("Start Connection");
        mSocket.emit("clientConnect", "begin connection");

        //button: done
        Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Start = new Intent(volumechecker.this, volumechecker.class);
                startActivity(Start);
            }
        });
    }

    private Emitter.Listener updateMic = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            System.out.println("DATA RECEIVED1");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
/*                    JSONObject data = (JSONObject) args[0];
                    double amplitude1 = 0.0;
                    double average1 = 0.0;
                    double amplitude2 = 0.0;
                    double average2 = 0.0;
                    try {
                        amplitude1 = Double.parseDouble(data.getString("amplitude1"));
                        average1 = Double.parseDouble(data.getString("average1"));
                        amplitude2 = Double.parseDouble(data.getString("amplitude2"));
                        average2 = Double.parseDouble(data.getString("average2"));
                    } catch (JSONException e) {
                        return;
                    }*/
                    System.out.println("DATA RECEIVED2");
                    String data = args[0].toString();
                    String[] parts = data.split(",");
                    String data1 = parts[0];
                    String data2 = parts[1];

                    double amplitude1 = 0.0;
                    double amplitude2 = 0.0;

                    amplitude1 = Double.parseDouble(data1);
                    amplitude2 = Double.parseDouble(data2);

                    update(amplitude1,amplitude2);
                }
            });
        }
    };

    public void update(double amp1, double amp2) {

        ///// --- MIC 1
        int percent1 = (int) ((amp1-global_min)/(global_max-global_min)*100);
        int percent1_min = (int) ((mic_min[0]-global_min)/(global_max-global_min)*100);
        int percent1_max = (int) ((mic_max[0]-global_min)/(global_max-global_min)*100);

        // update textview
        final TextView value1 = (TextView) findViewById(R.id.value1);
        value1.setText(Integer.toString((int) amp1)); //update displayed amp value

        // translate bargraph
        final View bar1 = findViewById(R.id.bar1);
        ObjectAnimator translatebar1 = ObjectAnimator.ofFloat(bar1, "TranslationY", dpToPx(-1 * percent1)).setDuration(200); //animate height
        translatebar1.start();
        // set color
        bar1.setBackgroundColor(Color.parseColor(generateColor(percent1_min, percent1_max, percent1))); //set color

        ///// --- MIC 2
        int percent2 = (int) ((amp2-global_min)/(global_max-global_min)*100);
        int percent2_min = (int) ((mic_min[1]-global_min)/(global_max-global_min)*100);
        int percent2_max = (int) ((mic_max[1]-global_min)/(global_max-global_min)*100);

        // update textview
        final TextView value2 = (TextView) findViewById(R.id.value2);
        value2.setText(Integer.toString((int) amp2)); //update displayed amp value

        // translate bargraph
        final View bar2 = findViewById(R.id.bar2);
        ObjectAnimator translatebar2 = ObjectAnimator.ofFloat(bar2, "TranslationY", dpToPx(-1 * percent2)).setDuration(200); //animate height
        translatebar2.start();
        // set color
        bar2.setBackgroundColor(Color.parseColor(generateColor(percent2_min, percent2_max, percent2))); //set color
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public String generateColor(int min, int max, int value) {
        int R = 61; int G = 249; int B = 249;
        int upper1 = (max+min)/2 + (max-min)/4; int upper2 = max + (max-min);
        int lower1 = (max+min)/2 - (max-min)/4; int lower2 = min - (max-min);

        if (value >= upper2) {
            R = 249; G = 61; B = 61; //red
            System.out.println("red");
        } else if (value > upper1 && value < upper2) { //green to red
            double percent = (value-upper1)/(double)(upper2-upper1);
            if (percent < 0.5) { R = (int) (61+percent/0.5*(249-61)); G = 249; B = 61; } //green to yellow
            else { R = 249; G = (int)(249 - (percent-0.5)/0.5*(249-61)); B = 61; } //yellow to red
            System.out.println("green to red");
        } else if (value >= lower1 && value <= upper1) { //green
            R = 61; G = 249; B = 61;
            System.out.println("green");
        } else if (value < lower1 && value > lower2) { //blue to green
            double percent = (value-lower2)/(double)(lower1-lower2);
            if (percent < 0.5) { R = 61; G = (int)(61+percent/0.5*(249-61)); B = 249; } //green to light blue
            else { R = 61; G = 249; B = (int) (249 - (percent-0.5)/0.5*(249-61)); } //light blue to dark blue
            System.out.println("blue to green");
        } else { //blue
            R = 61; G = 61; B = 249;
            System.out.println("blue");
        }

        String hex = String.format("#%02x%02x%02x", R, G, B);
        return hex;
    }



}



/*    public int get_rand1() {
        if (count == 1){
            count --;
            return 70;
        }
        return rand.nextInt(11);
    }

    public int get_rand2() {
        if (count == 1){
            count --;
            return 40;
        }
        return rand.nextInt(11);
    }

    public int randsign() {
        return rand.nextInt(2);
    }*/

/*        //----RANDOMISER DATA THREAD ----
                                    Thread t = new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                while (!isInterrupted()) {
                                                    Thread.sleep(200);
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            int num1 = Integer.parseInt(value1.getText().toString()); //get height value
                                                            int RAND = get_rand1(); //generate random increment
                                                            if (num1 + RAND > 100) {
                                                                num1 = num1 - RAND;
                                                            } else if (num1 - RAND < 0) {
                                                                num1 = num1 + RAND;
                                                            } else {
                                                                int sign = randsign();
                                                                if (sign == 0) {sign = -1;}
                                                                num1 = num1 + sign * RAND;
                                                            }

                                                            value1.setText(Integer.toString(num1)); //update height value
                                                            final View bar1 = findViewById(R.id.bar1);
                                                            ObjectAnimator translatebar1 = ObjectAnimator.ofFloat(bar1, "TranslationY", dpToPx(-1*num1)).setDuration(200); //animate height
                                                            translatebar1.start();
                                                            bar1.setBackgroundColor(Color.parseColor( generateColor(bar1_min, bar1_max, num1) )); //set color


                                                            int num2 = Integer.parseInt(value2.getText().toString()); //get height value
                                                            RAND = get_rand2(); //generate random increment
                                                            if (num2 + RAND > 100) {
                                                                num2 = num2 - RAND;
                                                            } else if (num2 - RAND < 0) {
                                                                num2 = num2 + RAND;
                                                            } else {
                                                                int sign = randsign();
                                    if (sign == 0) {sign = -1;}
                                    num2 = num2 + sign * RAND;
                                }

                                value2.setText(Integer.toString(num2)); //update height value
                                final View bar2 = findViewById(R.id.bar2);
                                ObjectAnimator translatebar2 = ObjectAnimator.ofFloat(bar2, "TranslationY", dpToPx(-1*num2)).setDuration(200); //animate height
                                translatebar2.start();
                                bar2.setBackgroundColor(Color.parseColor( generateColor(bar2_min, bar2_max, num2) )); //set color
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();*/