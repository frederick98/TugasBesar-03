package com.example.tugasbesar_03.mangaModel;

import java.util.ArrayList;

/*
    kelas ini memodelkan API yang akan diterima oleh WebService Mangaeden, sehingga SETIAP ATRIBUT
    yang dibuat harus sesuai dengan WebService Mangaeden, kalau tidak akan null
 */
public class Manga_Picture {
    protected ArrayList<ArrayList<Object>> images;       // atribut ini untuk load seluruh isi (gambar) dari manga berdasarkan chapternya

    public ArrayList<ArrayList<Object>> getImages() {
        return images;
    }

    public void setImages(ArrayList<ArrayList<Object>> images) {
        this.images = images;
    }
}
