package com.b2w.stackedcards;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by cavedon on 11/10/14.
 */
public class ExpandableLayout extends LinearLayout {

    boolean mIsScrolling;
    int mTouchSlop;
    int mChildCount;

    public ExpandableLayout(Context context) {
        this(context, null);
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mChildCount = getChildCount();
    }

}
