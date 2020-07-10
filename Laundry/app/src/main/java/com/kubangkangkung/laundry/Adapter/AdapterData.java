package com.kubangkangkung.laundry.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.kubangkangkung.laundry.Activity.MainActivity;
import com.kubangkangkung.laundry.Activity.UpdateActivity;
import com.kubangkangkung.laundry.Api.APIRequestData;
import com.kubangkangkung.laundry.Api.RetroServer;
import com.kubangkangkung.laundry.Model.DataModel;
import com.kubangkangkung.laundry.Model.ResponseModel;
import com.kubangkangkung.laundry.R;

import org.parceler.Parcels;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    public static final String DATA_LAUNDRY = "data_laundri";
    public static final String DATA_EXTRA = "data_extra";
    private Context ctx;
    private List<DataModel>listdata;
    int idlaundry;

    public AdapterData(Context ctx, List<DataModel> listdata) {
        this.ctx = ctx;
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
       HolderData holder=new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm=listdata.get(position);

        holder.tvid.setText(String.valueOf(dm.getId()));
        holder.tvnama.setText(dm.getNama());
        holder.tvalamat.setText(dm.getAlamat());
        holder.tvtelepon.setText(dm.getTelepon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah=new Intent(ctx, UpdateActivity.class);
//                pindah.putExtra(UpdateActivity.KEY_ID,listdata.get(position).getId());
//                pindah.putExtra(UpdateActivity.KEY_NAMA,listdata.get(position).getNama());
//                pindah.putExtra(UpdateActivity.KEY_ALAMAT,listdata.get(position).getAlamat());
//                pindah.putExtra(UpdateActivity.KEY_TELEPON,listdata.get(position).getTelepon());
                Bundle bundle=new Bundle();
                bundle.putParcelable(DATA_LAUNDRY, Parcels.wrap(listdata.get(position)));
                pindah.putExtra(DATA_EXTRA,bundle);
                ctx.startActivity(pindah);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public  class HolderData extends RecyclerView.ViewHolder{
        TextView tvid, tvnama,tvalamat,tvtelepon;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvnama=itemView.findViewById(R.id.id_nama);
            tvalamat=itemView.findViewById(R.id.id_alamat);
            tvtelepon=itemView.findViewById(R.id.id_telepon);
            tvid=itemView.findViewById(R.id.id_id);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alertPesan=new AlertDialog.Builder(ctx);
                    alertPesan.setMessage("Operasi");
                    alertPesan.setCancelable(true);

                    //ambil id
                    idlaundry=Integer.parseInt(tvid.getText().toString());

                    alertPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteData();
                            dialog.dismiss();
                            ((MainActivity)ctx).ambilData();

                        }
                    });

                    alertPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertPesan.show();
                    return false;
                }
            });
        }

        private void deleteData(){
            APIRequestData request= RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapus=request.ardHapusData(idlaundry);
            hapus.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode =response.body().getKode();
                    String pesan=response.body().getPesan();
                    Toast.makeText(ctx, "Berhasil dihapus"+kode+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghapus"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
