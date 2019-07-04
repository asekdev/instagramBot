package Utility;

import Engine.BotEngine;

import java.util.prefs.Preferences;

public class ChromePreferences {

    Preferences prefs = Preferences.userRoot().node(getClass().getName());
    private static ChromePreferences instance = null;

    private ChromePreferences() {}

    public static ChromePreferences getInstance() {
        //if no singleton has been initialised, create a new one
        if (instance == null) {
            instance = new ChromePreferences();
        }
        return instance;
    }

    public void setChromedriverPath(String path) {
        prefs.put("chromepath", path);
    }

    public String getChromedriverpath() {
        return prefs.get("chromepath", "");
    }

    public void resetChomeDriver() {
        prefs.remove("chromepath");
        prefs.put("chromepath", "");
    }
}
