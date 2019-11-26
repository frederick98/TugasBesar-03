package com.example.tugasbesar_03.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.tugasbesar_03.R;
import com.example.tugasbesar_03.adapter.Adapter_Manga;
import com.example.tugasbesar_03.adapter.Adapter_Page;
import com.example.tugasbesar_03.adapter.PageSelector;
import com.example.tugasbesar_03.mangaModel.Manga_Picture;
import com.example.tugasbesar_03.networking.MySingleton;
import com.example.tugasbesar_03.view.pinchToZoom.ImageZoom;
import com.example.tugasbesar_03.view.pinchToZoom.PageZoomOut;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class MangaReader extends FragmentActivity implements FragmentListener {
    private String BASE_URL = "https://www.mangaeden.com/api/chapter/";
    protected ImageZoom imageZoom;
    protected RecyclerView pageList;
    protected Adapter_Page pageAdapter;
    protected Adapter_Manga mangaAdapter;
    protected ArrayList<String> mangaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_reader);

        // findViewById()
        pageList = findViewById(R.id.amr_rv_pageList);
        imageZoom = findViewById(R.id.amr_vp_imageZoom);

        // setPageTransformer buat ViewPager di layout xmlnya
        imageZoom.setPageTransformer(true, new PageZoomOut());

        // adapter set
        mangaAdapter = new Adapter_Manga(getSupportFragmentManager(), mangaList);
        imageZoom.setAdapter(mangaAdapter);
        mangaAdapter.setFragmentListener(this);

        String intent = getIntent().getStringExtra("ID");
        BASE_URL += intent.trim() + "/";

        volleyCall();
    }

    private void volleyCall() {
        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Manga_Picture picture = gson.fromJson(response.toString(), Manga_Picture.class);
                for (Object object : picture.getImages()) {
                    String word = object.toString().replace("[", "");
                    word = word.replace("]", "");
                    String[] link = word.split(",");
                    mangaList.add(link[1]);
                }
                Collections.reverse(mangaList);

                pageAdapter = new Adapter_Page(mangaList, getApplicationContext());
                pageList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                pageList.setAdapter(pageAdapter);
                pageAdapter.setPageListener(new PageSelector() {
                    @Override
                    public void selectPage(int position) {
                        imageZoom.setCurrentItem(position);
                    }
                });
                mangaAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MangaReader.this, "" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    /* method ini ngatur biar pas user click si gambar manganya, listview page yg bagian bawahnya ilang, jadi kaya fullscreen gitu lah
     */
    @Override
    public void onClick(boolean status) {
        BottomNavigationView view_bottom = findViewById(R.id.amr_bnv_navigation);
        Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.pagelist_up);
        Animation bottomDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.pagelist_down);
        if (view_bottom.getVisibility() == View.VISIBLE) {
            // layoutnya dihide
            view_bottom.startAnimation(bottomDown);
            view_bottom.setVisibility(View.INVISIBLE);
        } else {
            // layoutnya dishow
            view_bottom.startAnimation(bottomUp);
            view_bottom.setVisibility(View.VISIBLE);
        }
    }

    /* method ini buat meng-allow sistem untuk handle "back" button.
     * nanti methodnya manggil method finish() buat activity ini jadi
     * kembali ke view sebelumnya
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


