package com.snaplava.SnapLava;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class ShareIActivity extends AppCompatActivity implements View.OnClickListener{
String uri;
    ShareDialog shareDialog;
    Profile profile;
    public TwitterLoginButton login_buttonTwitter_m;
    public LoginButton login_button_m;
    File file = null;

    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_i);
        uri =  getIntent().getStringExtra("uri");
          file =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), uri);
        Log.e("onCreate: ",Uri.fromFile(file).getPath() );
          String share = getIntent().getStringExtra("share");
       // Log.e( "onCreate: ",uri );

        shareDialog = new ShareDialog(ShareIActivity.this);
        callbackManager = CallbackManager.Factory.create();
        profile =  Profile.getCurrentProfile();

        login_buttonTwitter_m = (TwitterLoginButton) findViewById(R.id.login_buttonTwitter_m);
        login_button_m = (LoginButton) findViewById(R.id.login_button_m);
       // twitter call
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
        if(share.equals("facebook")){
            Log.e("onCreate: ","call f" );
            Share_on_facebook();
        }else if (share.equals("twitter")){
            Log.e( "onCreate: ","call t" );
            Share_on_twitter();
        }else if (share.equals("instagram")){
            Share_on_Instagram();
        };
        //sharedialog call bacck

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.e( "onError: ", result.toString()+" ");
         finish();
             }

            @Override
            public void onCancel() {
                Log.e( "onError: ", "cancelled");
           finish();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e( "onError: ", error.getMessage()+" ");
            }
        });


        // facebook login
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

// twitter callback



        //faacebook



        //login_button_m.setReadPermissions(Arrays.asList("user_likes", "user_status", "publish_actions"));;
        //login_button_m.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);


    }
    private void Share_on_Instagram() {

        Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.instagram.android");
        if (intent != null) {



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
    private void Share_on_twitter(TwitterSession session) {


        Log.e( "Share_on_twitter: ", String.valueOf(Uri.fromFile(file)));

        if(session!= null){

            final Intent intent = new ComposerActivity.Builder(ShareIActivity.this)
                    .session(session)
                    .image(Uri.fromFile(file))
                    .text("")
                    .hashtags("")
                    .createIntent();
            startActivity(intent);
        }else {
            login_buttonTwitter_m.performClick();
        }

    }
    private void Share_on_twitter()   {

     //   File file = new File(Uri.parse(uri).getPath());
       TwitterSession session = TwitterCore.getInstance().getSessionManager()
                .getActiveSession();

        Log.e( "Share_on_twitter: ", String.valueOf(Uri.fromFile(file)));
          if(session!= null){

            final Intent intent = new ComposerActivity.Builder(ShareIActivity.this)
                    .session(session)
                    .image(Uri.fromFile(file))
                    .text("")
                    .hashtags("")
                    .createIntent();
            startActivity(intent);
        }else {
            login_buttonTwitter_m.performClick();
        }


    }

    private void Share_on_facebook() {
        if(profile!= null) {
Bitmap bmap = null;
            // Bitmap bmap = and_imageview.getDrawingCache();
          // File file = CreateFile(bmap);
bmap = BitmapFactory.decodeFile(file.getPath());
            if(bmap != null){

                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bmap)
                        .build();



                if (shareDialog.canShow(SharePhotoContent.class)) {
                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

                }
            }else {

            }
        }  else {
            login_button_m.performClick();
        }

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
}
