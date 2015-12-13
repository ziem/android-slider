package me.ziem.slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;

import me.ziem.slider.provider.CircularPageIndexProvider;
import me.ziem.slider.provider.PageIndexProvider;

public class Slider extends ViewPager {
    private static final String TAG = ViewPager.class.getName();

    private static final int DEFAULT_SCROLL_DELAY_VALUE = 4000;
    private int mScrollDelay = DEFAULT_SCROLL_DELAY_VALUE;

    private static final int DEFAULT_SCROLL_DURATION_VALUE = 2000;
    private int mScrollDuration = DEFAULT_SCROLL_DURATION_VALUE;

    private static final boolean DEFAULT_TOUCH_VALUE = true;
    private boolean mTouchEnabled = DEFAULT_TOUCH_VALUE;

    private static final boolean DEFAULT_STOP_SCROLLING_AFTER_TOUCH_VALUE = true;
    private boolean mStopScrollingAfterTouchEnabled = DEFAULT_STOP_SCROLLING_AFTER_TOUCH_VALUE;

    private Handler mHandler;
    private Runnable mRunnable;
    private PageIndexProvider mPageIndexProvider;
    private FixedDurationScroller mFixedDurationScroller;

    private int mCurrentPageIndex = 0;
    private boolean mAnimated = true;
    private boolean mStopped = true;
    private boolean mWasDragged = false;

    public Slider(Context context) {
        super(context);
        init(context, null);
    }

    public Slider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

        onAdapterSet(adapter);
    }

    private void onAdapterSet(PagerAdapter adapter) {
        if (adapter == null) {
            stop();
        }
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Slider, 0, 0);

            try {
                mTouchEnabled = a.getBoolean(R.styleable.Slider_touchEnabled, DEFAULT_TOUCH_VALUE);
                mStopScrollingAfterTouchEnabled = a.getBoolean(R.styleable.Slider_stopScrollingAfterTouchEnabled, DEFAULT_STOP_SCROLLING_AFTER_TOUCH_VALUE);
                mScrollDuration = a.getInteger(R.styleable.Slider_scrollDuration, DEFAULT_SCROLL_DURATION_VALUE);
                mScrollDelay = a.getInteger(R.styleable.Slider_scrollDelay, DEFAULT_SCROLL_DELAY_VALUE);
            } finally {
                a.recycle();
            }
        }

        initScroller();
        setPageIndexProvider(new CircularPageIndexProvider());

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                onNextPage();
            }
        };
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);

        if (mStopped) {
            return;
        }

        if ((mWasDragged || position != mCurrentPageIndex) && offset == 0 && offsetPixels == 0) {
            mHandler.postDelayed(mRunnable, mScrollDelay);
            mCurrentPageIndex = position;
            mWasDragged = false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mTouchEnabled) {
            if (mStopScrollingAfterTouchEnabled) {
                stop();
            } else {
                if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                    mWasDragged = true;
                }
            }

            return super.onInterceptTouchEvent(ev);
        }

        return mTouchEnabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mTouchEnabled) {
            return super.onTouchEvent(ev);
        }

        return mTouchEnabled;
    }

    private void onNextPage() {
        int nextPageIndex = calculateNextPageIndex();
        setCurrentItem(nextPageIndex, mAnimated);
    }

    private int calculateNextPageIndex() {
        return mPageIndexProvider.getNextPageIndex(mCurrentPageIndex, getAdapter().getCount());
    }

    public void start() {
        mHandler.postDelayed(mRunnable, mScrollDelay);
        mStopped = false;
    }

    public void stop() {
        mHandler.removeCallbacks(mRunnable);
        mStopped = true;
    }

    public int getScrollDelay() {
        return mScrollDelay;
    }

    public void setScrollDelay(int scrollDelay) {
        this.mScrollDelay = scrollDelay;
    }

    public int getScrollDuration() {
        return mScrollDuration;
    }

    public void setScrollDuration(int scrollDuration) {
        if (mScrollDuration != scrollDuration) {
            mScrollDuration = scrollDuration;
            if (mFixedDurationScroller == null) {
                initScroller();
            } else {
                mFixedDurationScroller.setScrollDuration(mScrollDuration);
            }
        }
    }

    private void initScroller() {
        try {
            Field scroller;
            scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);

            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mFixedDurationScroller = new FixedDurationScroller(getContext(), (Interpolator) interpolator.get(null));
            mFixedDurationScroller.setScrollDuration(mScrollDuration);

            scroller.set(this, mFixedDurationScroller);
        } catch (Exception e) {
            Log.e(TAG, "Unable to set scroller");
        }
    }

    public PageIndexProvider getPageIndexProvider() {
        return mPageIndexProvider;
    }

    public void setPageIndexProvider(PageIndexProvider pageIndexProvider) {
        mPageIndexProvider = pageIndexProvider;
    }

    public boolean isAnimated() {
        return mAnimated;
    }

    public void setAnimated(boolean mAnimated) {
        this.mAnimated = mAnimated;
    }

    public boolean isTouchEnabled() {
        return mTouchEnabled;
    }

    public void setTouchEnabled(boolean touchEnabled) {
        mTouchEnabled = touchEnabled;
    }

    public boolean isStopScrollingAfterTouchEnabled() {
        return mStopScrollingAfterTouchEnabled;
    }

    public void setStopScrollingAfterTouchEnabled(boolean stopScrollingAfterTouchEnabled) {
        mStopScrollingAfterTouchEnabled = stopScrollingAfterTouchEnabled;
    }
}
