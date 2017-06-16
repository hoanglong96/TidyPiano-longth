package com.hust.hoanglong.android9_longth.nodeplayers;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by HoangLong on 6/14/2017.
 */

public class NotePlayer {
    // "c" => play sound_1.wav
    // "d" => play sound_3.wav
    public static HashMap<String,Integer> noteMap = new HashMap<>();
    public static SoundPool soundPool = new SoundPool(12, AudioManager.STREAM_MUSIC,1);
    public static void loadSounds(Context context){
        loadSound("c","sound_1",context);
        loadSound("cs","sound_2",context);
        loadSound("d","sound_3",context);
        loadSound("ds","sound_4",context);
        loadSound("e","sound_5",context);
        loadSound("f","sound_6",context);
        loadSound("fs","sound_7",context);
        loadSound("g","sound_8",context);
        loadSound("gs","sound_9",context);
        loadSound("a","sound_10",context);
        loadSound("as","sound_11",context);
        loadSound("b","sound_12",context);
    }

    private static int loadSound(String note,String fileName,Context context){
        int id = context.
                getResources().
                getIdentifier(fileName,"raw",context.getPackageName());
        int soundId = soundPool.load(context,id,1);
        //soundPool.load(context,id,1);
        noteMap.put(note,soundId); // "c" => id = 56464
        return id;
    }
    public static void play(String note){
        //node => id
        //id => play
        int soundId = noteMap.get(note);
        soundPool.play(soundId,1,1,1,0,1);
    }
}
