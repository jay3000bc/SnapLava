package com.snaplava.SnapLava;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import com.snaplava.SnapLava.customs.ClipArt;
import com.snaplava.SnapLava.fields.postcards;
import com.snaplava.SnapLava.utils.Blur;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PicviewActivity extends AppCompatActivity implements View.OnClickListener{

    boolean isUp = false;
    ImageView imv_1,imv_two, imv_4,imv_5;
    Animation fabopen, fabclose, fabrotateplus, fabrotateneg, appear, disappear,swipeleft,swipetop , swiperight,swapidown;
    LinearLayout anim_where;
    ImageView show_all;
    ImageView and_imageview;
    ImageView blurview;
    postcards postcard;

    public LoginButton login_button_m;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public TwitterLoginButton login_buttonTwitter_m;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    Profile profile;
    CircleImageView Poster_face;
    RequestQueue requestQueue;
    String IDUSER;
    Bitmap main_bitmap = null;
    TextView wallpaperset;
    //Bitmap bitmapCheck;
    FrameLayout frcirc_frm;
CardView card_one;
ShareButton shareButton;
String Bearer_token;
boolean isbackpressed = false;
TextView fullscreen_new;
Matrix initialMatrix = null;
TextView Caption,Datene;
LinearLayout likesCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_picview);
        getSupportActionBar().hide();
        verifyStoragePermissions(PicviewActivity.this);
        fabopen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabclose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabrotateplus = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clock);
        fabrotateneg = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticl);
        appear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear);
        disappear = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.disapear);
        frcirc_frm = (FrameLayout) findViewById(R.id.circ_frm);
        Caption = (TextView) findViewById(R.id.Caption);
        Datene = (TextView) findViewById(R.id.Date);
        likesCount = (LinearLayout) findViewById(R.id.likesCount);
        show_all = (ImageView) findViewById(R.id.show_all);
        swipeleft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.swipeleft);
        swipetop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.swipetop);
        swiperight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.swiperight);
        swapidown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.swipedown);
        and_imageview = (ImageView) findViewById(R.id.and_imageview);
        wallpaperset= (TextView) findViewById(R.id.wallpaperset);
 SharedPreferences preferences = getSharedPreferences("access_token", Context.MODE_PRIVATE);
        Bearer_token = preferences.getString("token", null);
        imv_1 = (ImageView) findViewById(R.id.imv1);
        imv_two = (ImageView) findViewById(R.id.imv2);

        imv_4 = (ImageView) findViewById(R.id.imv4);
        imv_5 = (ImageView) findViewById(R.id.imv5);
//-----

        anim_where = (LinearLayout) findViewById(R.id.translate_view);
        blurview = (ImageView) findViewById(R.id.realtimeblur);
        card_one = (CardView) findViewById(R.id.card_one);
        Poster_face = (CircleImageView) findViewById(R.id.Poster_face);
        callbackManager = CallbackManager.Factory.create();
         profile =  Profile.getCurrentProfile();
        shareDialog = new ShareDialog(PicviewActivity.this);
        login_buttonTwitter_m = (TwitterLoginButton) findViewById(R.id.login_buttonTwitter_m);
        login_button_m = (LoginButton) findViewById(R.id.login_button_m);
        postcard =(postcards) getIntent().getSerializableExtra("allover");
        if(!postcard.getTitle().equals("null")){
            Caption.setText(postcard.getTitle()+" ");
        }else{
            Caption.setText( " ");

        }
        if (postcard.getCategories().equals("null")){
            Datene.setText(" by "+ postcard.getUser_name());

        }else {
            Datene.setText(postcard.getCategories()+ " by "+ postcard.getUser_name());

        }

        Picasso.with(getApplicationContext()).load(postcard.getUser_profile_photo()).into(Poster_face);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        fullscreen_new = (TextView) findViewById(R.id.fullscren_new);
        wallpaperset.setVisibility(View.GONE);
      //  IDUSER = postcard.getUser();
        GetProfilePic();
//download listner
// walpaper listner

likesCount.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

       createRatingDialog();

    }
});
  and_imageview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          if(isUp){
              closechooser();
          }else if (card_one.getVisibility() == View.INVISIBLE) {
              Caption.setVisibility(View.VISIBLE);
              Datene.setVisibility(View.VISIBLE);
              card_one.setVisibility(View.VISIBLE);
              frcirc_frm.setVisibility(View.VISIBLE);
              fullscreen_new.setVisibility(View.VISIBLE);
              likesCount.setVisibility(View.VISIBLE);
              //and_imageview.resetZoom();
              //and_imageview.scalablePermit(false);
              wallpaperset.setVisibility(View.GONE);
          }
      }
  });
  Typeface typeface = Typeface.createFromAsset(getAssets(),"fa-light-300.ttf");

  fullscreen_new.setTypeface(typeface);
        wallpaperset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  //  Bitmap bmap2 = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.paper));
           //     Bitmap tempbitMap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
                and_imageview.buildDrawingCache();
                Bitmap bmap = and_imageview.getDrawingCache();
                    String pathofBmp = MediaStore.Images.Media.insertImage(getContentResolver(), bmap,"title", null);
                    Uri bmpUri = Uri.parse(pathofBmp);
                   // Log.e("onClick: ",CreateFile(bitmapCheck).getName()+" " );
                    Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                    //intent.setAction(Intent.ACTION_SET_WALLPAPER);
                    intent.setDataAndType(bmpUri, "image/*");
                    intent.putExtra("mimeType", "image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(
                            intent, "Set as:"));



            }
        });

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.e( "onError: ", result.toString()+" ");
            }

            @Override
            public void onCancel() {
                Log.e( "onError: ", "cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e( "onError: ", error.getMessage()+" ");
            }
        });

        imv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Download_file();
                closechooser();
            }
        });




//Picasso.with(getApplicationContext()).load(postcard.getImage_original()).into(and_imageview);





        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
              Bitmap  bitmap1 = cropBitmap1(bitmap);
                blurview.setImageBitmap(Blur.fastblur(getApplicationContext(),bitmap1, 20));
           Picasso.with(getApplicationContext()).load(postcard.getImage_original()).placeholder(new BitmapDrawable(getResources(), bitmap)).into(and_imageview, new com.squareup.picasso.Callback() {
               @Override
               public void onSuccess() {

               }

               @Override
               public void onError() {
                 Toast.makeText(getApplicationContext(), "error occured",Toast.LENGTH_SHORT).show();
               }
           });
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                blurview.setImageResource(R.drawable.vivid_white);
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.with(getApplicationContext()).load(postcard.getImage_thumbnail()).into(target);


        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUp== false){

                    openchooser();

                }else {

                    closechooser();
            }


            //====

        // callback twitter
                login_buttonTwitter_m.setCallback(new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                        TwitterAuthToken authToken = session.getAuthToken();
                        Share_on_twitter(session);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Do something on failure
                        Log.e("check ", "failure: "+exception.getMessage() );
                    }
                });
                //faacebook



              //login_button_m.setReadPermissions(Arrays.asList("user_likes", "user_status", "publish_actions"));;
                //login_button_m.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
              //  login_button_m.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);  login_button_m.setPublishPermissions("publish_actions");
                login_button_m.setReadPermissions(Arrays.asList("email"));
                login_button_m.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        String userid = loginResult.getAccessToken().getUserId();
                        Log.e("user id", userid);
                        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                profile =  Profile.getCurrentProfile();
                                Share_on_facebook();
                            }
                        });

                        Bundle bundle= new Bundle();
                        bundle.putString("fields","first_name,last_name,email,id,picture");
                        graphRequest.setParameters(bundle);
                        graphRequest.executeAsync();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });




imv_two.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        closechooser();
        final CharSequence[] items = {"Facebook", "Instagram", "Twitter"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PicviewActivity.this);
        builder.setTitle("Share in");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection

                Action_preceed(item);

                dialog.dismiss();
            }
        });
        builder.show();            }

});


}
        });

imv_4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        closechooser();
   StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.snaplava.com/live/public/api/v1/follow/user", new Response.Listener<String>() {
       @Override
       public void onResponse(String response) {
           Log.e( "onResponse: ", response);
           try {
               JSONObject jsonObject = new JSONObject(response);

                   Toast.makeText(getApplicationContext(), " "+ jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

           } catch (JSONException e) {
               e.printStackTrace();
           }

       }
   }, new Response.ErrorListener() {
       @Override
       public void onErrorResponse(VolleyError error) {

       }
   }){
       @Override
       public Map<String, String> getHeaders() throws AuthFailureError {
         Map<String, String> hasMap = new HashMap<>();
         hasMap.put("Authorization", "Bearer "+Bearer_token);
           return hasMap;
       }

       @Override
       protected Map<String, String> getParams() throws AuthFailureError {
          Map<String, String> params = new HashMap<>();
          params.put("following_user_id", postcard.getUser_id());
           return params;
       }
   };
   requestQueue.add(stringRequest);
    }
});

imv_5.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        closechooser();
    }
});
    }

    private void createRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PicviewActivity.this);
        builder.setTitle("Enter text");
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
      // -----
    TextView textView = new TextView(getApplicationContext());
    textView.setTextSize(18);
    textView.setText("Content & Composition");
    final ScaleRatingBar ratingBar = new ScaleRatingBar(this);
        ratingBar.setNumStars(5);
        ratingBar.setMinimumStars(1);
        ratingBar.setRating(3);
        ratingBar.setGravity(Gravity.CENTER);
        ratingBar.setStarPadding(10);
        ratingBar.setStepSize(0.5f);
        ratingBar.setStarWidth(125);
        ratingBar.setStarHeight(125);

//-------
        TextView textView1 = new TextView(getApplicationContext());
        textView1.setText("Image Quality");
        textView1.setTextSize(18);
        final ScaleRatingBar ratingBar1 = new ScaleRatingBar(this);
        ratingBar1.setNumStars(5);
        ratingBar1.setMinimumStars(1);
        ratingBar1.setRating(3);
        ratingBar1.setGravity(Gravity.CENTER);
        ratingBar1.setStarPadding(10);
        ratingBar1.setStepSize(0.5f);
        ratingBar1.setStarWidth(125);
        ratingBar1.setStarHeight(125);

        TextView textView2 = new TextView(getApplicationContext());
        textView2.setText("Professionalism");
        textView2.setTextSize(18);
        final ScaleRatingBar ratingBar2 = new ScaleRatingBar(this);
        ratingBar2.setNumStars(5);
        ratingBar2.setMinimumStars(1);
        ratingBar2.setRating(3);
        ratingBar2.setGravity(Gravity.CENTER);
        ratingBar2.setStarPadding(10);
        ratingBar2.setStepSize(0.5f);
        ratingBar2.setStarWidth(125);
        ratingBar2.setStarHeight(125);


        TextView textView3 = new TextView(getApplicationContext());
        textView3.setText("Excellent");
        textView3.setTextSize(18);
        final ScaleRatingBar ratingBar3 = new ScaleRatingBar(this);
        ratingBar3.setNumStars(5);
        ratingBar3.setMinimumStars(1);
        ratingBar3.setRating(3);
        ratingBar3.setGravity(Gravity.CENTER);
        ratingBar3.setStarPadding(10);
        ratingBar3.setStepSize(0.5f);
        ratingBar3.setStarWidth(125);
        ratingBar3.setStarHeight(125);


        TextView textView4 = new TextView(getApplicationContext());
        textView4.setText("World Class");
        textView4.setTextSize(18);
        final ScaleRatingBar ratingBar4 = new ScaleRatingBar(this);
        ratingBar4.setNumStars(5);
        ratingBar4.setMinimumStars(1);
        ratingBar4.setRating(3);
        ratingBar4.setGravity(Gravity.CENTER);
        ratingBar4.setStarPadding(10);
        ratingBar4.setStepSize(0.5f);
        ratingBar4.setStarWidth(125);
        ratingBar4.setStarHeight(125);


        Button button = new Button(getApplicationContext());
        button.setText("Add text");
        button.setGravity(Gravity.CENTER);
       linearLayout.addView(textView);
        linearLayout.addView(ratingBar);
        linearLayout.addView(textView1);
        linearLayout.addView(ratingBar1);
        linearLayout.addView(textView2);
        linearLayout.addView(ratingBar2);
        linearLayout.addView(textView3);
        linearLayout.addView(ratingBar3);
        linearLayout.addView(textView4);
        linearLayout.addView(ratingBar4);
        linearLayout.addView(button);
        builder.setView(linearLayout);
        // builder.setView(modeList);
        final Dialog dialog = builder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            PushRating(ratingBar.getRating(), ratingBar1.getRating(), ratingBar2.getRating(), ratingBar3.getRating(), ratingBar4.getRating(),dialog);
            }
        });
        dialog.show();

    }

    private void PushRating(final float rating, final float rating1, final float rating2, final float rating3, final float rating4, final Dialog dialog) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.snaplava.com/live/public/api/v1/photo/save-ratings", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(  "onResponse: ",response );

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("2")){
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                   dialog.dismiss();
                    }else {
                        Toast.makeText(getApplicationContext(), "Error occured.", Toast.LENGTH_LONG).show();
                    };

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), "some error occured", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
             Map <String, String> header = new HashMap<>();
             header.put("Authorization", "Bearer "+Bearer_token);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map <String, String> params = new HashMap<>();
               params.put("photo_id",postcard.getId());
                params.put("photo_owner_id",postcard.getUser_id());
                params.put("content_composition_rating", String.valueOf(rating));
                params.put("image_quality_rating", String.valueOf(rating1));
                params.put("professionalism_rating", String.valueOf(rating2));
                params.put("excellent_rating", String.valueOf(rating3));
                params.put("world_class_rating", String.valueOf(rating4));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void openchooser() {
        show_all.startAnimation(fabrotateplus);
        anim_where.setVisibility(View.VISIBLE);
        blurview.setVisibility(View.VISIBLE);
        imv_1.setVisibility(View.VISIBLE);

        imv_two.setVisibility(View.VISIBLE);
        imv_4.setVisibility(View.VISIBLE);
        imv_1.startAnimation(fabopen);
        imv_two.startAnimation(fabopen);

        imv_4.startAnimation(fabopen);
        imv_5.startAnimation(fabopen);
        isUp = true;
    }

    private void closechooser() {

        show_all.startAnimation(fabrotateneg);

        imv_1.startAnimation(fabclose);
        imv_two.startAnimation(fabclose);

        imv_4.startAnimation(fabclose);
        imv_5.startAnimation(fabclose);
        fabclose.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                anim_where.setVisibility(View.GONE);
                blurview.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        isUp = false;
   }




    private void Share_on_twitter(TwitterSession session) {
        and_imageview.buildDrawingCache();
        Bitmap bmap = and_imageview.getDrawingCache();
        try {
            File file = CreateFile(bmap);

            Log.e( "Share_on_twitter: ", String.valueOf(Uri.fromFile(file)));
            if(session!= null){

                final Intent intent = new ComposerActivity.Builder(PicviewActivity.this)
                        .session(session)
                        .image(Uri.fromFile(file))
                        .text("")
                        .hashtags("")
                        .createIntent();
                startActivity(intent);
            }else {
                login_buttonTwitter_m.performClick();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void Share_on_twitter()   {

        and_imageview.buildDrawingCache();
        Bitmap bmap = and_imageview.getDrawingCache();
        try {
            File file = CreateFile(bmap);

            Log.e( "Share_on_twitter: ", String.valueOf(Uri.fromFile(file)));
            TwitterSession session = TwitterCore.getInstance().getSessionManager()
                    .getActiveSession();
            if(session!= null){

                final Intent intent = new ComposerActivity.Builder(PicviewActivity.this)
                        .session(session)
                        .image(Uri.fromFile(file))
                        .text("")
                        .hashtags("")
                        .createIntent();
                startActivity(intent);
            }else {
                login_buttonTwitter_m.performClick();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void GetProfilePic() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.alegralabs.com/gunjan/Assignment1/getPhoto.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res = response.toString();
                if(!TextUtils.isEmpty(res)){
                    Picasso.with(getApplicationContext()).load(res).placeholder(R.drawable.user_icon).into(Poster_face);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hasmap = new HashMap<>();
                hasmap.put("id", IDUSER);
                return hasmap;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void Action_preceed(int item) {
        if(item==0){
            Share_on_facebook();
        }else if (item == 1){
            Share_on_Instagram();
        }else if (item ==2){
            Share_on_twitter();
        }
    }

    private void Share_on_Instagram() {

        Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.instagram.android");
        if (intent != null) {
            and_imageview.buildDrawingCache();
            Bitmap bmap = and_imageview.getDrawingCache();
            File file = null;
            try {
                file = CreateFile(bmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage("com.instagram.android");
            try {

                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), file.getPath(), " ", " ")));
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            shareIntent.setType("image/jpeg");
            startActivity(shareIntent);
        } else {
            // The application does not exist
            // Open GooglePlay or use the default system picker
        }

    }
public void TransitView(View view){
  Caption.startAnimation(swipeleft);
  Datene.startAnimation(swipeleft);
  card_one.startAnimation(swiperight);
  frcirc_frm.startAnimation(swipetop);
fullscreen_new.startAnimation(swipetop);
   likesCount.startAnimation(swapidown);
 //  and_imageview.scalablePermit(true);
    wallpaperset.setVisibility(View.VISIBLE);
//and_imageview.setStarte(true);

swiperight.setAnimationListener(new Animation.AnimationListener() {
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
     card_one.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
});
  swipeleft.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
       Caption.setVisibility(View.INVISIBLE);
       Datene.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
  });
  swipetop.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        frcirc_frm.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
  });
  swapidown.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        fullscreen_new.setVisibility(View.INVISIBLE);
        likesCount.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
  });

}//yYTiVizGlZ9lWD601x6KAbZyOVM
    private File CreateFile(Bitmap bmap) throws IOException {
        File f = new File(getApplicationContext().getCacheDir(), "myFile.jpeg");
        f.createNewFile();

//Convert bitmap to byte array
        Bitmap bitmap = bmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }
    private void Share_on_facebook() {
        if(profile!= null) {

           // Bitmap bmap = and_imageview.getDrawingCache();
            and_imageview.buildDrawingCache();
            Bitmap bmap = and_imageview.getDrawingCache();
            // File file = CreateFile(bmap);
            if(bmap != null){

                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bmap)
                        .build();



            if (shareDialog.canShow(SharePhotoContent.class)) {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content,ShareDialog.Mode.AUTOMATIC);

            }
        }else {

            }
      }  else {
            login_button_m.performClick();
        }

    }

    private Bitmap cropBitmap1(Bitmap bitmap) {

        Bitmap bmOverlay = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawRect(250, 30, 10, 10, paint);
        return bmOverlay;
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
        login_buttonTwitter_m.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

    }
    private void Download_file() {

        Toast.makeText(getApplicationContext(),"file saving",Toast.LENGTH_SHORT).show();

        and_imageview.buildDrawingCache(); Bitmap bmp = and_imageview.getDrawingCache();
        File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //context.getExternalFilesDir(null);

        File file = new File(storageLoc, System.currentTimeMillis() + ".jpg");

        try{
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            Toast.makeText(getApplicationContext(),"file saved.",Toast.LENGTH_SHORT).show();
            scanFile(getApplicationContext(), Uri.fromFile(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e( "Download_file: ",e.getMessage() );
        } catch (IOException e) {
            e.printStackTrace();
            Log.e( "Download_file: ",e.getMessage() );
        }


    }
    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    1
            );

        }else {

        }
    }

    private void scanFile(Context context, Uri uri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(uri);
        context.sendBroadcast(scanIntent);

    }
    @Override
    public void onBackPressed() {

        if(card_one.getVisibility() == View.INVISIBLE){
            Caption.setVisibility(View.VISIBLE);
            Datene.setVisibility(View.VISIBLE);
            card_one.setVisibility(View.VISIBLE);
            frcirc_frm.setVisibility(View.VISIBLE);
            fullscreen_new.setVisibility(View.VISIBLE);
            likesCount.setVisibility(View.VISIBLE);
            //and_imageview.resetZoom();
            //and_imageview.scalablePermit(false);
            wallpaperset.setVisibility(View.GONE);

        }else {
            super.onBackPressed();

        }

    }

}
