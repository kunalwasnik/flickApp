package com.example.anew;


import android.annotation.TargetApi;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.os.*;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.provider.Telephony;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;

import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView xText, yText, zText, xangle, yangle, zangle, other;
    private Sensor mysensor, anglesensor;
    private SensorManager sm, asm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create sensor manager

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        //asm = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Acceleration Service
        // mysensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mysensor = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        // register sensor Listener

        // sm.registerListener(this,mysensor,SensorManager.SENSOR_DELAY_FASTEST);
        sm.registerListener(this, mysensor, SensorManager.SENSOR_DELAY_NORMAL);


        xText = (TextView) findViewById(R.id.xText);
       /* yText  = (TextView)findViewById(R.id.yText);
        zText  = (TextView)findViewById(R.id.zText); */

        xangle = (TextView) findViewById(R.id.xangle);
        yangle = (TextView) findViewById(R.id.yangle);
        zangle = (TextView) findViewById(R.id.zangle);
        other = (TextView) findViewById(R.id.other);
        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {




                        try {
                            Runtime.getRuntime().exec("input keyevent " +
                                    Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));

                        } catch (IOException e) {
                            // Runtime.exec(String) had an I/O problem, try to fall back
                            String enforcedPerm = "android.permission.CALL_PRIVILEGED";
                            Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                                    Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                                            KeyEvent.KEYCODE_HEADSETHOOK));
                            Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                                    Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP,
                                            KeyEvent.KEYCODE_HEADSETHOOK));
                            Context mContext= getApplicationContext();
                            mContext.sendOrderedBroadcast(btnDown, enforcedPerm);
                            mContext.sendOrderedBroadcast(btnUp, enforcedPerm);
                            xText.setText("Hello Angle :" );
                        }





           }
       });



        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }


        } else {


        }


    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public int count = 0;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
      /*  if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            xText.setText("X : " + sensorEvent.values[0]);
            yText.setText("Y : " + sensorEvent.values[1]);
            zText.setText("Z : " + sensorEvent.values[2]);

        }*/
        float[] R = new float[9];
        float[] values = new float[3];
        SensorManager.getOrientation(R, values);
        DecimalFormat precision = new DecimalFormat("0.00");
// dblVariable is a number variable and not a String in this case
        //txtTextField.setText(precision.format(dblVariable));

       // sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR
        int azimuth , pitch;
        azimuth = (int)(sensorEvent.values[0]*57.2727) * -1;
        pitch = (int)(sensorEvent.values[1]*57.2727) * -1;

            xangle.setText("Azimuth Angle :" + azimuth );

            yangle.setText("Pitch Angle : "   + pitch);
            zangle.setText("Roll Angle: "  + precision.format(sensorEvent.values[2]*57.2727));
           // other.setText("Other Angle: " + sensorEvent.values[3]*57.2727);


            if(azimuth > 33 && azimuth < 50 || (azimuth*-1 > 33 && azimuth*-1 < 50)){
                //other.setText("KeepOn");

                        count++;
                        other.setText("Flicked at " + azimuth + " Times " + count);


                /*Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.gaana");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                } */
                Context mContext = getApplicationContext();

                try {
                    Runtime.getRuntime().exec("input keyevent " +
                            Integer.toString(KeyEvent.KEYCODE_HEADSETHOOK));
                } catch (IOException e) {
                    // Runtime.exec(String) had an I/O problem, try to fall back
                    String enforcedPerm = "android.permission.CALL_PRIVILEGED";
                    Intent btnDown = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                            Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                                    KeyEvent.KEYCODE_HEADSETHOOK));
                    Intent btnUp = new Intent(Intent.ACTION_MEDIA_BUTTON).putExtra(
                            Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP,
                                    KeyEvent.KEYCODE_HEADSETHOOK));
                    mContext= getApplicationContext();
                     mContext.sendOrderedBroadcast(btnDown, enforcedPerm);

                    mContext.sendOrderedBroadcast(btnUp, enforcedPerm);



                }

               /* if(count==1 ) {

                    count = 0;
                    xText.setText("Delay Started");


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Do something after 5s = 5000ms

                            xText.setText("Delay Finished");
                        }
                    }, 1000);
           try {
               //set time in mili
               Thread.sleep(500);

           }catch (Exception e){
               e.printStackTrace();
           }




                }*/



            }






    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

            switch (requestCode){

                case 1:{
                        if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                                 if(ContextCompat.checkSelfPermission(MainActivity.this ,
                                         Manifest.permission.READ_PHONE_STATE)==PackageManager.PERMISSION_GRANTED){

                                     Toast.makeText(this,"Permission granted" ,Toast.LENGTH_SHORT).show();

                                 }

                        }else{

                            Toast.makeText(this,"No Permission granted" ,Toast.LENGTH_SHORT).show();

                        }
                        return;

                }


            }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}

