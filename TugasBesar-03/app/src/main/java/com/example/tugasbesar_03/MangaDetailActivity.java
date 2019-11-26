package com.example.tugasbesar_03;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tugasbesar_03.adapter.Adapter_Chapter;
import com.example.tugasbesar_03.adapter.ViewChapter;
import com.example.tugasbesar_03.mangaModel.Manga_Chapter_Detail;
import com.example.tugasbesar_03.networking.MySingleton;
import com.example.tugasbesar_03.view.MangaReader;
import com.google.gson.Gson;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;


public class MangaDetailActivity extends AppCompatActivity implements ViewChapter {
    private String BASE_URL = "https://www.mangaeden.com/api/manga/";
    protected CoordinatorLayout mangaDetailLayout;
    protected ProgressDialog loading;
    
    protected RecyclerView recyclerView;
    protected ImageView ivManga;
    protected TextView tvTitle, tvAuthor, tvChapter, tvArtist, tvReleased, tvGenre, tvDescription;

    protected ArrayList<String> chapterNumber = new ArrayList<>();
    protected ArrayList<String> chapterId = new ArrayList<>();
    protected Adapter_Chapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_detail);

        // Object findViewById()
        recyclerView = findViewById(R.id.amd_rv_chapterList);
        mangaDetailLayout = findViewById(R.id.amd_cl_mangaDetail_layout);
        ivManga = findViewById(R.id.amd_iv_gambar_manga);
        tvTitle = findViewById(R.id.amd_tv_judul_manga);
        tvAuthor = findViewById(R.id.amd_tv_author_manga);
        tvChapter = findViewById(R.id.amd_tv_chapter);
        tvArtist = findViewById(R.id.amd_tv_artist_manga);
        tvReleased = findViewById(R.id.amd_tv_released_manga);
        tvGenre = findViewById(R.id.amd_tv_category_manga);
        tvDescription = findViewById(R.id.amd_tv_description_manga);

        // set recyclerView dengan adapternya, dan set layout nya juga
        adapter = new Adapter_Chapter(chapterNumber);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                                            .color(Color.RED)
                                            .sizeResId(R.dimen.amd_rv_space)
                                        .build());
        recyclerView.setAdapter(adapter);

        // "loading" ProgressDialog set-up, and set adapter for MangaDetail's chapter list
        loading = new ProgressDialog(this);
        loading.setMessage("Please wait, chapter is loading...");
        loading.setIndeterminate(true);
        Adapter_Chapter.setChapterList(this);
        loading.show();

        // url set
        BASE_URL += getIntent().getStringExtra("ID");

        volleyCall();
    }

    private void dialogBuilder(String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(description)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // bikin objek AlertDialog trus dishow
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void getChapter(int position) {
        chapterNumber.get(position);
        Intent intent = new Intent(getApplicationContext(), MangaReader.class);
        intent.putExtra("ID", chapterId.get(position));
        startActivity(intent);
    }

    private void volleyCall() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, BASE_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Manga_Chapter_Detail chapterDetail = new Manga_Chapter_Detail();
                        chapterDetail = gson.fromJson(response.toString(), Manga_Chapter_Detail.class);
                        Glide.with(getApplicationContext()).load("https://cdn.mangaeden.com/mangasimg/" + chapterDetail.getImage())
                                .apply(new RequestOptions()
                                        .override(154,250).centerCrop().placeholder(R.drawable.pic_image_placeholder).error(R.drawable.pic_image_null))
                                .into(ivManga);

                        // TextView setText
                        tvTitle.setText(chapterDetail.getTitle());
                        tvChapter.setText(chapterDetail.getChapters_len() + " chapter");
                        tvAuthor.setText("Author: " + chapterDetail.getAuthor());
                        tvArtist.setText("Artist: " + chapterDetail.getArtist());
                        tvReleased.setText("Released: " + chapterDetail.getReleased());
                        tvDescription.setText(chapterDetail.getDescription());

                        // StringBuffer untuk get categories nya, lalu set ke TextView tvGenre
                        StringBuffer stringBuffer = new StringBuffer();
                        for (String category : chapterDetail.getCategories()) {
                            stringBuffer.append(category + " | ");
                        }
                        tvGenre.setText(stringBuffer.toString());
                        
                        Object ov[] = chapterDetail.getChapters().toArray();
                        for (Object d : ov) {
                            Log.d("URL", d.toString() + 'd');
                            String red = d.toString().replace("[", "");
                            red = red.replace("]", "");
                            String newarray[] = red.split(",");
                            chapterNumber.add(newarray[0]);
                            chapterId.add(newarray[newarray.length - 1]);
                        }
                        adapter.notifyDataSetChanged();
                        loading.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }
}