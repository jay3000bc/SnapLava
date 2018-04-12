package com.snaplava.SnapLava;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.Profile;
import com.snaplava.SnapLava.Adapters.UploadAdapter;
import com.snaplava.SnapLava.fields.Upcount;
import com.snaplava.SnapLava.fields.nameing;
import com.snaplava.SnapLava.utils.MultiPartUtility;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;

public class GallaryActivity extends AppCompatActivity {
    int  LIMIT = 10;
    String FILE_UPLOAD_URL = "https://www.snaplava.com/live/public/api/v1/users/image-upload";
    public ProgressBar donut_progress;
    RecyclerView recyclerView;
    UploadAdapter adapter;
    RequestQueue requestQueue;
    ArrayList<Upcount> uplodContList= new ArrayList<>();
    Profile profile;
    int check =0;
    int size =0;
    int checnagan=0;
    ImageView uploading_image;
    TextView file_current,file_size;
    String USERID;
    String Vearer_token = null;
    TwitterSession sesssion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);
        donut_progress =(ProgressBar) findViewById(R.id.donut_progress) ;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_compl);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UploadAdapter(getApplicationContext(),uplodContList);
        recyclerView.setAdapter(adapter);
        uploading_image = (ImageView) findViewById(R.id.uploading_image);
        file_current = (TextView) findViewById(R.id.file_current);
        file_size = (TextView) findViewById(R.id.file_size);
SharedPreferences sharedPreferences = getSharedPreferences("access_token", Context.MODE_PRIVATE);
       Vearer_token = sharedPreferences.getString("token", null);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Intent intent = new Intent(this, AlbumSelectActivity.class);
        Log.e( "onCreate: ",Vearer_token );
        /* profile = Profile.getCurrentProfile();
        if (profile== null){
            sesssion = TwitterCore.getInstance().getSessionManager().getActiveSession();
            USERID = String.valueOf(sesssion.getUserId());
        }else {
            USERID = profile.getId();
        }
     */   intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, LIMIT); // set limit for image selection
       //startActivity(new Intent(getApplicationContext(), AddDetailActivity.class));
         startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);
             size = images.size();
            for (int i = 0; i < size; i++) {
              nameing  nam = new nameing();

                    Uri uri = Uri.fromFile(new File(images.get(i).path));
                nam.setUri(uri);
                Log.e("onActivityResult: ",uri.getLastPathSegment() );
                nam.setString(uri.getLastPathSegment());

//new Asyncfile().execute(nam);
                   new UploadFileToServer().execute(nam);

            }
        }else {
            finish();
        }
    }
    private String getfileName(Uri imageUri) {
        File f = new File(imageUri.getPath());
        return f.getName();
    }
    private String getfileSize(Uri imageUri) {
        File f = new File(imageUri.getPath());
        long size = f.length();
        return size/1024+" KB";
    }
    public Bitmap gtbitmap (Uri uri){
        uploading_image.setImageBitmap(null);
        Bitmap bitmap= null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public class Asyncfile extends  AsyncTask<nameing, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(nameing... nameings) {
            try {
                MultiPartUtility multipart = new MultiPartUtility(FILE_UPLOAD_URL, "UTF-8");

                Log.e( "doInBackground: ",Vearer_token );
                multipart.addHeaderField("Authorization", "Bearer "+ Vearer_token);
                //multipart.addHeaderField("User-Agent", "CodeJava");
               // multipart.addHeaderField("Test-Header", "Header-Value");

                //multipart.addFormField("Username", "TestUser");

                multipart.addFilePart(nameings[0].getString(),new File(nameings[0].getUri().getPath()));
                //    multipart.addFilePart("fileUpload[]", uploadFile2);

                String response = multipart.finish();
                Log.e( "onActivityResult: ",response );
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private void ShowUP(Uri uri) {
        AlertDialog.Builder builder;
        final AlertDialog alertDialog;

        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.upload_complete,
                null);

        // TextView text = (TextView) layout.findViewById(R.id.text);
        ImageView uploaded_image = (ImageView) layout.findViewById(R.id.uploaded_image);
        Button flat_btn1 = (Button) layout.findViewById(R.id.flat_btn1);
        Button flat_btn2 = (Button) layout.findViewById(R.id.flat_btn2);
        flat_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        flat_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        uploaded_image.setImageBitmap(gtbitmap(uri));
        builder = new AlertDialog.Builder(GallaryActivity.this);
        builder.setView(layout);

        alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getAttributes().windowAnimations =
                R.style.dialog_animation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

    }

    private class UploadFileToServer extends AsyncTask<nameing, String, String> {
        File sourceFile;
        int totalSize = 0;
        Uri uri= null;
        int counter =0;
        String FileName = null;
        String response;
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            donut_progress.setProgress(0);
            FileName= String.valueOf(System.currentTimeMillis());
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            Log.d("PROG", progress[0]);
            if (counter ==0){

                uploading_image.setImageBitmap(gtbitmap(uri));
                file_current.setText(getfileName(uri));
                file_size.setText(getfileSize(uri));
                counter ++;
            }           donut_progress.setProgress(Integer.parseInt(progress[0])); //Updating progress
        }

        @Override
        protected String doInBackground(nameing... args) {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = null;
            uri = args[0].getUri();
            FileName= args[0].getString();
            sourceFile = new File(uri.getPath());
            totalSize = (int)sourceFile.length();
            String fileName = FileName;

            try {
                connection = (HttpURLConnection) new URL(FILE_UPLOAD_URL).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Authorization", "Bearer "+Vearer_token);
                String boundary = "---------------------------boundary";
                String tail = "\r\n--" + boundary + "--\r\n";
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                connection.setDoOutput(true);

                String metadataPart = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
                        + "" + "\r\n";

                String fileHeader1 = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"image\"; filename=\""
                        + fileName + "\"\r\n"
                        + "Content-Type: application/octet-stream\r\n"
                        + "Content-Transfer-Encoding: binary\r\n";

                long fileLength = sourceFile.length() + tail.length();
                String fileHeader2 = "Content-length: " + fileLength + "\r\n";
                String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
                String stringData = metadataPart + fileHeader;

                long requestLength = stringData.length() + fileLength;
                connection.setRequestProperty("Content-length", "" + requestLength);
                connection.setFixedLengthStreamingMode((int) requestLength);
                connection.connect();

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(stringData);
                out.flush();

                int progress = 0;
                int bytesRead = 0;
                byte buf[] = new byte[1024];
                BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(sourceFile));
                while ((bytesRead = bufInput.read(buf)) != -1) {
                    // write output
                    out.write(buf, 0, bytesRead);
                    out.flush();
                    progress += bytesRead; // Here progress is total uploaded bytes
                    publishProgress(""+(int)((progress*100)/totalSize)); // sending progress percent to publishProgress
                }
                // Write closing boundary and close stream
                out.writeBytes(tail);
                out.flush();
                out.close();

                // Get server response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder builder = new StringBuilder();
                while((line = reader.readLine()) != null) {
                    builder.append(line);
                }
response = new JSONObject(builder.toString()).toString();
            } catch (Exception e) {
                // Exception
            } finally {
                if (connection != null) connection.disconnect();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Response", "Response from server: " + result);
           // Upload(FileName, uri);
            check ++;
            Log.e("allin","check"+ check+"size "+size);
            if(check==size && checnagan==0){
                //0ShowUP(uri);
                checnagan= 1;
               // Toast.makeText(getApplicationContext(), "finished", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AddDetailActivity.class));
finish();
            }
            adapter.AddElement(new Upcount(" ", (float) 0.0,uri));
            super.onPostExecute(result);
        }

    }

}

