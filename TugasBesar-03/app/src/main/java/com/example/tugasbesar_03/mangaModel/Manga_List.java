package com.example.tugasbesar_03.mangaModel;

import java.util.ArrayList;

public class Manga_List {
    protected String IMAGE_URL;
    protected String title;
    protected String id;
    protected String alias;
    protected String status;
    protected ArrayList<String> category;
    protected String lastChapterDate;
    protected String hits;

    public Manga_List(){

    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setLastChapterDate(String lastChapterDate) {
        this.lastChapterDate = lastChapterDate;
    }

    public String getLastChapterDate() {
        return lastChapterDate;
    }

    public void setHits(String hits) {
        this.hits = hits;
    }

    public String getHits() {
        return hits;
    }
}
