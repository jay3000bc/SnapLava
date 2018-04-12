package com.snaplava.SnapLava.service;

/**
 * Created by Dell on 2/5/2018.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

  public class MyResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            // success

           Toast.makeText(context, "success!!",Toast.LENGTH_SHORT).show();
              final Long tweetId =  intent.getExtras().getLong(TweetUploadService.EXTRA_TWEET_ID);
        } else if (TweetUploadService.UPLOAD_FAILURE.equals(intent.getAction())) {
            // failure
            Toast.makeText(context, "failure!!",Toast.LENGTH_SHORT).show();
            final Intent retryIntent =  intent.getExtras().getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
        } else if (TweetUploadService.TWEET_COMPOSE_CANCEL.equals(intent.getAction())) {
            // cancel
            Toast.makeText(context, "cancel!!",Toast.LENGTH_SHORT).show();
        }
    }
}

