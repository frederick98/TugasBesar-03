package com.example.tugasbesar_03.view.pinchToZoom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class ImageZoom extends ViewPager {
    private static final String TAG = "Zoom With ViewPager";
    protected static final String VIEWPAGER_OBJECT_TAG = "image #";
    private int previousPosition;
    private OnPageSelectedListener onPageSelectedListener;

    public ImageZoom(@NonNull Context context) {
        super(context);
        initialize();
    }

    public ImageZoom(Context context, AttributeSet attribute){
        super(context, attribute);
        initialize();
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ImageViewTouch) {
            if (((ImageViewTouch) v).getScale() == ((ImageViewTouch) v).getMinScale()) {
                return super.canScroll(v, checkV, dx, x, y);
            }
            return ((ImageViewTouch) v).canScroll(dx);
        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }

    protected void setOnPageSelectedListener(OnPageSelectedListener listener){
        onPageSelectedListener = listener;
    }

    private void initialize(){
        previousPosition = getCurrentItem();

        setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (onPageSelectedListener != null) {
                    onPageSelectedListener.selectedPage(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == SCROLL_STATE_SETTLING && previousPosition != getCurrentItem()) {
                    try {
                        ImageViewTouch imageViewTouch = (ImageViewTouch)
                                findViewWithTag(VIEWPAGER_OBJECT_TAG + getCurrentItem());
                        if (imageViewTouch != null) {
                            imageViewTouch.zoomTo(1f, 300);
                        }
                        previousPosition = getCurrentItem();
                    }
                    catch (ClassCastException ex) {
                        Log.e(TAG, "This view pager should have only ImageViewTouch as a children.", ex);
                    }
                }
            }
        });
    }
}