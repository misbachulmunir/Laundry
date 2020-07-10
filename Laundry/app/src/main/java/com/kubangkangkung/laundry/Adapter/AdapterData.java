package com.kubangkangkung.laundry.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kubangkangkung.laundry.Model.DataModel;
import com.kubangkangkung.laundry.R;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel>listdata;

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
        }
    }
}
