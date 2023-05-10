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

public class TopNewsAdapter extends RecyclerView.Adapter<TopNewsAdapter.TopNewsViewHolder> {

    Context context;
    static List<News> topNews;
    private static MainActivity activity;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TopNewsAdapter(MainActivity activity, Context context, List<News> topNews) {
        TopNewsAdapter.activity = activity;
        this.context = context;
        this.topNews = topNews;
    }

    @NonNull
    @Override
    public TopNewsAdapter.TopNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout
        View view = LayoutInflater.from(context).inflate(R.layout.topnews_recyclerview_layout, parent, false);
        return new TopNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopNewsAdapter.TopNewsViewHolder holder, int position) {
        // Set the data
        holder.topNewsTitleTV.setText(topNews.get(position).getTitle());
        int imageResource = context.getResources()
                .getIdentifier("image_" + topNews.get(position).getImage_src(),
                        "drawable", context.getPackageName());
        holder.topNewsIV.setImageResource(imageResource);
    }

    @Override
    public int getItemCount() {
        // Return the size of the list
        return topNews.size();
    }

    public static class TopNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView topNewsIV;
        TextView topNewsTitleTV;

        public TopNewsViewHolder(@NonNull View itemView) {
            // Initialize the views
            super(itemView);

            topNewsIV = itemView.findViewById(R.id.topNewsImage);
            topNewsTitleTV = itemView.findViewById(R.id.topNewsTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Handle the click event
            try {
                String news = objectMapper.writeValueAsString(topNews.get(getAdapterPosition()));
                String relatedNews = objectMapper.writeValueAsString(NewsHelper.getInstance()
                        .getNewsInCategory(topNews.get(getAdapterPosition()).getCategory(),
                                topNews.get(getAdapterPosition()).getTitle()));
                NewsFragment newsFragment = NewsFragment.newInstance(news, relatedNews);
                activity.addFragment(newsFragment);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

}
