package com.akash.gosi.myriadinternchallenge;

/**
 * Created by Akash on 3/16/2015.
 */
public class MenuItems {
    private String titles[];
    private int icons[];
    private String name;
    private String email;


    MenuItems(String[] titles, int icons[], String name, String email){
        this.titles = titles;
        this.icons = icons;
        this.name = name;
        this.email = email;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public int[] getIcons() {
        return icons;
    }

    public void setIcons(int[] icons) {
        this.icons = icons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
