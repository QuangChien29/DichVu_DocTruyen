package com.example.higo.dichvu_doctruyen;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.higo.dichvu_doctruyen.activity.ReviewActivity;
import com.example.higo.dichvu_doctruyen.adapter.AdapterListBook;
import com.example.higo.dichvu_doctruyen.fragment.ListBookFragment;
import com.example.higo.dichvu_doctruyen.models.Book;
import com.example.higo.dichvu_doctruyen.models.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

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
        new readJSON().execute("http://"+ipAddress+"/backend/book");
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogin: {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Đăng Nhập");
                dialog.setContentView(R.layout.dialog_login);
                final EditText edUsername = dialog.findViewById(R.id.edUsername);
                final EditText edPassword = dialog.findViewById(R.id.edPassword);
                Button btnDangNhap = (Button)dialog.findViewById(R.id.btnDangNhap);
                Button btnThoatDangNhap = (Button)dialog.findViewById(R.id.btnThoatDangNhap);
                btnThoatDangNhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                btnDangNhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = edUsername.getText().toString();
                        String password = edPassword.getText().toString();
                        if(userName.equals("") || password.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Hãy nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            User user = new User();
                            user.setUserName(userName);
                            user.setPassword(password);
                            RequestParams params = new RequestParams();
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("login", userName); map.put("password", password);
                            params.put("query", map); params.put("includeUserMiscInfo", "true");
                            AsyncHttpClient client = new AsyncHttpClient();
                            client.post("http://192.168.1.110:8080/backend/users",
                                    params, new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            dialog.hide();
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                            dialog.hide();
                                            if (statusCode == 404) {
                                                Toast.makeText(MainActivity.this,
                                                        "Requested resource not found",
                                                        Toast.LENGTH_LONG).show();
                                            }

                                            else if (statusCode == 500) {
                                                Toast.makeText(MainActivity.this,
                                                        "Something went wrong at server end",
                                                        Toast.LENGTH_LONG).show();
                                            }

                                            else {
                                                Toast.makeText(
                                                        MainActivity.this,
                                                        "Loi chưa xác định"
                                                        , Toast.LENGTH_LONG)
                                                        .show();
                                            }
                                        }

                                        });
                        }
                    }

                });
                dialog.show();
                break;

            }
        }
        return super.onOptionsItemSelected(item);
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

