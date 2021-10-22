package com.example.yazlab2map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bttnsorgu1 = (Button)findViewById(R.id.bttnsorgu1);
        Button bttnsorgu2 = (Button)findViewById(R.id.bttnsorgu2);
        Button bttnsorgu3 = (Button)findViewById(R.id.bttnsorgu3);

        EditText dayone = (EditText)findViewById(R.id.dayone);
        EditText daytwo = (EditText)findViewById(R.id.daytwo);
        EditText dayqthree = (EditText)findViewById(R.id.dayqthree);

        bttnsorgu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sorgu 1 fonksiyonu
                Sorgu1();
            }
        });

        bttnsorgu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sorgu2(dayone.getText().toString(), daytwo.getText().toString());
            }
        });

        bttnsorgu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sorgu 3 fonksiyonu
                Sorgu3(dayqthree.getText().toString());
            }
        });

    }

    public void Sorgu1(){

        String url = "https://us-central1-even-ally-310120.cloudfunctions.net/sorgu?sorgu13=1";
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(url).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(MainActivity.this, "Hata", Toast.LENGTH_SHORT).show();
                Log.d("HATA", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent toSonuc = new Intent(getApplicationContext(), sonucActivity.class);
                String resString = response.body().string();
                toSonuc.putExtra("SORGU1RES", resString);
                startActivity(toSonuc);

            }
        });
    }


    public void Sorgu2(String day1 , String day2){

        String url = "https://us-central1-even-ally-310120.cloudfunctions.net/sorgu?sorgu23=1&day1="+day1+"&day2="+day2;
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(url).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(MainActivity.this, "Hata", Toast.LENGTH_SHORT).show();
                Log.d("HATA", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent toSonuc = new Intent(getApplicationContext(), sonucActivity.class);
                String resString = response.body().string();
                toSonuc.putExtra("SORGU2RES", resString);
                startActivity(toSonuc);

            }
        });
    }

    public void Sorgu3(String day){

        String url = "https://us-central1-even-ally-310120.cloudfunctions.net/sorgu?sorgu31=1&day="+day;
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(url).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(MainActivity.this, "Hata", Toast.LENGTH_SHORT).show();
                Log.d("HATA", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent toSonuc = new Intent(getApplicationContext(), MapsActivity.class);
                String resString = response.body().string();
                toSonuc.putExtra("SORGU3RES", resString);
                startActivity(toSonuc);

            }
        });
    }
}