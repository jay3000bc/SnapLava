package com.snaplava.SnapLava.frags.layout;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.customs.RectangularView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Hor2CutFragment extends Fragment {

    RectangularView view_0ne,view_two;
    ArrayList<String> uriList;
    ImageView linearayout;
    LinearLayout image_afterSet;
    int starter,i=1,j=0;
    Boolean swipe_obne = false;
    Boolean swipe_two = false;

  @SuppressLint("ValidFragment")
  public Hor2CutFragment(ArrayList<String> uriList) {
        this.uriList = uriList;
        // Required empty public constructor
    }
    public Hor2CutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hor2_cut, container, false);
        view_0ne = (RectangularView) view.findViewById(R.id.View_one);
        view_two = (RectangularView) view.findViewById(R.id.View_two);

        image_afterSet = (LinearLayout) view.findViewById(R.id.image_aftersetting);

        view_0ne.setImageURI(Uri.parse(uriList.get(0)));
        linearayout = (ImageView) view.findViewById(R.id.drag_image);
        //l
        linearayout.setOnDragListener(new  MyDragListener());
        view_0ne.setOnDragListener(new  MyDragListener());
        view_two.setOnDragListener(new  MyDragListener());
        view_two.setImageURI(Uri.parse(uriList.get(1)));
        view_0ne.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                linearayout.setImageURI(Uri.parse(uriList.get(0)));
                Toast.makeText(getContext(), "long pressed", Toast.LENGTH_SHORT).show();
                starter = 0;
                view_0ne.scalablePermit(false);
                view_two.scalablePermit(false);
                //container_one.
                v.getDrawingCache(true);

                linearayout.setVisibility(View.VISIBLE);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view_0ne);
                linearayout.startDrag(null, shadowBuilder, view_0ne, 0);
                linearayout.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        view_two.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                linearayout.setImageURI(Uri.parse(uriList.get(1)));
                Toast.makeText(getContext(), "long pressed", Toast.LENGTH_SHORT).show();
                starter = 2;
                view_0ne.scalablePermit(false);
                view_two.scalablePermit(false);
                v.getDrawingCache(true);
                linearayout.setVisibility(View.VISIBLE);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view_two);
                linearayout.startDrag(null, shadowBuilder, view_two, 0);
                linearayout.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        return view;
    }
    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //TODO
                    // v.setBackgroundDrawable(Shape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundDrawable(Shape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    // owner.removeView(view);
                    RectangularView container = (RectangularView) v;
                    // Swipe(container);

                    if(starter==0){

                        //if(swipe_obne==false){
                        container.setImageURI(Uri.parse(uriList.get(j)));
                        view_0ne.setImageURI(Uri.parse(uriList.get(i)));
                        swipe_obne=true;
                        //}else {
                        // container.setImageURI(Uri.parse(uriList.get(1)));
                        //v0iew_0ne.setImageURI(Uri.parse(uriList.get(0)));
                        swipe_obne = false;
                        swipe_two = true;
                        //}
                        swipe_obne= true;

                    }else if(starter==2){
//if(swipe_two==false){
                        swipe_obne=true;
                        container.setImageURI(Uri.parse(uriList.get(i)));
                        view_two.setImageURI(Uri.parse(uriList.get(j)));


                        swipe_two=true;
//}else {

                        //container.setImageURI(Uri.parse(uriList.get(1)));
                        //view_two.setImageURI(Uri.parse(uriList.get(0)));
                        swipe_two=false;
//}

                    }
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //  v.setBackgroundDrawable(Shape);
                    // Log.e( "onDrag: ", v.getId()+" ");
                    int temp;
                    // String temp2 = uriList.get(0);
                    // uriList.remove(1);
                    //uriList.remove(0);
                    //uriList.add(temp);
                    ///uriList.add(temp2);
                    //Toast.makeText(getContext(),"swiped",Toast.LENGTH_SHORT).show();
                    temp = i;
                    i= j;
                    j=temp;
                    view_0ne.scalablePermit(true);
                    view_two.scalablePermit(true);
                default:
                    break;
            }
            return true;
        }
    }
}
