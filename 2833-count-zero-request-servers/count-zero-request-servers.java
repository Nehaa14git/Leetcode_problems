class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        int m = queries.length;
        Integer[] idx = new Integer[m];
        for (int i = 0; i < m; i++) idx[i] = i;

        Arrays.sort(idx, (a, b) -> Integer.compare(queries[a], queries[b]));

        int maxT = 0;
        for (int[] l : logs) maxT = Math.max(maxT, l[1]);
        for (int q : queries) maxT = Math.max(maxT, q);

        Fenwick bit = new Fenwick(maxT + 2);

        // Initially all servers have latest = 0.
        bit.add(0, n);

        int[] latest = new int[n + 1];
        int[] ans = new int[m];

        int p = 0;
        for (int qi : idx) {
            int q = queries[qi];

            while (p < logs.length && logs[p][1] <= q) {
                int id = logs[p][0];
                int t  = logs[p][1];

                if (latest[id] != t) {
                    bit.add(latest[id], -1);
                    bit.add(t, 1);
                    latest[id] = t;
                }
                ++p;
            }

            int L = q - x;
            ans[qi] = bit.prefix(L - 1);
        }

        return ans;
    }

    private static class Fenwick {
        int n;
        int[] bit;

        Fenwick(int n) {
            this.n = n;
            this.bit = new int[n + 1];
        }

        void add(int t, int delta) {
            for (int i = t + 1; i <= n; i += i & -i) {
                bit[i] += delta;
            }
        }

        int prefix(int t) {
            if (t < 0) return 0;
            int s = 0;
            for (int i = t + 1; i > 0; i -= i & -i) {
                s += bit[i];
            }
            return s;
        }
    }
}