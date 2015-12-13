package me.ziem.slider.provider;

import java.util.Random;

public class RandomPageIndexProvider implements PageIndexProvider {
    private Random mRandom;

    @Override
    public int getNextPageIndex(int currentPageIndex, int pageCount) {
        return randomNextPageIndex(pageCount);
    }

    private int randomNextPageIndex(int pageCount) {
        if (mRandom == null) {
            mRandom = new Random();
        }

        int min = 0;

        return mRandom.nextInt(pageCount - min) + min;
    }

}
