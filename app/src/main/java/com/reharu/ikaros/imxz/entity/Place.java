package com.reharu.ikaros.imxz.entity;

/**
 * Created by Imxz on 2017/3/23.
 */

public class Place {
    private String title;
    private String name;

    private static String[] index = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};

    public Place(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Place{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static String[] getIndex() {
        return index;
    }

}
