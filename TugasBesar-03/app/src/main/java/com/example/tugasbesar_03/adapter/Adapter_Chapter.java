package com.example.tugasbesar_03.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasbesar_03.R;

import java.util.ArrayList;

public class Adapter_Chapter extends RecyclerView.Adapter<Adapter_Chapter.ChapterVH> {
    public static ArrayList<String> chapterList;
    protected static ViewChapter listener;
    protected Context context;

    public Adapter_Chapter(ArrayList<String> stringArrayList) {
        this.chapterList = stringArrayList;
    }

    @Override
    public Adapter_Chapter.ChapterVH onCreateViewHolder(ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_list_item, parent,false);
        ChapterVH viewholder = new ChapterVH(view);
        context = view.getContext();
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Adapter_Chapter.ChapterVH holder, int position) {
        holder.tvChapter.setText(chapterList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static void setChapterList(ViewChapter listener2){
        listener = listener2;
    }

    class ChapterVH extends RecyclerView.ViewHolder{
        protected TextView tvChapter;

        public ChapterVH(View item) {
            super(item);
            // findViewById()
            tvChapter = item.findViewById(R.id.cli_tv_chapter);

            // setOnClickListener()
            tvChapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Adapter_Chapter.listener.getChapter(getAdapterPosition());

                }
            });

        }

    }
}



