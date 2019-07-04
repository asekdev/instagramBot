package Utility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Utils {

    public static void wait(int numSeconds) {
        int min = 2000;
        int max = numSeconds * 1000;
        int range = max - min + 1;
        double randomWaitTime = (Math.random() * range) + min;
        long waitTime = (long) randomWaitTime;

        try {
            Thread.sleep(waitTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scrollWindowDown(WebDriver driver, int numPhotos) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        int lengthToScroll = numPhotos * 180;
        String scrollLength = String.valueOf(lengthToScroll);
        for (int i = 1; i < numPhotos / 2; i++) {
            Utils.wait(4);
            jse.executeScript("window.scrollBy(0," + scrollLength + ")", "");
        }
    }
}
