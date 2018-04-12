package com.snaplava.SnapLava.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.fields.Upcount;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Dell on 1/19/2018.
 */

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.viewHolder> {
   ArrayList<Upcount> arrrayUps;
    Context context;
    UploadAdapter adapter;
    public UploadAdapter(Context context, ArrayList<Upcount> arrrayUps) {
       this.context =context;
        this.arrrayUps = arrrayUps;
        this.adapter = this;
    }
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_upload,parent,false);
        return new viewHolder(v);
    }
public void AddElement(Upcount upcounts){
    Collections.reverse(arrrayUps);
    arrrayUps.add(upcounts);
    Collections.reverse(arrrayUps);
    adapter.notifyDataSetChanged();

}
    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
            holder.image_unit.setImageBitmap(gtbitmap(arrrayUps.get(position).getImageUri()));
            holder.file_size.setText(getfileSize(arrrayUps.get(position).getImageUri()));
        holder.file_name.setText(getfileName(arrrayUps.get(position).getImageUri()));
        holder.image_progress.setBackgroundColor(Color.parseColor(String.valueOf(getColorRandom())));
    }

    private String getfileName(Uri imageUri) {
        File f = new File(imageUri.getPath());
        return f.getName();
    }
    private String getColorRandom() {
        int random = new Random().nextInt(9);
        Log.e( "getColorRandom: ",random+" " );
        String color = null;
        if(random==0){
            color = "#e62222";
        }else if (random==2){
            color = "#f1e10a";
        }else if (random==3){
            color = "#51ef13";
        }else if (random==4){
            color = "#0ff1da";
        }else if (random==5){
            color = "#1822f1";
        }else if (random==6){
            color ="#e919e2";
        }else if (random==7){
            color = "#f7182e";
        }else if (random==8){
            color = "#9e19f1";
        }else if (random==9){
            color = "#ed1ca4";
        }else if (random==1){
            color = "#0de785";
        }
        return color;
    }

    private String getfileSize(Uri imageUri) {
        File f = new File(imageUri.getPath());
        long size = f.length();
        return size/1024+" KB";
    }

    public Bitmap gtbitmap (Uri uri){
        Bitmap bitmap= null;
        try {
              bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return arrrayUps.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
TextView file_name,text100,file_size;
    ImageView image_unit,image_progress;
        public viewHolder(View itemView) {
            super(itemView);
            file_name = (TextView) itemView.findViewById(R.id.file_name);
            text100 = (TextView) itemView.findViewById(R.id.text100);
            file_size = (TextView) itemView.findViewById(R.id.file_size);
            image_unit = (ImageView) itemView.findViewById(R.id.image_unit);
            image_progress = (ImageView) itemView.findViewById(R.id.image_progress);

        }
    }
}
