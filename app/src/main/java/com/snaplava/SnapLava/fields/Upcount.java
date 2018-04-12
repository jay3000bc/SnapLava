package com.snaplava.SnapLava.fields;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Dell on 1/19/2018.
 */

public class Upcount implements Serializable {
    String filename;
    float fileSize;
    Uri imageUri;

    public Upcount(String filename, float fileSize, Uri imageUri) {
        this.filename = filename;
        this.fileSize = fileSize;
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {

        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public float getFileSize() {
        return fileSize;
    }

    public void setFileSize(float fileSize) {
        this.fileSize = fileSize;
    }
}
