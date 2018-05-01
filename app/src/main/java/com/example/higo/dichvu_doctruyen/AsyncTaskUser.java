package com.example.higo.dichvu_doctruyen;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.higo.dichvu_doctruyen.models.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class AsyncTaskUser extends AsyncTask<String,String,String> {
    private Activity activity ;
    private User user ;
    private Boolean isSignIn = false;

    public AsyncTaskUser(Activity activity, User user, Boolean isSignIn) {
        this.activity = activity;
        this.user = user;
        this.isSignIn = isSignIn;
    }

    @Override
    protected String doInBackground(String... strings) {
        RequestParams params = new RequestParams();
        params.add("name",user.getUserName());
        params.add("password",user.getPassword());
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(strings[0], params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(activity,"Loi o day",Toast.LENGTH_SHORT).show();


                    }
                });
            }
    });
        return null;
    }
}
