package com.snaplava.SnapLava.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snaplava.SnapLava.CollageActivity;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.frags.WriteFragment;

import java.util.ArrayList;

/**
 * Created by alegralabs on 13/03/18.
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.viewHolder> {
 ArrayList<String> arrayFonts;
 Context context;
 int container;
    int color=Color.BLUE;
    String textstyle="Coiny-Regular.ttf";
 ColorAdapter adapter;
    android.support.v4.app.FragmentManager  fragmentManager;
    public TextAdapter(Context context, ArrayList<String> arrayFonts, android.support.v4.app.FragmentManager  fragmentManager,int container) {
       this.context = context;
       this.arrayFonts = arrayFonts;
       this.fragmentManager = fragmentManager;
       this.container = container;


    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.textstyle,parent,false);
        viewHolder holder=new viewHolder(v);
        return holder;

    }
    public  void getAdapterOne(ColorAdapter adapter){
        this.adapter = adapter;
    }
    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.textStyle_one.setText("A");
         Typeface typeFace = Typeface.createFromAsset(context.getAssets(),"fonts/"+arrayFonts.get(position));

        holder.textStyle_one.setTypeface(typeFace);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
textstyle = arrayFonts.get(position);
color = adapter.getColor();
            WriteFragment fragm = (WriteFragment)fragmentManager.findFragmentByTag("writefrag");
        Bitmap bitmap =fragm.textAsBitmap("", 54f, color,arrayFonts.get(position));
        fragm.createViewBitmap(bitmap);
        }
    });
    }

    @Override
    public int getItemCount() {
        return arrayFonts.size();
    }
public String getStyle(){
        return textstyle;
}
    public class viewHolder extends RecyclerView.ViewHolder{
TextView textStyle_one;
    public viewHolder(View itemView) {
        super(itemView);
        textStyle_one = (TextView ) itemView.findViewById(R.id.textStyle_one);
    }
}
}
