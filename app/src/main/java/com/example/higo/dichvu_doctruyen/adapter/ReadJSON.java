package com.example.higo.dichvu_doctruyen.adapter;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadJSON extends AsyncTask<String,Void,String> {

    private String chuoiJSON;

    public ReadJSON() {
    }
    public String getChuoiJSON() {
        return chuoiJSON;
    }

    public void setChuoiJSON(String chuoiJSON) {
        this.chuoiJSON = chuoiJSON;
    }

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
        this.chuoiJSON = s;
    }


}

