package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button timerButton;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;

    int sbMin = 1;
    int sbMax = 60;
    int sbStart = 3;

    boolean isButtonStart = true;

    CountDownTimer cTimer = null;


    public void startTimer() {
        cTimer = new CountDownTimer(sbStart * 10000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                output((int) millisUntilFinished, true);

            }

            @Override
            public void onFinish() {

                mediaPlayer.start();
                timerButton.setText("Reset");

            }
        };
        cTimer.start();
    }

    public void cancelTimer() {
        if (cTimer != null) {
            cTimer.cancel();
        }
    }

    public void buttonClicked(View view) {

        if (isButtonStart) {
            isButtonStart = false;
            timerButton.setText("STOP!");
            seekBar.setEnabled(false);
            startTimer();

        } else {
            isButtonStart = true;
            timerButton.setText("START!");
            seekBar.setEnabled(true);
            sbStart = 3;
            seekBar.setProgress(sbStart);
            output(sbStart, false);
            cancelTimer();

        }

    }

    public void output(int num, boolean onTick) {

        if (!onTick){
            num = num * 10000;
        }


        int minutes = (num / 1000) / 60;

        int seconds = (num / 1000) % 60;

        String min = Integer.toString(minutes);
        String sec;
        if (seconds == 0) {

            sec = "00";

        } else if (seconds < 10) {

            sec = "0" + seconds;

        } else {

            sec = Integer.toString(seconds);

        }

        String out = min + ":" + sec;

        textView.setText(out);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);

        timerButton = findViewById(R.id.timerButton);
        textView = findViewById(R.id.textView);

        seekBar = findViewById(R.id.seekBar);

        seekBar.setMin(sbMin);
        seekBar.setMax(sbMax);
        seekBar.setProgress(sbStart);

        output(sbStart, false);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                sbStart = progress;
                output(progress, false);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}