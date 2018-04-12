package com.snaplava.SnapLava.fields;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.snaplava.SnapLava.Adapters.PaginatAdapter;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dell on 1/18/2018.
 */

public class Paginator {
    Context c;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    private PaginatAdapter adapter;

    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage=0;
    SharedPreferences preferences;
    RequestQueue requestQueue;
    String message;
    String URL,FIRST_URL;

    String Check;
    public Paginator(Context c, PullToLoadView pullToLoadView, RequestQueue requestQueue ) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;
        this.requestQueue = requestQueue;
        preferences = c.getSharedPreferences("access_token", Context.MODE_PRIVATE);
        message = preferences.getString("token", null);
        Log.e( "Paginator: ",message+" " );

        FIRST_URL = "https://www.snaplava.com/live/public/api/v1/home/images";
        URL = "https://www.snaplava.com/live/public/api/v1/home/images";
        adapter=new PaginatAdapter(c,new ArrayList<postcards>());
    ;
        //RECYCLERVIEW
          rv =pullToLoadView.getRecyclerView();

            rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            adapter.clear();
            rv.setAdapter(adapter);
            // pullToLoadView.initLoad();
            adapter.notifyDataSetChanged();



        // initializePaginator();
    }
    public void initializePaginator()
    {
        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {
            //LOAD MORE DATA
            @Override
            public void onLoadMore() {
                if(URL!=null){
                    loadData(nextPage);
                    //nextPage++;
                }else {
                    isLoading = false;
                    hasLoadedAll = true;
                }

            }
            //REFRESH AND TAKE US TO FIRST PAGE
            @Override
            public void onRefresh() {

                 adapter.clear();
                 hasLoadedAll = false;
                 nextPage = 0;
                 URL= FIRST_URL;
                 loadData(nextPage);

            }
            //IS LOADING
            @Override
            public boolean isLoading() {
                return isLoading;
            }
            //CURRENT PAGE LOADED
            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });
        pullToLoadView.initLoad();

    }


    private void loadData(final int nextPage) {
        isLoading=true;
        Log.e("stat","" +
                "" +
                "Load more");
        StringRequest stringreq = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                decodeMethod(response);
                Log.e("response",response.toString());
                pullToLoadView.setComplete();
                isLoading=false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pullToLoadView.setComplete();
                isLoading=false;
               // Toast.makeText(c,"some error"+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map <String,String> hasmap = new HashMap<>();
                hasmap.put("Authorization","Bearer"+" "+message );
                return hasmap;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hasmap = new HashMap<>();
                hasmap.put("pagination_limit", "5");
                return hasmap;
            }


        };
        requestQueue.add(stringreq);
    }

    private void decodeMethod(String response) {
        try {
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

                cards.setImage_thumbnail(collegeData.getString("image_thumbnail"));
                cards.setTitle(collegeData.getString("title"));
                cards.setDescription(collegeData.getString("description"));
                cards.setCategories(collegeData.getString("categories"));
                cards.setCreated_at(collegeData.getString("created_at"));
                cards.setUpdated_at(collegeData.getString("updated_at"));
                cards.setUser_name(collegeData.getString("user_name"));
                cards.setUser_profile_photo(collegeData.getString("user_profile_photo"));
                cards.setTotal_followers(collegeData.getString("total_followers"));


                  adapter.add(cards);
                  nextPage++;


            }
            JSONObject pagination = jsonObjectne.getJSONObject("pagination");
            URL = pagination.getString("next_page");

        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
}
