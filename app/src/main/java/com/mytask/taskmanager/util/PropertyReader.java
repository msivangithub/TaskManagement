package com.mytask.taskmanager.util;

/**
 * Created by NEWSYSTEM1 on 5/30/2016.
 */

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

/**
 * Property File Reader.
 */
public class PropertyReader {

    private Context context;
    private Properties properties;

    /**
     * Constructor.
     *
     * @param context application context.
     */
    public PropertyReader(Context context) {
        this.context = context;
        properties = new Properties();
    }

    /**
     * Get Property from resouce folder.
     *
     * @param resId Resource Id.
     * @return property object.
     */
    public Properties getMyProperties(int resId) {
        try {
            InputStream inputStream = context.getResources().openRawResource(resId);
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return properties;
    }
}
