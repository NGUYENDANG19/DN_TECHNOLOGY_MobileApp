package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.ItemClickListener;
import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietActivity;
import com.example.appbanhang.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class DienThoaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<SanPhamMoi> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public DienThoaiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai,parent, false);
            return new MyViewHodler(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loanding ,parent, false);
            return new LoandingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHodler){
           MyViewHodler myViewHodler = (MyViewHodler) holder;
            SanPhamMoi sanPham = array.get(position);
            myViewHodler.tensp.setText(sanPham.getTensp());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHodler. giasp.setText(" Gia: "+decimalFormat.format(Double.parseDouble(sanPham.getGiasp())) + "Đ");
            myViewHodler.mota.setText(sanPham.getMota());
            myViewHodler.idsp.setText(sanPham.getId()+"");
            Glide.with(context).load(sanPham.getHinhanh()).into(myViewHodler.hinhanh);
            myViewHodler.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if (!isLongClick){
                        //click
                        Intent  intent = new Intent(context, ChiTietActivity.class);
                        intent.putExtra("chi tiet",sanPham);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });



        }else {
            LoandingViewHolder loandingViewHolder = (LoandingViewHolder) holder;
            loandingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }


    @Override
    public int getItemCount() {
        return array.size();
    }


    public class LoandingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;

        public LoandingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }


    public class MyViewHodler extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tensp, giasp, mota, idsp;
        ImageView hinhanh;
        private ItemClickListener itemClickListener;

        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemdt_ten);
            giasp = itemView.findViewById(R.id.itemdt_gia);
            mota = itemView.findViewById(R.id.itemdt_mota);
            idsp = itemView.findViewById(R.id.itemdt_idsp);
            hinhanh = itemView.findViewById(R.id.itemdt_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v ,getAdapterPosition(),false);
        }
    }

}
