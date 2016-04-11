package com.example.usuario.ludiuca.clases;


import com.example.usuario.ludiuca.fragmentos.ContentFragment;
import android.support.v4.app.Fragment;

public class SamplePagerItem {
    private final CharSequence mTitle;
    private final int mIndicatorColor;
    private final int mDividerColor;

    public SamplePagerItem(CharSequence title, int indicatorColor, int dividerColor) {
        mTitle = title;
        mIndicatorColor = indicatorColor;
        mDividerColor = dividerColor;
    }

    public Fragment createFragment() {
        return ContentFragment.newInstance(mTitle, mIndicatorColor,
                mDividerColor);
    }


    public CharSequence getTitle() {
        return mTitle;
    }


    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    public int getDividerColor() {
        return mDividerColor;
    }
}