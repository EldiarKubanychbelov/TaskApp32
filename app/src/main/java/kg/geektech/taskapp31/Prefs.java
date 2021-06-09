package kg.geektech.taskapp31;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);

    }
    public void saveBoardsState(){
        preferences.edit().putBoolean("isShown",true).apply();

    }

    public boolean isShown(){
        return preferences.getBoolean("isShown",false);
    }


    public void clearPref(){
        preferences.edit().clear().commit();
    }
    public void clearEditText(){
        preferences.edit().remove(getString("autoSave"));
    }


    public void putString(String autoSave, String s) {
        preferences.edit().putString(autoSave, s).apply();
    }

    public String getString(String autoSave){
        return preferences.getString(autoSave, "");
    }
}
