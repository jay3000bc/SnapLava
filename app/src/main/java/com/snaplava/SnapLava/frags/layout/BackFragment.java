package com.snaplava.SnapLava.frags.layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snaplava.SnapLava.Adapters.BorderAdapter;
import com.snaplava.SnapLava.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackFragment extends Fragment {

    RecyclerView colorRecycler_border;
    public BackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_back, container, false);
        colorRecycler_border = (RecyclerView ) view.findViewById(R.id.colorRecycler_border);
        colorRecycler_border.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        BorderAdapter adapter = new BorderAdapter(getContext(), getActivity().getSupportFragmentManager(), getActivity());

        colorRecycler_border.setAdapter(adapter);


   return  view;
    }

}
