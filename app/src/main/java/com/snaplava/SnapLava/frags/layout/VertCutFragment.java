package com.snaplava.SnapLava.frags.layout;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snaplava.SnapLava.CollageActivity;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.customs.ClipArt;
import com.snaplava.SnapLava.customs.RectangularView;
import com.snaplava.SnapLava.customs.TriangleView;
import com.snaplava.SnapLava.frags.WriteFragment;
import com.twitter.sdk.android.core.models.Card;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class VertCutFragment extends Fragment  {
RectangularView view_0ne,view_two;
  TriangleView View_0ne;
    ArrayList<String> uriList;
   ImageView linearayout;
    Rect rect;
RelativeLayout image_afterSet;
  int starter,i=1,j=0;
  int height, weidth, height1, weith1;
   Boolean swipe_obne = false;
   int position;
    ClipArt finalclip = null;
    View view= null;
    int layout_id =0;
    CardView lay_cont1,defend ;
LinearLayout ukrey;
            Boolean swipe_two = false;Boolean firstswap = true;
float radius_count=0;
   ViewGroup.MarginLayoutParams params, params1;
      @SuppressLint("ValidFragment")
    public VertCutFragment(ArrayList<String> uriList, int position) {
        // Required empty public constructor
        this.uriList = uriList;
        this.position = position;
    }
    public VertCutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       if(position==0) {
            view = inflater.inflate(R.layout.fragment_vert_cut, container, false);

            if (((CollageActivity)getActivity()).getColor()!= 0){
                view.setBackgroundColor(((CollageActivity)getActivity()).getColor());
            }
       layout_id =1;
        }else if (position == 1){
            view = inflater.inflate(R.layout.fragment_hor2_cut, container, false);

            if (((CollageActivity)getActivity()).getColor()!= 0){

                view.setBackgroundColor(((CollageActivity)getActivity()).getColor());
            }
        }else if (position ==2){
            view = inflater.inflate(R.layout.layot_square_view, container, false);
           lay_cont1 =(CardView) view.findViewById(R.id.lay_cont);
           defend = (CardView) view.findViewById(R.id.defend);
            ukrey = (LinearLayout) view.findViewById(R.id.ukrey);
            if (((CollageActivity)getActivity()).getColor()!= 0){
                view.setBackgroundColor(((CollageActivity)getActivity()).getColor());

            }
        }

        view_0ne = (RectangularView) view.findViewById(R.id.View_one);
        view_two = (RectangularView) view.findViewById(R.id.View_two);
         image_afterSet = (RelativeLayout) view.findViewById(R.id.image_aftersetting);
 addClipart();

 image_afterSet.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         //if ()
     }
 });
   /*       ClipArt finalclip =((CollageActivity)getActivity()).getCa();

        if(finalclip!=null){
            finalclip.setLayoutParams(((CollageActivity)getActivity()).getLocation());

            WriteFragment fragment_writw = (WriteFragment)getActivity().getSupportFragmentManager().findFragmentByTag("writefrag");
            if (fragment_writw != null){
                fragment_writw.Remove();
                finalclip.disableAll();
                Toast.makeText(getContext(), "onCreate from fragment clipart added view removed", Toast.LENGTH_SHORT).show();

                image_afterSet.addView(finalclip);

            }else {
                //  fragment_writw.Remove();
                finalclip.disableAll();
                Toast.makeText(getContext(), "onCreate from fragment clipart added", Toast.LENGTH_SHORT).show();
                image_afterSet.addView(finalclip);
            }
        }else {
            Toast.makeText(getContext(), "onCreate from activity clipart null", Toast.LENGTH_SHORT).show();
        }
*/
        view_0ne.setImageURI(Uri.parse(uriList.get(0)));
    linearayout = (ImageView) view.findViewById(R.id.drag_image);

   view_0ne.setOnDragListener(new MyDragListener());

      view_two.setOnDragListener(new MyDragListener());

    view_two.setImageURI(Uri.parse(uriList.get(1)));
/*
        String temp= uriList.get(1);
        String temp2 = uriList.get(0);
        uriList.remove(1);
        uriList.remove(0);
        uriList.add(temp);
        uriList.add(temp2);*/
         params = (ViewGroup.MarginLayoutParams) view_0ne.getLayoutParams();;
        height = params.height;
        weidth = params.width;
       if (position!=2) {
           params1 = (ViewGroup.MarginLayoutParams) view_two.getLayoutParams();
           height1 = params1.height;
           weith1 = params1.width;
       }else {

       }
        view.setBackgroundColor(Color.BLACK);

        View.OnTouchListener viewFListner = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                      rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());

                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                            // User moved outside bounds


linearayout.setImageURI(Uri.parse(uriList.get(0)));
                          //  Toast.makeText(getApplicationContext(), "long pressed", Toast.LENGTH_SHORT).show();
                            //container_one.
                            v.getDrawingCache(true);
starter=0;
                            linearayout.setVisibility(View.VISIBLE);
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                                    view_0ne);
                            view_0ne.startDrag(null, shadowBuilder, view_0ne, 0);
                            linearayout.setVisibility(View.INVISIBLE);
                        }





                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;

            }
        };
        View.OnTouchListener viewSListner = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());

                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY()))
                        {
                            linearayout.setImageURI(Uri.parse(uriList.get(1)));

          starter =2;
                            v.getDrawingCache(true);

                            linearayout.setVisibility(View.VISIBLE);
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                                    view_two);
                            view_two.startDrag(null, shadowBuilder, view_two, 0);
                            linearayout.setVisibility(View.INVISIBLE);
                        }





                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;

            }
        };
        view_0ne.setOnTouchListener(viewFListner);
        view_two.setOnTouchListener(viewSListner);
        view_0ne.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

               linearayout.setImageURI(Uri.parse(uriList.get(0)));
              //  Toast.makeText(getContext(), "long pressed", Toast.LENGTH_SHORT).show();
             starter = 0;
              view_0ne.scalablePermit(false);
              view_two.scalablePermit(false);
              //container_one.
                v.getDrawingCache(true);

                linearayout.setVisibility(View.VISIBLE);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view_0ne);
                linearayout.startDrag(null, shadowBuilder, view_0ne, 0);
                linearayout.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        view_two.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                linearayout.setImageURI(Uri.parse(uriList.get(1)));
               // Toast.makeText(getContext(), "long pressed", Toast.LENGTH_SHORT).show();
                starter = 2;
                view_0ne.scalablePermit(false);
                view_two.scalablePermit(false);
                v.getDrawingCache(true);
                 linearayout.setVisibility(View.VISIBLE);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view_two);
                linearayout.startDrag(null, shadowBuilder, view_two, 0);
                linearayout.setVisibility(View.INVISIBLE);
                return false;
            }
        });
    return view;
    }
    public void changeRadius(float radius){
        view_0ne.setCornRAD((float) (1.5*radius));
        view_two.setCornRAD((float) (1.5*radius));
    if(position==2){
        lay_cont1.setRadius((float) (1.5*radius));
        defend.setRadius((float) (1.5*radius));

    }
    }

public void ChangeBorderThickness(int thickness){

   if (position !=2) {
      if (position==0) {
          params.height = height - thickness * (4 / 3);
          params.width = weidth - thickness;
          params.rightMargin = 2 * thickness / 3;
          params.leftMargin = 2 * thickness / 6;
          params.topMargin = 2 * thickness / 3;
          params.bottomMargin = 2 * thickness / 3;
          view_0ne.setLayoutParams(params);


          params1.height = height1 - thickness * (4 / 3);
          params1.width = weith1 - thickness;
          params1.leftMargin = 2 * thickness / 3;
          params1.rightMargin = 2 * thickness / 6;
          params1.topMargin = 2 * thickness / 3;
          params1.bottomMargin = 2 * thickness / 3;

          view_two.setLayoutParams(params1);
      }else if (position==1){
          params.height = height - thickness ;
          params.width = weidth - thickness* (4 / 3);
          params.rightMargin = 2 * thickness / 3;
          params.bottomMargin = 2 * thickness / 6;
          params.topMargin = 2 * thickness / 3;
          params.leftMargin = 2 * thickness / 3;
          view_0ne.setLayoutParams(params);


          params1.height = height1 - thickness  ;
          params1.width = weith1 - thickness* (4 / 3);
          params1.rightMargin = 2 * thickness / 3;
          params1.topMargin = 2 * thickness / 6;
          params1.bottomMargin = 2 * thickness / 3;
          params1.leftMargin = 2 * thickness / 3;

          view_two.setLayoutParams(params1);


      }


       //   mparam1.setMargins(thickness*(4/3),0,0,0);
     //  view_two.requestLayout();
   }
   else if (position==2) {
       params.height = height - thickness / 2;
       params.width = weidth - thickness / 2;
       Log.e("ChangeBorderThickness: ", params.height + " " + params.width);

       view_0ne.setLayoutParams(params);
       //(thickness/4,thickness/4,thickness/4,thickness/4);
        ukrey.setPadding(thickness/4,thickness/4,thickness/4,thickness/4);
       // view_two.setPadd

   }
    }
    public void setBackGroundC(Integer color){
   ukrey.setBackground(new BitmapDrawable(getResources(),getCircularBitmap(getBitmap(color))));

    }

    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //TODO

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:

                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();

                    RectangularView container = (RectangularView) v;


                    if(starter==0){


                        if(container != view) {
                           if(firstswap ){
                               container.setImageBitmap(null);
                               container.setImageURI(Uri.parse(uriList.get(0)));
                               view_0ne.setImageBitmap(null);
                               view_0ne.setImageURI(Uri.parse(uriList.get(1)));
                               swipe_obne = true;
                               //}else {
                               // container.setImageURI(Uri.parse(uriList.get(1)));
                               //v0iew_0ne.setImageURI(Uri.parse(uriList.get(0)));
                               swipe_obne = false;
                               swipe_two = true;
                               /*String temp= uriList.get(1);
                               String temp2 = uriList.get(0);
                               uriList.remove(1);
                               uriList.remove(0);
                               uriList.add(temp);
                               uriList.add(temp2);
                              */ firstswap =false;
                           }else {
                               container.setImageBitmap(null);
                               container.setImageURI(Uri.parse(uriList.get(1)));
                               view_0ne.setImageBitmap(null);
                               view_0ne.setImageURI(Uri.parse(uriList.get(0)));
                               swipe_obne = true;
                               //}else {
                               // container.setImageURI(Uri.parse(uriList.get(1)));
                               //v0iew_0ne.setImageURI(Uri.parse(uriList.get(0)));
                               swipe_obne = false;
                               swipe_two = true;
                               String temp= uriList.get(1);
                               String temp2 = uriList.get(0);
                               uriList.remove(1);
                               uriList.remove(0);
                               uriList.add(temp);
                               uriList.add(temp2);
                           }
                            //}
                            swipe_obne = true;
                        }else {
                        }
                     }else if(starter==2){

                        if(container != view) {
                           if (firstswap){
                               swipe_obne = true;
                               container.setImageBitmap(null);
                               container.setImageURI(Uri.parse(uriList.get(1)));
                               view_two.setImageBitmap(null);
                               view_two.setImageURI(Uri.parse(uriList.get(0)));
 /*                              String temp= uriList.get(1);
                               String temp2 = uriList.get(0);
                               uriList.remove(1);
                               uriList.remove(0);
                               uriList.add(temp);
                               uriList.add(temp2);
*/
                               swipe_two = true;

                               swipe_two = false;
                               firstswap = false;

                           }else {
                               swipe_obne = true;
                               container.setImageBitmap(null);
                               container.setImageURI(Uri.parse(uriList.get(0)));
                               view_two.setImageBitmap(null);
                               view_two.setImageURI(Uri.parse(uriList.get(1)));
                               String temp= uriList.get(1);
                               String temp2 = uriList.get(0);
                               uriList.remove(1);
                               uriList.remove(0);
                               uriList.add(temp);
                               uriList.add(temp2);

                               swipe_two = true;

                               swipe_two = false;

                           }
                        }else {

                        }
                        }
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    view_0ne.scalablePermit(true);
                    view_two.scalablePermit(true);
                default:
                    break;
            }
            return true;
        }
    }
public File getfileCrop () throws IOException {
  /*  ClipArt finalclip =((CollageActivity)getActivity()).getCa();

if(finalclip!=null){
    finalclip.setLayoutParams(((CollageActivity)getActivity()).getLocation());

    WriteFragment fragment_writw = (WriteFragment)getActivity().getSupportFragmentManager().findFragmentByTag("writefrag");
    if (fragment_writw != null){
        fragment_writw.Remove();
finalclip.disableAll();
        image_afterSet.addView(finalclip);

    }else {
      //  fragment_writw.Remove();
        finalclip.disableAll();
        image_afterSet.addView(finalclip);
    }
}
*/
  if (finalclip!=null) {
      finalclip.disableAll();
      if(position==2){
        //  ukrey.setVisibility(View.GONE);
      }
  }
    Bitmap returnedBitmap = Bitmap.createBitmap(image_afterSet.getWidth(), image_afterSet.getHeight(),Bitmap.Config.ARGB_8888);

    //Bind a canvas to it
    Canvas canvas = new Canvas(returnedBitmap);

    Drawable bgDrawable =image_afterSet.getBackground();
    if (bgDrawable!=null) {

        bgDrawable.draw(canvas);
    }   else{

        canvas.drawColor(Color.WHITE);
    }

    image_afterSet.draw(canvas);
    File f = new File(getContext().getCacheDir(), "filename");
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
public void addClipart(){
     finalclip =((CollageActivity)getActivity()).getCa();

    if(finalclip!=null){
        if (finalclip.getParent()!= image_afterSet) {
            finalclip.setLayoutParams(((CollageActivity) getActivity()).getLocation());

            WriteFragment fragment_writw = (WriteFragment) getActivity().getSupportFragmentManager().findFragmentByTag("writefrag");
            if (fragment_writw != null) {
                fragment_writw.Remove();
                finalclip.disableAll();
                //Toast.makeText(getContext(), "onRresume from fragment clipart added view removed", Toast.LENGTH_LONG).show();
                image_afterSet.addView(finalclip);

            } else {
                //  fragment_writw.Remove();

                finalclip.disableAll();
                //Toast.makeText(getContext(), "onRresume from fragment clipart added", Toast.LENGTH_LONG).show();
                image_afterSet.addView(finalclip);
            }
        }
    }else {
        //Toast.makeText(getContext(), "onRresume from activity clipart null", Toast.LENGTH_LONG).show();
    }
}
    @Override
    public void onResume() {

    super.onResume();


}
    protected Bitmap getCircularBitmap(Bitmap srcBitmap) {
        // Calculate the circular bitmap width with border
        int squareBitmapWidth = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());

        // Initialize a new instance of Bitmap
        Bitmap dstBitmap = Bitmap.createBitmap(
                squareBitmapWidth, // Width
                squareBitmapWidth, // Height
                Bitmap.Config.ARGB_8888 // Config
        );


        Canvas canvas = new Canvas(dstBitmap);

        // Initialize a new Paint instance
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Rect rect = new Rect(0, 0, squareBitmapWidth, squareBitmapWidth);


        RectF rectF = new RectF(rect);


        canvas.drawOval(rectF, paint);


        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


        float left = (squareBitmapWidth-srcBitmap.getWidth())/2;
        float top = (squareBitmapWidth-srcBitmap.getHeight())/2;


        canvas.drawBitmap(srcBitmap, left, top, paint);


        srcBitmap.recycle();


        return dstBitmap;
    }
public void removeViewca(){
    image_afterSet.removeView(((CollageActivity)getActivity()).getCa());
}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            ((CollageActivity)getActivity()).setFile(getfileCrop());
        } catch (IOException e) {
            e.printStackTrace();
        }
        image_afterSet.removeAllViews();
        Log.e("onDestroyView: ","called" );
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e( "onDestroy: ","called" );
        image_afterSet.removeAllViews();
    }

    @Override
    public void onPause() {
        /*try {
          //  ((CollageActivity)getActivity()).setFile(getfileCrop());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        image_afterSet.removeAllViews();
        Log.e( "onPause: ","called" );
        super.onPause();
    }
    public void setBackground(Integer color){
//view.setBackgroundColor(color);
    }
    public Bitmap getBitmap(int color){
        Bitmap bmp=Bitmap.createBitmap(50,50, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmp);
        canvas.drawColor(color);
        return bmp;
    }
}


