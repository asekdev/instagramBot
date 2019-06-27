package Utility;

public class Utils {

    public static void wait(int numSeconds) {
        int min = 2000;
        int max = numSeconds * 1000;
        int range = max - min + 1;
        double randomWaitTime = (Math.random() * range) + min;
        long waitTime = (long) randomWaitTime;

        try {
            Thread.sleep(waitTime);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
