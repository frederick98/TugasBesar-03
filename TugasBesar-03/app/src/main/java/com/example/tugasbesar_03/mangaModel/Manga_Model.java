package com.example.tugasbesar_03.mangaModel;

import java.util.ArrayList;

public class Manga_Model {
    protected int pageNum;
    protected int startPage;
    protected int endPage;
    protected int totalPage;
    protected ArrayList<Manga_List> manga;

    public Manga_Model(){

    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<Manga_List> getManga() {
        return manga;
    }

    public void setManga(ArrayList<Manga_List> manga) {
        this.manga = manga;
    }
}
