package com.snaplava.SnapLava.Adapters;

/**
 * Created by alegralabs on 08/03/18.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TextView;

import com.snaplava.SnapLava.frags.GallaryFragment;
import com.snaplava.SnapLava.frags.MyPhotoFragment;
import com.snaplava.SnapLava.frags.SnapPopFragment;

import java.util.ArrayList;

/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    TextView textVuew;
    ArrayList<String> ListImage;
    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount, TextView textVuew, ArrayList<String> ListImage) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.textVuew = textVuew;
        this.ListImage = ListImage;

    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                MyPhotoFragment tab1 = new MyPhotoFragment();
                return tab1;
            case 1:
                GallaryFragment tab2 = new GallaryFragment(textVuew,ListImage);
                return tab2;
            case 2:
                SnapPopFragment tab3 = new SnapPopFragment();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
              return "My photos";
            case 1:
             return  "Gallery";
            case 2:
              return "Popular";
            default:
                return null;
        }

       // return super.getPageTitle(position);
    }
}