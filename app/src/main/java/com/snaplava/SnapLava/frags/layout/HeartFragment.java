package com.snaplava.SnapLava.frags.layout;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.customs.RectangularView;
import com.snaplava.SnapLava.customs.TouchImageView;
import com.snaplava.SnapLava.customs.TriangleView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeartFragment extends Fragment {
    RectangularView view_0ne;
    TouchImageView view_two;
    TriangleView View_0ne;
    ArrayList<String> uriList;
    ImageView linearayout;
    LinearLayout image_afterSet;
    int starter,i=1,j=0;
    int height, weidth, height1, weith1;
    Boolean swipe_obne = false;
    int position;
    Boolean swipe_two = false;
    ViewGroup.LayoutParams params, params1;
    @SuppressLint("ValidFragment")
    public HeartFragment(ArrayList<String> uriList, int position) {
        // Required empty public constructor

            // Required empty public constructor
            this.uriList = uriList;
            this.position = position;

    }

    public HeartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        // Inflate the layout for this fragment
        if (position == 4) {
            view = inflater.inflate(R.layout.fragment_heart, container, false);
        } else if (position == 5) {

        }
        view_0ne = (RectangularView) view.findViewById(R.id.View_one);
        view_two = (TouchImageView) view.findViewById(R.id.View_two);
        image_afterSet = (LinearLayout) view.findViewById(R.id.image_aftersetting);

        view_0ne.setImageURI(Uri.parse(uriList.get(0)));
        linearayout = (ImageView) view.findViewById(R.id.drag_image);
        linearayout.setOnDragListener(new MyDragListener());
        view_0ne.setOnDragListener(new MyDragListener());
//  if(position!=7){
        view_two.setOnDragListener(new MyDragListener());
        view.setBackgroundColor(Color.BLACK);
        view_two.setImageURI(Uri.parse(uriList.get(1)));
        //
        params = view_0ne.getLayoutParams();
        params1 = view_two.getLayoutParams();
        height = params.height;
        weidth = params.width;
        height1 = params1.height;
        weith1 = params1.width;
        view_0ne.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                linearayout.setImageURI(Uri.parse(uriList.get(0)));
                Toast.makeText(getContext(), "long pressed", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "long pressed", Toast.LENGTH_SHORT).show();
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
    public void ChangeBorderThickness(int thickness){
        Toast.makeText(getContext(), "called", Toast.LENGTH_SHORT).show();

        //  params.height = 130;

        Log.e( "ChangeBorderThickness: ",height+" "+weidth );
        params.height = height - thickness/5;
        params.width = weidth - thickness/5;
        Log.e( "ChangeBorderThickness: ", params.height+" "+params.width);

        view_0ne.setLayoutParams(params);

        //  params.height = 130;

        params1.height = height1 - thickness/5;
        params1.width = weith1 - thickness/5;

        view_two.setLayoutParams(params1);
        //image_afterSet.removeView(view_0ne);
   /*
    LinearLayout.MarginLayoutParams marginParams = new LinearLayout.LayoutParams(view_0ne.getLayoutParams());
     marginParams.setMargins(thickness, thickness, thickness, thickness);

    view_0ne.setLayoutParams(marginParams);
   image_afterSet.addView(view_0ne);*/
    }
    public File getfileCrop () throws IOException {
        Bitmap returnedBitmap = Bitmap.createBitmap(image_afterSet.getWidth(), image_afterSet.getHeight(),Bitmap.Config.ARGB_8888);

        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =image_afterSet.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
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


    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //TODO
                    // v.setBackgroundDrawable(Shape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundDrawable(Shape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    // owner.removeView(view);


                    // Swipe(container);

                    if(starter==0){
                        ImageView container = (ImageView) v;
                        if(container != view) {
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
                            //}
                            swipe_obne = true;
                        }else {
                            //Toast.makeText(getApplicationContext(), "toas container", Toast.LENGTH_SHORT).show();
                        }

                    }else if(starter==2){
//if(swipe_two==false){
                        ImageView container = (ImageView) v;
                        if(container != view) {
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
//}else {

                            //container.setImageURI(Uri.parse(uriList.get(1)));
                            //view_two.setImageURI(Uri.parse(uriList.get(0)));
                            swipe_two = false;
//}
                        }else {
                            //  Toast.makeText(getApplicationContext(), "toas container", Toast.LENGTH_SHORT).show();
                        }

                    }
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //  v.setBackgroundDrawable(Shape);
                    // Log.e( "onDrag: ", v.getId()+" ");
                    int temp;
                    // String temp2 = uriList.get(0);
                    // uriList.remove(1);
                    //uriList.remove(0);
                    //uriList.add(temp);
                    ///uriList.add(temp2);
                    //Toast.makeText(getContext(),"swiped",Toast.LENGTH_SHORT).show();
                    temp = i;
                    i= j;
                    j=temp;
                    view_0ne.scalablePermit(true);
                    view_two.scalablePermit(true);
                default:
                    break;
            }
            return true;
        }
    }
}
