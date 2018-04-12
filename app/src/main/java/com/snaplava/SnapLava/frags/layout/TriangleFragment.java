package com.snaplava.SnapLava.frags.layout;


import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.customs.RevTriView;
import com.snaplava.SnapLava.customs.TriangleView;
import com.snaplava.SnapLava.fields.DetailPaginator;
import com.snaplava.SnapLava.fields.userPaginator;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TriangleFragment extends Fragment {
PullToLoadView pull_load;
userPaginator paginator;
RequestQueue requestQueue;
    public TriangleFragment() {
        // Required empty public constructor
    }


    public userPaginator getPaginator() {
        return paginator;
    }

    public void setPaginator(userPaginator paginator) {
        this.paginator = paginator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_triangle, container, false);
         pull_load = (PullToLoadView) view.findViewById(R.id.pull_load);
       requestQueue = Volley.newRequestQueue(getContext());
        paginator= new userPaginator(getContext(),pull_load,requestQueue);
        paginator.initializePaginator(1);

    return  view;

    }

}
