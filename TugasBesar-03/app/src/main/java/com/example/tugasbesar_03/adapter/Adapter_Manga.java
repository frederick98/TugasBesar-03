package com.example.tugasbesar_03.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.tugasbesar_03.view.FragmentListener;
import com.example.tugasbesar_03.view.ReadingFragment;

import java.util.ArrayList;

public class Adapter_Manga extends FragmentStatePagerAdapter implements FragmentListener {
    protected ArrayList<String> link = new ArrayList<>();

    protected FragmentListener listener;

    public Adapter_Manga(FragmentManager fragmentManager, ArrayList<String> imageLink){
        super(fragmentManager);
        this.link = imageLink;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ReadingFragment readingFragment = new ReadingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("URL", link.get(position));
        readingFragment.setArguments(bundle);
        readingFragment.setFragmentListener(listener);
        return readingFragment;
    }

    @Override
    public int getCount() {
        return link.size();
    }

    @Override
    public void onClick(boolean status) {
        listener.onClick(status);
    }

    public void setFragmentListener(FragmentListener listener) {
        this.listener = listener;
    }
}
