package com.example.tugasbesar_03.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.tugasbesar_03.R;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class ReadingFragment extends Fragment {
    protected ImageViewTouch ivtManga;
    protected GestureDetector gestureDetector;
    protected static FragmentListener listener;
    ProgressBar progressBar;

    public ReadingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_read , container, false);

        // findViewById()
        ivtManga = root.findViewById(R.id.fr_ivt_layout);
        progressBar = root.findViewById(R.id.fr_pb_page);

        progressBar.setVisibility(View.VISIBLE);

        gestureDetector = new GestureDetector(getContext(), new TapListener());

        String link = getArguments().getString("URL");
        Glide.with(getActivity())
                .load("https://cdn.mangaeden.com/mangasimg/" + link.trim())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(ivtManga);
        ivtManga.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean touched = gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
        return root;
    }

    public void setFragmentListener(FragmentListener listener){
        this.listener = listener;
    }

    private class TapListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e){
            ReadingFragment.listener.onClick(true);
            return true;
        }
    }

}

