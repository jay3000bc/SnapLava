package com.snaplava.SnapLava.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snaplava.SnapLava.CollageActivity;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.frags.layout.CustomDFragment;
import com.snaplava.SnapLava.frags.layout.VertCutFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alegralabs on 15/03/18.
 */

public class BorderAdapter extends RecyclerView.Adapter<BorderAdapter.viewHolder> {
    Context context;
    android.support.v4.app.FragmentManager fragmentManager ;
    Activity activity;
    ArrayList<Integer> colorList = new ArrayList<>();
    public BorderAdapter(Context context, android.support.v4.app.FragmentManager fragmentManager, Activity activity) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
        colorList.add(Color.argb(255,230,34,34));
        colorList.add(Color.argb(255,241,225,10));
        colorList.add(Color.argb(255,81,239,19));
        colorList.add(Color.argb(255,15,241,218));
        colorList.add(Color.argb(255,24,34,241));
        colorList.add(Color.argb(255,247,24,46));
        colorList.add(Color.argb(255,158,25,241));
        colorList.add(Color.argb(255,237,28,164));
        colorList.add(Color.argb(255, 255,255,255));
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.textcolor,parent,false);
        viewHolder holder=new viewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
holder.imageview.setImageBitmap(getBitmap(colorList.get(position)));
if(position ==8){
    holder.imageview.setImageBitmap(addBorderToCircularBitmap(getCircularBitmap(getBitmap(colorList.get(position))),2,Color.BLACK));
}
holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          ((CollageActivity)activity).setColor(colorList.get(position));
int layout_selected=((CollageActivity)activity).getLayout_position();
if(layout_selected!=3){
    VertCutFragment vertCutFragment=((VertCutFragment)fragmentManager.findFragmentByTag("layoutvertcut"));
    vertCutFragment.getView().setBackgroundColor(colorList.get(position));
     if (layout_selected==2){
         vertCutFragment.setBackGroundC(colorList.get(position));
     }
}else {
    CustomDFragment cfragment = (CustomDFragment)fragmentManager.findFragmentByTag("customlay");
cfragment.changeColor(colorList.get(position));
}
      }
  });
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageview;
        public viewHolder(View itemView) {
            super(itemView);
            imageview = (CircleImageView) itemView.findViewById(R.id.image_color_1);
        }
    }
    public Bitmap getBitmap(int color){
        Bitmap bmp=Bitmap.createBitmap(50,50, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmp);
        canvas.drawColor(color);
        return bmp;
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
    protected Bitmap addBorderToCircularBitmap(Bitmap srcBitmap, int borderWidth, int borderColor){
        // Calculate the circular bitmap width with border
        int dstBitmapWidth = srcBitmap.getWidth()+borderWidth*2;

        // Initialize a new Bitmap to make it bordered circular bitmap
        Bitmap dstBitmap = Bitmap.createBitmap(dstBitmapWidth,dstBitmapWidth, Bitmap.Config.ARGB_8888);

        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(dstBitmap);
        // Draw source bitmap to canvas
        canvas.drawBitmap(srcBitmap, borderWidth, borderWidth, null);

        // Initialize a new Paint instance to draw border
        Paint paint = new Paint();
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);



        canvas.drawCircle(
                canvas.getWidth() / 2, // cx
                canvas.getWidth() / 2, // cy
                canvas.getWidth()/2 - borderWidth / 2, // Radius
                paint // Paint
        );

        // Free the native object associated with this bitmap.
        srcBitmap.recycle();

        // Return the bordered circular bitmap
        return dstBitmap;
    }

}
