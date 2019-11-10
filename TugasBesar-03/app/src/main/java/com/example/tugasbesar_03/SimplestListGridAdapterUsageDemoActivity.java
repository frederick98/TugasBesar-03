package com.example.tugasbesar_03;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SimplestListGridAdapterUsageDemoActivity extends Activity {

    private ListView listview;
    private ArrayList<Item> dataList;
    private SimplestDemoAdadpter listadapter;

    private final int MAX_CARDS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listview = new ListView(this);
        listview.setDivider(null);
        setContentView(listview);
        generateSomeDummyDataList();
        listadapter = new SimplestDemoAdadpter(getApplicationContext(),
                MAX_CARDS);
        listadapter.addItemsInGrid(dataList);
        addHeaderFooters();
        listview.setAdapter(listadapter);
    }

    private void addHeaderFooters() {
        final int cardSpacing = listadapter.getCardSpacing();

        // Header View
        View headerView = getHeaderFooterView("HEADER", cardSpacing);
        headerView.setPadding(cardSpacing, cardSpacing, cardSpacing, 0);
        listview.addHeaderView(headerView);

        // Footer View
        View footerView = getHeaderFooterView("FOOTER", cardSpacing);
        footerView.setPadding(cardSpacing, 0, cardSpacing, cardSpacing);
        listview.addFooterView(footerView);
    }

    private View getHeaderFooterView(String text, int cardSpacing) {
        // New footer/header view.
        View view = listadapter.getLayoutInflater().inflate(
                R.layout.simplest_header_footer_layout, null);

        // Header-Footer card sizing.
        View headerFooterView = view.findViewById(R.id.card_main_parent);
        int headerFooterWidth = listadapter.getDeviceWidth()
                - (2 * cardSpacing); // Left-right so *2
        int headerFooterHeight = listadapter.getCardWidth(MAX_CARDS);
        headerFooterView.getLayoutParams().width = headerFooterWidth;
        headerFooterView.getLayoutParams().height = headerFooterHeight;

        // Setting text value
        ((TextView) view.findViewById(R.id.name)).setText(text);
        return view;
    }

    private void generateSomeDummyDataList() {
        dataList = new ArrayList<Item>();
        // Generate some dummy data.
        for (int i = 0; i < 51; i++) {
            dataList.add(new Item(i));
        }
    }

}