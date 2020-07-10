package com.kubangkangkung.laundry.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kubangkangkung.laundry.Adapter.AdapterData;
import com.kubangkangkung.laundry.Api.APIRequestData;
import com.kubangkangkung.laundry.Api.RetroServer;
import com.kubangkangkung.laundry.Model.DataModel;
import com.kubangkangkung.laundry.Model.ResponseModel;
import com.kubangkangkung.laundry.R;

import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private DataModel datasaya;
    EditText txtnama,txtalaamt,txtxtelepon;
    private int id;
    Button btnUpdate;
    String nama,alamat,telepon;

//    public static final String KEY_ID = "key_id";
//    public static final String KEY_NAMA = "key_nama";
//    public static final String KEY_ALAMAT = "key_alamat";
//    public static final String KEY_TELEPON = "key_telepon";
    private static final String TAG = "UpdateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        txtnama=findViewById(R.id.id_nama_update);
        txtalaamt=findViewById(R.id.id_alamat_update);
        txtxtelepon=findViewById(R.id.di_telepon_update);
        btnUpdate=findViewById(R.id.id_update);
//        final int dataID =getIntent().getIntExtra(KEY_ID,0);
//        final int dataNama =getIntent().getIntExtra(KEY_NAMA,0);
//        final int dataAlamat =getIntent().getIntExtra(KEY_ALAMAT,0);
//        final int dataTelepon =getIntent().getIntExtra(KEY_TELEPON,0);
//        Log.d(TAG, "onCreate: "+dataID +dataNama+dataAlamat+dataTelepon);
        Bundle bundle=getIntent().getBundleExtra(AdapterData.DATA_EXTRA);
        datasaya = Parcels.unwrap(bundle.getParcelable(AdapterData.DATA_LAUNDRY));

        txtnama.setText(datasaya.getNama());
        txtalaamt.setText(datasaya.getAlamat());
        txtxtelepon.setText(datasaya.getTelepon());

//
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=datasaya.getId();
                nama=txtnama.getText().toString();
                alamat=txtalaamt.getText().toString();
                telepon=txtxtelepon.getText().toString();

                if(nama.trim().equals("")){
                    txtnama.setError("Nama belum di isi");
                }else if (alamat.trim().equals("")){
                    txtalaamt.setError("Alamat belum di isi");
                }else if(telepon.trim().equals("")){
                    txtxtelepon.setError("Telepon belum di isi");
                }else{
                 UpdateData();
                }

            }
        });


    }

    private void UpdateData(){
        APIRequestData request= RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> dataUpdate=request.ardUpdateData(id,nama,alamat,telepon);
        Log.d(TAG, "onCreate: "+id+nama+alamat+telepon);

        dataUpdate.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode =response.body().getKode();
                String pesan =response.body().getPesan();
                Toast.makeText(UpdateActivity.this, "Berhasil di Update"+pesan +kode, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "Gagal Update Data "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}