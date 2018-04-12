package com.snaplava.SnapLava.fields;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by alegralabs on 27/02/18.
 */

public class nameing implements Serializable {
    Uri uri;
    String string;

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Uri getUri() {

        return uri;
    }

    public String getString() {
        return string;
    }
}
