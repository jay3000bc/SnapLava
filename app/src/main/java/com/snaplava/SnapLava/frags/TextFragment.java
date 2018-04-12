package com.snaplava.SnapLava.frags;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snaplava.SnapLava.Adapters.ColorAdapter;
import com.snaplava.SnapLava.Adapters.TextAdapter;
import com.snaplava.SnapLava.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment {
RecyclerView textstyle_recycler,textcolor_recycler;
ArrayList<String> fontArray = new ArrayList<>();
int containerone;
  String textStyle="Coiny-Regular.ttf";
  int color = Color.BLACK;
    @SuppressLint("ValidFragment")
    public TextFragment(int containerone) {
        // Required empty public constructor
    this.containerone = containerone;

    }

public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text, container, false);
textstyle_recycler = (RecyclerView) view.findViewById(R.id.textstyle_recycler);
textcolor_recycler =(RecyclerView) view.findViewById(R.id.textcolor_recycler);
textstyle_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
textcolor_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
createListFont();
// adapter added
        ColorAdapter adapter_two = new ColorAdapter(getContext(), getActivity().getSupportFragmentManager(),containerone);
        textcolor_recycler.setAdapter(adapter_two);
        TextAdapter adapter_one = new TextAdapter(getContext(), fontArray,getActivity().getSupportFragmentManager(), containerone);
        textstyle_recycler.setAdapter(adapter_one);
        adapter_two.getAdapterOne(adapter_one);
adapter_one.getAdapterOne(adapter_two);

    return view;
    }
public void createListFont(){
        fontArray.add("Coiny-Regular.ttf");
        fontArray.add("FiraSans-BoldItalic.ttf");
        fontArray.add("FiraSans-ExtraLightItalic.ttf");
        fontArray.add("FiraSans-Italic.ttf");
        fontArray.add("FiraSans-SemiBoldItalic.ttf");
        fontArray.add("KronaOne-Regular.ttf");
        fontArray.add("OpenSans-LightItalic.ttf");
        fontArray.add("OpenSans-SemiBoldItalic.ttf");

}

}
