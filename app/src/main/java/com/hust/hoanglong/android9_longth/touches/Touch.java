package com.hust.hoanglong.android9_longth.touches;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

/**
 * Created by HoangLong on 6/14/2017.
 */

public class Touch {
    private float x;
    private float y;
    private TouchAction action;
    private int touchId;

    public Touch(float x, float y, TouchAction action, int touchId) {
        this.x = x;
        this.y = y;
        this.action = action;
        this.touchId = touchId;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public TouchAction getAction() {
        return action;
    }

    public int getTouchId() {
        return touchId;
    }

    public boolean checkHit(View v){
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];

        int right = location[0] + v.getWidth();
        int bottom = location[1] + v.getHeight();

        return x < right && x > left && y > top && y < bottom;
    }

    public static List<Touch> processEvent(MotionEvent motionEvent){
        ArrayList<Touch> touches = new ArrayList<>();
        int maskedAction = motionEvent.getActionMasked(); //Bit
        if(maskedAction == ACTION_DOWN || maskedAction == ACTION_POINTER_DOWN){
            Touch touch = getTouch(motionEvent,motionEvent.getActionIndex(),TouchAction.DOWN);
            touches.add(touch);
        }
        if(maskedAction == ACTION_UP || maskedAction == ACTION_POINTER_UP){
            Touch touch = getTouch(motionEvent,motionEvent.getActionIndex(),TouchAction.UP);
            touches.add(touch);
        }else if(maskedAction == ACTION_MOVE){
            for(int pointerIndex =0;pointerIndex<motionEvent.getPointerCount();pointerIndex++){
                Touch touch = getTouch(motionEvent,pointerIndex,TouchAction.MOVE);
                touches.add(touch);
            }
        }

        return touches;
    }
    @Override
    public String toString(){
        return "Touch{" +
                "x=" + x +
                ", y=" + y +
                ", action=" + action+
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        Touch other = (Touch) obj;
        return other.touchId == this.touchId;
    }

    private static Touch getTouch(MotionEvent motionEvent, int pointerIndex, TouchAction action){
        float x = motionEvent.getX(pointerIndex);
        float y = motionEvent.getY(pointerIndex);
        int id = motionEvent.getPointerId(pointerIndex);
        return new Touch(x,y,action,id);
    }
}
