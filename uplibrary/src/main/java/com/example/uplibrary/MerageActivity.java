package com.example.uplibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.MergeAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.uplibrary.adapter.BanerAdapter;
import com.example.uplibrary.adapter.GoodsAdapter;
import com.example.uplibrary.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MerageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MergeAdapter mergeAdapter;
    BanerAdapter banerAdapter;
    NewsAdapter newsAdapter;
    GoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merage);

        recyclerView = findViewById(R.id.recycler_view);
        initDatas();

    }

    private void initDatas() {

        List<String>banns = new ArrayList<>();
        banns.add("1");

        List<String>news = new ArrayList<>();
        news.add("1");
        news.add("2");
        news.add("3");

        List<String>goods = new ArrayList<>();
        for (int i=0;i<5;i++){
            goods.add(""+i);
        }
        banerAdapter = new BanerAdapter(R.layout.banner_layout,banns);
        newsAdapter = new NewsAdapter(R.layout.news_layout,news);
        goodsAdapter = new GoodsAdapter(R.layout.goods_layout,goods);

        mergeAdapter = new MergeAdapter(banerAdapter,newsAdapter,goodsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mergeAdapter);
    }
}
