package com.snaplava.SnapLava;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.snaplava.SnapLava.fields.postcards;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewPictureActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView and_imageview;
    postcards postcards;

    private ImageView FullScreen , Chat,Share_dialog,Download, Follow_post, like_post;
    CircleImageView  Poster_face;
    public LoginButton login_button_m;
    public TwitterLoginButton login_buttonTwitter_m;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    Profile profile;
    RequestQueue requestQueue;
    String IDUSER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_picture);
        getSupportActionBar().hide();
        and_imageview = (ImageView) findViewById(R.id.and_imageview);
        // initializing variable of menifest.
        init();
        //Downlod event
        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Download_file();
            }
        });
        postcards = (postcards) getIntent().getSerializableExtra("allover");
      //  IDUSER = postcards.getUser();
        //Picasso.with(getApplicationContext()).load("http://www.alegralabs.com/gunjan/Assignment1/thumb/"+postcards.getImage()).into(and_imageview);
//callback download dialog
        Share_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Facebook", "Instagram", "Twitter"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewPictureActivity.this);
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

//callback facebook
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



    }

    private void Share_on_twitter(TwitterSession session) {

        and_imageview.buildDrawingCache();
        Bitmap bmap = and_imageview.getDrawingCache();
        try {
            File file = CreateFile(bmap);

            Log.e( "Share_on_twitter: ", String.valueOf(Uri.fromFile(file)));
            if(session!= null){

                final Intent intent = new ComposerActivity.Builder(ViewPictureActivity.this)
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

  /*  private void Download_file() {
        Toast.makeText(getApplicationContext(),"file saving",Toast.LENGTH_SHORT).show();
        Poster_face.buildDrawingCache(); Bitmap bmp = Poster_face.getDrawingCache();
        File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //context.getExternalFilesDir(null);

      //  File file = new File(storageLoc, postcards.getTime() + ".jpg");

        try{
          //  FileOutputStream fos = new FileOutputStream(file);
            //bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            //fos.close();
            Toast.makeText(getApplicationContext(),"file saved.",Toast.LENGTH_SHORT).show();
            //scanFile(getApplicationContext(), Uri.fromFile(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
 private void scanFile(Context context, Uri uri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(uri);
        context.sendBroadcast(scanIntent);

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
// twitter share

    private void Share_on_twitter()   {

        and_imageview.buildDrawingCache();
        Bitmap bmap = and_imageview.getDrawingCache();
        try {
            File file = CreateFile(bmap);

            Log.e( "Share_on_twitter: ", String.valueOf(Uri.fromFile(file)));
            TwitterSession session = TwitterCore.getInstance().getSessionManager()
                    .getActiveSession();
            if(session!= null){

                final Intent intent = new ComposerActivity.Builder(ViewPictureActivity.this)
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

//insta share


    private void Share_on_Instagram(){
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

    private File CreateFile(Bitmap bmap) throws IOException {
        File f = new File(getApplicationContext().getCacheDir(), "myFile");
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

    public void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        FullScreen = (ImageView) findViewById(R.id.ic_fullscreen);
        Chat = (ImageView) findViewById(R.id.ic_Message);
        Share_dialog= (ImageView) findViewById(R.id.ic_share);
        Download = (ImageView) findViewById(R.id.ic_download);
        Follow_post = (ImageView) findViewById(R.id.ic_plusone);
        like_post = (ImageView) findViewById(R.id.ic_add_like);
        Poster_face = (CircleImageView) findViewById(R.id.Poster_face);
        callbackManager = CallbackManager.Factory.create();
        profile =  Profile.getCurrentProfile();
        shareDialog = new ShareDialog(this);
        login_buttonTwitter_m = (TwitterLoginButton) findViewById(R.id.login_buttonTwitter_m);
        login_button_m = (LoginButton) findViewById(R.id.login_button_m);
        GetProfilePic();

    }

//face of the uploader

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
    private void Share_on_facebook() {
        if(profile!= null) {
            and_imageview.buildDrawingCache();
            Bitmap bmap = and_imageview.getDrawingCache();
            // File file = CreateFile(bmap);
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(bmap)
                    .build();


            if (shareDialog.canShow(SharePhotoContent.class)) {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }else {

            }
        }else {
            Log.e("Share_on_facebook: ","profile null" );
            login_button_m.performClick();
        }
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        login_buttonTwitter_m.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {

    }

}
