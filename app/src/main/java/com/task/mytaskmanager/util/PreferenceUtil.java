package com.task.mytaskmanager.util;

import android.content.Context;

/**
 * Utils class to save/retrieve key/value pairs to and from SharedPreferences.
 *
 * @author rameshlaavu
 */
public class PreferenceUtil {

    private static PreferenceUtil ourInstance = new PreferenceUtil();

    private PreferenceUtil() {
    }

    public static PreferenceUtil getInstance() {
        return ourInstance;
    }

    /**
     * Saves int value at given key to shared preferences.
     *
     * @param context
     * @param key
     * @param value
     */
    public void saveInt(Context context, String key, int value) {
        if (context != null) {
            context.getSharedPreferences(ProjectVariables.PREFERENCES_NAME, Context.MODE_PRIVATE).edit().putInt(key, value).commit();
        }
    }

    /**
     * Returns the int value stored at given key in shared preferences.
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return int value if existed, defaultValue otherwise.
     */
    public int getInt(Context context, String key, int defaultValue) {
        if (context != null) {
            return context.getSharedPreferences(ProjectVariables.PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * Saves long value at given key to shared preferences.
     *
     * @param context
     * @param key
     * @param value
     */
    public void saveLong(Context context, String key, long value) {
        if (context != null) {
            context.getSharedPreferences(ProjectVariables.PREFERENCES_NAME, Context.MODE_PRIVATE).edit().putLong(key, value).commit();
        }
    }

    /**
     * Returns long value stored at given key in shared preferences.
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return long value if existed, defaultValue otherwise.
     */
    public long getLong(Context context, String key, long defaultValue) {
        if (context != null) {
            return context.getSharedPreferences(ProjectVariables.PREFERENCES_NAME, Context.MODE_PRIVATE).getLong(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * Saves String value at given key to shared preferences.
     *
     * @param context
     * @param key
     * @param value
     */
    public void saveString(Context context, String key, String value) {
        if (context != null) {
            context.getSharedPreferences(ProjectVariables.PREFERENCES_NAME, Context.MODE_PRIVATE).edit().putString(key, value).commit();
        }
    }

    /**
     * Returns the String value stored at given key in shared preferences.
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return String value if existed, defaultValue otherwise.
     */
    public String getString(Context context, String key, String defaultValue) {
        if (context != null) {
            return context.getSharedPreferences(ProjectVariables.PREFERENCES_NAME, Context.MODE_PRIVATE).getString(key, defaultValue);
        }
        return defaultValue;
    }

    /**
     * Saves boolean value at given key to shared preferences.
     *
     * @param context
     * @param key
     * @param value
     */
    public void saveBoolean(Context context, String key, boolean value) {
        if (context != null) {
            context.getSharedPreferences(ProjectVariables.PREFERENCES_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
        }
    }

    /**
     * Returns the boolean value stored at given key in shared preferences.
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return boolean value if existed, defaultValue otherwise.
     */
    public boolean getBoolean(Context context, String key, boolean defaultValue) {
        if (context != null) {
            return context.getSharedPreferences(ProjectVariables.PREFERENCES_NAME, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
        }
        return defaultValue;
    }


    /**
     * Clears the saved shared preferences.
     *
     * @param context
     */
    public void clearSharedPreferences(Context context) {
        context.getSharedPreferences(ProjectVariables.PREFERENCES_NAME, Context.MODE_PRIVATE).edit().clear().commit();
    }

}
