package com.snaplava.SnapLava.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
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
import com.snaplava.SnapLava.PicviewActivity;
import com.snaplava.SnapLava.R;

import org.json.JSONObject;

import java.util.Arrays;

public class FacebookActivity extends AppCompatActivity implements View.OnClickListener{

Profile profile;
Bitmap bmap;
String url;
CallbackManager  callbackManager;
    ShareDialog shareDialog;
    public LoginButton login_button_m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_facebook);
        profile =  Profile.getCurrentProfile();
        url = getIntent().getStringExtra("bitmap");
        bmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(FacebookActivity.this);
        login_button_m = (LoginButton) findViewById(R.id.login_button_m);
        Share_on_facebook();
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

        login_button_m.setPublishPermissions(Arrays.asList("publish_actions"));
        login_button_m.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
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

    private void Share_on_facebook() {
        if(profile!= null) {

            // File file = CreateFile(bmap);
            if(bmap != null){

                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bmap)
                        .build();


                if (shareDialog.canShow(SharePhotoContent.class)) {
                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    shareDialog.show(FacebookActivity.this,content);

                }
            }else {
                login_button_m.performClick();
            }}else {
            Toast.makeText(getApplicationContext(), "bitmap null", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

    }
}
