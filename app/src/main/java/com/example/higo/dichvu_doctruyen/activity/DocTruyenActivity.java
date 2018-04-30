package com.example.higo.dichvu_doctruyen.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.higo.dichvu_doctruyen.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.higo.dichvu_doctruyen.MainActivity.ipAddress;

public class DocTruyenActivity extends AppCompatActivity {
    TextView tvContent,tvTenChuong,tvIdChuong;
    String content,idchapter,idBook;
    ImageButton btnNext,btnBack;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readbook);
        addControls();
        getDatafromChapterFragment();
        id =  Integer.parseInt(idchapter);
        loadChapter(id);
        addEvents();
    }

    private void loadChapter(int id) {
        new readJSON().execute("http://"+ipAddress+"/backend/book/"+idBook+"/chapter/"+id);
        tvIdChuong.setText(id+"");
    }

    private void addEvents() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id++;
                loadChapter(id);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id--;
                loadChapter(id);
            }
        });
    }



    private void getDatafromChapterFragment() {
        Intent intent = getIntent();
        idchapter=intent.getStringExtra("idchapter");
        idBook=intent.getStringExtra("idBook");
    }

    private void addControls() {
        tvContent = findViewById(R.id.tvContent);
        btnNext = findViewById(R.id.btnNextChapter);
        btnBack = findViewById(R.id.btnBackChapter);
        tvIdChuong = findViewById(R.id.tvIdChuong);
        tvTenChuong = findViewById(R.id.tvTenChuong);
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

            //Toast.makeText(ReviewActivity.this,s,Toast.LENGTH_LONG).show();
            try {
                JSONObject object = new JSONObject(s);
                tvContent.setText(object.getString("content"));
                tvTenChuong.setText(object.getString("name"));
//
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
