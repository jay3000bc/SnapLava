package com.snaplava.SnapLava;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.Profile;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.snaplava.SnapLava.fields.Upcount;
import com.snaplava.SnapLava.fields.nameing;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;

public class CameraActivity extends AppCompatActivity {
int CAMERA_REQUEST =1;
    ImageView justImage;
   // private Button takePictureButton;
 //   private ImageView imageView;
    private Uri file;
    LinearLayout progress_area;
    DonutProgress donut_progress;
    File sourceFile;
    FloatingActionButton now_up;
    int totalSize;String Vearer_token;
    RequestQueue requestQueue;
    String FILE_UPLOAD_URL = "https://www.snaplava.com/live/public/api/v1/users/image-upload";
    String USER_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Profile profile = Profile.getCurrentProfile();
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if(profile !=null){
            USER_ID = profile.getId();
        }else{
            USER_ID = String.valueOf(session.getId());
        }
        justImage = (ImageView) findViewById(R.id.justImage);
        SharedPreferences sharedPreferences = getSharedPreferences("access_token", Context.MODE_PRIVATE);
        Vearer_token = sharedPreferences.getString("token", null);
        Log.e(  "onCreate: ", Vearer_token);
        progress_area = (LinearLayout) findViewById(R.id.progress_area);
        donut_progress = (DonutProgress) findViewById(R.id.donut_progress);
        now_up = (FloatingActionButton) findViewById(R.id.now_up);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = Uri.fromFile(getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

            startActivityForResult(intent, 100);

        }
now_up.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(file!= null){
            nameing nam = new nameing();
            nam.setUri(file);

            nam.setString(file.getLastPathSegment());
            Log.e("onClick: ",file.getLastPathSegment() );
            Log.e("onClick: ",file.toString() );
            new UploadFileToServer().execute(nam);
    }else {
        Toast.makeText(getApplicationContext(), "no file ", Toast.LENGTH_SHORT).show();
    }

    }
});
    }
public void createDialog(){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("your pic is at SnapLava");
    builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            progress_area.setVisibility(View.GONE);
            finish();
        }
    })
            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
                });

                 builder.create().show();
}
    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                System.currentTimeMillis() + ".jpg");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
     //       imageView.setImageBitmap(photo);
          if(file!= null){
              justImage.setImageURI(file);
          }
         //   Log.e(  "onActivityResult: ",new File(images.get(0).path).getPath() );
        }else{
            finish();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
     //           takePictureButton.setEnabled(true);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = Uri.fromFile(getOutputMediaFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

                startActivityForResult(intent, 100);
            }else {
                finish();
            }
        }
    }

    /*private class UploadFileToServer extends AsyncTask<nameing, String, String> {
        String response;
        String fileName;
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            donut_progress.setProgress(0);
             now_up.setVisibility(View.GONE);
            justImage.setVisibility(View.GONE);
            // Making the uploader area screen invisible
            progress_area.setVisibility(View.VISIBLE); // Showing the stylish material progressbar

//            Log.e(  "onPreExecute: ",totalSize +" "+sourceFile.getName()); super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            Log.d("PROG", progress[0]);
            donut_progress.setProgress(Integer.parseInt(progress[0])); //Updating progress
        }

        @Override
        protected String doInBackground(nameing... args) {
            HttpURLConnection.setFollowRedirects(false);
            sourceFile = new File(args[0].getUri().getPath());
            totalSize = (int)sourceFile.length();
            fileName = args[0].getString();
            HttpURLConnection connection = null;

            Log.e("doInBackground: ",fileName );
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
           // Upload(sourceFile.getName(),file);
            createDialog();
            super.onPostExecute(result);
        }

    }*/
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

            donut_progress.setProgress(0);
            now_up.setVisibility(View.GONE);
            justImage.setVisibility(View.GONE);
            // Making the uploader area screen invisible
            progress_area.setVisibility(View.VISIBLE); // Sho
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            Log.d("PROG", progress[0]);
            donut_progress.setProgress(Integer.parseInt(progress[0]));
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


                // Toast.makeText(getApplicationContext(), "finished", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AddDetailActivity.class));
                finish();


            super.onPostExecute(result);
        }

    }
}
