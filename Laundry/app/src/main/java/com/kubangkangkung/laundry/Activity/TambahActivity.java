package com.kubangkangkung.laundry.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kubangkangkung.laundry.Api.APIRequestData;
import com.kubangkangkung.laundry.Api.RetroServer;
import com.kubangkangkung.laundry.Model.ResponseModel;
import com.kubangkangkung.laundry.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText ednama,edalamat,edtelepon;
    private Button btnsimpan;
    private String nama,alamat,telepon;
    private static final String TAG = "TambahActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        ednama=findViewById(R.id.id_nama_tambah);
        edalamat=findViewById(R.id.id_nama_tambah);
        edtelepon=findViewById(R.id.di_telepon_tambah);
        btnsimpan=findViewById(R.id.id_tambah);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama=ednama.getText().toString();
                alamat=edalamat.getText().toString();
                telepon=edtelepon.getText().toString();

                if(nama.trim().equals("")){
                    ednama.setError("Nama belum di isi");
                }else if (alamat.trim().equals("")){
                    edalamat.setError("Alamat belum di isi");
                }else if(telepon.trim().equals("")){
                    edtelepon.setError("Telepon belum di isi");
                }else{
                    MasukanData() ;
                }

            }
        });

    }

    //method create
    public void MasukanData(){
        APIRequestData request= RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData=request.ardCreateData(nama,alamat,telepon);
        Log.d(TAG, "MasukanData: "+nama+alamat+telepon);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode=response.body().getKode();
                String pesan=response.body().getPesan();
                Toast.makeText(TambahActivity.this, "Berhasil Menambah Data ", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menambah Data"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}