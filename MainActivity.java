package com.deltorostudios.punchclockmockup;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    /* Variables for Layout Objects, for the Handler
        and for the variables we need for the time. */

    EditText time, time2, time3;
    Button punch, clock, reset;
    Boolean timer = true, resetBool = false;
    Handler myhandler;
    long longseconds, timeOnClick;
    int totalSeconds = 0, seconds = 0, minutes = 0, hours = 0, clockedTime;


    // onCreate method
    //
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Assigning Layout Object and Handler variables */
        punch = (Button) findViewById(R.id.buttonPunch);
        reset = (Button) findViewById(R.id.buttonReset);
        clock = (Button) findViewById(R.id.buttonClock);
        time = (EditText) findViewById(R.id.Time);
        time2 = (EditText) findViewById(R.id.Time2);
        time3 = (EditText) findViewById(R.id.Time3);
        myhandler = new Handler();

        /* Loads totalSeconds/timeOnClick from SharPref, or sets to 0 for default*/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        timeOnClick = preferences.getLong("timeOnClick", 0);
        totalSeconds = preferences.getInt("totalSeconds", 0);
        clockedTime = preferences.getInt("clockedTime", 0);


        /* Only is in use on initial startup of program
           Lists "00:00:00" in "Time" on creation, and the adjusted time on restoreInstance
           Will put a zero in front of each number in "hours:minutes:seconds"
           if it is less then 10, keeping the "00:00:00" format */
        if (timer = true) {
            if (seconds <= 9 && minutes <= 9 && hours <= 9) {
                time.setText("0" + hours + ":0" + minutes + ":0" + seconds);
            } else if (minutes <= 9 && hours <= 9) {
                time.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else if (seconds <= 9 && hours <= 9) {
                time.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else {
                time.setText(hours + ":" + minutes + ":" + seconds);
            }

            timer = false;
        }

        /* In use after onDestroy to updated time 3
           Lists "00:00:00" in "Time3" on creation, and the adjusted time on restoreInstance
           Will put a zero in front of each number in "hours:minutes:seconds"
           if it is less then 10, keeping the "00:00:00" format */
        if (clockedTime > 0) {
            if (seconds <= 9 && minutes <= 9 && hours <= 9) {
                time3.setText("0" + hours + ":0" + minutes + ":0" + seconds);
            } else if (minutes <= 9 && hours <= 9) {
                time3.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else if (seconds <= 9 && hours <= 9) {
                time3.setText("0" + hours + ":0" + minutes + ":" + seconds);

            } else {
                time3.setText(hours + ":" + minutes + ":" + seconds);
            }
        }

        /* Runs runny on restoreInstanceState
           (Which is automatically called to restore EditText widgets) */
        if (totalSeconds > 0) {

            myhandler.postDelayed(runny, 0);
        }


        /* Setting on-click functionality for "Punch In!" button
           Runs "runny" after a delay

           We grab SystemClock time value to subtract from the new one
           in runny, allowing us to get the value back to zero to begin counting
           elapsedRealtime() = time since the system as booted in millis */
        punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeOnClick = SystemClock.elapsedRealtime();
                myhandler.postDelayed(runny, 0);

            }
        });

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder altdial = new AlertDialog.Builder(MainActivity.this);
                altdial.setMessage("Are you sure you want to reset the timer?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                clockedTime = totalSeconds;

                                if (seconds <= 9 && minutes <= 9 && hours <= 9) {
                                    time3.setText("0" + hours + ":0" + minutes + ":0" + seconds);

                                } else if (minutes <= 9 && hours <= 9) {
                                    time3.setText("0" + hours + ":0" + minutes + ":" + seconds);

                                } else if (seconds <= 9 && hours <= 9) {
                                    time3.setText("0" + hours + ":0" + minutes + ":" + seconds);

                                } else {
                                    time3.setText(hours + ":" + minutes + ":" + seconds);
                                }

                                myhandler.removeCallbacks(runny);
                                timeOnClick = 0;
                                longseconds = 0;
                                totalSeconds = 0;
                                seconds = 0;
                                minutes = 0;
                                hours = 0;

                                time.setText("00:00:00");
                                time2.setText(" Seconds: " + totalSeconds);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = altdial.create();
                alert.setTitle("Dialog Header");
                alert.show();

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder altdial = new AlertDialog.Builder(MainActivity.this);
                altdial.setMessage("Are you sure you want to reset the timer?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myhandler.removeCallbacks(runny);
                                timeOnClick = 0;
                                longseconds = 0;
                                totalSeconds = 0;
                                seconds = 0;
                                minutes = 0;
                                hours = 0;

                                time.setText("00:00:00");
                                time2.setText(" Seconds: " + totalSeconds);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = altdial.create();
                alert.setTitle("Dialog Header");
                alert.show();


            }
        });

    }
    /* End of onCreate */

    // onDestroy Method - Used to save variables in SharPref
    //
    //
    @Override
    public void onDestroy() {
        super.onDestroy();


        storeTimeOnClick(timeOnClick);
        storeTotalSeconds(totalSeconds);
        storeHours(clockedTime);

    }


    // Runnable "runny"
    // Runner time baby, which allows for the timer, basically looping itself.
    // longseconds becomes zero, then .0001, then .0002, then .0003...
    //
    //
    public Runnable runny = new Runnable() {

        public void run() {

            longseconds = SystemClock.elapsedRealtime() - timeOnClick;

            // Converting millis to total seconds/minutes/hours
            totalSeconds = (int) (longseconds / 1000);
            minutes = (int) (longseconds / 60000);
            hours = minutes / 60;


            // Converting total seconds/minutes/hours into analog clock
            seconds = totalSeconds % 60;
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


            time2.setText(" Seconds: " + totalSeconds);

            myhandler.postDelayed(this, 0);
        }
    };

    // storeTimeOnClick Method - saves the time on click for "punch" button in SharPref
    //                           allowing us to keep track of the time past app termination
    //
    public void storeTimeOnClick(long z) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("timeOnClick", z);
        editor.apply();
    }

    // storeTotalSeconds Method - saves the total number of seconds for if statements and
    //                            and
    //
    public void storeTotalSeconds(int z) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("totalSeconds", z);
        editor.apply();
    }

    public void storeHours(int z) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("clockedTime", z);
        editor.apply();
    }
}
