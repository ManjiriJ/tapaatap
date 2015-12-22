package com.tapaatap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;



public class FirstActivity extends Activity {

    private ViewFlipper viewFlipper;
    private float lastX;

    int accurate=0,topspeed=0,Enumber=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_first);


        SharedPreferences pre2 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = pre2.edit();

        editor.putInt("accurate",accurate);
        editor.putInt("topspeed",topspeed);
        editor.putInt("Enumber",Enumber);

        editor.apply();

        try{
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (prefs.getBoolean("first_time", false)) {
                Intent intenta = new Intent(".MainActivity");
                intenta.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intenta);
            }

        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
        addListenerOnButton();


    }

    public void addListenerOnButton() {
        Button button;
        button = (Button) findViewById(R.id.button_start);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SharedPreferences pre = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = pre.edit();
                editor.putBoolean("first_time", true);

                int device_id=0;
                //call for getDeviceID function
                editor.putInt("Device_ID", device_id);
                editor.apply();

                Intent intent = new Intent(".KeyboardSelectActivity");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });

    }

    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();

                // Handling left to right screen swap.
                if (lastX < currentX) {

                    // If there aren't any other children, just break.
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // Next screen comes in from left.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    // Current screen goes out from right.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);

                    // Display next screen.
                    viewFlipper.showNext();
                }

                // Handling right to left screen swap.
                if (lastX > currentX) {

                    // If there is a child (to the left), kust break.
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;

                    // Next screen comes in from right.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    // Current screen goes out from left.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);

                    // Display previous screen.
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;

    }
}
