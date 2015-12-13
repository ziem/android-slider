package me.ziem.slider.provider;

public interface PageIndexProvider {
    int getNextPageIndex(int currentPageIndex, int pageCount);
}
