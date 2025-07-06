package com.tkchy.animatednav;



import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AnimatedBottomNav extends BottomNavigationView {

    private int animationType = 0;

    public AnimatedBottomNav(Context context) {
        super(context);
        init(null);
    }

    public AnimatedBottomNav(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AnimatedBottomNav(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AnimatedBottomNav);
            animationType = ta.getInt(R.styleable.AnimatedBottomNav_animationType, 0);
            ta.recycle();
        }

        setOnNavigationItemSelectedListener(item -> {
            View view = findViewById(item.getItemId());
            animateView(view);
            return true;
        });
    }

    private void animateView(View view) {
        if (view == null) return;

        Animation animation;
        switch (animationType) {
            case 1:
                animation = new ScaleAnimation(1f, 1.3f, 1f, 1.3f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(200);
                break;
            case 2:
                animation = new RotateAnimation(0f, 360f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(500);
                break;
            case 3:
                animation = new AlphaAnimation(0.3f, 1f);
                animation.setDuration(300);
                break;
            case 0:
            default:
                animation = new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(300);
                animation.setInterpolator(new BounceInterpolator());
                break;
        }

        view.startAnimation(animation);
    }
}

