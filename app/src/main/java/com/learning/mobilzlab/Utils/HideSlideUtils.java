package com.learning.mobilzlab.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.learning.mobilzlab.R;

public class HideSlideUtils {

    private static final int ANIMATION_DURATION = 512;

    private View toHideView;
    private ImageView hideIcon;
    private View clickListener;

    private boolean opened;

    private Drawable SHOW_OPEN;
    private Drawable SHOW_CLOSE;

    private Activity homeScreen;

    public HideSlideUtils(View toHideView, ImageView hideIcon, View clickListener, Activity homeScreen, boolean opened) {

        this.toHideView = toHideView;
        this.hideIcon = hideIcon;
        this.clickListener = clickListener;
        this.homeScreen = homeScreen;
        this.opened = opened;

        initializeComponents();
        initializeListeners();

    }

    private void initializeComponents() {

        this.SHOW_OPEN = ContextCompat.getDrawable(homeScreen, R.drawable.ic_baseline_arrow_drop_up_24);
        this.SHOW_CLOSE = ContextCompat.getDrawable(homeScreen, R.drawable.ic_baseline_arrow_drop_down_24);
    }

    private void initializeListeners() {

        clickListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (opened) {
                    closeView();
                    opened = false;
                } else {
                    openView();
                    opened = true;

                }

            }
        });
    }

    private void openView() {

        toHideView.setAlpha(0f);

        toHideView.setVisibility(View.VISIBLE);
        hideIcon.setImageDrawable(SHOW_CLOSE);

        toHideView.animate()
                .alpha(1f)
                .setDuration(ANIMATION_DURATION);


    }

    private void closeView() {

        toHideView.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {


                    @Override
                    public void onAnimationEnd(Animator animation) {

                        if (!opened) {

                            toHideView.setVisibility(View.GONE);
                            hideIcon.setImageDrawable(SHOW_OPEN);
                        }
                    }
                });


    }


}
