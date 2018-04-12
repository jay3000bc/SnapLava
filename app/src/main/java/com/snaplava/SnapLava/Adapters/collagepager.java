package com.snaplava.SnapLava.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.snaplava.SnapLava.frags.StyleFragment;
import com.snaplava.SnapLava.frags.TextFragment;
import com.snaplava.SnapLava.frags.WriteFragment;
import com.snaplava.SnapLava.frags.layout.BackFragment;
import com.snaplava.SnapLava.frags.layout.Layout2imageFragment;
import com.snaplava.SnapLava.frags.MyPhotoFragment;
import com.snaplava.SnapLava.frags.SnapPopFragment;
import com.snaplava.SnapLava.frags.layout.WaterFragment;

import java.util.ArrayList;

/**
 * Created by alegralabs on 09/03/18.
 */

public class collagepager extends FragmentStatePagerAdapter {
    int size;
    ArrayList<String> uriList;
    Integer container;
    android.support.v4.app.FragmentManager fragmentManager;
    public collagepager(FragmentManager fm, Context context, int size, Integer container, ArrayList<String> uriList, android.support.v4.app.FragmentManager fragmentManager) {
        super(fm);
        this.size =size;
        this.container = container;
        this.uriList = uriList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Layout2imageFragment tab1 = new Layout2imageFragment(size,container, uriList);
                return tab1;
            case 1:
                StyleFragment tab2 = new StyleFragment(container);
                return tab2;
            case 2:
               // fragmentManager.beginTransaction().add(container,new WriteFragment(),"writefrag").commit();
                TextFragment tab3 = new TextFragment(container);
               // BackFragment tab3 = new BackFragment();
                return tab3;
            case 3:
                BackFragment tab4  = new BackFragment();
                return tab4;
            case 4:
                SnapPopFragment tab5 = new SnapPopFragment();

                return tab5;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
  /*  @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "layout";
            case 1:
                return  "style";
            case 2:
                return "text";
            case 3:
                return "background";
            case 4:
                return "watermark";

                default:
                return null;
        }

        // return super.getPageTitle(position);
    }*/
}
