package com.snaplava.SnapLava;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snaplava.SnapLava.Adapters.Pager;

import java.util.ArrayList;

public class LetsCollageActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout tabLayout;
    CoordinatorLayout co_ord;
TextView restrictions, next_;
ArrayList <String> listArray = new ArrayList<>();

    //This is our viewPager
    private ViewPager viewPager;
    ArrayList<String> ListImage = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_collage);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
         getSupportActionBar().hide();
        //Initializing the tablayout

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("My photos"));
        tabLayout.addTab(tabLayout.newTab().setText("Gallery"));
        tabLayout.addTab(tabLayout.newTab().setText("popular"));
      //  tabLayout.setTabGravity(TabLayout.TEXT_ALIGNMENT_GRAVITY);
        //tabLayout.setTab
        co_ord = (CoordinatorLayout) findViewById(R.id.co_ord);
        restrictions = (TextView) findViewById(R.id.restrictions);
        next_ = (TextView) findViewById(R.id.next_);
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(),
                3,restrictions, ListImage);
next_.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     //   Log.e("onClick: ", ListImage.get(0).getUri().getPath());
       for(int i =0;i<ListImage.size();i++){
           listArray.add(ListImage.get(i));
       }
       if(listArray.size()==0){
           Snackbar snackbar = Snackbar.make(co_ord, "You must select at least 2 pics.", Snackbar.LENGTH_LONG);
snackbar.show();
       }else if (listArray.size() ==1){
           Snackbar snackbar = Snackbar.make(co_ord, "one pic cannot creeate collage", Snackbar.LENGTH_LONG);
snackbar.show();
       }else {
           Intent intent =new Intent(LetsCollageActivity.this, CollageActivity.class);
           intent.putExtra("arraylist",listArray);
           startActivity(intent);
           finish();
       }

    }
});
        //Adding adapter to pager
        viewPager.setAdapter(adapter);
tabLayout.setupWithViewPager(viewPager);
        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);


    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
