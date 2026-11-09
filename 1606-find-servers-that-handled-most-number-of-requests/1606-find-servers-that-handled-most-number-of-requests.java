class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        // Available servers, ordered by index.
        TreeSet<Integer> free = new TreeSet<>();
        for (int i = 0; i < k; i++) free.add(i);

        // Busy servers: (finishTime, serverIndex)
        PriorityQueue<long[]> busy = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return Long.compare(a[0], b[0]);
            return Long.compare(a[1], b[1]);
        });

        int[] count = new int[k];
        int n = arrival.length;

        for (int i = 0; i < n; i++) {
            long t = arrival[i];

            // Release all servers that finish at or before this arrival.
            while (!busy.isEmpty() && busy.peek()[0] <= t) {
                free.add((int) busy.poll()[1]);
            }

            if (free.isEmpty()) continue; // request dropped

            int start = i % k;
            Integer server = free.ceiling(start);
            if (server == null) server = free.first();

            free.remove(server);
            count[server]++;

            busy.offer(new long[]{t + load[i], server});
        }

        int best = 0;
        for (int c : count) best = Math.max(best, c);

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (count[i] == best) ans.add(i);
        }
        return ans;
    }
}