package com.snaplava.SnapLava.frags;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snaplava.SnapLava.CollageActivity;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.customs.ClipArt;
import com.snaplava.SnapLava.frags.layout.HeartFragment;
import com.snaplava.SnapLava.frags.layout.VertCutFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteFragment extends Fragment  {

    Button btnAdd;
    RelativeLayout layBg;
    private TextView vstup;
    private Bundle savedState = null;

boolean check = true;
     RelativeLayout.LayoutParams locations ;
    ClipArt ca  ;
    String Writtin;
    FloatingActionButton fab_text;
   int count = 0;
     public WriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        fab_text = (FloatingActionButton) view.findViewById(R.id.fab_text);
        locations=  ((CollageActivity)getActivity()).getLocation();
        layBg = (RelativeLayout) view.findViewById(R.id.laybg);
        Log.e( "onCreateView: ",locations.toString() );
       locations = ((CollageActivity)getActivity()).getLocation();
      ca= ((CollageActivity)getActivity()).getCa();
      if (ca!= null){
          layBg.addView(ca);
      }else {
          AddDrawingField();
      }
        //   if(TextUtils.isEmpty(addtext.getText().toString())){
     /*     final InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
          inputManager.toggleSoftInput(
                  InputMethodManager.SHOW_FORCED,
                  InputMethodManager.HIDE_IMPLICIT_ONLY
          );
          addtext.requestFocus();

               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setTitle("Enter text");
               LinearLayout linearLayout = new LinearLayout(getContext());
               linearLayout.setOrientation(LinearLayout.VERTICAL);
               EditText editText = new EditText(getContext());
               Button button = new Button(getContext());
               button.setText("Add text");
               button.setGravity(Gravity.CENTER);
               linearLayout.addView(editText);
               linearLayout.addView(button);
               builder.setView(linearLayout);
               // builder.setView(modeList);
               final Dialog dialog = builder.create();

               dialog.show();


           }
    */
    /*
        image_sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!TextUtils.isEmpty(addtext.getText())){
                    Bitmap bitmap = textAsBitmap(addtext.getText().toString(),54f,Color.BLUE,"Coiny-Regular.ttf");
                    if(ca == null) {
                        ca = new ClipArt(getContext(), getActivity());
                        // ca.setId(900);
                    }else {
                        if(layBg.getChildCount()==0){
                            layBg.addView(ca);

                        }
                    }

                    ((CollageActivity)getActivity()).setCa(ca);
                createViewBitmap(bitmap);
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                }else{
                    Toast.makeText(getContext(),"enter some text",Toast.LENGTH_SHORT).show();
                }





            }
        });
*/

       // Bitmap bitmap = textAsBitmap("enter text", 54f, Color.BLUE,"FiraSans-ExtraLightItalic.ttf");
     // createViewBitmap(bitmap);
/*
        ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputManager.toggleSoftInput(
                        InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY
                );


            }
        });*/
fab_text.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     AddDrawingField();
       }
});
        layBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(check) {
    disableall();
    check = false;
}else{
    ca.visiball();
    check = true;
}
            }
        });


        return  view;
    }
    public void AddDrawingField(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter text");
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editText = new EditText(getContext());
        Button button = new Button(getContext());
        button.setText("Add text");
        button.setGravity(Gravity.CENTER);
        linearLayout.addView(editText);
        linearLayout.addView(button);
        builder.setView(linearLayout);
        // builder.setView(modeList);
        final Dialog dialog = builder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(editText.getText())){
                    Writtin = editText.getText().toString();
                    Bitmap bitmap = textAsBitmap(editText.getText().toString(),54f,Color.BLUE,"Coiny-Regular.ttf");
                    IBinder token = editText.getWindowToken();
                    ( ( InputMethodManager ) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );
                    if(ca == null) {
                        ca = new ClipArt(getContext(), getActivity());
                        // ca.setId(900);
                    }else {
                        if(layBg.getChildCount()==0){
                            layBg.addView(ca);

                        }
                    }
                    // hide soft input

                    ((CollageActivity)getActivity()).setCa(ca);
                    createViewBitmap(bitmap);
                    dialog.dismiss();

                }else{
                    Toast.makeText(getContext(),"enter some text",Toast.LENGTH_SHORT).show();
                }


            }
        });
        dialog.show();

    }

    public void createViewBitmap( Bitmap bm) {
        layBg.removeAllViews();
        ca.getImageView().setImageBitmap(bm);
        ca.setbaseY(locations);
if(count==0) {
   count++;
    ca.setAngle(((CollageActivity)getActivity()).getAngle());
}
        layBg.addView(ca);}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
      if(ca == null){
          ((CollageActivity)getActivity()).setRefered(true);
      }else {
          ((CollageActivity)getActivity()).setCa(ca);
          ((CollageActivity)getActivity()).setAngle(ca.getRotationAngle());
          ((CollageActivity)getActivity()).setLocation(ca.getbaseY());
          layBg.removeView(ca);
      }
         }
public void Remove(){
        if(layBg.getChildCount()==1){
            layBg.removeView(ca);
        }

}
    @Override
    public void onPause() {
        if(ca == null){
            ((CollageActivity)getActivity()).setRefered(true);
        }else {
            ((CollageActivity)getActivity()).setCa(ca);
            ((CollageActivity)getActivity()).setAngle(ca.getRotationAngle());
            ((CollageActivity)getActivity()).setLocation(ca.getbaseY());
         layBg.removeView(ca);
        }   super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    if(ca== null){
        ca = new ClipArt(getContext(), getActivity());

    }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        Log.e( "onSaveInstanceState: ","called" );

    }

    public void disableall() {

        for (int i = 0; i < layBg.getChildCount(); i++) {

            if (layBg.getChildAt(i) instanceof ClipArt) {

                ((ClipArt) layBg.getChildAt(i)).disableAll();

            }

        }

    }
    public Bitmap textAsBitmap(String tex, float textSize, int textColor, String font) {
    if (tex.equals("")){
        tex = Writtin;
    }
        Paint paint = new Paint(ANTI_ALIAS_FLAG);

        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setAntiAlias(true);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+font);
        paint.setTypeface(typeface);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(tex) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + +0.5f);
        Bitmap image = Bitmap.createBitmap(width, 2*height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(tex, 0, baseline, paint);
        return image;
    }

public ClipArt getClipart(){
        layBg.removeView(ca);
        return  ca;
}
    public File getfileCrop () throws IOException {
      int position = ((CollageActivity)getActivity()).getLayout_position();
        File file = null;
      if(position==0 || position ==1 || position ==7){
         file= ((VertCutFragment) getActivity().getSupportFragmentManager().findFragmentByTag("layoutvertcut")).getfileCrop();

      }else if (position ==4){
          file= ((HeartFragment) getActivity().getSupportFragmentManager().findFragmentByTag("heartlay")).getfileCrop();
      }
          ca.disableAll();
      ca.setEnabled(false);
       Drawable drawable = new BitmapDrawable(BitmapFactory.decodeFile(file.getPath()));
     layBg.setBackground(drawable);
        Bitmap returnedBitmap = Bitmap.createBitmap(layBg.getWidth(), layBg.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =layBg.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        layBg.draw(canvas);
        File f = new File(getContext().getCacheDir(), "filename");
        f.createNewFile();
ca.setEnabled(true);
ca.visiball();
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

}
