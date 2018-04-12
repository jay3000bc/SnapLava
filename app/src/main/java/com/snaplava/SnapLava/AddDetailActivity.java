package com.snaplava.SnapLava;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.snaplava.SnapLava.Adapters.TitleAdapter;
import com.snaplava.SnapLava.fields.postcards;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddDetailActivity extends AppCompatActivity {
ArrayList<postcards> postArray = new ArrayList<>();
RequestQueue requestQueue;
RecyclerView recyclerView;
SharedPreferences sharedPreferences = null;
Button button;
TitleAdapter adapter ;

String[] title;
    String Bearer_token = null;
ProgressDialog progressDialog ;
     String[] category;
    String[] description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        button = (Button) findViewById(R.id.button_submit) ;
        progressDialog = new ProgressDialog(AddDetailActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Uploading, please wait ...");
        // adapter = new TitleAdapter(getApplicationContext(), null, AddDetailActivity.this);
        sharedPreferences = getSharedPreferences("access_token", Context.MODE_PRIVATE);

          Bearer_token = sharedPreferences.getString("token", null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView.setAdapter(adapter);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.snaplava.com/live/public/api/v1/users/less-detailed-images", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e( "onResponse: ", response.toString());

                try {
                    Decode_method(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e( "onErrorResponse: ",error.getMessage() );
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer "+Bearer_token);
                return params;
            }
        };
        requestQueue.add(stringRequest);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //Toast.makeText(getApplicationContext(), title.length+""+category[1]+description[2],Toast.LENGTH_SHORT).show();
    UpdateDetail();
    }
});
    }

    private void UpdateDetail() {
      //  progressDialog.show();
        progressDialog.show();
 StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.snaplava.com/live/public/api/v1/users/store-image-details",   new Response.Listener<String>() {
     @Override
     public void onResponse(String response) {
         Log.e( "onResponse: ",response.toString() );
         try {
             JSONObject Json = new JSONObject(response);
          String jsonstats = Json.getString("status");
           if (jsonstats.equals("2")){
               String Messagwe = Json.getString("message");
               Toast.makeText(getApplicationContext(), Messagwe,Toast.LENGTH_SHORT).show();
           finish();
           }else {
               Toast.makeText(getApplicationContext(),"update failed", Toast.LENGTH_SHORT).show();
           }
         } catch (JSONException e) {
             e.printStackTrace();
         }

     }
 }, new Response.ErrorListener() {
     @Override
     public void onErrorResponse(VolleyError error) {
         Log.e(  "onErrorResponse: ",error.getMessage()+" " );

     progressDialog.hide();}
 }){

     @Override
     public Map<String, String> getHeaders()  {
         Map<String, String> heasder = new HashMap<>();
         heasder.put("Authorization", "Bearer "+Bearer_token);
         return  heasder;
     }

     @Override
     protected Map<String, String> getParams()  {
         Map<String, String> params = new HashMap<>(postArray.size());
         JSONObject jsonObject = new JSONObject();
         for (int i =0 ;i< postArray.size();i++) {

    params.put("imageTitle" + postArray.get(i).getId(), title[i]);
    params.put("imageDescription" + postArray.get(i).getId(), description[i]);
    params.put("imageCategory" + postArray.get(i).getId(), category[i]);
    params.put("imageId" + (i+1), postArray.get(i).getId());

          /*   try {
                 jsonObject.put("imageTitle" + postArray.get(i).getId(), title[i]);
                 jsonObject.put("imageDescription" + postArray.get(i).getId(), description[i]);
                 jsonObject.put("imageCategory" + postArray.get(i).getId(), category[i]);
               //  jsonObject.put("imageId" + (i+1), postArray.get(i).getId());

             } catch (JSONException e) {
                 e.printStackTrace();
             }
             params.put("params", jsonObject.toString());
        */  }
        params.put("totalNumberOfSet", String.valueOf(postArray.size()));
         return params;
     }
 };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);

    }

    private void Decode_method(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonObjectne = jsonObject.getJSONObject("content");
        JSONArray result = jsonObjectne.getJSONArray("data");
        Log.e( "decodeMethod: ",result.length() +" ");
        for (int i = 0; i < result.length(); i++) {
            JSONObject collegeData = result.getJSONObject(i);
            postcards cards = new postcards();
            cards.setId(collegeData.getString("id"));
            cards.setUser_id(collegeData.getString("user_id"));
            cards.setImage_medium(collegeData.getString("image_medium"));
            cards.setImage_original(collegeData.getString("image_original"));

            cards.setImage_original(collegeData.getString("image_thumbnail"));
            cards.setTitle(collegeData.getString("title"));
            cards.setDescription(collegeData.getString("description"));
            cards.setCategories(collegeData.getString("categories"));
            cards.setCreated_at(collegeData.getString("created_at"));
            cards.setUpdated_at(collegeData.getString("updated_at"));
           // cards.setUser_name(collegeData.getString("user_name"));
//            cards.setUser_profile_photo(collegeData.getString("user_profile_photo"));
  //          cards.setTotal_followers(collegeData.getString("total_followers"));
            postArray.add(cards);



        }
progressDialog.hide();
        title = new String[postArray.size()];
        category =  new String[postArray.size()];
        description =  new String[postArray.size()];
        adapter = new TitleAdapter(getApplicationContext(), postArray,AddDetailActivity.this, title, category, description);
        recyclerView.setAdapter(adapter);
    }

}
