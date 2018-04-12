package com.snaplava.SnapLava.frags.layout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.snaplava.SnapLava.PictureActivity;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.fields.DetailPaginator;
import com.snaplava.SnapLava.fields.Paginator;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaterFragment extends Fragment {
int position ;
PullToLoadView listView;
    ArrayList<String> dataone = new ArrayList<>();
RequestQueue requestQueue;
int mode=0;
DetailPaginator paginator;
    public WaterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_water, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        listView = (PullToLoadView) view.findViewById(R.id.listView);
       mode= ((PictureActivity)getActivity()).getSearch_mode();
       paginator= new DetailPaginator(getContext(),listView,requestQueue);
       paginator.initializePaginator(0);

       return  view;
    }

    public DetailPaginator getPaginator() {
        return paginator;
    }

    public void setPaginator(DetailPaginator paginator) {
        this.paginator = paginator;
    }
}
