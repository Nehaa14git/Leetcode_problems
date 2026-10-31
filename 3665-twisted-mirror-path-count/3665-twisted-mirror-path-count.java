class Solution {
    public int uniquePaths(int[][] grid) {
        final int MOD = 1_000_000_007;
        int m = grid.length, n = grid[0].length;
        if (m == 1 && n == 1) return 1;

        long[][] dp = new long[m][n];
        dp[0][0] = 1;
        long answer = 0;

        for (int s = 0; s <= m + n - 2; s++) {
            for (int x = 0; x < m; x++) {
                int y = s - x;
                if (y < 0 || y >= n) continue;

                long ways = dp[x][y];
                if (ways == 0) continue;

                // Right leg
                if (y + 1 < n) {
                    int cx = x, cy = y + 1, dir = 0;
                    while (cx < m && cy < n) {
                        if (cx == m - 1 && cy == n - 1) { answer = (answer + ways) % MOD; break; }
                        if (grid[cx][cy] == 0) { dp[cx][cy] = (dp[cx][cy] + ways) % MOD; break; }
                        dir = 1 - dir;
                        if (dir == 0) cy++; else cx++;
                    }
                }

                // Down leg
                if (x + 1 < m) {
                    int cx = x + 1, cy = y, dir = 1;
                    while (cx < m && cy < n) {
                        if (cx == m - 1 && cy == n - 1) { answer = (answer + ways) % MOD; break; }
                        if (grid[cx][cy] == 0) { dp[cx][cy] = (dp[cx][cy] + ways) % MOD; break; }
                        dir = 1 - dir;
                        if (dir == 0) cy++; else cx++;
                    }
                }
            }
        }

        return (int) answer;
    }
}