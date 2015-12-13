package me.ziem.slider.provider;

public class CircularPageIndexProvider implements PageIndexProvider {
    private int mCurrentAddValue = 1;

    @Override
    public int getNextPageIndex(int currentPageIndex, int pageCount) {
        int nextPageIndex = currentPageIndex;

        if ((nextPageIndex == (pageCount - 1)) && mCurrentAddValue == 1) {
            mCurrentAddValue = -mCurrentAddValue;
        }

        if ((nextPageIndex == 0) && mCurrentAddValue == -1) {
            mCurrentAddValue = -mCurrentAddValue;
        }

        nextPageIndex += mCurrentAddValue;

        return nextPageIndex;
    }
}
