package com.example.tugasbesar_03.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tugasbesar_03.MangaDetailActivity;
import com.example.tugasbesar_03.R;
import com.example.tugasbesar_03.mangaModel.Manga_List;

import java.util.ArrayList;

public class Adapter_Main extends RecyclerView.Adapter<Adapter_Main.MainVH>{
    private String BASE_URL = "https://cdn.mangaeden.com/mangasimg/";
    private static ArrayList<Manga_List> mangaList = new ArrayList<>();
    Context context;

    public void setArrayList(ArrayList<Manga_List> mangaList){
        this.mangaList = mangaList;
    }

    public Adapter_Main(){

    }

    @NonNull
    @Override
    public MainVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manga_list_item, parent, false);
        MainVH holder = new MainVH(view);
        context = view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainVH holder, int position) {
        holder.tvMangaName.setText(mangaList.get(position).getTitle());
        Glide.with(context).load(BASE_URL + mangaList.get(position).getIMAGE_URL())
                .apply(new RequestOptions().override(200,150).centerCrop().placeholder(R.drawable.pic_image_placeholder).error(R.drawable.pic_image_null))
                .into(holder.ivMangaPic);
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }


    class MainVH extends RecyclerView.ViewHolder{
        private ImageView ivMangaPic;
        private TextView tvMangaName;

        public MainVH(View mangaView){
            super(mangaView);

            // findViewById()
            ivMangaPic = mangaView.findViewById(R.id.mli_iv_mangaPic);
            tvMangaName = mangaView.findViewById(R.id.mli_tv_mangaTitle);

            mangaView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MangaDetailActivity.class);
                    intent.putExtra("ID", mangaList.get(getAdapterPosition()).getId());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}
