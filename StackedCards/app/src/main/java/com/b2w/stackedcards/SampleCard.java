package com.b2w.stackedcards;

import android.content.Context;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cavedon on 11/10/14.
 */
public class SampleCard extends CardView implements View.OnClickListener, View.OnTouchListener {

    private static final int PIXELS_PER_SECOND = 1000;
    private static final int UNDEFINED = -1;

    private int mActivePointerId = UNDEFINED;
    private int mTouchSlop;
    private int mCardPosition = -1;

    private float mPositionY = 0;
    private float mLastTouchY = 0;
    private float mOriginalY = 0;
    private float mContainerOriginalHeight = 0;

    private VelocityTracker mVelocityTracker;
    private TextView mTextView;

    private SampleCard previous;
    private SampleCard next;

    public SampleCard(Context context) {
        this(context, null);
    }

    public SampleCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_card, this, true);

        mTextView = (TextView) findViewById(R.id.card_text_view);
        setRadius(4.f);

        ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
        setOnTouchListener(this);

    }

    public SampleCard(MainActivity context, int position) {
        this(context);
        this.mCardPosition = position;
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setPrevious(SampleCard card) {
        this.previous = card;
    }

    public void setNext(SampleCard card) {
        this.next = card;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        setTranslationY(-200 * mCardPosition);

        mOriginalY = getY();
        mContainerOriginalHeight = ((ViewGroup) getParent()).getHeight();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }

                mActivePointerId = event.getPointerId(0);
                mLastTouchY = event.getY(mActivePointerId);

                if (mPositionY == 0) {
                    mPositionY = view.getY();
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                int pointerId = event.getPointerId(pointerIndex);

                if (pointerId == mActivePointerId) {
                    int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndexMove = event.findPointerIndex(mActivePointerId);
                float yDelta = event.getY(pointerIndexMove) - mLastTouchY;

                mVelocityTracker.addMovement(event);
                mVelocityTracker.computeCurrentVelocity(PIXELS_PER_SECOND);

                float yVelocity = VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerIndexMove);

                if (Math.abs(yVelocity) > mTouchSlop) {
                    mPositionY += (yDelta) * (1 + (0.21 * mCardPosition));

                    if (mPositionY > mOriginalY) {
                        view.setY(mPositionY);
                    }
                }

                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = UNDEFINED;
                break;
            }
        }
        if (previous != null) {
            previous.setNext(null);
            previous.onTouch(previous, event);
            previous.setNext(this);
        }
        if (next != null) {
            next.setPrevious(null);
            next.onTouch(next, event);
            next.setPrevious(this);
        }
        ((ViewGroup) getParent()).invalidate();
        return true;
    }
}
