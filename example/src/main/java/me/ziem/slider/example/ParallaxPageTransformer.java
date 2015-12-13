package me.ziem.slider.example;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ParallaxPageTransformer implements ViewPager.PageTransformer {
    public void transformPage(View view, float position) {
        View dummyImageView = view.findViewById(R.id.image);
        int pageWidth = view.getWidth();

        if (position < -1) {
            ViewCompat.setAlpha(view, 1);

        } else if (position <= 1) {
            ViewCompat.setTranslationX(dummyImageView, -position * (pageWidth / 2));
        } else {
            ViewCompat.setAlpha(view, 1);
        }
    }
}