package com.snaplava.SnapLava.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.fields.postcards;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by alegralabs on 26/03/18.
 */

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.viewHolder>  {
ArrayList<postcards> postArray ;
ArrayList<String> dataone = new ArrayList<>();
String[] title, category, description;
Activity activity;
Context context;
    public TitleAdapter(Context context, ArrayList<postcards> postArray, Activity activity, String [] title,String []   category, String []   description) {
        this.context = context;
        this.postArray = postArray;
        this.activity = activity;
        this.title = title;
        this.category = category;
        this.description = description;
        dataone.add("Abstract");
        dataone.add("Aerial");
        dataone.add("Animal");
        dataone.add("Architecture");dataone.add("Astrophotography");
        dataone.add("Avian");
        dataone.add("Black and White");
        dataone.add("Cityscape");
        dataone.add("Current Events");
        dataone.add("Decisive Moment");
        dataone.add("Defocused");
        dataone.add("Documentary");
        dataone.add("Emotive");
        dataone.add("Expression");dataone.add("Family");

        dataone.add("Fashion");
        dataone.add("Film"); dataone.add("Fine Art"); dataone.add("Food"); dataone.add("Glamour"); dataone.add("HDRI (High Dynamic Range Imaging)"); dataone.add("Humorous"); dataone.add("ICM (intentional camera movement)"); dataone.add("Industrial"); dataone.add("Infrared"); dataone.add("Interior"); dataone.add("Journalism");
        dataone.add("Landscape"); dataone.add("Lomo"); dataone.add("Macro"); dataone.add("Nature"); dataone.add("Nude"); dataone.add("Panoramas/Mosaics"); dataone.add("Performance"); dataone.add("Pinhole");
        dataone.add("Portrait");dataone.add("Product");dataone.add("Publicity");dataone.add("Random");dataone.add("Recycled Art");dataone.add("Rough Camera");dataone.add("Rural");dataone.add("Sea and Sand");dataone.add("Sky");dataone.add("Snapshot");dataone.add("Sports");dataone.add("Still Life");dataone.add("Stock");dataone.add("Street Photography");dataone.add("Suburban");
        dataone.add("Suburban");dataone.add("Swimsuit");dataone.add("Tourist");dataone.add("Travel");dataone.add("Underwater");dataone.add("Urban");dataone.add("Vehicle");dataone.add("Vintage");dataone.add("Weather");dataone.add("Wedding");

    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_unit,parent,false);
        viewHolder holder=new viewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        Picasso.with(context).load(postArray.get(position).getImage_medium()).into(holder.imageView);

        holder.description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              description[position] = s.toString();
            }
        });

        holder.title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              title[position] = s.toString();
            }
        });

        holder.image_category.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Create_dialog(holder.image_category, position);
    }
});

    }

    @Override
    public int getItemCount() {
        return postArray.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
TextView image_category;EditText description, title ;
   ImageView imageView;

        public viewHolder(View itemView) {
            super(itemView);
            image_category = (TextView) itemView.findViewById(R.id.image_category);
            imageView = (ImageView) itemView.findViewById(R.id.image_recycler);
            description = (EditText) itemView.findViewById(R.id.description);
            title = (EditText) itemView.findViewById(R.id.image_Title);
        }
    }
    private void Create_dialog(final TextView holder, final int position_){
       final ArrayList<String> refined_list = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Categories");
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        EditText editText = new EditText(context);
        final ListView modeList = new ListView(context);
        String[] stringArray = new String[] { "Bright Mode", "Normal Mode" };
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, dataone);
        modeList.setAdapter(modeAdapter);linearLayout.addView(editText);
        linearLayout.addView(modeList);
        builder.setView(linearLayout);
        // builder.setView(modeList);
        final Dialog dialog = builder.create();

        dialog.show();

        modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (refined_list.size() ==0){
                    holder.setText(dataone.get(position));
                    category[position_] = dataone.get(position);
                }else {
                    holder.setText(refined_list.get(position));
                    category[position_] = refined_list.get(position);
                }
                dialog.dismiss();
            }
        });


editText.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        refined_list.clear();

        for(int i =0; i<dataone.size();i++){
    if(dataone.get(i).contains(s.toString())){
        refined_list.add(dataone.get(i));
    }
}
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, refined_list);
        modeList.setAdapter(modeAdapter);
    }
});


    }
}
