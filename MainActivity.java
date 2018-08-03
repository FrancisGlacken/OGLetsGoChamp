package com.deltorostudios.punchclockmockup;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /* Variables for Layout Objects, for the Handler
        and for the variables we need for the time. */

    EditText time, time2;
    Button punch, clock;
    Handler myhandler;
    long longseconds, SysClock;
    int seconds = 0, minutes = 0, hours = 0;



    /* onCreate method thing */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /* Assigning Layout Object and Handler variables */
        punch = (Button) findViewById(R.id.PunchIn);
        time = (EditText) findViewById(R.id.Time);
        time2 = (EditText) findViewById(R.id.Time2);
        myhandler = new Handler();


        /* Only is in use on initial startup of program
           Lists "00:00:00" in "Time" on creation, and the adjusted time on restoreInstance
           Will put a zero in front of each number in "hours:minutes:seconds"
           if it is less then 10, keeping the "00:00:00" format
         */

        if (savedInstanceState == null) {
            if (seconds <= 9 && minutes <= 9 && hours <= 9) {
                time.setText("0" + hours + ":0" + minutes + ":0" + seconds);
            } else if (minutes <= 9 && hours <= 9) {
                time.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else if (seconds <= 9 && hours <= 9) {
                time.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else {
                time.setText(hours + ":" + minutes + ":" + seconds);
            }

        }





        /* Setting on-click functionality for "Punch In!" button
           Runs "runny" after a delay

           We grab SystemClock time value to subtract from the new one
           in runny, allowing us to get the value back to zero to begin counting
         */
        punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SysClock = SystemClock.elapsedRealtime();
                myhandler.postDelayed(runny, 0);

            }
        });

        /* Runs runny on restoreInstanceState
           (Which is automatically called to restore EditText widgets) */
        if (savedInstanceState != null) {
            SysClock = SystemClock.elapsedRealtime();
            myhandler.postDelayed(runny, 0);

        }


    }
    /* End of onCreate */


    //Save Instance State
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
    }


    /* Runner time baby, which allows for the timer, basically looping itself.
       longseconds becomes zero, then .0001, then .0002, then .0003...

     */
    public Runnable runny = new Runnable() {

        public void run() {

            longseconds = SystemClock.elapsedRealtime() - SysClock;

            // Assigning accurate seconds/minutes/hours variables
            seconds = (int) (longseconds / 1000);
            minutes = (int) (longseconds / 60000);
            hours = minutes / 60;


            // Halting times at 60 EDIT THIS COMMENT
            seconds = seconds % 60;
            minutes = minutes % 60;

            if (minutes == 60) {
                hours = hours++;
                minutes = 0;
            }


            /* Starts the count in "Time" textView
                Will put a zero in front of each number in "hours:minutes:seconds"
                if it is less then 10, keeping the "00:00:00" format */
            if (seconds <= 9 && minutes <= 9 && hours <= 9) {
                time.setText("0" + hours + ":0" + minutes + ":0" + seconds);

            } else if (minutes <= 9 && hours <= 9) {
                time.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else if (seconds <= 9 && hours <= 9) {
                time.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else {
                time.setText(hours + ":" + minutes + ":" + seconds);
            }


            time2.setText("Milliseconds: " + longseconds);

            myhandler.postDelayed(this, 0);
        }
    };
}
