package com.jagadish.tweebrjagadish;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JAGADISH on 7/19/2018.
 */

public class PreferenceUtil {

    public static String saveData(String key, String value , Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("Tweebr", Activity.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();

        return key;
    }

    public static String getData(String key, Context context){
        SharedPreferences prefs = context.getSharedPreferences("Tweebr", Activity.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void clearUser(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("Tweebr", Activity.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
    public static void removeKey(String key,Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences("Tweebr", Activity.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();

    }

    public static boolean hasKey(String key,Context context)
    {
        SharedPreferences sf = context.getSharedPreferences("Tweebr", Activity.MODE_PRIVATE);
       boolean ans= sf.contains(key);

       return ans;

    }
}
