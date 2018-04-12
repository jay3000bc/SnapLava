package com.snaplava.SnapLava;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

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
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.snaplava.SnapLava.ErrorCheck.NetworkCheckActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;

import static com.snaplava.SnapLava.R.string.com_twitter_sdk_android_CONSUMER_SECRET;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView newUser;
    CallbackManager callbackManager;
    LoginButton LoginWithFaceBook;
    Button fb;

    Button twitter_login;
    String login ="wait";
    RelativeLayout activity_main;
    SharedPreferences sharedpreferences;
ProgressDialog progressDialog;
    TwitterLoginButton twitter_loginButton;
    Button signin;
    Profile profile;
    String Facebook_email = "noemail";
    String  emai_l = "noemail";

    EditText username, password;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
       Twitter.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        signin = (Button) findViewById(R.id.signin);
        //https://www.snaplava.com/live/public/api/v1/login
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        newUser = (TextView) findViewById(R.id.newUser);
        fb =(Button) findViewById(R.id.fb_login);
        twitter_login = (Button) findViewById(R.id.twitter_login);
        twitter_loginButton = (TwitterLoginButton) findViewById(R.id.login_buttonTwitter);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        newUser.setPaintFlags(newUser.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        callbackManager = CallbackManager.Factory.create();
        LoginWithFaceBook = (LoginButton) findViewById(R.id.login_button);
        LoginWithFaceBook.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);

        activity_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            }
        });
    LoginWithFaceBook.setReadPermissions(Arrays.asList("email"));
//        LoginWithFaceBook.setPublishPermissions("publish_actions");
    //LoginWithFaceBook.setReadPermissions("public_profile");
    progressDialog = new ProgressDialog(MainActivity.this);
    LoginWithFaceBook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

//                final String userid = loginResult.getAccessToken().toString();
               // Log.e("user id", userid);
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                      //  DisplayUserInfo(object);
                        Intent intent = new Intent(MainActivity.this,PictureActivity.class);
                       // startActivity(intent);
                        Log.e( "onCompleted: ",object.toString() );
                        Set<String> deniedPermissions = loginResult.getRecentlyDeniedPermissions();
                        if (deniedPermissions.contains("email")) {

                            //LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("user_friends"));

                        }else {
                            try {
                                Facebook_email = object.getString("email");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Profile profile = Profile.getCurrentProfile();

                  Login_facebook(profile);
                    //    Toast.makeText(getApplicationContext(), adcessT + " ", Toast.LENGTH_LONG).show();
                       // finish();
                    }
                });

                Bundle bundle= new Bundle();
                bundle.putString("fields","first_name,last_name,email,name,id,picture");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e( "onError: ",error.getMessage() );
            }
        });
newUser.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

    }
});

signin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText())){
            Toast.makeText(getApplicationContext(), "Enter both the fields", Toast.LENGTH_SHORT).show();
        }else {
            Login_user();
        }
    }
});
twitter_loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                login ="twitter";
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                Login_twitter(session);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.e("check ", "failure: "+exception.getMessage() );
            }
        });
    }

    private void Login_facebook(final Profile object) {
        progressDialog.setMessage("Logging in...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Log.e(  "Login_facebook: ",object.getId() + Facebook_email+object.getName() +object.getProfilePictureUri(200,200));
   StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.snaplava.com/live/public/api/v1/login/facebook/android", new Response.Listener<String>() {
       @Override
       public void onResponse(String response) {
           Log.e( "onResponse: ",response );
           try {
               JSONObject jsonobject = new JSONObject(response);
               String status = jsonobject.getString("status");

               if (status.equals("2")) {
                   JSONObject jsonobnext = jsonobject.getJSONObject("content");
                   String message = jsonobnext.getString("access_token");
                   sharedpreferences = getSharedPreferences("access_token",
                           Context.MODE_PRIVATE);
                   //  Log.e( "onResponse: ",message );
                   SharedPreferences.Editor editor = sharedpreferences.edit();
                   editor.putString("token", message);
                   editor.commit();
                   progressDialog.hide();
                   progressDialog.dismiss();
                   startActivity(new Intent(getApplicationContext(), PictureActivity.class));
finish();
               }else if (status.equals("3")){
                   progressDialog.hide();
                   String message = jsonobject.getString("message");
                   new AlertDialog.Builder(MainActivity.this)
                           .setTitle("Login failed!")
                           .setMessage(message)
                           .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {

                               }
                           }).show();
               }else if (status.equals("5")){
                   progressDialog.hide();
                   String message = jsonobject.getString("message");
                   new AlertDialog.Builder(MainActivity.this)
                           .setTitle("Login failed!")
                           .setMessage(message)
                           .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {

                               }
                           }).show();

               }
           }catch (JSONException e){

           }
       }
   }, new Response.ErrorListener() {
       @Override
       public void onErrorResponse(VolleyError error) {
           progressDialog.hide();
           Log.e(  "onErrorResponse: ",error.getMessage()+" " );

       }
   }){
       @Override
       protected Map<String, String> getParams() throws AuthFailureError {
           Map<String, String> params = new HashMap<>();
           params.put("id",object.getId());
           params.put("name", object.getName());
           params.put("first_name", object.getFirstName());
           params.put("last_name",object.getLastName());
           params.put("email",Facebook_email);
           params.put("photo",String.valueOf(object.getProfilePictureUri(200,200)));
           Log.e( "getParams: ",params.toString() );
           return params;
       }
   };
   requestQueue.add(stringRequest);

    }

    private void Login_user() {
 progressDialog.setMessage("Logging in...");
  progressDialog.setCanceledOnTouchOutside(false);
  progressDialog.show();
   StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.snaplava.com/live/public/api/v1/login", new Response.Listener<String>() {
       @Override
       public void onResponse(String response) {
           try {
               Log.e(  "onResponse:loin manual ",response );
               JSONObject jsonobject = new JSONObject(response);
               String status = jsonobject.getString("status");

               if (status.equals("2")) {
                   JSONObject jsonobnext = jsonobject.getJSONObject("content");
                   String message = jsonobnext.getString("access_token");
                   sharedpreferences = getSharedPreferences("access_token",
                           Context.MODE_PRIVATE);
                 //  Log.e( "onResponse: ",message );
                   SharedPreferences.Editor editor = sharedpreferences.edit();
                   editor.putString("token", message);
                   editor.commit();
                   progressDialog.hide();
                   startActivity(new Intent(getApplicationContext(), PictureActivity.class));
finish();
               }else if (status.equals("3")){
                   String message = jsonobject.getString("message");
                   JSONObject object = jsonobject.getJSONObject("content");
                   String title = object.getString("error");
                   progressDialog.hide();
                   new AlertDialog.Builder(MainActivity.this)
                           .setTitle(title)

                           .setMessage(message)
                           .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {

                               }
                           }).show();
               }
           }catch (JSONException e){

           }

       }
   }, new Response.ErrorListener() {
       @Override
       public void onErrorResponse(VolleyError error) {
progressDialog.hide();
Toast.makeText(getApplicationContext(), "issue in login", Toast.LENGTH_SHORT).show();
       }
   }){
       @Override
       protected Map<String, String> getParams() throws AuthFailureError {
          Map <String , String> hasmap = new HashMap<>();
          hasmap.put("email", String.valueOf(username.getText()));
          hasmap.put("password", password.getText().toString());
           return hasmap;
       }
   };
   requestQueue.add(stringRequest);
    }

    private void Login_twitter(final TwitterSession session) {
        Call<User> user = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false,false);
        user.enqueue(new Callback<User>() {
            @Override
            public void success(final Result<User> userResult) {
                final String name = userResult.data.name;
                String email = userResult.data.email;


                final String photoUrlNormalSize   = userResult.data.profileImageUrl;
                String photoUrlBiggerSize   = userResult.data.profileImageUrl.replace("_normal", "_bigger");
                String photoUrlMiniSize     = userResult.data.profileImageUrl.replace("_normal", "_mini");
                String photoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");

               new TwitterAuthClient().requestEmail(session, new com.twitter.sdk.android.core.Callback<String>() {
                    @Override
                    public void success(Result<String> emailResult) {
                         emai_l = emailResult.data;

                        Log.e(  "success: ",emai_l+" " );
                        Login_DataSav(emai_l,name,userResult.data.idStr,photoUrlNormalSize);
                        // ...
                    }

                    @Override
                    public void failure(TwitterException e) {
                       // callback.onTwitterSignInFailed(e);
                        Login_DataSav(emai_l,name,userResult.data.idStr,photoUrlNormalSize);
                    }
                });
                Log.e( "success: ",name+email+photoUrlNormalSize );
              //

            }

            @Override
            public void failure(TwitterException exc) {
                Log.d("TwitterKit", "Verify Credentials Failure", exc);

            }
        });


    }

    private void Login_DataSav(final String email, final String name, final String idStr, final String photoUrlNormalSize) {

        Log.e( "Login_DataSav: ", name+idStr);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.snaplava.com/live/public/api/v1/login/twitter/android", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e( "onResponse: ",response );
                try {
                    JSONObject jsonobject = new JSONObject(response);
                    String status = jsonobject.getString("status");

                    if (status.equals("2")) {
                        JSONObject jsonobnext = jsonobject.getJSONObject("content");
                        String message = jsonobnext.getString("access_token");
                        sharedpreferences = getSharedPreferences("access_token",
                                Context.MODE_PRIVATE);
                        //  Log.e( "onResponse: ",message );
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("token", message);
                        editor.commit();
                        progressDialog.hide();
                        startActivity(new Intent(getApplicationContext(), PictureActivity.class));
                      finish();
                    }else if (status.equals("3")){
                        String message = jsonobject.getString("message");
                        progressDialog.hide();
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Login failed!")
                                .setMessage(message)
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }else if (status.equals("5")){
                        progressDialog.hide();
                        String message = jsonobject.getString("message");
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Login failed!")
                                .setMessage(message)
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();

                    }
                }catch (JSONException e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse: ",error.toString() );
               Toast.makeText(getApplicationContext(), "login failed please try again",Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",idStr);
                params.put("name", name);
                params.put("photo",photoUrlNormalSize);
                params.put("email", email+" ");
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        profile = Profile.getCurrentProfile();
        if(profile!= null){
            Log.e("onResume: ",profile.getFirstName() );
        }super.onResume();
    }



    @Override
    public void onClick(View view) {
        if (view == fb ) {
            LoginWithFaceBook.performClick();

        }
        else if (view ==twitter_login ){
            twitter_loginButton.performClick();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            callbackManager.onActivityResult(requestCode, resultCode, data);

            twitter_loginButton.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

}
