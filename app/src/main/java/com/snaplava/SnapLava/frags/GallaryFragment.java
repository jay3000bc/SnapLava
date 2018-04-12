package com.snaplava.SnapLava.frags;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.snaplava.SnapLava.Adapters.GallaryAdapter;
import com.snaplava.SnapLava.R;
import com.snaplava.SnapLava.fields.gallary;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GallaryFragment extends Fragment {

RecyclerView recycler_gal;

    private GridView gridview = null;
    private Cursor cc = null;
    private Button btnMoreInfo = null;
    TextView textview;
    ArrayList<String> ListImage;
    private ProgressDialog myProgressDialog = null;
ArrayList<gallary> gallarylist = new ArrayList<>();
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @SuppressLint("ValidFragment")
    public  GallaryFragment (TextView textview, ArrayList<String> ListImage){
       this.textview = textview;
       this.ListImage = ListImage;

    }
    public GallaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallary, container, false);
        recycler_gal = (RecyclerView) view.findViewById(R.id.recycler_gal);
        recycler_gal.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

       verifyStoragePermissions(getActivity());
        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cc = getContext().getApplicationContext().getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    null);
            if(cc!= null) {

new taskSync().execute(cc);
               /*
               *  new Thread() {
                    public void run() {
                        try {
                            cc.moveToFirst();

                            mUrls = new Uri[10];
                            strUrls = new String[10];
                            mNames = new String[10];
                            for (int i = 0; i < 10; i++) {
                                cc.moveToPosition(i);
                                gallarylist.add(new gallary(Uri.parse(cc.getString(1)), cc.getString(1), cc.getString(3)));
                            }

                        } catch (Exception e) {
                        }
                        //           myProgressDialog.dismiss();
                    }
                }.start();

               * */


            }else {
Toast.makeText(getContext(), "no cc", Toast.LENGTH_SHORT).show();
            }


        return view;
    }
    public boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    1
            );

        }else {

        }
        return false;
    }
    public class taskSync extends AsyncTask <Cursor , Void , ArrayList<gallary>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<gallary> doInBackground(Cursor... cursors) {
           ArrayList<gallary> gallarylist1 = new ArrayList<>();
            Cursor cursor = cursors[0];
            cc.moveToFirst();
            for (int i = 0; i < cc.getCount(); i++) {
                cc.moveToPosition(i);
                Log.e( "doInBackground: ",cc.getString(1)+" " );
                gallarylist1.add(new gallary(Uri.parse(cc.getString(1)), cc.getString(1), cc.getString(3)));
            }
            return gallarylist1;
        }

        @Override
        protected void onPostExecute(ArrayList<gallary> gallaries) {
           GallaryAdapter adapter = new GallaryAdapter(getContext(), gallaries,textview,ListImage);
           recycler_gal.setAdapter(adapter);

            super.onPostExecute(gallaries);
        }
    }

}
