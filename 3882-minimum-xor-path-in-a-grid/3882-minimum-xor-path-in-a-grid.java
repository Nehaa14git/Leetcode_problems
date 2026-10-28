class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;

        boolean[][] dp = new boolean[total][1024];

        // Start cell
        dp[0][grid[0][0]] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;

                int curId = i * n + j;
                int val = grid[i][j];

                if (i > 0) {
                    boolean[] up = dp[(i - 1) * n + j];
                    for (int x = 0; x < 1024; x++) {
                        if (up[x]) {
                            dp[curId][x ^ val] = true;
                        }
                    }
                }

                if (j > 0) {
                    boolean[] left = dp[i * n + (j - 1)];
                    for (int x = 0; x < 1024; x++) {
                        if (left[x]) {
                            dp[curId][x ^ val] = true;
                        }
                    }
                }
            }
        }

        boolean[] res = dp[(m - 1) * n + (n - 1)];
        for (int x = 0; x < 1024; x++) {
            if (res[x]) return x;
        }

        return -1;
    }
}