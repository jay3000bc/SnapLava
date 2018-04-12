package com.snaplava.SnapLava;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
EditText name_reg ;
EditText email_reg;
EditText pass_reg;
Button button;
RelativeLayout e_layout;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        e_layout = (RelativeLayout) findViewById(R.id.e_layout);
        sharedPreferences = getSharedPreferences("access_token", Context.MODE_PRIVATE);
       name_reg = (EditText) findViewById(R.id.name_reg);
       email_reg = (EditText) findViewById(R.id.email_reg);
       pass_reg = (EditText) findViewById(R.id.pass_reg);
        e_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            }
        });
       button = (Button) findViewById(R.id.create_acc);
         requestQueue = Volley.newRequestQueue(getApplicationContext());
       progressDialog = new ProgressDialog(RegisterActivity.this);
         button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
if(TextUtils.isEmpty(name_reg.getText().toString())
        || TextUtils.isEmpty(email_reg.getText().toString())
        || TextUtils.isEmpty(pass_reg.getText().toString())

        ){
    Toast.makeText(getApplicationContext(), "please fil all the forms", Toast.LENGTH_SHORT).show();
        }else {
    createAccount();

}}
       });

    }

    private void createAccount() {
progressDialog.setMessage("Creating account..");
progressDialog.setCanceledOnTouchOutside(false);
progressDialog.show();
StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.snaplava.com/live/public/api/v1/users/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

              try {
                  JSONObject jsonobject = new JSONObject(response);
                  JSONObject jsonobnext = jsonobject.getJSONObject("content");
                  String message = jsonobject.getString("status");
              if(message.equals("2")){
                  progressDialog.hide();
              SharedPreferences.Editor editor = sharedPreferences.edit();
              editor.putString("token",jsonobnext.getString("access_token"));
              editor.commit();
               startActivity(new Intent(getApplicationContext(), PictureActivity.class));
              }else {
                  progressDialog.hide();
                  CreateDialog(jsonobnext.getString("email"), jsonobject.getString("message"));
              }
              }catch (JSONException e){
                  Log.e( "onResponse: ",e.getMessage() );
              }
                  Log.e( "onResponse: ",response );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e( "onErrorResponse: ",error +  " " );
progressDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name",name_reg.getText().toString() );
                params.put("email", email_reg.getText().toString());
                params.put("password", pass_reg.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }

    private void CreateDialog(String email, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(message);
        alertDialogBuilder.setMessage(email);
                alertDialogBuilder.setPositiveButton("okay",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
