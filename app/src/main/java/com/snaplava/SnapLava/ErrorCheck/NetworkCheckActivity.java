package com.snaplava.SnapLava.ErrorCheck;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.snaplava.SnapLava.R;

public class NetworkCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_network_check);
        getSupportActionBar().hide();
        Alert();

    }
    private void Alert() {
//Alert();
        AlertDialog.Builder builder;
        final AlertDialog alertDialog;

        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.alert_network,
                null);

        // TextView text = (TextView) layout.findViewById(R.id.text);

        builder = new AlertDialog.Builder(this);
        builder.setView(layout);

        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getAttributes().windowAnimations =
                R.style.dialog_animation;

        alertDialog.show();

    }
}
