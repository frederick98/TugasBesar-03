package com.example.tugasbesar_03;

public class Item {
    private int position;

    protected Item(int position) {
        this.position = position;
    }

    public String getPositionText() {
        return Integer.toString(position);
    }
}