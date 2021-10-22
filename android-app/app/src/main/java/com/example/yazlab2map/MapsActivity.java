package com.example.yazlab2map;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static List<LatLng>LatlngList = new ArrayList<LatLng>();
    public String dirUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        TextView puLocTW = (TextView)findViewById(R.id.puloc);
        TextView doLocTW = (TextView)findViewById(R.id.doloc);
        TextView tdTW = (TextView)findViewById(R.id.tdist);



        if(getIntent().hasExtra("SORGU3RES")) {
            ArrayList<String> items = new ArrayList<String>();

            try {
                List<String> LocsList = parseQ3(getIntent().getExtras().getString("SORGU3RES"),puLocTW, doLocTW, tdTW );
				//aşağıdaki satırda key= den sonrasına kendi google directions api keyini yazmalısın
                dirUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=" + LocsList.get(0).replace(" ", "+") + "," + LocsList.get(1).replace(" ", "+") + "&destination=" + LocsList.get(2).replace(" ", "+") + "," +LocsList.get(3).replace(" ", "+") + "&key=";
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        PolylineOptions polylineOptions = new PolylineOptions();

//        String enc = "mwwwFlaqbMWp@~BvA|B~AvFnDhMnIzT`OtMrI`g@d\\zYrR~FtDjCpAbElBrEvB~HxDzDhBlBfA|BdAbBt@j@L`DZa@`J_@zIK~AS`EQbB|Nt@|Gn@bHt@bGv@zL|AbKxAfLnB~KfBtFpAfAPxJ~ApDt@fA@f@BfCn@xCz@b@AZKd@m@XkA\\Y`@O`AEp@NdAb@ZX~@VpD|@tDn@fAHfABrD?dCY`Ck@`E_BjCkAxIaCxWaHhHuBxEyBtEqC`@_@x@w@xAwA~@g@NIrDmBvAiApEqDdFkClCkA~Aa@lA]nBS`@OpB{@bB_AhBuApFsEnDyCzCwBnKwGfB}@b@GjReK|OoIzFyCrBk@r@I~@Ij@AhBD`Dj@lBlAxC~CpVpWfGlGpTlU`CdClJ~JvKfLtB~BfIlIrAnAvAxA|RtSxPpQlAvAnCdE|@jBt@`CXvARjBFdBCzCWvCe@zBgAbD{B`Hk@fD[zDE~BFrD^pEl@tChApCl@~@rA|AxAbAvClAdEvAvDzAxMrFpNnF`KfDt@PbF|@dLx@~JX~A?tICjDI`He@vGy@zP{BrD}@pAg@tBiArA}@dDyClCoDlBeClCkCtDkCpAw@\\QxDwBbAe@rAcA|B{B~AkBxAsBr@u@v@sA`AwBlAgD\\{Ab@qDLgC?kFUeHOoFRmGb@sDnBsJnE_TxCmQpA_GbAuD~AuEzBaFlJcQfFuJn@}AnAsEv@mF\\aCp@{DRy@dAcDr@cBvAiCrCcEjA}A|CuDtBwBxCmChDgCvDwBfG_CjD{ArAu@dBkAfB_B`BuBbAcB~@kBlAiDhBiHjA}HbAaIn@eHt@qJ`@}ETsIAcFWcHc@cFiBeP_AqMi@mQq@qPcAsNmAyOMoG?wFPgGZmE\\qEVyDByBAkGQ{G_AuUiAaPw@kI_BsPS_EI{FVqG\\uCr@oDzAeH`D{Ot@sEt@cGJ}AVuJ?{EG{Fi@uKc@gFm@gEaAyDsCiHqA_DeDqH_AwB{AgCwAgBuBkBkBgAsAk@qCs@_AM{AKkAEsCL}IhAk@DyAByBKi@IoD{@wCuAEWU_@Oe@C_ANw@Tc@\\[n@O`@Av@L\\VXf@Ht@Mr@[p@m@`AQR[^w@|@yS`V{CnDsCdDM\\cHbIiD|D{HzI[LqBlB[\\qAfAwD~DeCpCuGpHeDuFcAmBo@iA}@aBWKg@y@{CgFeAgBuJaQs@oA{@}A`AgA`@WFGbEaF~CqDv@y@dAgApAe@~@MjABbAPbAZjBfA~ApA";
//        List<LatLng> latlngList = PolyUtil.decode(enc);

        latLngs(dirUrl);
        SystemClock.sleep(5000);

        for(int i = 0; i <LatlngList.size(); i++)
        {
            polylineOptions.add(LatlngList.get(i));
        }

        mMap.addPolyline(polylineOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatlngList.get(0)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
    }
    public List<String> parseQ3(String json, TextView puLoc, TextView doLoc, TextView td) throws JSONException {

        List<String> LocsList =  new ArrayList<String>();
        JSONArray arr = new JSONArray(json);
        for(int i = 0; i<arr.length(); i++)
        {
            JSONObject jsonObject = arr.getJSONObject(i);
            JSONObject starting = jsonObject.getJSONArray("StartingLocation").getJSONObject(0);
            String puLocText = starting.getString("Borough") + starting.getString("Zone");

            JSONObject ending = jsonObject.getJSONArray("EndingLocation").getJSONObject(0);
            String doLocText = ending.getString("Borough") + ending.getString("Zone");

            LocsList.add(starting.getString("Borough"));
            LocsList.add(starting.getString("Zone"));
            LocsList.add(ending.getString("Borough"));
            LocsList.add(ending.getString("Zone"));


            String tdText = jsonObject.getString("trip_distance");
            puLoc.setText(puLocText);
            doLoc.setText(doLocText);
            td.setText(tdText);
        }

        return LocsList;
    }
    public void latLngs(String url)
    {
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

                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String enc = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points");
                    //JSONArray enc2 = new JSONArray(enc1);
                    LatlngList = PolyUtil.decode(enc);
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });


    }
}