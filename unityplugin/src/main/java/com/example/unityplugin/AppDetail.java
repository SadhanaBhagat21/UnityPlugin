package com.example.unityplugin;

import android.graphics.drawable.Drawable;
public class AppDetail {
   public CharSequence label;
    public CharSequence name;
    //Drawable icon;
    public AppDetail(CharSequence label, CharSequence name) {
        this.label = label;
        this.name = name;
    }
    public AppDetail() {

    }
    public CharSequence getLabel() {
        return label;
    }
    public void setLabel(CharSequence label) {
        this.label = label;
    }
    public CharSequence getName() {
        return name;
    }
    public void setName(CharSequence name) {
        this.name = name;
    }
}
