package com.example.tugasbesar_03;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tugasbesar_03.adapter.Adapter_Main;
import com.example.tugasbesar_03.mangaModel.Manga_List;
import com.example.tugasbesar_03.mangaModel.Manga_Model;
import com.example.tugasbesar_03.mangaModel.Manga_Search;
import com.example.tugasbesar_03.networking.MySingleton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    protected String BASE_URL = "https://www.mangaeden.com/api/list/0/";

    protected ArrayList<Manga_List> mangaList = new ArrayList<>();
    protected ArrayList<Manga_List> mangaCategory = new ArrayList<>();
    protected static ArrayList<Manga_List> mangaList2 = new ArrayList<>();
    protected static ArrayList<Manga_Search> mangaSearch = new ArrayList<>();

    protected Adapter_Main mainAdapter;

    protected ProgressDialog progressDialog;
    protected MaterialSearchView searchView;
    protected LinearLayout previousButton, nextButton;
    protected Toolbar toolbar;
    protected DrawerLayout drawerLayout;

    protected NavigationView navigationView;

    protected SharedPreferences categoryPreferences;
    protected SharedPreferences.Editor searchEditor;

    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // findViewById()
        drawerLayout = findViewById(R.id.am_dl);

        // inisilisasi toolbar lalu setSupportActionBar() untuk si toolbarnya biar ga null
        toolbar = findViewById(R.id.am_tb_menu);
        setSupportActionBar(toolbar);

        // inisialisasi method search supaya standby dulu
        searchVolleyRequest();

        recyclerView = findViewById(R.id.am_rv_allManga);

        // inisialisasi ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        // inisialisasi method volleyRequest() supaya standby dulu
        volleyRequest(0);

        // mulai retrieve manga dari server
        progressDialog.setMessage("Please Wait while manga is retrieved");
        progressDialog.show();
        categoryPreferences = getSharedPreferences("CATEGORY", MODE_PRIVATE);
        searchEditor = categoryPreferences.edit();

        final List<String> mangaList3 = new ArrayList<String>();

        // inisialisi si adapter buat main fragment lalu set ke recyclerView nya
        mainAdapter = new Adapter_Main();
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mainAdapter);

        // inisialisasi si searchviewnya
        searchView = findViewById(R.id.am_sv);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for(Manga_Search search : mangaSearch){
                    if(search.getMangaTitle() != null){
                        if(search.getMangaTitle().equals(query)){
                            Intent intent = new Intent(getApplicationContext(), Manga_List.class);
                            intent.putExtra("ID", search.getMangaID());
                            startActivity(intent);
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(@NonNull String newText) {
                //search manga here

                if (newText.length() > 2) {
                    mangaList3.clear();
                    for (Manga_Search search : mangaSearch) {
                        String alias = search.getMangaTitle().toLowerCase().trim();
                        alias = alias.replace("*","");
                        alias = alias.replace("+","");
                        alias = alias.replace("/","");
                        alias = alias.replace("!","");
                        alias = alias.replace(":","");
                        alias = alias.replace("?","");
                        alias = alias.replace("-","");
                        alias = alias.replace("[","");
                        alias = alias.replace("]","");
                        alias = alias.replace("@","");
                        alias = alias.replace("(","");
                        alias = alias.replace(")","");
                        alias = alias.replace("_","");
                        String cmpStr=newText.toLowerCase().trim();
                        if(alias!=null &&! alias.isEmpty()){
                            if (alias.contains(cmpStr)){
                                mangaList3.add(search.getMangaTitle());
                                Log.d("RAG",search.getMangaTitle());
                            }
                        }
                    }
                    sortMangaList(mangaList3);
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener(){
            @Override
            public void onSearchViewShown() {
                Log.d("Tag", "searchView shown");
            }

            @Override
            public void onSearchViewClosed() {
                Log.d("Tag", "searchView is closed");
            }
        });

        // setVisibility dari LinearLayout previous, karena untuk page 1 kan pertama, jadi
        // belum butuh "previous"
        previousButton = findViewById(R.id.ambn_ll_previous);
        nextButton = findViewById(R.id.ambn_ll_next);
        previousButton.setVisibility(View.GONE);

        // setOnClickListener dari LinearLayout previous dan next
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if(currentPage<=30 && currentPage>0){
                    volleyRequest(currentPage--);
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if(currentPage<30 && currentPage>=0){
                    volleyRequest(currentPage++);
                }
            }
        });

        // set si drawer kiri nya
        drawerLayout = findViewById(R.id.am_dl);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.am_nv);
        navigationView.setNavigationItemSelectedListener(this);
        try{
            navigationView.getMenu().getItem(categoryPreferences.getInt("ID", 0)).setChecked(true);
        }
        catch (Exception e){
            try {
                navigationView.getMenu().getItem(2).setChecked(true);
            }catch (Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu, menu);
       // MenuItem item = menu.findItem(R.id.action_search);
        //searchView.setMenuItem(item);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed(){
        if(searchView.isSearchOpen() == true){
            searchView.closeSearch();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();
        switch (id){
            case R.id.all:
                searchEditor.putString("CAT","All");
                searchEditor.putInt("ID",0);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.action:
                searchEditor.putString("CAT","Action");
                searchEditor.putInt("ID",1);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.adult:
                searchEditor.putString("CAT","Adult");
                searchEditor.putInt("ID",2);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.adventure:
                searchEditor.putString("CAT","Adventure");
                searchEditor.putInt("ID",3);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.comedy:
                searchEditor.putString("CAT","Comedy");
                searchEditor.putInt("ID",4);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.drama:
                searchEditor.putString("CAT","Drama");
                searchEditor.putInt("ID",5);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.mature:
                searchEditor.putString("CAT","Mature");
                searchEditor.putInt("ID",6);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.school:
                searchEditor.putString("CAT","School Life");
                searchEditor.putInt("ID",7);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.supernatural:
                searchEditor.putString("CAT","Supernatural");
                searchEditor.putInt("ID",8);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.yuri:
                searchEditor.putString("CAT","Yuri");
                searchEditor.putInt("ID",9);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.romance:
                searchEditor.putString("CAT","Romance");
                searchEditor.putInt("ID",10);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.sci:
                searchEditor.putString("CAT","Sci-fi");
                searchEditor.putInt("ID",11);
                searchEditor.apply();
                showcategoryData();
                break;
            case R.id.sports:
                searchEditor.putString("CAT","Sports");
                searchEditor.putInt("ID",12);
                searchEditor.apply();
                showcategoryData();
                break;
            default:
                searchEditor.putString("CAT","All");
                searchEditor.putInt("ID",13);
                searchEditor.apply();
                showcategoryData();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * method ini untuk sort si array lalu dimasukkan ke MaterialSearchView
     */
    private void sortMangaList(@NonNull List<String> list){
        String[] sorted = new String[list.size()];
        sorted = list.toArray(sorted);
        searchView.setSuggestions(sorted);
    }

    private void volleyRequest(final int i){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, BASE_URL + "?p=" + i, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(@NonNull JSONObject response) {
                        if(i == 0){
                            if(previousButton.getVisibility()==View.VISIBLE){
                                previousButton.setVisibility(View.GONE);
                            }
                        }
                        if(i == 30){
                            if(nextButton.getVisibility()==View.VISIBLE){
                                nextButton.setVisibility(View.GONE);
                            }
                        }
                        else{
                            if(nextButton.getVisibility()==View.GONE){
                                nextButton.setVisibility(View.VISIBLE);
                            }
                        }
                        if(i == 0){
                            if(previousButton.getVisibility()==View.VISIBLE){
                                previousButton.setVisibility(View.GONE);
                            }

                        }
                        else{
                            if(previousButton.getVisibility()==View.GONE){
                                previousButton.setVisibility(View.VISIBLE);
                            }
                        }
                        Gson gson = new Gson();
                        Manga_Model model;
                        model = gson.fromJson(response.toString(), Manga_Model.class);
                        mangaList = model.getManga();
                        mangaList2 = model.getManga();
                        showcategoryData();
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        // TODO Auto-generated method stub
                        View view = findViewById(R.id.am_cl_main);
                        Log.d("TAG",error.toString());
                        Snackbar.make(view,"Check internet connection or refresh",Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                volleyRequest(0);
                            }
                        }).show();
                        progressDialog.dismiss();
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void showcategoryData(){
        String category = categoryPreferences.getString("CAT","All");
        if(category.equals("All")){
            mainAdapter.setArrayList(mangaList2);
            mainAdapter.notifyDataSetChanged();
        }
        else{
            ArrayList<Manga_List> news = new ArrayList<>();
            for (Manga_List list:mangaList){
                for(String s : list.getC()){
                    if(s.equals(category)){
                        news.add(list);
                    }
                }
            }
            mainAdapter.setArrayList(news);
            mainAdapter.notifyDataSetChanged();
        }
    }

    private void searchVolleyRequest(){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, BASE_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(@NonNull JSONObject response) {
                        InputStream inputStream=new ByteArrayInputStream(response.toString().getBytes());
                        try {
                            Reader streamReader=new InputStreamReader(inputStream,"UTF-8");
                            JsonReader reader = new JsonReader(streamReader);
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String name = reader.nextName();
                                if (name.equals("manga")) {
                                    reader.beginArray();
                                    while (reader.hasNext()) {
                                        Manga_Search searchManga = new Manga_Search();
                                        reader.beginObject();
                                        while (reader.hasNext()) {
                                            String title = reader.nextName();
                                            if (title.equals("t")) {
                                                String arc = reader.nextString();
                                                searchManga.setMangaTitle(arc);
                                            }
                                            else if(title.equals("i")){
                                                String mangaID = reader.nextString();
                                                searchManga.setMangaID(mangaID);
                                            }
                                            else if(title.equals("im") && reader.peek()!=JsonToken.NULL) {
                                                String mangaPic = reader.nextString();
                                                searchManga.setMangaIMG(mangaPic);
                                            }
                                            else {
                                                reader.skipValue();
                                            }
                                        }
                                        reader.endObject();
                                        if(searchManga.getMangaTitle() != null){
                                            mangaSearch.add(searchManga);
                                        }
                                    }
                                    reader.endArray();
                                }else {
                                    reader.skipValue(); // avoid some unhandle events
                                }
                            }
                            reader.endObject();
                            reader.close();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(@NonNull VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("TAG1",error.toString());
                        Toast.makeText(MainActivity.this, "error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }
}