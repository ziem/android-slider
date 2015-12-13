package me.ziem.slider;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * {@link Scroller} implementation with custom scroll duration.
 */
class FixedDurationScroller extends Scroller {
    /**
     * Custom scroll duration
     */
    private int mScrollDuration;

    public FixedDurationScroller(Context context) {
        super(context);
    }

    public FixedDurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FixedDurationScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    /**
     * Gets scroll duration.
     *
     * @return Current scroll duration.
     */
    public int getScrollDuration() {
        return mScrollDuration;
    }

    /**
     * Changes scroll duration.
     *
     * @param scrollDuration FixedDurationScroller new scroll duration.
     *                       Best result are achieved when using values from 2000 to 6000.
     */
    public void setScrollDuration(int scrollDuration) {
        mScrollDuration = scrollDuration;
    }
}