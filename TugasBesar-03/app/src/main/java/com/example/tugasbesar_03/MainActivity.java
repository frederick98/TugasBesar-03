package com.example.tugasbesar_03;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.tugasbesar_03.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    GridView gridView;

    String[] numWord = {"1","2","3","4","5", "6","7","8","9","10"};

    int[] numberImage = {1,2,3,4,5,6,7,8,9,10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());


        gridView = findViewById(R.id.grid_view);

        MainAdapter adapter = new MainAdapter(MainActivity.this,numWord,numberImage);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"You Clicked"+numWord[+position],
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}