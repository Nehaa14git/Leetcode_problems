class Solution {
    public int closedIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        // 1) Flood-fill border-connected land.
        Deque<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if ((i == 0 || i == m - 1 || j == 0 || j == n - 1) && grid[i][j] == 0) {
                    grid[i][j] = 1;
                    q.offer(new int[]{i, j});
                }
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];
            for (int[] d : dirs) {
                int nx = x + d[0], ny = y + d[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == 0) {
                    grid[nx][ny] = 1;
                    q.offer(new int[]{nx, ny});
                }
            }
        }

        // 2) Count remaining islands.
        int count = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (grid[i][j] == 0) {
                    count++;
                    grid[i][j] = 1;
                    q.offer(new int[]{i, j});
                    while (!q.isEmpty()) {
                        int[] cur = q.poll();
                        int x = cur[0], y = cur[1];
                        for (int[] d : dirs) {
                            int nx = x + d[0], ny = y + d[1];
                            if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == 0) {
                                grid[nx][ny] = 1;
                                q.offer(new int[]{nx, ny});
                            }
                        }
                    }
                }
        return count;
    }
}