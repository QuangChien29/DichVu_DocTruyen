package com.example.higo.dichvu_doctruyen.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.higo.dichvu_doctruyen.MainActivity.ipAddress;

public class DocTruyenActivity extends AppCompatActivity {
    TextView tvContent,tvTenChuong,tvIdChuong;
    String content,chapterNO;
    String idBook ="";
    ImageButton btnNext,btnBack;
    ArrayList<String> dsFont;
    ArrayAdapter<String> fontAdapter;
    ListView lvFont;
    private int textSize;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readbook);
        addControls();
        getDatafromChapterFragment();
        id =  Integer.parseInt(chapterNO);
        loadChapter(id);
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_font,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuFont:{
                ShowAlertDialogListFont();

                break;
            }
            case R.id.menuNhoChu:{
                if(textSize>0) {
                    textSize-=2;
                    tvContent.setTextSize(textSize);
                    editor.putInt("textSize",textSize);
                }
                break;
            }
            case R.id.menuToChu:{
                if (textSize<40){
                textSize+=2;
                tvContent.setTextSize(textSize);
                editor.putInt("textSize",textSize);
                }
                break;
            }
        }
        editor.commit();


        return super.onOptionsItemSelected(item);
    }

    public void ShowAlertDialogListFont()
    {
        dsFont = new ArrayList<>();
        String[] arrFontName={""};
        final AssetManager assetManager = getAssets();
        try {
            arrFontName=assetManager.list("fonts");
            dsFont.addAll(Arrays.asList(arrFontName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Fonts");
        final String[] finalArrFontName = arrFontName;
        dialogBuilder.setItems(arrFontName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String fontChu = finalArrFontName[item];
                Typeface typeface = Typeface.createFromAsset(assetManager,"fonts/"+fontChu);
                tvContent.setTypeface(typeface);
                editor.putString("fontChu",fontChu);
                editor.commit();
                Toast.makeText(DocTruyenActivity.this,fontChu,Toast.LENGTH_LONG).show();

            }
        });
        AlertDialog alertDialogObject = dialogBuilder.create();
        alertDialogObject.show();
    }



    private void loadChapter(int id) {
        new readJSON().execute("http://"+ipAddress+"/backend/book/"+idBook+"/chapterNo/"+id);
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
                if(id>1){
                id--;
                loadChapter(id);
                }
            }
        });
    }



    private void getDatafromChapterFragment() {
        Intent intent = getIntent();
        chapterNO=intent.getStringExtra("chapterNO");
        idBook=intent.getStringExtra("idBook");
    }

    private void addControls() {
        tvContent = findViewById(R.id.tvContent);
        btnNext = findViewById(R.id.btnNextChapter);
        btnBack = findViewById(R.id.btnBackChapter);
        tvTenChuong = findViewById(R.id.tvTenChuong);
        sharedPreferences = getSharedPreferences("user_setting" ,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        textSize = sharedPreferences.getInt("textSize",18);
        tvContent.setTextSize(textSize);
        final AssetManager assetManager = getAssets();
        String fontChu = sharedPreferences.getString("fontChu","");
        Typeface typeface = Typeface.createFromAsset(assetManager,"fonts/"+fontChu);
        tvContent.setTypeface(typeface);
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
