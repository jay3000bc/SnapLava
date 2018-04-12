package com.snaplava.SnapLava.fields;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
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
import com.snaplava.SnapLava.Adapters.DetailPaginattAdapter;
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

public class DetailPaginator {
    Context c;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;

    private DetailPaginattAdapter adapter_detail;
    private PaginatAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    SharedPreferences preferences;
    String token ;
    private int nextPage=1;
    RequestQueue requestQueue;
    String Search ="Categories";
    String search_key = null;
    String search_categ = null;
    String FIRST_URL = "https://www.snaplava.com/live/public/api/v1/search";
    String URL = "https://www.snaplava.com/live/public/api/v1/search";
    String Check;

    public DetailPaginator (Context c, PullToLoadView pullToLoadView, RequestQueue requestQueue ) {
        this.c = c;
        preferences = c.getSharedPreferences("access_token", Context.MODE_PRIVATE);
        token = preferences.getString("token", null);
        Log.e(  "DetailPaginator: ",token );
        this.pullToLoadView = pullToLoadView;
        this.requestQueue = requestQueue;

         rv =pullToLoadView.getRecyclerView();
        this.Check = Check;
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        }




    public void initializePaginator(final int mode)
    {
        adapter_detail=new DetailPaginattAdapter(c,new ArrayList<postcards>(), mode);
        rv.setAdapter(adapter_detail);

        if (mode ==0){
            Search = "users";
     }else  if (mode ==1){
            Search = "users";

        }

        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {
            //LOAD MORE DATA
            @Override
            public void onLoadMore() {
                if (URL != null) {
                    loadData (nextPage);

                }else {
                    isLoading = false;
                    hasLoadedAll = true;
                }

                }
            //REFRESH AND TAKE US TO FIRST PAGE
            @Override
            public void onRefresh() {

                    adapter_detail.clear();
                    hasLoadedAll = false;
                    nextPage = 1;
                    URL = FIRST_URL;
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

    }

    private void loadData(final int nextPage) {
        isLoading=true;
        StringRequest stringreq = new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                decodeMethod(response);
                Log.e("response",response);
                pullToLoadView.setComplete();
                isLoading=false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pullToLoadView.setComplete();
                isLoading=false;
            }
        }){
            @Override
            public Map<String, String> getHeaders()   {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+token);
                return  header;
            }

            @Override
            protected Map<String, String> getParams()   {
                Map <String,String> hasmap = new HashMap<>();
                hasmap.put("search_type", "categories");
                hasmap.put("search_query",search_key);
                hasmap.put("search_categories", search_categ);
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



                adapter_detail.add(cards);
                    nextPage++;

            }
            JSONObject pagination = jsonObjectne.getJSONObject("pagination");
            URL = pagination.getString("next_page");
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    public void initLoad(String search_key, String search_categ){
        this.search_categ = search_categ;
        this.search_key = search_key;
        pullToLoadView.initLoad();

    }
}
