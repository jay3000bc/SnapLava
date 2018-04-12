package com.snaplava.SnapLava;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snaplava.SnapLava.Adapters.Pager;
import com.snaplava.SnapLava.Adapters.collagepager;
import com.snaplava.SnapLava.customs.ClipArt;
import com.snaplava.SnapLava.frags.WriteFragment;
import com.snaplava.SnapLava.frags.layout.CustomDFragment;
import com.snaplava.SnapLava.frags.layout.HeartFragment;
import com.snaplava.SnapLava.frags.layout.VertCutFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollageActivity extends AppCompatActivity {
ArrayList<String> uriList;
float angle ;
ClipArt ca ;
String font,colorname;
TextView cancel_colage,proceed_colage;
File file;
LinearLayout relativeLayout;
boolean refered = true;
    private TabLayout tabLayout;
    int layout_position=0;
    String over = null;

    boolean layoutadd= false;
    ArrayList<Integer> arrayint = new ArrayList<>();
    ArrayList<String> aray = new ArrayList<>();
int color;
int thickness;



    int radius;
String collage_file  ;


    private ViewPager viewPager;
  RelativeLayout.LayoutParams  location = new RelativeLayout.LayoutParams(250,250);

    public boolean isRefered() {
        return refered;
    }

    public void setRefered(boolean refered) {
        this.refered = refered;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
     //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
relativeLayout = (LinearLayout) findViewById(R.id.layout_final);
       getSupportActionBar().hide();
       cancel_colage = (TextView) findViewById(R.id.cancel_colage);
     proceed_colage = (TextView) findViewById(R.id.proceed_colage);
       uriList = getIntent().getStringArrayListExtra("arraylist");
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutone);
        viewPager = (ViewPager) findViewById(R.id.pagerone);

        collage_file = System.currentTimeMillis() + ".jpg";
        arrayint.add(R.drawable.ic_action_one);
        arrayint.add(R.drawable.ic_action_two);
        arrayint.add(R.drawable.ic_action_three);
        arrayint.add(R.drawable.ic_action_four);
        arrayint.add(R.drawable.ic_action_five);
        aray.add("layout");
        aray.add("style");
        aray.add("text");
        aray.add("background");
        aray.add("watermark");
cancel_colage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     cancelCollage();

    }
});
        collagepager adapter = new collagepager(getSupportFragmentManager(),getApplicationContext(),uriList.size(), R.id.container_critical,uriList, getSupportFragmentManager());
         viewPager.setAdapter(adapter);
         viewPager.setCurrentItem(0);
         tabLayout.setupWithViewPager(viewPager);
        /*TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();*/
        getSupportFragmentManager().beginTransaction().add(R.id.container_critical, new VertCutFragment(uriList, 0), "layoutvertcut").commit();


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
             @Override
             public void onTabSelected(TabLayout.Tab tab) {
                 if(tab.getPosition()==2){

//if(getCa()== null) {
    getSupportFragmentManager().beginTransaction().add(R.id.container_critical, new WriteFragment(), "writefrag").commit();
//}
                     if(ca!= null) {
                         if (ca.getParent() != null) {
                             VertCutFragment vertCutFragment = (VertCutFragment) getSupportFragmentManager().findFragmentByTag("layoutvertcut");
                             vertCutFragment.removeViewca();
                         }
                     }

                 }else if (tab.getPosition() ==0 ){
                     VertCutFragment vertCutFragment = (VertCutFragment) getSupportFragmentManager().findFragmentByTag("layoutvertcut");
                   if (ca!= null){
                       if(layout_position!=3) {
                           vertCutFragment.addClipart();
                       }
                   }
                     }
             }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {
if(tab.getPosition()==2){
    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0);

    getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("writefrag")).commit();
}
             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

             }
         });

proceed_colage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       //------



        AlertDialog.Builder builder1 = new AlertDialog.Builder(CollageActivity.this);
        builder1.setMessage("Want to save the collage ??");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(CollageActivity.this);
                        builder.setTitle("would you like to share the collage?");

// add a radio button list
                        String[] animals = {"Facebook", "Twitter", "instagram"};
                        int checkedItem = 1; // cow

                        builder.setSingleChoiceItems(animals,checkedItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //WriteFragment write_frag = (WriteFragment) getSupportFragmentManager().findFragmentByTag("writefrag");
                                String share = null;
                               if (which==0){
                                   share = "facebook";
                               }else if (which==1){
                                   share = "twitter";
                               }else if (which ==2){
                                   share = "instagram";
                               }
                                if(getLayout_position() == 0 || getLayout_position() == 1 || getLayout_position() == 2){
                                    VertCutFragment fragment = (VertCutFragment) getSupportFragmentManager().findFragmentByTag("layoutvertcut");
                                    //  WriteFragment write_frag = (WriteFragment) getSupportFragmentManager().findFragmentByTag("writefrag");

                                    //if(write_frag== null){
                                    try {
                                        File file = fragment.getfileCrop();
                                      // Uri uri = Uri.fromFile(file);
                                        Download_file(BitmapFactory.decodeFile(file.getPath()));

                                        Intent intent = new Intent(getApplicationContext(), ShareIActivity.class);
                                       intent.putExtra("share", share);
                                       intent.putExtra("uri", collage_file);
                                       startActivity(intent);

                                         finish();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }else if (getLayout_position() ==3){
                                    CustomDFragment fragment = (CustomDFragment) getSupportFragmentManager().findFragmentByTag("customlay");
                                    try {
                                        File file = fragment.getfileCrop();
                                        Download_file(BitmapFactory.decodeFile(file.getPath()));
                                        //Uri uri = Uri.fromFile(file);
                                       Intent intent = new Intent(getApplicationContext(), ShareIActivity.class);
                                        intent.putExtra("share", share);
                                      intent.putExtra("uri",collage_file);
                                       startActivity(intent);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                // user checked an item
                            }
                        });

// add OK and Cancel buttons

                        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(getLayout_position() == 0 || getLayout_position() == 1 || getLayout_position() == 2){
                                    VertCutFragment fragment = (VertCutFragment) getSupportFragmentManager().findFragmentByTag("layoutvertcut");
                                      WriteFragment write_frag = (WriteFragment) getSupportFragmentManager().findFragmentByTag("writefrag");
if (write_frag != null){
write_frag.Remove();
                                    }
                                    //if(write_frag== null){
                                    try {
                                        File file = fragment.getfileCrop();
                                        Download_file(BitmapFactory.decodeFile(file.getPath()));
                                        // finish();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }else if (getLayout_position() ==3){
                                    CustomDFragment fragment = (CustomDFragment) getSupportFragmentManager().findFragmentByTag("customlay");
                                    try {
                                        File file = fragment.getfileCrop();
                                        Download_file(BitmapFactory.decodeFile(file.getPath()));
                                        // finish();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                dialog.cancel();
                            }
                        });

// create and show the alert dialog
                        AlertDialog ddialog = builder.create();
                        ddialog.show();



                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        //-------

  /*
        try {
            File file = fragment.getfileCrop();
           File file1  = write_frag.getfileCrop();


           Download_file(BitmapFactory.decodeFile(file1.getPath()));


        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }
});
        for (int i=0; i<tabLayout.getTabCount();i++){
            LinearLayout linearLayout = new LinearLayout(this);
            TextView textView = new TextView(this);
            textView.setText(aray.get(i));
            textView.setCompoundDrawablesWithIntrinsicBounds(arrayint.get(i), 0, 0, 0);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.addView(textView);
            tabLayout.getTabAt(i).setCustomView(linearLayout);
        }
    }
    public File getfileCrop(Bitmap bitmap) throws IOException {
        Bitmap returnedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
       Drawable bgDrawable =relativeLayout.getBackground();
bgDrawable.draw(canvas);
        //new BitmapDrawable(bitmap).draw(canvas);
        // draw the view on the canvas
        relativeLayout.draw(canvas);
      //  ca.draw(canvas);
        File f = new File(getCacheDir(), "filename");
        f.createNewFile();

//Convert bitmap to byte array

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        returnedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }
public void cancelCollage(){
    AlertDialog.Builder builder1 = new AlertDialog.Builder(CollageActivity.this);
    builder1.setMessage("Want to cancel collage ?? ");
    builder1.setCancelable(true);

    builder1.setPositiveButton(
            "Yes",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    finish();
                }
            });

    builder1.setNegativeButton(
            "No",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

    AlertDialog alert11 = builder1.create();
    alert11.show();
}
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(CollageActivity.this);
        builder1.setMessage("Want to cancel collage ?? ");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
     //   super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(layout_position != 3) {
            getSupportFragmentManager().beginTransaction().add(R.id.container_critical, new VertCutFragment(uriList, layout_position), "layoutvertcut").commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_critical, new CustomDFragment(uriList), "customlay").commit();
        }
        super.onResume();
    }

    public static Bitmap overlay(File bmp1, File bmp2) {
      Bitmap bitmap = BitmapFactory.decodeFile(bmp1.getPath());

        Bitmap bmOverlay = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bitmap, new Matrix(), null);
        bitmap = BitmapFactory.decodeFile(bmp2.getPath());
        canvas.drawBitmap(bitmap, 0,0, null);
        return bmOverlay;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ClipArt getCa() {
        return ca;
    }

    public void setCa(ClipArt ca) {
        this.ca = ca;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getColorname() {
        return colorname;
    }

    public void setColorname(String colorname) {
        this.colorname = colorname;
    }

   public float getAngle() {
        return angle;
    }

    public int getLayout_position() {
        return layout_position;
    }

    public void setLayout_position(int layout_position) {
        this.layout_position = layout_position;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public  void setLocation(RelativeLayout.LayoutParams loc){
  location = loc;
    }
public RelativeLayout.LayoutParams getLocation (){
        return location;
}
    private void Download_file(Bitmap bmp) {

        Toast.makeText(getApplicationContext(),"file saving",Toast.LENGTH_SHORT).show();


        File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //context.getExternalFilesDir(null);

        File file = new File(storageLoc, collage_file);

        try{
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            Toast.makeText(getApplicationContext(),"file saved.",Toast.LENGTH_SHORT).show();
            VertCutFragment fragment = (VertCutFragment) getSupportFragmentManager().findFragmentByTag("layoutvertcut");
             getSupportFragmentManager().beginTransaction().remove(fragment);
            finish();
            scanFile(getApplicationContext(), Uri.fromFile(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e( "Download_file: ",e.getMessage() );
        } catch (IOException e) {
            e.printStackTrace();
            Log.e( "Download_file: ",e.getMessage() );
        }


    }
    private void scanFile(Context context, Uri uri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(uri);
        context.sendBroadcast(scanIntent);


    }
}
