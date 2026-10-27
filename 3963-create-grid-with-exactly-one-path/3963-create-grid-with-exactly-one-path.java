class Solution {
    public String[] createGrid(int m, int n) {
        if (m == 1) {
            return new String[]{ ".".repeat(n) };
        }
        if (n == 1) {
            String[] res = new String[m];
            Arrays.fill(res, ".");
            return res;
        }

        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(grid[i], '#');
        }

        // Top row: go right until the second-to-last column.
        for (int j = 0; j < n - 1; j++) {
            grid[0][j] = '.';
        }

        // Second-to-last column: go down to the last row.
        for (int i = 0; i < m; i++) {
            grid[i][n - 2] = '.';
        }

        // Final step: move right to the bottom-right cell.
        grid[m - 1][n - 1] = '.';

        String[] res = new String[m];
        for (int i = 0; i < m; i++) {
            res[i] = new String(grid[i]);
        }
        return res;
    }
}