package com.example.tugasbesar_03.mangaModel;

import java.util.ArrayList;

public class Manga_Chapter_Detail {
    protected String mangaTitle;
    protected int chapterLength;
    protected ArrayList<String> mangaCategories;
    protected String mangaAuthor;
    protected String mangaArtist;
    protected String mangaPicture;
    protected String mangaDescription;
    protected int mangaTimeReleased;
    protected double mangaLastChapterAddedDate;
    protected ArrayList<Object> mangaChapter;

    public Manga_Chapter_Detail() {
    }

    public String getMangaTitle() {
        return mangaTitle;
    }

    public void setMangaTitle(String mangaTitle) {
        this.mangaTitle = mangaTitle;
    }

    public int getChapterLength() {
        return chapterLength;
    }

    public void setChapterLength(int chapterLength) {
        this.chapterLength = chapterLength;
    }

    public ArrayList<String> getMangaCategories() {
        return mangaCategories;
    }

    public void setMangaCategories(ArrayList<String> mangaCategories) {
        this.mangaCategories = mangaCategories;
    }

    public String getMangaAuthor() {
        return mangaAuthor;
    }

    public void setMangaAuthor(String mangaAuthor) {
        this.mangaAuthor = mangaAuthor;
    }

    public String getMangaArtist() {
        return mangaArtist;
    }

    public void setMangaArtist(String mangaArtist) {
        this.mangaArtist = mangaArtist;
    }

    public String getMangaPicture() {
        return mangaPicture;
    }

    public void setMangaPicture(String mangaPicture) {
        this.mangaPicture = mangaPicture;
    }

    public String getMangaDescription() {
        return mangaDescription;
    }

    public void setMangaDescription(String mangaDescription) {
        this.mangaDescription = mangaDescription;
    }

    public int getMangaTimeReleased() {
        return mangaTimeReleased;
    }

    public void setMangaTimeReleased(int mangaTimeReleased) {
        this.mangaTimeReleased = mangaTimeReleased;
    }

    public double getMangaLastChapterAddedDate() {
        return mangaLastChapterAddedDate;
    }

    public void setMangaLastChapterAddedDate(double mangaLastChapterAddedDate) {
        this.mangaLastChapterAddedDate = mangaLastChapterAddedDate;
    }

    public ArrayList<Object> getMangaChapter() {
        return mangaChapter;
    }

    public void setMangaChapter(ArrayList<Object> mangaChapter) {
        this.mangaChapter = mangaChapter;
    }
}
