class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        final int INF = 1_000_000_000;

        // cost[l][r] = min changes to make s[l..r] a semi-palindrome.
        // Length-1 substrings are invalid, so they stay INF.
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; i++) {
            java.util.Arrays.fill(cost[i], INF);
        }

        for (int len = 2; len <= n; len++) {
            for (int l = 0; l + len <= n; l++) {
                int r = l + len - 1;
                int best = INF;

                // Try every valid divisor d of len with d < len.
                for (int d = 1; d < len; d++) {
                    if (len % d != 0) continue;

                    int cur = 0;
                    int m = len / d; // length of each group

                    // For each remainder off, compare symmetric positions
                    // directly, without allocating temporary arrays.
                    for (int off = 0; off < d; off++) {
                        for (int a = 0, b = m - 1; a < b; a++, b--) {
                            char ca = s.charAt(l + off + a * d);
                            char cb = s.charAt(l + off + b * d);
                            if (ca != cb) cur++;
                        }
                    }

                    if (cur < best) best = cur;
                }

                cost[l][r] = best;
            }
        }

        // dp[i] = min changes to partition the first i characters
        // into the current number of parts.
        int[] dp = new int[n + 1];
        int[] ndp = new int[n + 1];
        java.util.Arrays.fill(dp, INF);
        dp[0] = 0;

        for (int t = 1; t <= k; t++) {
            java.util.Arrays.fill(ndp, INF);

            // Each part must have length >= 2, so t parts need >= 2*t chars.
            for (int i = 2 * t; i <= n; i++) {
                int best = INF;

                // Previous prefix must have length >= 2*(t-1),
                // and the last part must have length >= 2.
                for (int j = 2 * (t - 1); j <= i - 2; j++) {
                    if (dp[j] == INF) continue;
                    if (cost[j][i - 1] == INF) continue;

                    int val = dp[j] + cost[j][i - 1];
                    if (val < best) best = val;
                }

                ndp[i] = best;
            }

            int[] tmp = dp;
            dp = ndp;
            ndp = tmp;
        }

        return dp[n];
    }
}