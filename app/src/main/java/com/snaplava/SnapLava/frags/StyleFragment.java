package com.snaplava.SnapLava.frags;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.snaplava.SnapLava.Adapters.BorderAdapter;
import com.snaplava.SnapLava.CollageActivity;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.frags.layout.CustomDFragment;
import com.snaplava.SnapLava.frags.layout.VertCutFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class StyleFragment extends Fragment {
SeekBar seekbar_bordthick, seekbar_radius;

int container;
    @SuppressLint("ValidFragment")
    public StyleFragment(int container) {
        // Required empty public constructor
    this.container = container;
    }

    public StyleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_style, container, false);

        seekbar_bordthick = (SeekBar) view.findViewById(R.id.seekbar_bordthick);

         seekbar_radius = (SeekBar) view.findViewById(R.id.seekbar_radius);



       // getActivity().getSupportFragmentManager().beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentByTag("writefrag")).commit();
        WriteFragment writeFragment =((WriteFragment)getActivity().getSupportFragmentManager().findFragmentByTag("writefrag"));
       if (writeFragment != null){
           writeFragment.onDestroyView();
       }
        // getActivity().getSupportFragmentManager().beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentByTag("layoutvertcut")).commit();
        seekbar_radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int position_layout = ((CollageActivity)getActivity()).getLayout_position();
            if (position_layout == 3){
              ((CustomDFragment)getActivity().getSupportFragmentManager().findFragmentByTag("customlay")).changeRadius(progress);
            }else {
                ((VertCutFragment) getActivity().getSupportFragmentManager().findFragmentByTag("layoutvertcut")).changeRadius(progress);
            }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar_bordthick.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int position_layout = ((CollageActivity)getActivity()).getLayout_position();
        if (position_layout == 3){
            ((CustomDFragment)getActivity().getSupportFragmentManager().findFragmentByTag("customlay")).changeBoarderThickness(progress);
        }else {
            ((VertCutFragment)getActivity().getSupportFragmentManager().findFragmentByTag("layoutvertcut")).ChangeBorderThickness(progress);
        }
        Log.e("onProgressChanged: ",progress+" " );
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});

        return view;
    }

}
