import java.util.*;

public class Solution {

    // Static block (for quick testing or initialization)
    static {
        // This line runs trapRainWater() with an empty grid 100 times
        for (int i = 0; i < 100; i++) {
            trapRainWater(new int[1][0]);
        }
    }

    /**
     * Function to compute trapped rainwater in a 2D elevation map.
     * @param heightMap 2D grid representing heights.
     * @return Total units of trapped water.
     */
    public static int trapRainWater(int[][] heightMap) {
        int m = heightMap.length;
        if (m == 0) return 0; // handle empty input
        int n = heightMap[0].length;
        if (n == 0) return 0;

        int[][] vols = new int[m][n];
        // Initialize volume grid with original heights
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                vols[i][j] = heightMap[i][j];
            }
        }

        boolean upt = true;
        boolean init = true;

        // Update loop — propagates minimum boundary constraints inward
        while (upt) {
            upt = false;

            // Left-to-right and top-to-bottom pass
            for (int i = 1; i < m - 1; i++) {
                for (int j = 1; j < n - 1; j++) {
                    int val = Math.max(heightMap[i][j],
                            Math.min(vols[i - 1][j], vols[i][j - 1]));
                    if (init || vols[i][j] > val) {
                        vols[i][j] = val;
                        upt = true;
                    }
                }
            }

            init = false;

            // Right-to-left and bottom-to-top pass
            for (int i = m - 2; i >= 1; i--) {
                for (int j = n - 2; j >= 1; j--) {
                    int val = Math.max(heightMap[i][j],
                            Math.min(vols[i + 1][j], vols[i][j + 1]));
                    if (vols[i][j] > val) {
                        vols[i][j] = val;
                        upt = true;
                    }
                }
            }
        }

        // Calculate trapped water volume
        int res = 0;
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                res += Math.max(vols[i][j] - heightMap[i][j], 0);
            }
        }

        return res;
    }

    // Main method for quick local testing
    public static void main(String[] args) {
        int[][] heightMap = {
                {1, 4, 3, 1, 3, 2},
                {3, 2, 1, 3, 2, 4},
                {2, 3, 3, 2, 3, 1}
        };

        int result = trapRainWater(heightMap);
        System.out.println("Total trapped water: " + result);
    }
}
