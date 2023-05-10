package com.application.newsapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class NewsFragment extends Fragment {

    private static final String ARG_PARAM1 = "news";
    private static final String ARG_PARAM2 = "relatedNews";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private News news;
    private List<News> relatedNews;
    private MainActivity activity;

    TopNewsAdapter topNewsAdapter;
    RecyclerView.LayoutManager topNewsLayoutManager;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(String news, String relatedNews) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, news);
        args.putString(ARG_PARAM2, relatedNews);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                news = objectMapper.readValue(getArguments().getString(ARG_PARAM1), News.class);
                relatedNews = objectMapper.readValue(getArguments().getString(ARG_PARAM2), new TypeReference<List<News>>() {});
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            activity = (MainActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        Context context = requireContext();

        // Set views
        TextView newsTitleTV = view.findViewById(R.id.fragmentNewsTitle);
        TextView newsCategoryTV = view.findViewById(R.id.fragmentNewsCategory);
        ImageView newsImageIV = view.findViewById(R.id.fragmentImageView);
        TextView newsDescriptionTV = view.findViewById(R.id.fragmentNewsDescription);
        Button backButton = view.findViewById(R.id.fragmentBackButton);

        newsTitleTV.setText(news.getTitle());
        newsCategoryTV.setText(news.getCategory());
        int imageResource = context.getResources()
                .getIdentifier("image_" + news.getImage_src(),
                        "drawable", context.getPackageName());
        newsImageIV.setImageResource(imageResource);
        newsDescriptionTV.setText(news.getDescription());

        // Set recycler view
        RecyclerView relatedNewsRV = view.findViewById(R.id.fragmentRecyclerView);
        topNewsLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        topNewsAdapter = new TopNewsAdapter(activity, context, relatedNews);
        relatedNewsRV.setLayoutManager(topNewsLayoutManager);
        relatedNewsRV.setAdapter(topNewsAdapter);

        backButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.remove(NewsFragment.this);
            fragmentTransaction.commit();
        });

        return view;
    }
}