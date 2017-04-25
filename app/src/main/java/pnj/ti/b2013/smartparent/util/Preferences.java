package pnj.ti.b2013.smartparent.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

/**
 * Created by badrinteractive on 3/19/16.
 */
public class Preferences {

    public static final String BASE_DIR = Environment.getExternalStorageDirectory() + "/iGrow/Surveyor";

    public static final String LOCALE = "locale";
    public static final String PROFILE = "profile";
    public static final String REPORTS = "reports";
    public static final String TOKEN = "token";
    public static final String GCM_TOKEN = "gcm_token";
    public static final String FCM_TOKEN = "fcm_token";

    private static final String DEFAULT_LOCALE = "en";

    private static Preferences instance;
    private SharedPreferences sharedPreferences;
    public int id = 0;

    private Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
    }

    public static Preferences getInstance(Context context) {
        if (instance == null) {
            synchronized (Preferences.class) {
                if (instance == null) {
                    instance = new Preferences(context);
                }
            }
        }
        return instance;
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void storeString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }


    public String getLocale() {
        return sharedPreferences.getString(LOCALE, DEFAULT_LOCALE);
    }



    public void storeNotifId(String key) {
        id++;
        sharedPreferences.edit().putInt(key, id).apply();
    }

    public int getNotifId(String key){
        return sharedPreferences.getInt(key,0);
    }

}
