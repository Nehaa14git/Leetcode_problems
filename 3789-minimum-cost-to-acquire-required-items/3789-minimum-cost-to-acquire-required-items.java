class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long n1 = need1, n2 = need2;

        long[] candidates = {
            0,
            Math.min(n1, n2),
            n1,
            n2,
            Math.max(n1, n2)
        };

        long ans = Long.MAX_VALUE;

        for (long c : candidates) {
            long a = Math.max(0L, n1 - c);
            long b = Math.max(0L, n2 - c);

            long total = c * costBoth + a * cost1 + b * cost2;
            ans = Math.min(ans, total);
        }

        return ans;
    }
}