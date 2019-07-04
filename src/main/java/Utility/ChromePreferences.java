package Utility;

import Engine.BotEngine;

import java.util.prefs.Preferences;

public class ChromePreferences {

    Preferences prefs = Preferences.systemNodeForPackage(ChromePreferences.class);

    public void setChromedriverPath(String path) {
        prefs.put("chromepath", path);
    }

    public String getChromedriverpath() {
        return prefs.get("chromepath","");
    }

    public void removeChromeDriver() {
        prefs.remove("chromepath");
    }
}
