package com.snaplava.SnapLava.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.bumptech.glide.Glide;
import com.snaplava.SnapLava.PicviewActivity;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.ViewImageActivity;
import com.snaplava.SnapLava.ViewPictureActivity;
import com.snaplava.SnapLava.fields.postcards;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dell on 1/18/2018.
 */

public class PaginatAdapter extends RecyclerView.Adapter<PaginatAdapter.ViewHolder> {
    ArrayList  <postcards> PostCardList;
    Context context;
    PaginatAdapter adapter;
    public PaginatAdapter(Context context, ArrayList  <postcards> PostCardList) {
      this.context = context;
      this.PostCardList = PostCardList;
        this.adapter =this;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_grid,parent,false);
        ViewHolder holder=new ViewHolder(v);
        return holder;

    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.id_imageView.setVisibility(View.INVISIBLE);
        Picasso.with(context).load(PostCardList.get(position).getImage_medium()).resize(200,0).into(holder.id_imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.INVISIBLE);
            holder.id_imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

            }
        });


        Log.e( "onBindViewHolder: ", PostCardList.get(position).getImage_medium() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PicviewActivity.class);
                intent.putExtra("allover",PostCardList.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity(intent);

            }
        });
    }
    // Adds element when loads
    public void add(postcards postcards) {
        PostCardList.add(postcards);
        adapter.notifyDataSetChanged();
    }
    // clears the list on refresh
    public void clear() {
       PostCardList.clear();
       adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return PostCardList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
ImageView id_imageView;ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);
            id_imageView = (ImageView) itemView.findViewById(R.id.id_imageView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_show);
        }
    }
}
