package com.snaplava.SnapLava.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.fields.gallary;

import java.util.ArrayList;

/**
 * Created by alegralabs on 08/03/18.
 */

public class GallaryAdapter extends RecyclerView.Adapter<GallaryAdapter.viewHolder> {

ArrayList  <gallary> gallaryList;
ArrayList<String> ListImage;
Bitmap overlayBitmap ;
ArrayList<Integer> selectedPosition = new ArrayList<>();

    Bitmap bmp;
     viewHolder holderf;  Bitmap overlayadded;
  int maximage =6;
  TextView textView;
    int positionF;
Context context;
    public GallaryAdapter(Context context, ArrayList<gallary> gallaryList, TextView textView, ArrayList<String> ListImage) {
        this.gallaryList = gallaryList;
        this.context = context;
        this.ListImage = ListImage;
        this.textView = textView;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_select,parent,false);
       viewHolder holder=new viewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
       bmp = decodeURI(gallaryList.get(position).getUri().getPath());
      positionF = position;
        holderf= holder;
        holder.imageview.setImageBitmap(bmp);
        overlayBitmap = getABitmap(bmp.getHeight(), bmp.getWidth());
        overlayadded =    overlay(bmp, overlayBitmap);
        try {

             //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            // imageView.setPadding(8, 8, 8, 8);
for (int i=0;i<selectedPosition.size();i++){
    if(positionF==selectedPosition.get(i)){
        holder.imageview.setImageBitmap(overlayadded);
        Log.e( "onClick: ",positionF+" " );
        break;


        //BitmapFactory.decodeFile(mUrls[position].getPath());



    }else {
        holder.imageview.setImageBitmap(bmp);
    }
}


        } catch (Exception e) {

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmp = decodeURI(gallaryList.get(position).getUri().getPath());
                holder.imageview.setImageBitmap(bmp);
                overlayBitmap = getABitmap(bmp.getHeight(), bmp.getWidth());
                overlayadded =    overlay(bmp, overlayBitmap);
                positionF = position;
                holderf= holder;
                new asyncClass().execute();
            }
        });

    }

    @Override
    public int getItemCount() {
        return gallaryList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
   ImageView imageview;
        public viewHolder(View itemView) {
            super(itemView);
            imageview = (ImageView) itemView.findViewById(R.id.id1_imageView);
        }
    }
    public Bitmap decodeURI(String filePath){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Only scale if we need to
        // (16384 buffer for img processing)
        Boolean scaleByHeight = Math.abs(options.outHeight - 100) >= Math.abs(options.outWidth - 100);
        if(options.outHeight * options.outWidth * 2 >= 16384){
            // Load, scaling to smallest power of 2 that'll get it <= desired dimensions
            double sampleSize = scaleByHeight
                    ? options.outHeight / 100
                    : options.outWidth / 100;
            options.inSampleSize =
                    (int)Math.pow(2d, Math.floor(
                            Math.log(sampleSize)/Math.log(2d)));
        }

        // Do the actual decoding
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[512];
        Bitmap output = BitmapFactory.decodeFile(filePath, options);

        return output;
    }
    public Bitmap getABitmap (int height, int weidth){
        //int width = 200;
        //int height = 300;
        Bitmap bitmap = Bitmap.createBitmap(weidth, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.argb(130,144,144,153));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setColor(Color.argb(255,255,255,255));
        paint.setAntiAlias(true);
        paint.setTextSize(12.f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Selected", (weidth / 2.f) , (height / 2.f), paint);
        return  bitmap;
    }
    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;
    }
    public class asyncClass extends AsyncTask<Void, Void,Void>{
int executrCode = 0;
    int pis =99;
        @Override
        protected Void doInBackground(Void... voids) {
            if(selectedPosition.size() !=0) {
                for (int i = 0; i < selectedPosition.size(); i++) {
                    if (positionF == selectedPosition.get(i)) {
                executrCode=1;
                pis=i;
                        break;
                    } else if (i == selectedPosition.size() - 1) {
                        executrCode =2;
                        pis =i;
                    }
                }
            }else {

executrCode =3;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(executrCode==1){
                ListImage.remove(pis);
                selectedPosition.remove(pis);
                holderf.imageview.setImageBitmap(bmp);

                textView.setText("Select photos["+(maximage - selectedPosition.size()) +"left]");
            }else if(executrCode ==2){
          if(selectedPosition.size()!=maximage) {
              selectedPosition.add(positionF);
              holderf.imageview.setImageBitmap(overlayadded);
//            Log.e("onClick2: ", positionF + " ");
             textView.setText("Select photos["+(maximage- selectedPosition.size()) +"left]");
              ListImage.add(  gallaryList.get(positionF).getUri().getPath() );
          }
          else {
              Toast.makeText(context,"max limit reached", Toast.LENGTH_SHORT).show();
          }
            }else if (executrCode==3){
                selectedPosition.add(positionF);
                Log.e("onClick1: ", positionF + " ");
                textView.setText("Select photos["+(maximage- selectedPosition.size()) +"left]");
                ListImage.add( gallaryList.get(positionF).getUri().getPath() );
                holderf.imageview.setImageBitmap(overlayadded);

            }
            super.onPostExecute(aVoid);
        }
    }
}
