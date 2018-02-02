package com.example.smilinknight.pictureplay.helpers;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/*

FROM: https://gist.githubusercontent.com/nesquena/b2f023bb04190b2653c7/raw/06de9fa1509e0b4e2ee966841441ada62f1a5b5d/OnDoubleTapListener.java
AND FROM: https://gist.githubusercontent.com/nesquena/ed58f34791da00da9751/raw/eb3a0b2be08c82c3836096839e65e73b76a0bf84/OnSwipeTouchListener.java

Usage:

  myView.setOnTouchListener(new OnDoubleTapListener(this) {
    @Override
    public void onDoubleTap(MotionEvent e) {
      Toast.makeText(MainActivity.this, "Double Tap", Toast.LENGTH_SHORT).show();
    }
  });

*/
public class MegaGestureListener implements View.OnTouchListener {

    private GestureDetector gestureDetector;

    public MegaGestureListener(Context c) {
        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            MegaGestureListener.this.onDoubleTap(e);
            return super.onDoubleTap(e);
        }

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeDown();
                        } else {
                            onSwipeUp();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            MegaGestureListener.this.onLongPress(e);
            super.onLongPress(e);
        }
    }

    public void onLongPress(MotionEvent e) {
        // To be overridden when implementing listener
    }

    public void onDoubleTap(MotionEvent e) {
        // To be overridden when implementing listener
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeUp() {
    }

    public void onSwipeDown() {
    }

}