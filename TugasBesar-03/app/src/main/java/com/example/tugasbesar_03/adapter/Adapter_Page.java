package com.example.tugasbesar_03.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasbesar_03.R;

import java.util.ArrayList;

public class Adapter_Page extends RecyclerView.Adapter<Adapter_Page.MyViewHolder>{
    protected Context context;
    protected ArrayList<String> mangaURL = new ArrayList<>();
    protected static PageSelector listener;

    public Adapter_Page(ArrayList<String> image, Context context){
        this.context = context;
        this.mangaURL = image;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_list_item,null);
        MyViewHolder viewHolder = new MyViewHolder(view) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvPage.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return mangaURL.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        protected TextView tvPage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // findViewById()
            tvPage = itemView.findViewById(R.id.pli_tv_page);

            // setOnClickListener buat TextView nya
            tvPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.selectPage(getAdapterPosition());
                    Log.d("Page", "Page: " + getAdapterPosition());
                }
            });
        }
    }
}
