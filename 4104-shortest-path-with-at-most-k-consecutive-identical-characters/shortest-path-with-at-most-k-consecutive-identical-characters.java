class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        // Build adjacency list
        List<int[]>[] adj = new List[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] e : edges) {
            adj[e[0]].add(new int[]{e[1], e[2]});
        }

        int W = k + 1;
        int total = n * W;

        long INF = Long.MAX_VALUE;
        long[] dist = new long[total];
        Arrays.fill(dist, INF);

        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));

        int startId = 0 * W + 1;
        dist[startId] = 0;
        pq.offer(new long[]{0, startId});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0];
            int id = (int) cur[1];

            if (d != dist[id]) continue;

            int u = id / W;
            int r = id % W;

            if (u == n - 1) return (int) d;

            char cu = labels.charAt(u);
            for (int[] e : adj[u]) {
                int v = e[0];
                int w = e[1];
                char cv = labels.charAt(v);

                int nr;
                if (cv == cu) {
                    nr = r + 1;
                    if (nr > k) continue;
                } else {
                    nr = 1;
                }

                int nid = v * W + nr;
                long nd = d + w;

                if (nd < dist[nid]) {
                    dist[nid] = nd;
                    pq.offer(new long[]{nd, nid});
                }
            }
        }

        return -1;
    }
}