package com.hust.hoanglong.android9_longth;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.hust.hoanglong.android9_longth.nodeplayers.NotePlayer;
import com.hust.hoanglong.android9_longth.touches.Touch;
import com.hust.hoanglong.android9_longth.touches.TouchAction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView white1, white2, white3, white4, white5, white6, white7;
    private ImageView black1, black2, black3, black4, black5;
    private List<ImageView> listBlacks = new ArrayList<>();
    private List<ImageView> listWhites = new ArrayList<>();
    private List<TouchInfo> touchInfoList = new ArrayList<>();
    private static final String TAG = MainActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        white1 = (ImageView) findViewById(R.id.white1);
        white2 = (ImageView) findViewById(R.id.white2);
        white3 = (ImageView) findViewById(R.id.white3);
        white4 = (ImageView) findViewById(R.id.white4);
        white5 = (ImageView) findViewById(R.id.white5);
        white6 = (ImageView) findViewById(R.id.white6);
        white7 = (ImageView) findViewById(R.id.white7);
        black1 = (ImageView) findViewById(R.id.black1);
        black2 = (ImageView) findViewById(R.id.black2);
        black3 = (ImageView) findViewById(R.id.black3);
        black4 = (ImageView) findViewById(R.id.black4);
        black5 = (ImageView) findViewById(R.id.black5);

        listBlacks.add(black1);
        listBlacks.add(black2);
        listBlacks.add(black3);
        listBlacks.add(black4);
        listBlacks.add(black5);

        listWhites.add(white1);
        listWhites.add(white2);
        listWhites.add(white3);
        listWhites.add(white4);
        listWhites.add(white5);
        listWhites.add(white6);
        listWhites.add(white7);

        NotePlayer.loadSounds(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        List<Touch> touches = Touch.processEvent(event);
        Log.d(TAG, String.format("onTouchEvent: %s", touches));

        if (touches.size() == 0) {
            return false;
        }
        Touch firstTouch = touches.get(0);
        if (firstTouch.getAction() == TouchAction.DOWN) {
            ImageView pressedKey = findKeyByTouch(firstTouch);
            if (!isPressKey(pressedKey)) {
                //TODO: PLay node
                String node = pressedKey.getTag().toString();
                NotePlayer.play(node);
                touchInfoList.add(new TouchInfo(pressedKey, firstTouch));
            }
        } else if (firstTouch.getAction() == TouchAction.UP) {
            ImageView pressedKey = findKeyByTouch(firstTouch);
            Iterator<TouchInfo> touchInfoIterator = touchInfoList.iterator();
            while (touchInfoIterator.hasNext()) {
                TouchInfo touchInfo = touchInfoIterator.next();
                if (touchInfo.pressedKey == pressedKey) {
                    touchInfoIterator.remove();
                }
            }
        } else if (firstTouch.getAction() == TouchAction.MOVE) {
            for (Touch touch : touches) {
                ImageView pressedKey = findKeyByTouch(touch);
                Iterator<TouchInfo> touchInfoIterator = touchInfoList.iterator();

                while (touchInfoIterator.hasNext()) {
                    TouchInfo touchInfo = touchInfoIterator.next();
                    if (touchInfo.touch.equals(touch) && touchInfo.pressedKey != pressedKey) {
                        touchInfoIterator.remove();
                    }
                }

                if (!isPressKey(pressedKey)) {
                    String node = pressedKey.getTag().toString();
                    NotePlayer.play(node);
                    touchInfoList.add(new TouchInfo(pressedKey, touch));
                }
            }
        }
        updateKeyImage();
//        for(ImageView blackKey : listBlacks){
//            if(isInside(event.getX(),event.getY(),blackKey)){
//                Log.d(TAG, "onTouchEvent: " + blackKey.getTag());
//            }
//        }
//        for(ImageView whiteKey : listWhites){
//            if(isInside(event.getX(),event.getY(),whiteKey)){
//                Log.d(TAG, "onTouchEvent: " + whiteKey.getTag());
//            }
//        }
        return super.onTouchEvent(event);
    }

    private void updateKeyImage() {
        for (ImageView blackKey : listBlacks) {
            if (isPressKey(blackKey)) {
                blackKey.setImageResource(R.drawable.pressed_black_key);
            } else {
                blackKey.setImageResource(R.drawable.default_black_key);
            }
        }
        for (ImageView whiteKey : listWhites) {
            if (isPressKey(whiteKey)) {
                whiteKey.setImageResource(R.drawable.pressed_white_key);
            } else {
                whiteKey.setImageResource(R.drawable.default_white_key);
            }
        }
    }

    private ImageView findKeyByTouch(Touch touch) {
        for (ImageView blackKey : listBlacks) {
            if (touch.checkHit(blackKey)) {
                return blackKey;
            }
        }
        for (ImageView whileKey : listWhites) {
            if (touch.checkHit(whileKey)) {
                return whileKey;
            }
        }
        return null;
    }

    private boolean isPressKey(ImageView pressKey) {
        for (TouchInfo touchInfo : touchInfoList) {
            if (touchInfo.pressedKey == pressKey) {
                return true;
            }
        }
        return false;
    }

    //    public boolean isInside(float x,float y,View v){
//        int[] location = new int[2];
//        v.getLocationOnScreen(location);
//        int left = location[0];
//        int top = location[1];
//
//        int right = location[0] + v.getWidth();
//        int bottom = location[1] + v.getHeight();
//
//        return x < right && x > left && y > top && y < bottom;
//    }
    class TouchInfo {
        public ImageView pressedKey;
        public Touch touch;

        public TouchInfo(ImageView pressedKey, Touch touch) {
            this.pressedKey = pressedKey;
            this.touch = touch;
        }
    }
}
