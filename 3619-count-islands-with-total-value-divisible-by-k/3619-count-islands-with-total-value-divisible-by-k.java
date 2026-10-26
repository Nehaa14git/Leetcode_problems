class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int ans = 0;

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        Deque<int[]> q = new ArrayDeque<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue;

                long sum = 0;

                sum += grid[i][j];
                grid[i][j] = 0; // mark visited
                q.offer(new int[]{i, j});

                while (!q.isEmpty()) {
                    int[] cur = q.poll();
                    int x = cur[0], y = cur[1];

                    for (int[] d : dirs) {
                        int nx = x + d[0];
                        int ny = y + d[1];

                        if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                        if (grid[nx][ny] == 0) continue;

                        sum += grid[nx][ny];
                        grid[nx][ny] = 0; // mark visited
                        q.offer(new int[]{nx, ny});
                    }
                }

                if (sum % k == 0) ans++;
            }
        }

        return ans;
    }
}