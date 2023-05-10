package com.application.newsapplication;

import java.util.List;
import java.util.stream.Collectors;

public class NewsHelper {

    private static NewsHelper instance;

    private static List<News> newsList;

    private NewsHelper(List<News> news) {
        newsList = news;
    }

    public static NewsHelper instantiate(List<News> news) {
        if (instance == null) {
            instance = new NewsHelper(news);
        }
        return instance;
    }

    public static NewsHelper getInstance() {
        return instance;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    // Get all news in a category excluding the one provided
    public List<News> getNewsInCategory(String category, String title) {
        return newsList.stream()
                .filter(news -> news.getCategory().equals(category) && !news.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

}
