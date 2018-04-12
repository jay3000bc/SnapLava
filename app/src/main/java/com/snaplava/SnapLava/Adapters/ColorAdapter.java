package com.snaplava.SnapLava.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.frags.WriteFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alegralabs on 13/03/18.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.viewHolder> {
  Context context;
    android.support.v4.app.FragmentManager fragmentManager;
    int container;
    String textstylde="Coiny-Regular.ttf";
    TextAdapter adapter;
    int color=Color.BLUE;
ArrayList<Integer> colorList = new ArrayList<>();
    public ColorAdapter(Context context,android.support.v4.app.FragmentManager fragmentManager, int container) {
       this.context = context;
      this.fragmentManager = fragmentManager;
      this.container = container;




      colorList.add(Color.argb(255,230,34,34));
        colorList.add(Color.argb(255,241,225,10));
        colorList.add(Color.argb(255,81,239,19));
        colorList.add(Color.argb(255,15,241,218));
        colorList.add(Color.argb(255,24,34,241));
        colorList.add(Color.argb(255,247,24,46));
        colorList.add(Color.argb(255,158,25,241));
        colorList.add(Color.argb(255,237,28,164));

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
   holder.itemView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           color = colorList.get(position);
          textstylde = adapter.getStyle();
           WriteFragment fragm = (WriteFragment)fragmentManager.findFragmentByTag("writefrag");

           Bitmap bitmap = fragm.textAsBitmap("", 54f,color, textstylde );
           fragm.createViewBitmap(bitmap);
       }
   });
    }
public  void getAdapterOne( TextAdapter adaper){
        this.adapter = adaper;
}
    @Override
    public int getItemCount() {
        return 8;
    }
public int getColor(){
        return  color;
}
    public class viewHolder extends RecyclerView.ViewHolder{
CircleImageView imageview;
        public viewHolder(View itemView) {
            super(itemView);
            imageview = (CircleImageView) itemView.findViewById(R.id.image_color_1);
        }
    }
    public Bitmap getBitmap(int color){
        Bitmap bmp=Bitmap.createBitmap(50,50, Config.ARGB_8888);
        Canvas canvas=new Canvas(bmp);
        canvas.drawColor(color);
        return bmp;
    }
}
