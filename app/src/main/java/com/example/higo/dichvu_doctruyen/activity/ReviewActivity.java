package com.example.higo.dichvu_doctruyen.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.higo.dichvu_doctruyen.R;
import com.example.higo.dichvu_doctruyen.adapter.TabAdapter;
import com.example.higo.dichvu_doctruyen.fragment.ChapterFragment;
import com.example.higo.dichvu_doctruyen.fragment.ReviewFragment;
import com.example.higo.dichvu_doctruyen.models.Book;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static com.example.higo.dichvu_doctruyen.MainActivity.ipAddress;

public class ReviewActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tvTenTruyen,tvTacGia;
    private ImageView imgReview;
    private String idBook;
    private Book book;
    Button btnDocTruyen;
    ReviewFragment reviewFragment;
    ChapterFragment chapterFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        addControls();
        addEvents();


    }

    private void addEvents() {
    btnDocTruyen.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ReviewActivity.this, DocTruyenActivity.class);
            intent.putExtra("idchapter","1");
            intent.putExtra("idBook",idBook);
            startActivity(intent);
        }
    });

    }

    private void addControls() {

        book = new Book();
        reviewFragment = new ReviewFragment();
        chapterFragment = new ChapterFragment();

        tvTenTruyen =  findViewById(R.id.tvTenTruyen);
        tvTacGia =  findViewById(R.id.tvTacGia);
        imgReview =  findViewById(R.id.imgReview);
        btnDocTruyen =  findViewById(R.id.btnDocTruyen);

        Intent intent = getIntent();
        idBook = intent.getStringExtra("idBook");

        new readJSON().execute("http://"+ipAddress+"/backend/book/"+idBook);
        Bundle bundle = new Bundle();
        bundle.putString("idBook",idBook);
        chapterFragment.setArguments(bundle);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPage(viewPager);
        tabLayout.setupWithViewPager(viewPager);
       // Toast.makeText(ReviewActivity.this,idBook,Toast.LENGTH_LONG).show();
    }


    private void setupViewPage(ViewPager viewPager) {
            TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
            //chapterFragment.setDataForListView(idBook);
            adapter.addFragment(reviewFragment, "Review");
            adapter.addFragment(chapterFragment, "Chapter");
            viewPager.setAdapter(adapter);
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
//                book = new Book();
                book.setName(object.getString("name"));
                //book.setAuthor(object.getString("description"));
                book.setImgURL(object.getString("imgSrc"));
                book.setDescription(object.getString("description"));
                tvTenTruyen.setText(book.getName());
                //tvTacGia.setText(book.getAuthor());
                Picasso.with(ReviewActivity.this).load(book.getImgURL()).into(imgReview);

                reviewFragment.setTextReview(book.getDescription());
                //Toast.makeText(ReviewActivity.this,book.getName(),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}


