package com.example.tugasbesar_03.mangaModel;

import java.util.ArrayList;

/*
    kelas ini mengimplementasikan detil manga yang nanti dipanggil ke WebService Mangaeden, sehingga
    SETIAP ATRIBUT yang dibuat harus sesuai dengan WebService Mangaeden, kalau tidak akan null
 */
public class Manga_Chapter_Detail {
    protected String title;                     // buat title si manga nya
    protected int chapters_len;                 // buat panjang chapter manga nya
    protected ArrayList<String> categories;     // buat jenis kategori manga nya
    protected String author;                    // buat author manga nya
    protected String artist;                    // buat artist manga nya
    protected String image;                     // buat gambar manga nya
    protected String description;               // buat deskripsi manga nya
    protected int released;                     // buat kapan manga tsb dirilis
    protected double last_chapter_date;         // buat liat kapan terakhir chapter dirilis
    protected ArrayList<Object> chapters;       // buat list daftar chapter yg dimiliki si manga

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChapters_len() {
        return chapters_len;
    }

    public void setChapters_len(int chapters_len) {
        this.chapters_len = chapters_len;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleased() {
        return released;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    public double getLast_chapter_date() {
        return last_chapter_date;
    }

    public void setLast_chapter_date(double last_chapter_date) {
        this.last_chapter_date = last_chapter_date;
    }

    public ArrayList<Object> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<Object> chapters) {
        this.chapters = chapters;
    }
}