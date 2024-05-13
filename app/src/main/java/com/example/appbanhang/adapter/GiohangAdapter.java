package com.example.appbanhang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.Interface.IImageClickListenner;
import com.example.appbanhang.R;
import com.example.appbanhang.model.EventBus.TinhTongEvent;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GiohangAdapter extends RecyclerView.Adapter<GiohangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;


    public GiohangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSoluong()+" ");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder. item_giohang_gia.setText(" Gia: "+decimalFormat.format((gioHang.getGiasp())) + "Đ");
        long  gia = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia)+"$");

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Utils.manggiohang.get(holder.getAdapterPosition()).setCheck(true);
                    if (!Utils.mangmuahang.contains(gioHang)){
                        Utils.mangmuahang.add(gioHang);
                    }
                    EventBus.getDefault().postSticky(new TinhTongEvent());

                }else {
                    Utils.manggiohang.get(holder.getAdapterPosition()).setCheck(false);
                    for (int i =0; i<Utils.mangmuahang.size(); i++){
                        if (Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()){
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    }

                }

            }
        });
        holder.checkBox.setChecked(gioHang.isCheck());





        holder.setListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1){
                    if (gioHangList.get(pos).getSoluong()>1){
                        int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong()+" ");
                        long  gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia)+"$");
                        EventBus.getDefault().postSticky(new TinhTongEvent());

                    } else if (gioHangList.get(pos).getSoluong() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Notification");
                        builder.setMessage("Do You Want Delete This Product Out Of Card");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());

                            }
                        });
                        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });
                        builder.show();

                    }
                } else if (giatri == 2) {
                    if (gioHangList.get(pos).getSoluong() < 11){
                        int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                    holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong()+" ");
                    long  gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia)+"$");
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, imgtru, imgcong;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong,item_giohang_giasp2;
        IImageClickListenner listenner;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);
            checkBox = itemView.findViewById(R.id.item_giohang_check);


            // event click
            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);


        }

        public void setListenner(IImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View v) {
            if (v == imgtru){
                listenner.onImageClick(v, getAdapterPosition(),1);
                // 1tru
            }else if (v == imgcong){
                //2 cong
                listenner.onImageClick(v, getAdapterPosition(),2);

            }

        }
    }
}
