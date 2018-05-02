package com.example.higo.dichvu_doctruyen.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.higo.dichvu_doctruyen.MainActivity;
import com.example.higo.dichvu_doctruyen.R;
import com.example.higo.dichvu_doctruyen.activity.DocTruyenActivity;
import com.example.higo.dichvu_doctruyen.activity.ReviewActivity;
import com.example.higo.dichvu_doctruyen.models.Book;
import com.example.higo.dichvu_doctruyen.models.Chapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.higo.dichvu_doctruyen.MainActivity.ipAddress;

/**
 * Created by Huy on 4/12/2018.
 */

public class ChapterFragment extends Fragment  {
   public ListView lvChapter;

    //String[] listChapter={"1","2"};
    public ArrayList<String> listChapter = new ArrayList<>() ;
    public ArrayList<String> listid = new ArrayList<>() ;
    public ArrayAdapter<String> adapter;
    public String idchapter;
    public String idBook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chapters, container, false);
        lvChapter = rootView.findViewById(R.id.lvChapter);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listChapter);
        lvChapter.setAdapter(adapter);
        idBook = getArguments().getString("idBook");
        new readJSON().execute("http://"+ipAddress+"/backend/book/"+idBook+"/chapter");
        lvChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idchapter = listid.get(position).toString();

                Intent intent = new Intent(getActivity(), DocTruyenActivity.class);
                intent.putExtra("idchapter",idchapter);
                intent.putExtra("idBook",idBook);
                startActivity(intent);


            }
        });
        return rootView;
    }




    public void setDataForListView(String idBook) {

        new readJSON().execute("http://"+ipAddress+"/backend/book/"+idBook+"/chapter");

    }
    class readJSON extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while((line = bufferedReader.readLine())!=null){
                    content.append(line);
                }

                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
            listChapter.clear();
            listid.clear();
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0 ; i< array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    Chapter chapter = new Chapter();
                    chapter.setChapterName(object.getString("name"));
                    //listChapter[listChapter.length]=chapter.getChapterName();
                    listChapter.add(chapter.getChapterName());
                    listid.add(object.getString("chapterNO"));
                }
                adapter.notifyDataSetChanged();

                //Toast.makeText(ReviewActivity.this,book.getName(),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
