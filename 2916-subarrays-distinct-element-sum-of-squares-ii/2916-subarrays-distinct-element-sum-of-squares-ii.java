class Solution {
    static final int MOD = 1_000_000_007;

    static class Node {
        long sum = 0;
        long sq = 0;
        long lazy = 0;
    }

    Node[] seg;
    int n;

    void apply(int node, int l, int r, long val) {
        long len = r - l + 1L;
        long oldSum = seg[node].sum;

        seg[node].sq = (
            seg[node].sq
            + 2L * val % MOD * oldSum % MOD
            + val * val % MOD * len % MOD
        ) % MOD;

        seg[node].sum = (oldSum + val * len) % MOD;
        seg[node].lazy = (seg[node].lazy + val) % MOD;
    }

    void push(int node, int l, int r) {
        if (seg[node].lazy == 0 || l == r) {
            return;
        }

        int mid = (l + r) / 2;
        long val = seg[node].lazy;

        apply(node * 2, l, mid, val);
        apply(node * 2 + 1, mid + 1, r, val);

        seg[node].lazy = 0;
    }

    void update(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) {
            return;
        }

        if (ql <= l && r <= qr) {
            apply(node, l, r, 1);
            return;
        }

        push(node, l, r);

        int mid = (l + r) / 2;

        update(node * 2, l, mid, ql, qr);
        update(node * 2 + 1, mid + 1, r, ql, qr);

        seg[node].sum =
            (seg[node * 2].sum + seg[node * 2 + 1].sum) % MOD;

        seg[node].sq =
            (seg[node * 2].sq + seg[node * 2 + 1].sq) % MOD;
    }

    long query(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) {
            return 0;
        }

        if (ql <= l && r <= qr) {
            return seg[node].sq;
        }

        push(node, l, r);

        int mid = (l + r) / 2;

        return (
            query(node * 2, l, mid, ql, qr)
            + query(node * 2 + 1, mid + 1, r, ql, qr)
        ) % MOD;
    }

    public int sumCounts(int[] nums) {
        n = nums.length;
        seg = new Node[4 * n + 5];

        for (int i = 0; i < seg.length; i++) {
            seg[i] = new Node();
        }

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];
            int prev = last[x];

            update(1, 0, n - 1, prev + 1, i);

            ans = (ans + query(1, 0, n - 1, 0, i)) % MOD;

            last[x] = i;
        }

        return (int) ans;
    }
}