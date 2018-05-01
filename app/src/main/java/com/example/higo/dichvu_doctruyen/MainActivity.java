package com.example.higo.dichvu_doctruyen;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.higo.dichvu_doctruyen.activity.ReviewActivity;
import com.example.higo.dichvu_doctruyen.adapter.AdapterListBook;
import com.example.higo.dichvu_doctruyen.fragment.ListBookFragment;
import com.example.higo.dichvu_doctruyen.models.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvBook;
    AdapterListBook adapterListBook;
    ArrayList<Book> listBook;
    public static String ipAddress ="192.168.1.110:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        ListBookFragment listBookFragment = new ListBookFragment();
//        fragmentTransaction.add(R.id.frameContent,listBookFragment);
//        fragmentTransaction.commit();
        new readJSON().execute("http://"+ipAddress+"/backend/book");
        addEvents();
    }



    private void addEvents() {
        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this,listBook.get(position).getId(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                intent.putExtra("idBook",listBook.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        lvBook =findViewById(R.id.lvBook);
        listBook = new ArrayList<>();

        adapterListBook = new AdapterListBook(this,
                R.layout.item_book,// lấy custom layout
                listBook);
        lvBook.setAdapter(adapterListBook);
        adapterListBook.notifyDataSetChanged();

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
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0 ; i< array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    Book book = new Book();
                    book.setName(object.getString("name"));
                    book.setDescription(object.getString("description"));
                    book.setSumChapter(object.getString("numOfChapter"));
                    book.setImgURL(object.getString("imgSrc"));
                    book.setId(object.getString("id"));
                    listBook.add(book);
                }
                adapterListBook.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

