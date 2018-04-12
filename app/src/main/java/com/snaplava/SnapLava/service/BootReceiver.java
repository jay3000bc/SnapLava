package com.snaplava.SnapLava.service;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.snaplava.SnapLava.R;

import java.io.IOException;

/**
 * Created by alegralabs on 27/02/18.
 */

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG="BootReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
try{
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
       // Bitmap tempbitMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.img);
        //Bitmap bitmap = Bitmap.createScaledBitmap(tempbitMap,width,height, true);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        wallpaperManager.setWallpaperOffsetSteps(1, 1);
        wallpaperManager.suggestDesiredDimensions(width, height);
        try {
            wallpaperManager.setBitmap(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }catch(Exception e){
        Log.e(TAG,e.toString());
    }
}
}
