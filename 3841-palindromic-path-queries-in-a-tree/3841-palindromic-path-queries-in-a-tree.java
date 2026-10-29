class Solution {
    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        // ---------- Build adjacency ----------
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        // ---------- Heavy-Light Decomposition ----------
        int[] parent = new int[n];
        int[] depth  = new int[n];
        int[] sz     = new int[n];
        int[] heavy  = new int[n];
        int[] head   = new int[n];
        int[] pos    = new int[n];
        Arrays.fill(parent, -1);
        Arrays.fill(heavy, -1);

        // Iterative BFS-style DFS for parent + depth + order
        int[] order = new int[n];
        int ordLen = 0;
        order[ordLen++] = 0;

        for (int i = 0; i < ordLen; i++) {
            int u = order[i];
            for (int v : adj[u]) {
                if (v == parent[u]) continue;
                parent[v] = u;
                depth[v] = depth[u] + 1;
                order[ordLen++] = v;
            }
        }

        // Subtree sizes and heavy child
        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];
            sz[u] = 1;
            int bestSize = 0;
            for (int v : adj[u]) {
                if (parent[v] != u) continue;
                sz[u] += sz[v];
                if (sz[v] > bestSize) {
                    bestSize = sz[v];
                    heavy[u] = v;
                }
            }
        }

        // Chain decomposition (iterative)
        int timer = 0;
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0, 0});

        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            int u = top[0], h = top[1];

            while (u != -1) {
                head[u] = h;
                pos[u] = timer++;

                for (int v : adj[u]) {
                    if (parent[v] == u && v != heavy[u]) {
                        stack.push(new int[]{v, v});
                    }
                }

                u = heavy[u];
            }
        }

        // ---------- Fenwick tree for XOR ----------
        int[] bit = new int[n + 1];
        int[] nodeMask = new int[n];

        for (int i = 0; i < n; i++) {
            nodeMask[i] = 1 << (s.charAt(i) - 'a');
            for (int j = pos[i] + 1; j <= n; j += j & -j) {
                bit[j] ^= nodeMask[i];
            }
        }

        // ---------- Process queries ----------
        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int newMask = 1 << (c - 'a');
                int delta = nodeMask[u] ^ newMask;
                nodeMask[u] = newMask;

                for (int j = pos[u] + 1; j <= n; j += j & -j) {
                    bit[j] ^= delta;
                }
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int res = 0;

                while (head[u] != head[v]) {
                    if (depth[head[u]] < depth[head[v]]) {
                        int tmp = u; u = v; v = tmp;
                    }

                    int l = pos[head[u]], r = pos[u];
                    int xr = 0;
                    for (int j = r + 1; j > 0; j -= j & -j) xr ^= bit[j];
                    if (l > 0) {
                        for (int j = l; j > 0; j -= j & -j) xr ^= bit[j];
                    }
                    res ^= xr;

                    u = parent[head[u]];
                }

                if (depth[u] > depth[v]) { int tmp = u; u = v; v = tmp; }
                int l = pos[u], r = pos[v];
                int xr = 0;
                for (int j = r + 1; j > 0; j -= j & -j) xr ^= bit[j];
                if (l > 0) {
                    for (int j = l; j > 0; j -= j & -j) xr ^= bit[j];
                }
                res ^= xr;

                answer.add(Integer.bitCount(res) <= 1);
            }
        }

        return answer;
    }
}