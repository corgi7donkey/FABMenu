package com.sjb.bupt.mylibrary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * Created by sjb on 2017/6/20.
 */

public class FloatingActionMenu extends ViewGroup implements View.OnClickListener {

    
    private FloatingActionButton add_fab;
    private boolean isOpened;

    private int distance = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
//    private int distance =10;

    private static String TAG = "fam";

    //展开的方向
    public static final int EXPAND_UP = 0;
    public static final int EXPAND_DOWN = 1;
    public static final int EXPAND_LEFT = 2;
    public static final int EXPAND_RIGHT = 3;
    private int mExpandDirection;
    private int addFabColor;
    private int addFabSrc;

    public FloatingActionMenu(Context context) {
        this(context, null);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs);
        initAddFab();
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionMenu);

        mExpandDirection = array.getInt(R.styleable.FloatingActionMenu_fab_expandDirection, EXPAND_DOWN);
        addFabColor = array.getColor(R.styleable.FloatingActionMenu_add_fab_color, Color.RED);
        addFabSrc = array.getResourceId(R.styleable.FloatingActionMenu_add_fab_src, R.drawable.ic_launcher);

        array.recycle();
    }

    private void initAddFab() {
        add_fab = new FloatingActionButton(getContext());
        add_fab.setImageResource(addFabSrc);
        add_fab.setBackgroundTintList(ColorStateList.valueOf(addFabColor));


        LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        addView(add_fab);
        add_fab.setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count = getChildCount();
        int width = 0, height = 0;

        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            Log.d(TAG, "onMeasure: viewdaxiao:"+view.getMeasuredHeight()+"..."+view.getMeasuredWidth());
            switch (mExpandDirection) {
                case EXPAND_DOWN:
                case EXPAND_UP:
                    width = Math.max(width, getChildAt(i).getMeasuredWidth());
                    if (i == count - 1) {
                        height += getChildAt(i).getMeasuredHeight();
                    } else {
                        height += getChildAt(i).getMeasuredHeight() + distance;
                    }
                    break;
                case EXPAND_LEFT:
                case EXPAND_RIGHT:
                    height = Math.max(height, getChildAt(i).getMeasuredHeight());
                    if (i == count - 1) {
                        width += getChildAt(i).getMeasuredWidth();
                    } else {
                        width += getChildAt(i).getMeasuredWidth() + distance;
                    }
                    break;
            }
            
            if (i != 0) {
                view.setVisibility(View.GONE);
            }
        }
        Log.d(TAG, "onMeasure:width.. " + width + ".height." + height);
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed) {
            layoutButton();
        }

    }

    private void layoutButton() {
        View view = getChildAt(0);
        int l = 0;
        int t = 0;
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();

        int preHeight = 0;
        int preWidth = 0;
        switch (mExpandDirection) {
            case EXPAND_DOWN:

                for (int i = 0; i < getChildCount(); i++) {
//            view.layout(l,t,l+width,t+height);
                    getChildAt(i).layout(l, t, l + width, t + height);
                    preHeight = getChildAt(i).getMeasuredHeight();
                    width=getChildAt(i).getMeasuredWidth();
                    height=getChildAt(i).getMeasuredHeight();
                    t = t + preHeight + distance;
                }
                break;
            case EXPAND_RIGHT:

                for (int i = 0; i < getChildCount(); i++) {
//            view.layout(l,t,l+width,t+height);
                    getChildAt(i).layout(l, t, l + width, t + height);
                    preWidth = getChildAt(i).getMeasuredWidth();
                    width=getChildAt(i).getMeasuredWidth();
                    height=getChildAt(i).getMeasuredHeight();
                    l = l + preWidth + distance;
                }
                break;
            case EXPAND_UP:
                for (int i = getChildCount()-1; i >=0 ; i--) {
//            view.layout(l,t,l+width,t+height);
                    width=getChildAt(i).getMeasuredWidth();
                    height=getChildAt(i).getMeasuredHeight();
                    getChildAt(i).layout(l, t, l + width, t + height);
                    preHeight = getChildAt(i).getMeasuredHeight();
                    t = t + preHeight + distance;
                }
                break;
            case EXPAND_LEFT:
                for (int i = getChildCount()-1; i >=0 ; i--) {
//            view.layout(l,t,l+width,t+height);
                    width=getChildAt(i).getMeasuredWidth();
                    height=getChildAt(i).getMeasuredHeight();
                    getChildAt(i).layout(l, t, l + width, t + height);
                    preWidth = getChildAt(i).getMeasuredWidth();
                    l = l + preWidth + distance;
                }
                break;
        }

    }

    @Override
    public void onClick(View v) {
        toggle();
    }

    private void toggle() {
        if (isOpened) {
            rotateView(add_fab, 270f, 0f, 200);
            closeMenu();
            isOpened = false;
        } else {
            rotateView(add_fab, 0f, 270f, 200);
            openMenu();
            isOpened = true;
        }
    }

    private void rotateView(View add_fab, float fromDegree, float toDegree, int duration) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegree, toDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        add_fab.startAnimation(rotateAnimation);
    }

    private void openMenu() {
        int count = getChildCount();
        int tem_width = add_fab.getMeasuredWidth();
        int tem_height = add_fab.getMeasuredHeight();

        for (int i = 1; i < count; i++) {
            final View view = getChildAt(i);
            view.setVisibility(View.VISIBLE);
//            view.getMeasuredWidth();
//            int from_height = (-tem_height - distance)*i;

            int fromX=0, toX=0, fromY=0, toY=0;

            switch (mExpandDirection) {
                case EXPAND_DOWN:
                    fromY=(-tem_height - distance)*i;
                    break;
                case EXPAND_UP:
                    fromY=(tem_height + distance)*i;
                    break;
                case EXPAND_RIGHT:
                    fromX=(-tem_width - distance)*i;
                    break;

                case EXPAND_LEFT:
                    fromX=(tem_width + distance)*i;
                    break;
            }

            openTranslateAnim(view, fromX, toX, fromY, toY);
             tem_width = getChildAt(i).getMeasuredWidth();
             tem_height = getChildAt(i).getMeasuredHeight();
        }

    }

    private void openTranslateAnim(View view, int fromX,int toX,int fromY,int toY) {
        //            AnimationSet animationSet = new AnimationSet(true);
//            animationSet.setInterpolator(new OvershootInterpolator(2F));

        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(300);
//            translateAnimation.setStartOffset((i * 100) / (count - 1));
//            translateAnimation.setStartOffset((i-1) * 5000);


        RotateAnimation rotateAnimation = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
//            animationSet.addAnimation(rotateAnimation);
//            animationSet.addAnimation(translateAnimation);
//            view.startAnimation(animationSet);
        view.startAnimation(translateAnimation);
    }

    private void closeMenu() {

        int count = getChildCount();
        int tem_width = add_fab.getMeasuredWidth();
        int tem_height = add_fab.getMeasuredHeight();

        for (int i = 1; i < count; i++) {
            final View view = getChildAt(i);
            view.setVisibility(View.VISIBLE);
//            view.getMeasuredWidth();
//            int from_height = (-tem_height - distance) * i;

            int fromX=0, toX=0, fromY=0, toY=0;

            switch (mExpandDirection) {
                case EXPAND_DOWN:
                    toY=(-tem_height - distance)*i;
                    break;
                case EXPAND_RIGHT:
                    toX=(-tem_width - distance)*i;
                    break;
                case EXPAND_UP:
                    toY=(tem_height + distance)*i;
                    break;
                case EXPAND_LEFT:
                    toX=(tem_width + distance)*i;
                    break;
            }


            closeTranslateAnim(view, fromX, toX, fromY, toY);
             tem_width = getChildAt(i).getMeasuredWidth();
             tem_height = getChildAt(i).getMeasuredHeight();
        }

    }

    private void closeTranslateAnim(final View view, int fromX,int toX,int fromY,int toY) {
//        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, from);
        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//            translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(300);
        view.startAnimation(translateAnimation);
    }
}
