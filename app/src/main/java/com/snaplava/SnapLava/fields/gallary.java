package com.snaplava.SnapLava.fields;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by alegralabs on 08/03/18.
 */

public class gallary implements Serializable{
    Uri uri;
    String strUri;
    String nameuri;

    public gallary(Uri uri, String strUri, String nameuri) {
        this.uri = uri;
        this.strUri = strUri;
        this.nameuri = nameuri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getStrUri() {
        return strUri;
    }

    public void setStrUri(String strUri) {
        this.strUri = strUri;
    }

    public String getName() {
        return nameuri;
    }

    public void setName(String name) {
        this.nameuri = name;
    }
}
