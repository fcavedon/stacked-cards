package com.b2w.stackedcards;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by Tarik Coelho on 12/11/2014.
 */
public class MyScrollView extends ScrollView {

    private int mTouchSlop;
    private boolean mIsScrolling;

    private float mLastY = 0;
    private float mStartY = 0;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mLastY = event.getY();
                mStartY = mLastY;

                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                mIsScrolling = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float y = event.getY();

                float yDeltaTotal = mStartY - y;

                if (Math.abs(yDeltaTotal) > mTouchSlop * 8) {
                    mIsScrolling = true;
                    mStartY = y;

                    return true;
                }
                break;
            }
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP: {
                mIsScrolling = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float y = event.getY();

                float yDeltaTotal = mStartY - y;

                if (!mIsScrolling && Math.abs(yDeltaTotal) > mTouchSlop) {
                    mIsScrolling = true;
                    mStartY = y;
                    yDeltaTotal = 0;
                }
                if (yDeltaTotal < 0)
                    yDeltaTotal = 0;

                if (mIsScrolling) {
                    scrollTo(0, (int) yDeltaTotal);
                }

                mLastY = y;
                break;
            }
        }

        return true;
    }
}
