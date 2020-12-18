package com.learning.mobilzlab.Models.UtilModels;

import android.graphics.drawable.Drawable;

public class Brand {


    private String brandName;
    private Drawable brandImage;

    public Brand() {
    }

    public Brand(String brandName, Drawable brandImage) {
        this.brandName = brandName;
        this.brandImage = brandImage;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Drawable getBrandImage() {
        return brandImage;
    }

    public void setBrandImage(Drawable brandImage) {
        this.brandImage = brandImage;
    }
}
