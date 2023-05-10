package com.application.newsapplication;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    RecyclerView topNewsRV;
    RecyclerView newsRV;

    TopNewsAdapter topNewsAdapter;
    RecyclerView.LayoutManager topNewsLayoutManager;
    NewsAdapter newsAdapter;
    RecyclerView.LayoutManager newsLayoutManager;

    ObjectMapper objectMapper = new ObjectMapper();
    List<News> allNewsList;
    List<News> topNewsList;
    List<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get news
        InputStream inputStream = getResources().openRawResource(R.raw.news);
        try {
            allNewsList = objectMapper.readValue(inputStream, new TypeReference<List<News>>() {});
            NewsHelper.instantiate(allNewsList);
            topNewsList = getTopNews();
            newsList = getNews();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set recycler views
        topNewsRV = findViewById(R.id.topNewsRecyclerView);
        topNewsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topNewsAdapter = new TopNewsAdapter(this, this, topNewsList);
        topNewsRV.setLayoutManager(topNewsLayoutManager);
        topNewsRV.setAdapter(topNewsAdapter);


        newsRV = findViewById(R.id.newsRecyclerView);
        newsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsAdapter = new NewsAdapter(this, this, newsList);
        newsRV.setLayoutManager(newsLayoutManager);
        newsRV.setAdapter(newsAdapter);
    }

    // Get top news (Just returns top 5 items in list)
    private List<News> getTopNews() {
        return allNewsList.subList(0, 5);
    }

    // Get all news excluding the top 5
    private List<News> getNews() {
        return allNewsList.subList(5, allNewsList.size());
    }

    public void addFragment(Fragment fragment) {
        // Get the fragment manager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the fragment to the layout
        fragmentTransaction.add(R.id.fragment_container_view, fragment);

        // Commit the transaction
        fragmentTransaction.commit();
    }

}