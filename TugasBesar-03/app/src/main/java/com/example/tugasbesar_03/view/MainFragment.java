package com.example.tugasbesar_03.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasbesar_03.R;
import com.example.tugasbesar_03.adapter.Adapter_Main;
import com.example.tugasbesar_03.mangaModel.Manga_List;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    protected RecyclerView recyclerView;
    protected Adapter_Main mainAdapter;
    protected ArrayList<Manga_List> mangaList = new ArrayList<>();

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // findViewById()
        recyclerView = view.findViewById(R.id.fm_rv_mangaList);

        // adapter set
        mainAdapter = new Adapter_Main();

        return view;
    }
}