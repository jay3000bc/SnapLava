package com.snaplava.SnapLava.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.snaplava.SnapLava.CollageActivity;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.frags.layout.CustomDFragment;
import com.snaplava.SnapLava.frags.layout.HeartFragment;
import com.snaplava.SnapLava.frags.layout.Hor2CutFragment;
import com.snaplava.SnapLava.frags.layout.Layout2imageFragment;
import com.snaplava.SnapLava.frags.layout.TriangleFragment;
import com.snaplava.SnapLava.frags.layout.VertCutFragment;

import java.util.ArrayList;

/**
 * Created by alegralabs on 09/03/18.
 */

public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.layoutview> {
   ArrayList<Integer> intarray;
   Context context;
   int container;
  layoutview hold= null; ArrayList<String> uriList;LayoutAdapter adapter ;
   android.support.v4.app.FragmentManager activity;
   int laypos;
   Activity activity_current;
    public LayoutAdapter(Context context, ArrayList<Integer> intarray, int container, android.support.v4.app.FragmentManager activity,ArrayList<String> uriList, Activity activity_current ) {
        this.context = context;
        this.intarray = intarray;
       this.container = container;
       this.activity = activity;
       this.activity_current = activity_current;
       this.uriList = uriList;
       this.adapter = this;
        laypos = ((CollageActivity)activity_current).getLayout_position();
    }

    @Override
    public layoutview onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_lay,parent,false);
        layoutview holder=new layoutview(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final layoutview holder, final int position) {
        holder.imageView.setImageResource(intarray.get(position));
        if(laypos ==position){
            holder.imageView.setBackgroundColor(Color.GRAY);
            hold = holder;
        }
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

   if(position==0 || position ==1 || position ==2){

       activity.beginTransaction().replace(container, new VertCutFragment(uriList,position),"layoutvertcut").commit();

   }else if(position ==3) {
       activity.beginTransaction().replace(container, new CustomDFragment(uriList), "customlay").commit();

   }

   holder.imageView.setBackgroundColor(Color.GRAY);;
   if(hold != null){
       hold.imageView.setBackgroundColor(Color.WHITE);

   }
   hold = holder;
        ((CollageActivity)activity_current).setLayout_position(position);
    }
});
    }

    @Override
    public int getItemCount() {
        return intarray.size();
    }

    public class layoutview extends RecyclerView.ViewHolder{
ImageView imageView;
        public layoutview(View itemView) {

            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.id2_imageView);

        }
    }
}
