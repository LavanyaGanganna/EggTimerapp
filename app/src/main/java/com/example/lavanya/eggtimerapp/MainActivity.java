package com.example.lavanya.eggtimerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView textView;
    boolean counterisactive=false;
    Button controlbutton;
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         textView = (TextView) findViewById(R.id.texttimer);
        controlbutton= (Button) findViewById(R.id.controlbutton);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(600);//10 minutes;10*60
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int minutes = i / 60;
                int secondsleft = i - minutes * 60;
                String secondstring = Integer.toString(secondsleft);
                if (secondstring == "0") {
                    secondstring = "00";
                }
                textView.setText(Integer.toString(minutes) + ":" + secondstring);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
        public void controltimer(View view){
            if(counterisactive ==false) {
                counterisactive=true;
                seekBar.setEnabled(false);
                controlbutton.setText("Stop");
                countDownTimer=new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) { //+100 fix for little delay in countdown//multiply by 1000 for millisecond
                    @Override
                    public void onTick(long l) {

                        int minutes = (int) (l / 1000) / 60;
                        int secondsleft = (int) (l / 1000) - (minutes * 60);
                        String secondstring = Integer.toString(secondsleft);
                        if (secondsleft <= 9) {
                            secondstring = "0" + secondstring;
                        }
                        textView.setText(Integer.toString(minutes) + ":" + secondstring);
                    }

                    @Override
                    public void onFinish() {
                        textView.setText("0:00");
                        seekBar.setEnabled(true);
                        seekBar.setProgress(30);
                        //  textView.setText("0:30");
                        countDownTimer.cancel();
                        controlbutton.setText("Go");
                        counterisactive=false;
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                        mediaPlayer.start();
                    }

                }.start();
            }
            else{
                seekBar.setEnabled(true);
                seekBar.setProgress(30);
              //  textView.setText("0:30");
                countDownTimer.cancel();
                controlbutton.setText("Go");
                counterisactive=false;
            }
    }

}
