package com.kubangkangkung.laundry.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kubangkangkung.laundry.Adapter.AdapterData;
import com.kubangkangkung.laundry.Api.APIRequestData;
import com.kubangkangkung.laundry.Api.RetroServer;
import com.kubangkangkung.laundry.Model.DataModel;
import com.kubangkangkung.laundry.Model.ResponseModel;
import com.kubangkangkung.laundry.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
private RecyclerView rvData;
private List<DataModel>listdata=new ArrayList<>();
SwipeRefreshLayout swipeakudong;
FloatingActionButton fabTAmbah;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvData=findViewById(R.id.recyclemain);
        swipeakudong=findViewById(R.id.id_swipe);
        fabTAmbah=findViewById(R.id.fab_tambah);


     //   ambilData();
        rvData.setAdapter(new AdapterData(MainActivity.this,listdata));
        rvData.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //ketika di swipe
        swipeakudong.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeakudong.setRefreshing(true);
                ambilData();
                swipeakudong.setRefreshing(false);
            }
        });

        //ketika floating action button di pencet
        fabTAmbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TambahActivity.class));
            }
        });

//        rvData.setAdapter(rvAdapter);
//        rvLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ambilData();
    }

    public void ambilData(){
        //loading
        final ProgressDialog loding=new ProgressDialog(MainActivity.this);
        loding.setMessage("Tunggu Sebentar...");
        loding.show();
        //menghubungkan apirequestdata dengan restoserver
        APIRequestData request= RetroServer.konekRetrofit().create(APIRequestData.class);

        Call<ResponseModel>tampilData=request.ardRetrieveData();

        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode=response.body().getKode();
                String pesan=response.body().getPesan();
              //  Toast.makeText(MainActivity.this, "Kode"+kode +" Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                listdata=response.body().getData();
//                rvAdapter=new AdapterData(MainActivity.this,listdata);
//                rvData.setAdapter(rvAdapter);
//                rvAdapter.notifyDataSetChanged();
                rvData.setAdapter(new AdapterData(MainActivity.this,listdata));
                loding.dismiss();

                Log.d(TAG, "onResponse: "+listdata);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}