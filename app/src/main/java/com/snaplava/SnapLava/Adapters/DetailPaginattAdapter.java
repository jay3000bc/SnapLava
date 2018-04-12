package com.snaplava.SnapLava.Adapters;

        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import com.snaplava.SnapLava.PicviewActivity;
        import com.snaplava.SnapLava.R;
        import com.snaplava.SnapLava.ViewImageActivity;
        import com.snaplava.SnapLava.ViewPictureActivity;
        import com.snaplava.SnapLava.fields.postcards;
        import com.snaplava.SnapLava.utils.TimeAgo;
        import com.squareup.picasso.Callback;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;

/**
 * Created by Dell on 2/6/2018.
 */

public class DetailPaginattAdapter extends RecyclerView.Adapter<DetailPaginattAdapter.viewHolder> {
    ArrayList  <postcards> PostCardList;
    Context context;
    DetailPaginattAdapter adapter;
    int mode;
    public DetailPaginattAdapter(Context context,ArrayList<postcards> PostCardList, int mode) {
        this.context = context;
        this.PostCardList = PostCardList;
        this.mode = mode;
        this.adapter =this;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.unit_grid,parent,false);
        viewHolder holder=new viewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        holder.id_imageView.setVisibility(View.INVISIBLE);
        String url = null;
       if (mode ==0){
           url = PostCardList.get(position).getImage_medium();
       }else if (mode ==1){
           url= PostCardList.get(position).getUser_profile_photo();
       }
        Picasso.with(context).load(url).resize(200,0).into(holder.id_imageView, new Callback() {
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

    @Override
    public int getItemCount() {
        return PostCardList.size();
    }
    public void add(postcards postcards) {

        PostCardList.add(postcards);
        adapter.notifyDataSetChanged();
    }
    // clears the list on refresh
    public void clear() {
        PostCardList.clear();
        adapter.notifyDataSetChanged();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView id_imageView;ProgressBar progressBar;
        public viewHolder(View itemView) {
            super(itemView);
            id_imageView = (ImageView) itemView.findViewById(R.id.id_imageView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_show);
        }
    }
}
