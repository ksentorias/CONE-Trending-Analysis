package com.trendinganalysis.conetrading;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Created by Paul on 12/7/2015.
 */
public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void onSwipeLeft() {
        System.out.println("KANI KAI BOTTOM SLIDE NI SIYA");
    }

    public void onSwipeRight() {
        System.out.println("TOP SLIDE NI SIYA");
    }

    private void onSwipeBottom() {
    }

    private void onSwipeUp() {
    }

    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceY > 0)
                    onSwipeLeft();
//                else if(distanceY > 0)
//                    onSwipeRight();
//                else if(distanceX > 0)
//                   // onSwipeLeft();
                else
                    onSwipeRight();

                return true;
            }
            return false;
        }
    }


}