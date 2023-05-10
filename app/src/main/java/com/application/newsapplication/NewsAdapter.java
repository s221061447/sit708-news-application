package com.application.newsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    static List<News> news;
    private static MainActivity activity;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public NewsAdapter(MainActivity activity, Context context, List<News> news) {
        NewsAdapter.activity = activity;
        this.context = context;
        NewsAdapter.news = news;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        View view = LayoutInflater.from(context).inflate(R.layout.news_recyclerview_layout, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {
        // Set the data
        holder.newsTitleTV.setText(news.get(position).getTitle());
        System.out.println("image_" + news.get(position).getImage_src());
        int imageResource = context.getResources()
                .getIdentifier("image_" + news.get(position).getImage_src(),
                        "drawable", context.getPackageName());
        holder.newsIV.setImageResource(imageResource);
        holder.newsDescriptionTV.setText(news.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        // Return the size of the list
        return news.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView newsIV;
        TextView newsTitleTV;
        TextView newsDescriptionTV;

        public NewsViewHolder(@NonNull View itemView) {
            // Initialize the views
            super(itemView);

            newsIV = itemView.findViewById(R.id.newsImage);
            newsTitleTV = itemView.findViewById(R.id.newsTitle);
            newsDescriptionTV = itemView.findViewById(R.id.newsDescription);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Handle the click event
            System.out.println("Clicked on " + news.get(getAdapterPosition()).getTitle());
            try {
                String _news = objectMapper.writeValueAsString(news.get(getAdapterPosition()));
                String relatedNews = objectMapper.writeValueAsString(NewsHelper.getInstance()
                        .getNewsInCategory(news.get(getAdapterPosition()).getCategory(),
                                news.get(getAdapterPosition()).getTitle()));
                NewsFragment newsFragment = NewsFragment.newInstance(_news, relatedNews);
                activity.addFragment(newsFragment);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

}
