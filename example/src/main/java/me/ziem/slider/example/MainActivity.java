package me.ziem.slider.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.ziem.slider.Slider;

public class MainActivity extends ActionBarActivity {
    private Slider mSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the Slider with the slide adapter.
        mSlider = (Slider) findViewById(R.id.pager);
        mSlider.setOffscreenPageLimit(4);
        mSlider.setAdapter(new SlidePagerAdapter(getSupportFragmentManager()));
        mSlider.setScrollDuration(2000);
        mSlider.setScrollDelay(2000);
        mSlider.setTouchEnabled(true);
        mSlider.setStopScrollingAfterTouchEnabled(false);
        mSlider.setPageTransformer(true, new ParallaxPageTransformer());
        mSlider.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSlider != null) {
            mSlider.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mSlider != null) {
            mSlider.stop();
        }
    }

    public static class SlideFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        private int mSection = 0;

        public SlideFragment() {

        }

        public static SlideFragment newInstance(int sectionNumber) {
            SlideFragment fragment = new SlideFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getArguments() != null) {
                mSection = getArguments().getInt(ARG_SECTION_NUMBER);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
            TextView textView = (TextView) rootView.findViewById(R.id.text);

            int drawableId = -1;
            String text = "";

            switch (mSection) {
                case 0:
                    drawableId = R.drawable.slide1;
                    text = "Fly";
                    break;
                case 1:
                    drawableId = R.drawable.slide2;
                    text = "Funny insect";
                    break;
                case 2:
                    drawableId = R.drawable.slide3;
                    text = "Ants";
                    break;
                case 3:
                    drawableId = R.drawable.slide4;
                    text = "Spider";
                    break;
            }

            Bitmap slide = BitmapFactory.decodeResource(getResources(), drawableId);
            Palette palette = Palette.generate(slide);

            int textColor = palette.getLightMutedColor(R.color.white);
            int backgroundColor = getColorWithAlpha(palette.getDarkMutedColor(R.color.black), 180);

            imageView.setImageResource(drawableId);
            textView.setText(text);
            textView.setBackgroundColor(backgroundColor);
            textView.setTextColor(textColor);

            return rootView;
        }

        private int getColorWithAlpha(int color, int alpha) {
            return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        }
    }

    public class SlidePagerAdapter extends FragmentPagerAdapter {

        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SlideFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
