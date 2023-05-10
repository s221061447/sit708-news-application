package com.application.newsapplication;

public class News {
    private String category;
    private String title;
    private String small_description;
    private String description;
    private String image_src;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSmall_description() {
        return small_description;
    }

    public void setSmall_description(String small_description) {
        this.small_description = small_description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }
}
