package com.example.yazlab2map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class sonucActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);

        ListView listelem = (ListView)findViewById(R.id.listelem);


        if(getIntent().hasExtra("SORGU1RES")){
            System.out.printf(getIntent().getExtras().getString("SORGU1RES"));
            ArrayList<String> items = new ArrayList<String>();

            try {
                parseQ1(getIntent().getExtras().getString("SORGU1RES"), items);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> a1 = new ArrayAdapter<String>(this, R.layout.listq1, R.id.textfield, items );
            listelem.setAdapter(a1);

        }
        else if(getIntent().hasExtra("SORGU2RES")){
            ArrayList<String> items = new ArrayList<String>();

            try {
                parseQ2(getIntent().getExtras().getString("SORGU2RES"), items);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> a1 = new ArrayAdapter<String>(this, R.layout.listq1, R.id.textfield, items );
            listelem.setAdapter(a1);
        }




    }

    public void parseQ1(String json, ArrayList<String> listitems) throws JSONException {

        JSONArray arr = new JSONArray(json);
        for(int i = 0; i<arr.length(); i++)
        {

            JSONObject jsonObject = arr.getJSONObject(i);
            listitems.add(Integer.toString(i+1)+". Yolculuk");
            listitems.add("Alma zamanı: "+jsonObject.getString("tpep_pickup_datetime"));
            listitems.add("Bırakma zamanı: "+jsonObject.getString("tpep_dropoff_datetime"));
            listitems.add("Mesafe: "+jsonObject.getString("trip_distance"));

        }
    }

    public void parseQ2(String json, ArrayList<String> listitems) throws JSONException {

        JSONArray arr = new JSONArray(json);
        for(int i = 0; i<arr.length(); i++)
        {

            JSONObject jsonObject = arr.getJSONObject(i);
            listitems.add(Integer.toString(i+1)+". Yolculuk");
            listitems.add("Alma zamanı: "+jsonObject.getString("tpep_pickup_datetime"));
            listitems.add("Bırakma zamanı: "+jsonObject.getString("tpep_dropoff_datetime"));
            listitems.add("Mesafe: "+jsonObject.getString("trip_distance"));
            listitems.add("Yolcu Sayısı: "+jsonObject.getString("passanger_count"));
            listitems.add("PU ID: "+jsonObject.getString("PULocationID"));
            listitems.add("DO ID: "+jsonObject.getString("DOLocationID"));

        }
    }


}