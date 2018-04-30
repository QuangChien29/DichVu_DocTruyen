package com.example.higo.dichvu_doctruyen.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.higo.dichvu_doctruyen.MainActivity;
import com.example.higo.dichvu_doctruyen.R;
import com.example.higo.dichvu_doctruyen.models.Book;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class AdapterListBook extends ArrayAdapter<Book>{
    ArrayList<Book> objects = null;
    Activity context = null;
    int resource;
    TextView tvTenTruyen,tvDescription,tvTongChuong,tvSTT;
    ImageView imgTruyen ;


    public AdapterListBook(@NonNull Activity context, int resource, @NonNull ArrayList<Book> objects) {
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
        tvDescription = convertView.findViewById(R.id.tvDescription);
        tvTenTruyen = convertView.findViewById(R.id.tvTenTruyen);
        tvTongChuong = convertView.findViewById(R.id.tvTongChuong);
        imgTruyen = convertView.findViewById(R.id.imgTruyen);
        tvSTT = convertView.findViewById(R.id.tvSTT);

        Book book = objects.get(position);
        tvTenTruyen.setText(book.getName());
        tvDescription.setText(book.getDescription());
        tvTongChuong.setText(book.getSumChapter()+" Chương");
        tvSTT.setText(position+1+"");

        Picasso.with(context).load(book.getImgURL()).into(imgTruyen);
        return convertView;
    }
}
