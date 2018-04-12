package com.snaplava.SnapLava.frags.layout;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snaplava.SnapLava.Adapters.LayoutAdapter;
import com.snaplava.SnapLava.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Layout2imageFragment extends Fragment {
RecyclerView collage_layout_select;
int size;
Integer container1;
ArrayList<String> uriList;
    ArrayList<Integer> intarray = new ArrayList<>();
public Layout2imageFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public Layout2imageFragment(int size, Integer container,ArrayList<String> uriList) {
        // Required empty public constructor
    this.size =size;
    this.container1 = container;
    this.uriList = uriList;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_layout2image, container, false);
        collage_layout_select = (RecyclerView) view.findViewById(R.id.collage_layout_select);
        collage_layout_select.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        two_imageReady();
        LayoutAdapter adapter = new LayoutAdapter(getContext(), intarray, container1, getActivity().getSupportFragmentManager(),uriList, getActivity());
        collage_layout_select.setAdapter(adapter);

        return view;
    }
    public void two_imageReady (){
        intarray.add(R.drawable.g21);
        intarray.add(R.drawable.g22);
        intarray.add(R.drawable.g28);
        intarray.add(R.drawable.g29);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
