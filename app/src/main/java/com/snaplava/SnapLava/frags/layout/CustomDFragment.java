package com.snaplava.SnapLava.frags.layout;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.snaplava.SnapLava.CollageActivity;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.customs.ClipArt;
import com.snaplava.SnapLava.customs.MultiTouchView;
import com.snaplava.SnapLava.frags.WriteFragment;
import com.twitter.sdk.android.core.models.Card;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomDFragment extends Fragment {
ArrayList<String> uriList;

// CardView cardOne;
 ViewGroup.LayoutParams params;
 int height, weidth;
  int radius =0;

  @SuppressLint("ValidFragment")
  public CustomDFragment(ArrayList<String> uriList) {
        // Required empty public constructor
        this.uriList = uriList;
    }
    public CustomDFragment() {
        // Required empty public constructor
    }
    RelativeLayout relativeLayout;
MultiTouchView multiTouchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_d, container, false);
        multiTouchView = (MultiTouchView) view.findViewById(R.id.canvas_view);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.layout_main);
        multiTouchView.setActivityContext (getContext());
        //cardOne = (CardView) view.findViewById(R.id.layout_card);
        params = multiTouchView.getLayoutParams();
        height = params.height;
        weidth = params.width;
        multiTouchView.setPinchWidget(BitmapFactory.decodeFile(ResizedSource(Uri.parse(uriList.get(0)).getPath()).getPath()));
        multiTouchView.setPinchWidget2(BitmapFactory.decodeFile(ResizedSource(Uri.parse(uriList.get(1)).getPath()).getPath()));

    return  view;
    }

    private Uri ResizedSource(String uri){
        Uri uri_result = null;
        try {
            File compressedImage = new Compressor(getActivity())
                    .setMaxWidth(200)
                    .setMaxHeight(200)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .compressToFile(new File(Uri.parse(uri).getPath()));
             uri_result = Uri.fromFile(compressedImage);
          //  uriArrayList.add(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri_result;
    }
    private Uri ResizedSource(String uri, float factorX, float Y){
        Log.e("ResizedSource: ",factorX +" "+ Y );
        Uri uri_result = null;
        try {
            File compressedImage = new Compressor(getActivity())
                    .setMaxWidth((int) (factorX*200))
                    .setMaxHeight((int) (Y*200))
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .compressToFile(new File(Uri.parse(uri).getPath()));
            uri_result = Uri.fromFile(compressedImage);
            //  uriArrayList.add(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri_result;
    }

    public void changeBoarderThickness(int thickness){

        multiTouchView.setPinchWidget(addBorderToRoundedBitmap(getRoundedBitmap(BitmapFactory.decodeFile(ResizedSource(Uri.parse(uriList.get(0)).getPath(),multiTouchView.getmPinchWidget().getScaleFactor(),multiTouchView.getmPinchWidget().getScaleFactor()).getPath()), radius),radius, thickness/8, Color.GRAY));

        multiTouchView.setPinchWidget2(addBorderToRoundedBitmap(getRoundedBitmap(BitmapFactory.decodeFile(ResizedSource(Uri.parse(uriList.get(1)).getPath(),multiTouchView.getmPinchWidget2().getScaleFactor(), multiTouchView.getmPinchWidget2().getScaleFactor()).getPath()),radius), radius, thickness/8,Color.GRAY));

        multiTouchView.invalidate();

    }
    public  void changeRadius(int thickness){

        multiTouchView.setPinchWidget(getRoundedBitmap(BitmapFactory.decodeFile(ResizedSource(Uri.parse(uriList.get(0)).getPath(),multiTouchView.getmPinchWidget().getScaleFactor(),multiTouchView.getmPinchWidget().getScaleFactor()).getPath()), thickness));

multiTouchView.setPinchWidget2(getRoundedBitmap(BitmapFactory.decodeFile(ResizedSource(Uri.parse(uriList.get(1)).getPath(),multiTouchView.getmPinchWidget2().getScaleFactor(), multiTouchView.getmPinchWidget2().getScaleFactor()).getPath()),thickness));

        multiTouchView.invalidate();
        radius = thickness;
   }
   public void changeColor(int color){
        multiTouchView.setColor(color);
   }
    public File getfileCrop () throws IOException {
        ClipArt finalclip =((CollageActivity)getActivity()).getCa();

        if(finalclip!=null){
            finalclip.setLayoutParams(((CollageActivity)getActivity()).getLocation());

            WriteFragment fragment_writw = (WriteFragment)getActivity().getSupportFragmentManager().findFragmentByTag("writefrag");
            if (fragment_writw != null){
                fragment_writw.Remove();
                finalclip.disableAll();
                relativeLayout.addView(finalclip);

            }else {
                finalclip.disableAll();
                relativeLayout.addView(finalclip);
            }
        }

        Bitmap returnedBitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(),Bitmap.Config.ARGB_8888);

        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);

        Drawable bgDrawable =relativeLayout.getBackground();
        if (bgDrawable!=null) {

            bgDrawable.draw(canvas);
        }   else{

            canvas.drawColor(Color.WHITE);
        }

        relativeLayout.draw(canvas);
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
    protected Bitmap getRoundedBitmap(Bitmap srcBitmap, int cornerRadius) {
        // Initialize a new instance of Bitmap
        Bitmap dstBitmap = Bitmap.createBitmap(
                srcBitmap.getWidth(), // Width
                srcBitmap.getHeight(), // Height

                Bitmap.Config.ARGB_8888 // Config
        );

        /*
            Canvas
                The Canvas class holds the "draw" calls. To draw something, you need 4 basic
                components: A Bitmap to hold the pixels, a Canvas to host the draw calls (writing
                into the bitmap), a drawing primitive (e.g. Rect, Path, text, Bitmap), and a paint
                (to describe the colors and styles for the drawing).
        */
        // Initialize a new Canvas to draw rounded bitmap
        Canvas canvas = new Canvas(dstBitmap);

        // Initialize a new Paint instance
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        /*
            Rect
                Rect holds four integer coordinates for a rectangle. The rectangle is represented by
                the coordinates of its 4 edges (left, top, right bottom). These fields can be accessed
                directly. Use width() and height() to retrieve the rectangle's width and height.
                Note: most methods do not check to see that the coordinates are sorted correctly
                (i.e. left <= right and top <= bottom).
        */
        /*
            Rect(int left, int top, int right, int bottom)
                Create a new rectangle with the specified coordinates.
        */
        // Initialize a new Rect instance
        Rect rect = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());

        /*
            RectF
                RectF holds four float coordinates for a rectangle. The rectangle is represented by
                the coordinates of its 4 edges (left, top, right bottom). These fields can be
                accessed directly. Use width() and height() to retrieve the rectangle's width and
                height. Note: most methods do not check to see that the coordinates are sorted
                correctly (i.e. left <= right and top <= bottom).
        */
        // Initialize a new RectF instance
        RectF rectF = new RectF(rect);

        /*
            public void drawRoundRect (RectF rect, float rx, float ry, Paint paint)
                Draw the specified round-rect using the specified paint. The roundrect will be
                filled or framed based on the Style in the paint.

            Parameters
                rect : The rectangular bounds of the roundRect to be drawn
                rx : The x-radius of the oval used to round the corners
                ry : The y-radius of the oval used to round the corners
                paint : The paint used to draw the roundRect
        */
        // Draw a rounded rectangle object on canvas
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

        /*
            public Xfermode setXfermode (Xfermode xfermode)
                Set or clear the xfermode object.
                Pass null to clear any previous xfermode. As a convenience, the parameter passed
                is also returned.

            Parameters
                xfermode : May be null. The xfermode to be installed in the paint
            Returns
                xfermode
        */
        /*
            public PorterDuffXfermode (PorterDuff.Mode mode)
                Create an xfermode that uses the specified porter-duff mode.

            Parameters
                mode : The porter-duff mode that is applied

        */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        /*
            public void drawBitmap (Bitmap bitmap, float left, float top, Paint paint)
                Draw the specified bitmap, with its top/left corner at (x,y), using the specified
                paint, transformed by the current matrix.

                Note: if the paint contains a maskfilter that generates a mask which extends beyond
                the bitmap's original width/height (e.g. BlurMaskFilter), then the bitmap will be
                drawn as if it were in a Shader with CLAMP mode. Thus the color outside of the
                original width/height will be the edge color replicated.

                If the bitmap and canvas have different densities, this function will take care of
                automatically scaling the bitmap to draw at the same density as the canvas.

            Parameters
                bitmap : The bitmap to be drawn
                left : The position of the left side of the bitmap being drawn
                top : The position of the top side of the bitmap being drawn
                paint : The paint used to draw the bitmap (may be null)
        */
        // Make a rounded image by copying at the exact center position of source image
        canvas.drawBitmap(srcBitmap, 0, 0, paint);

        // Free the native object associated with this bitmap.
        srcBitmap.recycle();

        // Return the circular bitmap
        return dstBitmap;
    }

    // Custom method to add a border around rounded bitmap
    protected Bitmap addBorderToRoundedBitmap(Bitmap srcBitmap, int cornerRadius, int borderWidth, int borderColor){
        // We will hide half border by bitmap
        borderWidth = borderWidth*2;

        // Initialize a new Bitmap to make it bordered rounded bitmap
        Bitmap dstBitmap = Bitmap.createBitmap(
                srcBitmap.getWidth() + borderWidth, // Width
                srcBitmap.getHeight() + borderWidth, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(dstBitmap);

        // Initialize a new Paint instance to draw border
        Paint paint = new Paint();
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);

        // Initialize a new Rect instance
        Rect rect = new Rect(
                borderWidth/2,
                borderWidth/2,
                dstBitmap.getWidth() - borderWidth/2,
                dstBitmap.getHeight() - borderWidth/2
        );

        // Initialize a new instance of RectF;
        RectF rectF = new RectF(rect);

        // Draw rounded rectangle as a border/shadow on canvas
        canvas.drawRoundRect(rectF,cornerRadius,cornerRadius,paint);

        // Draw source bitmap to canvas
        canvas.drawBitmap(srcBitmap, borderWidth / 2, borderWidth / 2, null);

        /*
            public void recycle ()
                Free the native object associated with this bitmap, and clear the reference to the
                pixel data. This will not free the pixel data synchronously; it simply allows it to
                be garbage collected if there are no other references. The bitmap is marked as
                "dead", meaning it will throw an exception if getPixels() or setPixels() is called,
                and will draw nothing. This operation cannot be reversed, so it should only be
                called if you are sure there are no further uses for the bitmap. This is an advanced
                call, and normally need not be called, since the normal GC process will free up this
                memory when there are no more references to this bitmap.
        */
        srcBitmap.recycle();

        // Return the bordered circular bitmap
        return dstBitmap;
    }
}
