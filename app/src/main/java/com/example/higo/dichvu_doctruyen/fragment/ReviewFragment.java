package com.example.higo.dichvu_doctruyen.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.higo.dichvu_doctruyen.R;
import com.example.higo.dichvu_doctruyen.models.Chapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class ReviewFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private TextView tvReview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_review_fragment,null);
        tvReview = (TextView) view.findViewById(R.id.tvReview);
        String description = getArguments().getString("description");
        tvReview.setText(description);
        return view;
    }

    public void setTextReview(String textReview){
        tvReview.setText(textReview);

    }
}
