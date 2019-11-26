package com.example.tugasbesar_03.mangaModel;

import java.util.ArrayList;

/*
    kelas ini memodelkan API yang akan diterima oleh WebService Mangaeden, sehingga SETIAP ATRIBUT
    yang dibuat harus sesuai dengan WebService Mangaeden, kalau tidak akan null
 */
public class Manga_List {
    protected String im;            // Manga's Image
    protected String t;             // Manga's Title
    protected String i;             // Manga's ID
    protected String a;             // Manga's Alias
    protected String s;             // Manga's Status
    protected ArrayList<String> c;  // Manga's Category
    protected String ld;            // Manga's Last Chapter Update
    protected String h;             // Manga's Hits

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public ArrayList<String> getC() {
        return c;
    }

    public void setC(ArrayList<String> c) {
        this.c = c;
    }

    public String getLd() {
        return ld;
    }

    public void setLd(String ld) {
        this.ld = ld;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }
}