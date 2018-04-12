package com.snaplava.SnapLava.frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snaplava.SnapLava.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SnapPopFragment extends Fragment {


    public SnapPopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_snap_pop, container, false);
        return view;

    }

}
