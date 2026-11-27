import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] factor = new int[n], price = new int[n], out = new int[n];

        for (int i = 0; i < n; i++) {
            factor[i] = items[i][0];
            price[i] = items[i][1];
        }

        // out[i] = number of other items j with factor[i] | factor[j]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                if (factor[j] % factor[i] == 0) out[i]++;
            }
        }

        // val[i] = copies obtained by activating item i (1 purchased + out[i] free)
        int[] val = new int[n];
        for (int i = 0; i < n; i++) val[i] = 1 + out[i];

        final int MAXP = 1500;
        List<Integer>[] group = new List[MAXP + 1];
        for (int p = 0; p <= MAXP; p++) group[p] = new ArrayList<>();
        for (int i = 0; i < n; i++) group[price[i]].add(val[i]);

        // pref[p][k] = sum of the k largest vals among items of price p
        int[][] pref = new int[MAXP + 1][];
        for (int p = 1; p <= MAXP; p++) {
            if (group[p].isEmpty()) { pref[p] = new int[]{0}; continue; }
            group[p].sort(Collections.reverseOrder());
            int sz = group[p].size();
            pref[p] = new int[sz + 1];
            for (int k = 1; k <= sz; k++) {
                pref[p][k] = pref[p][k - 1] + group[p].get(k - 1);
            }
        }

        final int NEG = Integer.MIN_VALUE / 2;
        int[] dp = new int[budget + 1];
        Arrays.fill(dp, NEG);
        dp[0] = 0;

        int ans = 0;

        // Process prices from largest to smallest.
        // When we process p, dp holds the best value using items of price > p.
        for (int p = MAXP; p >= 1; p--) {
            if (group[p].isEmpty()) continue;
            int sz = group[p].size();

            // Consider p as the minimum price among activated items.
            for (int k = 1; k <= sz; k++) {
                int costK = k * p;
                if (costK > budget) break;
                int valK = pref[p][k];

                for (int c = 0; c <= budget - costK; c++) {
                    if (dp[c] == NEG) continue;
                    int remaining = budget - c - costK;
                    int extra = remaining / p;   // extra copies of cheapest item
                    int total = dp[c] + valK + extra;
                    if (total > ans) ans = total;
                }
            }

            // Add items of price p into the 0/1 knapsack for future (smaller) prices.
            for (int v : group[p]) {
                for (int c = budget; c >= p; c--) {
                    if (dp[c - p] != NEG) {
                        dp[c] = Math.max(dp[c], dp[c - p] + v);
                    }
                }
            }
        }

        return ans;
    }
}