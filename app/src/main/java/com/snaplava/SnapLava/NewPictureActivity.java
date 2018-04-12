package com.snaplava.SnapLava;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.snaplava.SnapLava.fields.DetailPaginator;
import com.snaplava.SnapLava.fields.Paginator;
import com.srx.widget.PullToLoadView;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class NewPictureActivity extends AppCompatActivity {
    PullToLoadView pullToLoadView;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_picture);
        getSupportActionBar().setTitle("Recent uploads");
        pullToLoadView = (PullToLoadView) findViewById(R.id.pullToLoad);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alert();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void Alert() {
        AlertDialog.Builder builder;
        final AlertDialog alertDialog;

        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.main_alert,
                null);
        ImageView id_cam = (ImageView) layout.findViewById(R.id.id_cam);
        ImageView id_gall = (ImageView) layout.findViewById(R.id.id_gall);
        builder = new AlertDialog.Builder(NewPictureActivity.this, R.style.CustomDialog);
        builder.setView(layout);

        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        ///wmlp.x = 100;   //x position
        wmlp.y = 100;   //y position

        alertDialog.getWindow().getAttributes().windowAnimations =
                R.style.dialog_animation;

        alertDialog.show();
        id_gall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),GallaryActivity.class));
            }
        });
        id_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),CameraActivity.class));
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
