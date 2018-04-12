package com.snaplava.SnapLava;

import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.facebook.CallbackManager;
import com.facebook.Profile;

import com.snaplava.SnapLava.Adapters.ExampleAdapter;
import com.snaplava.SnapLava.Adapters.ListAdapter;
import com.snaplava.SnapLava.ErrorCheck.NetworkCheckActivity;
import com.snaplava.SnapLava.fields.Paginator;

import com.snaplava.SnapLava.frags.layout.TriangleFragment;
import com.snaplava.SnapLava.frags.layout.WaterFragment;
import com.squareup.picasso.Picasso;
import com.srx.widget.PullToLoadView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;


public class PictureActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    PullToLoadView pullToLoadView;
    RequestQueue requestQueue;

    TwitterSession sesssion;
    Profile profile;
    SearchView searchView;
    MenuItem item1;
    CallbackManager callbackManager;
    ListView list_view_inside_nav;
    WaterFragment waterFragment ;
    TriangleFragment triangleFragment;
    SharedPreferences preferences;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    AppBarLayout barl;
    Toolbar toolbar;
    TextView textView, name;
    ImageView suggestions;
    CircleImageView imageview;
    TextView advanceSearch;
    int position=0;
    int get_position=0;
    ArrayList<String> dataone = new ArrayList<>();
    LayoutInflater inflater;
    int mode = 9;
    ArrayList<String> listArray = new ArrayList<>();

    private boolean isCategoryOpen = false;
    boolean isModeSelected = false;
    String message_token;
    int search_mode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_picture);
        ActivityCompat.requestPermissions(PictureActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},

                1);


        //searchVie
dataone.add("All categories");
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

          advanceSearch = (TextView) findViewById(R.id.acvance_);
          toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
barl = (AppBarLayout) findViewById(R.id.appbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        list_view_inside_nav = (ListView) findViewById(R.id.list_view_inside_nav);

        // Get_hash_key();
        //  LoginManager.getInstance().logOut();
        listArray.add("category");
        listArray.add("category");
        listArray.add("category");
        listArray.add("category");
        listArray.add("user");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alert();
            }
        });
        getSupportActionBar().setTitle("Recent uploads");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        SharedPreferences check = getSharedPreferences("check", Context.MODE_PRIVATE);

       preferences = getSharedPreferences("access_token", Context.MODE_PRIVATE);
       message_token = preferences.getString("token", null);

     String mes_Cehck = check.getString("val", "100");
       if (mes_Cehck.equals("100")){
           if (message_token!=null){
               SharedPreferences.Editor editor1 = preferences.edit();
               editor1.clear();
               editor1.commit();
               SharedPreferences check1 = getSharedPreferences("check", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = check1.edit();
               editor.putString("val","1");
               editor.commit();
               message_token = null;
           }
       }
        profile = Profile.getCurrentProfile();
        sesssion = TwitterCore.getInstance().getSessionManager().getActiveSession();
        callbackManager = CallbackManager.Factory.create();
        if(isOnline()== true){
            if ( !TextUtils.isEmpty(message_token)) {
                pullToLoadView = (PullToLoadView) findViewById(R.id.pullToLoadView);
                new Paginator(getApplicationContext(),pullToLoadView,requestQueue).initializePaginator();
              /*  if(profile!= null){
                    //,Login_DataSav(profile.getFirstName()+" "+profile.getLastName(),profile.getId(), String.valueOf(profile.getProfilePictureUri(60,60)));
                }else if (sesssion != null){

                    Call<User> user = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false,false);
                    user.enqueue(new Callback<User>() {
                        @Override
                        public void success(Result<User> userResult) {
                            String name = userResult.data.name;
                            String email = userResult.data.email;


                            String photoUrlNormalSize   = userResult.data.profileImageUrl;
                            String photoUrlBiggerSize   = userResult.data.profileImageUrl.replace("_normal", "_bigger");
                            String photoUrlMiniSize     = userResult.data.profileImageUrl.replace("_normal", "_mini");
                            String photoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");
                            Log.e( "success: ",name+email+photoUrlNormalSize );
                           // Login_DataSav(name,userResult.data.idStr,photoUrlNormalSize);
                        }

                        @Override
                        public void failure(TwitterException exc) {
                            Log.d("TwitterKit", "Verify Credentials Failure", exc);

                        }
                    });
                    //
                }*/
            }else {
               startActivity(new Intent(getApplicationContext(), MainActivity.class));
               finish();
            }
        }
        else if (isOnline() == false ){
           startActivity(new Intent(getApplicationContext(), NetworkCheckActivity.class));
           finish();
        }

        // nevigation drawercode

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drwaer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

// header items nevigation drawer
       imageview = (CircleImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.name);
         name = (TextView) findViewById(R.id.textView) ;
        ImageView inageView =   (ImageView) findViewById(R.id.back_go);
        Login_DataSav();
        ArrayList <String> data = new ArrayList<>();
        data.add("Home");
        data.add("My photos");
        data.add("Profile");
        data.add("Collage");
        data.add("Change password");
        data.add("Sign Out");
        ListAdapter adapter = new ListAdapter(getApplicationContext(),PictureActivity.this, data, inageView, isCategoryOpen,drawer,list_view_inside_nav,dataone );
        list_view_inside_nav.setAdapter(adapter);





       // adapter_cat = new SearchCAtegory(inflater, getApplicationContext(), searchBar);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(PictureActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
            }
            return;
            }

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void Get_hash_key() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.snaplava.SnapLava", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    private void Alert() {
//Alert();
        AlertDialog.Builder builder;
        final AlertDialog alertDialog;
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.main_alert,
                null);
        ImageView id_cam = (ImageView) layout.findViewById(R.id.id_cam);
        ImageView id_gall = (ImageView) layout.findViewById(R.id.id_gall);
        builder = new AlertDialog.Builder(PictureActivity.this, R.style.CustomDialog);
        int height= layout.getHeight();
        int width = layout.getWidth();
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER;
        ///wmlp.x = 100;   //x position
        wmlp.y = 200;   //y position

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getAttributes().windowAnimations =
                R.style.dialog_animation;

        alertDialog.show();

//           WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        id_gall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),GallaryActivity.class));
            }
        });
        id_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),CameraActivity.class));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
       // MenuItem myMenuItem = menu.findItem(R.id.my_menu_item);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        final MenuItem item=menu.findItem(R.id.procura);
       final  MenuItem item1 = menu.findItem(R.id.categoryswitch);
       this.item1 =item1;
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //tbs.setVisibility(View.GONE);
                //vwpgr.setVisibility(View.GONE);

               item1.setEnabled(true);
                item1.setVisible(true);
                toolbar.setBackgroundColor(Color.WHITE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_picture, new WaterFragment(), "search").commit();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //tbs.setVisibility(View.VISIBLE);
              //  vwpgr.setVisibility(View.VISIBLE);

                removeFragment();
                invalid_menu( );

                return true;
            }
        });
        searchView=(SearchView) MenuItemCompat.getActionView(item);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
         searchView.setBackgroundColor(Color.WHITE);
        searchView.setQueryHint("Search by category");

        final SearchView.SearchAutoComplete mSearchSrcTextView = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

      mSearchSrcTextView.setTextColor(Color.BLACK);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                MakeSEarch(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              //  SearchView.SearchAutoComplete mSearchSrcTextView = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
                //mSearchSrcTextView.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
                //mSearchSrcTextView.setDropDownBackgroundResource(R.color.color_like);

                return false;
            }
        });
        final Spinner spinner = (Spinner) item1.getActionView();
         spinner.setDropDownWidth(2000);
         spinner.setBackgroundResource(R.drawable.bg_spinner);
         spinner.setDropDownVerticalOffset(112);
        // getResources().getDimensionPixelSize(R.id.toolbar);
         //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.drop, R.layout.spinner_item);
       //spinner.dispatchSetSelected(false);
       // spinner.setAdapter(adapter);
        CustomAdapter<String> adapter3 = new CustomAdapter<String>(this,get_position,
                android.R.layout.simple_spinner_dropdown_item, new String[] {"      Search by category", "      Search by Users"});
        adapter3.setDropDownViewResource(R.layout.dropitem);
        spinner.setAdapter(adapter3);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                waterFragment = (WaterFragment) getSupportFragmentManager().findFragmentByTag("search");
                triangleFragment = (TriangleFragment) getSupportFragmentManager().findFragmentByTag("triangle");
                if (position==0){
                 mSearchSrcTextView.setHint("Search by category");search_mode=0;
                mSearchSrcTextView.setHintTextColor(Color.GRAY);
                 advanceSearch.setVisibility(View.VISIBLE);mSearchSrcTextView.setText(null);

                 removeFragment();
                     getSupportFragmentManager().beginTransaction().replace(R.id.content_picture, new WaterFragment(), "search").commit();



             }else {
                 mSearchSrcTextView.setHint("Search by users");search_mode=1;
                 mSearchSrcTextView.setHintTextColor(Color.GRAY);
                 advanceSearch.setVisibility(View.GONE);mSearchSrcTextView.setText(null);
             removeFragment();
                 getSupportFragmentManager().beginTransaction().replace(R.id.content_picture, new TriangleFragment(), "triangle").commit();

             }
                get_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
      advanceSearch.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {


                                               final ArrayList<String> refined_list = new ArrayList<>();
                                               AlertDialog.Builder builder = new AlertDialog.Builder(PictureActivity.this);
                                               LayoutInflater inflater = getLayoutInflater();
                                               View dialogView = inflater.inflate(R.layout.listlay, null);


                                               final ListView modeList = (ListView) dialogView.findViewById(R.id.listView);
                                               final ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice
                                                       , android.R.id.text1, dataone);
                                               modeList.setAdapter(modeAdapter);
                                               Button button = (Button) dialogView.findViewById(R.id.search_button);


                                               // builder.setView(modeList);
                                               button.setText("Search");
                                               ImageView cancel_dialog = (ImageView) dialogView.findViewById(R.id.cancel_dialog);
                                               button.setGravity(Gravity.BOTTOM);
                                               get_position=1;

                                               builder.setView(dialogView);

                                               modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                   @Override
                                                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                       if (position==0) {
                                                           if (get_position == 0) {
                                                               for (int i = 0; i < dataone.size(); i++) {
                                                                   modeList.setItemChecked(i, true);
                                                               }
                                                               get_position = 1;
                                                           } else if (get_position == 1) {


                                                               for (int i = 0; i < dataone.size(); i++) {
                                                                   modeList.setItemChecked(i, false);
                                                               }
                                                               get_position =0;

                                                           }
                                                       }else {
                                                           get_position=0;
                                                           modeList.setItemChecked(0,false);
                                                       }
                                                   }
                                               });
                                               final Dialog dialog = builder.create();

                                               dialog.show();

                                               modeList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                                               for (int i =0;i<modeList.getAdapter().getCount();i++){
                                                   modeList.setItemChecked(i,true);
                                               }
                                               cancel_dialog.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       dialog.cancel();
                                                   }
                                               });
                                   button.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           String concat = "";
                                           String Searchby = "Search by ";
                                           int len = modeList.getCount();
                                           SparseBooleanArray checked = modeList.getCheckedItemPositions();
                                           int check = 0;
                                           for (int i = 0; i < len; i++)
                                           {       if (checked.get(0)) {
                                                   concat = dataone.get(0);
                                                   break;
                                               } else if (checked.get(i)) {

                                                   concat = concat + dataone.get(i)+",";

                                                   //mSearchSrcTextView.setText(Searchby+ " "+concat);

                                               }
                                       }
                                           concat = concat.substring(0, concat.length()-1);
                                           Log.e(  "onClick: ",concat );
                                        mSearchSrcTextView.setText(Searchby + " "+ concat);
                                          get_position=0;

                                          CallCAtegorySearch(dialog,concat);
                                         //  dialog.dismiss();
                                       }

                                   });
                                           }
                                       });


        //  final AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //this remove left padding
       // searchTextView.setDropDownAnchor(R.id.appbar);
        // this is not working
       // searchTextView.setDropDownWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
   /*  String[] columnNames = {"_id","text"};
        MatrixCursor cursor = new MatrixCursor(columnNames);
        String[] array = getResources().getStringArray(R.array.drop); //if strings are in resources
        String[] temp = new String[2];
        int id = 0;
        for(String item2 : array){
            temp[0] = Integer.toString(id++);
            temp[1] = item2;
            cursor.addRow(temp);
        }
     //   String[] from = {"text"};
       // int[] to = {R.id.name_entry};
        ExampleAdapter adapter = new ExampleAdapter(getApplicationContext(), cursor);
        searchView.setSuggestionsAdapter(adapter);
        //and i tryed this too: searchTextView.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
  /*      try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.ic_action_one); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
        }
*/
        return super.onCreateOptionsMenu(menu);
    }

    public void invalid_menu(   ) {
        toolbar.setBackgroundColor(getColor(R.color.colorPrimary));advanceSearch.setVisibility(View.GONE);
        supportInvalidateOptionsMenu();
        item1.setEnabled(false);
        item1.setVisible(false);
    }

    public void CallCAtegorySearch(Dialog dialog, String search_cat) {
   waterFragment = (WaterFragment) getSupportFragmentManager().findFragmentByTag("search");
  dialog.cancel();
   if (waterFragment != null){
      // waterFragment.getPaginator().initializePaginator(0, );
       waterFragment.getPaginator().initLoad("", search_cat);
   }
    }

    private void MakeSEarch(String sarch_key) {

    if (search_mode==0){
        waterFragment = (WaterFragment) getSupportFragmentManager().findFragmentByTag("search");
        if (waterFragment!=null){
          //  waterFragment.getPaginator().initializePaginator(search_mode,);
            waterFragment.getPaginator().initLoad(sarch_key, "");
        }
    }else if (search_mode ==1 ){
        triangleFragment = (TriangleFragment) getSupportFragmentManager().findFragmentByTag("triangle");
        if (triangleFragment != null){
          //  triangleFragment.getPaginator().initializePaginator(search_mode, );
            triangleFragment.getPaginator().initLoad(sarch_key, "");
        }
    }

    }

    public void removeFragment() {
        TriangleFragment fragment_tri = (TriangleFragment)getSupportFragmentManager().findFragmentByTag("triangle");
        WaterFragment waterFragment = (WaterFragment) getSupportFragmentManager().findFragmentByTag("search");
        if (fragment_tri != null){
            getSupportFragmentManager().beginTransaction().remove( getSupportFragmentManager().findFragmentByTag("triangle")).commit();

        }else                 if (waterFragment!= null){
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("search")).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*  if (id == R.id.action_bars){
            TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
            Profile profile =Profile.getCurrentProfile();
            if(session!= null){
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
            } if (profile!= null){
                LoginManager.getInstance().logOut();
            }
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }*/
        return super.onOptionsItemSelected(item);
    }
    private void Login_DataSav(  ) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.snaplava.com/live/public/api/v1/users/details", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(  "onResponse: ",response );
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonnext = jsonObject.getJSONObject("content");
                    textView.setText(jsonnext.getString("name"));
                    name.setText(jsonnext.getString("email"));
                    Picasso.with(getApplicationContext()).load(jsonnext.getString("user_profile_photo")).into(imageview);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(  "onErrorResponse: ",error+" " );
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> hasmap = new HashMap<>();
                hasmap.put("Authorization","Bearer "+message_token);
                return hasmap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public int getSearch_mode() {
        return search_mode;
    }

    public void setSearch_mode(int search_mode) {
        this.search_mode = search_mode;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drwaer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drwaer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private static class CustomAdapter<T> extends ArrayAdapter<String> {
       int get_position;
        public CustomAdapter(Context context,int get_position, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        this.get_position=get_position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText("");
            return view;
        }
    }

}
