package me.ziem.slider.provider;

public class DefaultPageIndexProvider implements PageIndexProvider {
    @Override
    public int getNextPageIndex(int currentPageIndex, int pageCount) {
        int nextPageIndex = currentPageIndex;

        nextPageIndex++;

        if (nextPageIndex >= pageCount) {
            nextPageIndex = 0;
        }

        return nextPageIndex;
    }
}
