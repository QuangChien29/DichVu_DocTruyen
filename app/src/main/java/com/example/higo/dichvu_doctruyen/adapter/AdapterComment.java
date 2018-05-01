package com.example.higo.dichvu_doctruyen.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.higo.dichvu_doctruyen.R;
import com.example.higo.dichvu_doctruyen.models.Book;
import com.example.higo.dichvu_doctruyen.models.Comment;

import java.util.ArrayList;
import java.util.List;

public class AdapterComment extends ArrayAdapter<Comment> {
    ArrayList<Comment> objects = null;
    Activity context = null;
    int resource;
    TextView tvComment,tvLike,tvDislike;

    public AdapterComment(@NonNull Activity context, int resource, @NonNull ArrayList<Comment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resource,null);
        tvComment = convertView.findViewById(R.id.tvComment);
        tvLike = convertView.findViewById(R.id.tvLike);
        tvDislike = convertView.findViewById(R.id.tvDislike);
        Comment comment = objects.get(position);
        tvComment.setText(comment.getContent());
        tvLike.setText(comment.getLike());
        tvDislike.setText(comment.getDislike());
        return convertView;
    }
}
