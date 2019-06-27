package Utility;

public class GridCalculator {

    public static int determineRows(int numPhotos) {
        double nTimes = numPhotos / 3;
        double remaining = numPhotos % 3;

        if (remaining != 0.0) {
            nTimes++;
        }

        return (int) Math.floor(nTimes);
    }

    public static int determineCols(int rows) {
        double calculate = rows % 3;
        double cols = 3 - calculate;

        if (cols == 0) {
            return 3;
        }

        return (int) cols;
    }
}
