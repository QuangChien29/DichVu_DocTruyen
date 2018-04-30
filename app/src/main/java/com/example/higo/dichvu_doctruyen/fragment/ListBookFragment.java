package com.example.higo.dichvu_doctruyen.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.higo.dichvu_doctruyen.R;
import com.example.higo.dichvu_doctruyen.adapter.AdapterListBook;
import com.example.higo.dichvu_doctruyen.models.Book;

import java.util.ArrayList;

public class ListBookFragment extends Fragment {
    RecyclerView recyclerListBook;
    AdapterListBook adapterListBook;
    ArrayList<Book> listBook;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = new RecyclerView(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rv;
   }

    private void addControls() {


    }
}
