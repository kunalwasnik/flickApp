package com.example.anew;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.Toast;

public class InterceptCall extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            Toast.makeText(context,"Ringing" ,Toast.LENGTH_SHORT).show();



        }catch (Exception e){

            e.printStackTrace();

        }



    }
}
