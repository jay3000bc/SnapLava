package com.snaplava.SnapLava.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snaplava.SnapLava.R;

/**
 * Created by alegralabs on 09/04/18.
 */

public class ExampleAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;

    public ExampleAdapter(Context context, Cursor c) {
        super(context, c);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.deal_simple, parent, false);
        return v;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tex_sug = view.findViewById(R.id.text_suggestion);
        tex_sug.setText("users");
    }
}
