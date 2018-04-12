package com.snaplava.SnapLava.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.snaplava.SnapLava.LetsCollageActivity;
import com.snaplava.SnapLava.MainActivity;
import com.snaplava.SnapLava.PictureActivity;
import com.snaplava.SnapLava.ProfileActivity;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.frags.layout.TriangleFragment;
import com.snaplava.SnapLava.utils.ResizeAnimation;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by alegralabs on 20/02/18.
 */

public class ListAdapter  extends BaseAdapter{
    private static LayoutInflater inflater=null;
    ArrayList<String> data;
    ImageView imageView;
    boolean iscatergory = false;
    DrawerLayout drawer;
    Activity activity;
ListView list_view_inside_nav;
ArrayList <String> dataone  ;
    ArrayAdapter<String> arrayAdapter;
    Context context;
    ListAdapter adapter;
public ListAdapter(Context context, Activity activity, ArrayList<String> data, ImageView imageview, boolean isCategory, DrawerLayout drawer, ListView list_view_inside_nav, ArrayList<String> dataone ){
    inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.context = context;
    this.data = data;
    this.imageView = imageview;
    this.activity = activity;
    this.drawer = drawer;
    dataone.add("Abstract");
    dataone.add("Aerial");
    dataone.add("Animal");
    this.adapter = this;
    dataone.add("Architecture");dataone.add("Astrophotography");
this.list_view_inside_nav = list_view_inside_nav;
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
    arrayAdapter = new ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_1,
            dataone){
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView text = (TextView) view.findViewById(android.R.id.text1);
            text.setTextColor(Color.BLACK);
            return view;
        }
    };;

}





    @Override
    public int getCount() {
      return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.unit_list, null);
        TextView title = (TextView)vi.findViewById(R.id.meview); // title
        ImageView cv = (ImageView) vi.findViewById(R.id.icon_identifier);
        //======
        title.setText(data.get(position));
        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(position == 0){

              //   AllExcess();
                 //   list_view_inside_nav.setBackgroundColor(Color.argb(100,52, 111,219));
                    list_view_inside_nav.setAdapter(arrayAdapter);
                  iscatergory = true;
                }else*/
                    if (position== 3){
                  Intent intent = new Intent(context, LetsCollageActivity.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else if (position ==0){
                        ((PictureActivity)activity).removeFragment();
                        ((PictureActivity)activity).invalid_menu();
                        drawer.closeDrawer(GravityCompat.START);
                     } else if(position==5){
                    TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    Profile profile = Profile.getCurrentProfile();
                    if(session!= null){

                        TwitterCore.getInstance().getSessionManager().clearActiveSession();
                    } if (profile!= null){
                        LoginManager.getInstance().logOut();
                    }

                  SharedPreferences sharedPreferences = context.getSharedPreferences("access_token", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(context,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    activity.finish();
                }else if (position == 2){
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AllExcess();
            }
        });
        return vi;
    }

    private void AllExcess() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            if(iscatergory==true){

                //  drawer.openDrawer(GravityCompat.START);
//adapter.
                list_view_inside_nav.setBackgroundColor(Color.argb(0, 255,255,255));
        list_view_inside_nav.setAdapter(adapter);
                iscatergory=false;
            }else if(iscatergory==false){
                drawer.closeDrawer(GravityCompat.START);

            }
            Log.e("AllExcess: ",iscatergory+" ");
        }

    }
}
