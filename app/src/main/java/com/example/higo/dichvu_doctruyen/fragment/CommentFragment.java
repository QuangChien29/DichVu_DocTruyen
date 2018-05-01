package com.example.higo.dichvu_doctruyen.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.higo.dichvu_doctruyen.R;
import com.example.higo.dichvu_doctruyen.adapter.AdapterComment;
import com.example.higo.dichvu_doctruyen.models.Chapter;
import com.example.higo.dichvu_doctruyen.models.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.higo.dichvu_doctruyen.MainActivity.ipAddress;

public class CommentFragment extends android.support.v4.app.Fragment{

    ListView lvComment;
    AdapterComment adapterComment;
    ArrayList<Comment> listComment;
    String idBook;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comment, container, false);
        lvComment = rootView.findViewById(R.id.lvComment);
        listComment = new ArrayList<>();
        adapterComment = new AdapterComment(getActivity(),
                R.layout.item_comment,// lấy custom layout
                listComment);
        lvComment.setAdapter(adapterComment);
        idBook = getArguments().getString("idBook");
        new readJSON().execute("http://"+ipAddress+"/backend/book/"+idBook+"/comment");
        return rootView;
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
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0 ; i< array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    Comment comment = new Comment();
                    comment.setContent(object.getString("content"));
                    comment.setLike(object.getString("numOfLike"));
                    comment.setDislike(object.getString("numOfDisLike"));
                    //listChapter[listChapter.length]=chapter.getChapterName();
                    listComment.add(comment);
                }
                adapterComment.notifyDataSetChanged();

                //Toast.makeText(ReviewActivity.this,book.getName(),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
