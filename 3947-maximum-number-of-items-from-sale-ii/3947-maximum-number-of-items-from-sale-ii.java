import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        long pMin = Long.MAX_VALUE;
        for (int[] it : items) {
            pMin = Math.min(pMin, (long) it[1]);
        }

        if (budget < pMin) return 0;

        // Frequency of each factor value
        int[] freq = new int[n + 1];
        for (int[] it : items) {
            int f = it[0];
            if (f <= n) freq[f]++;
        }

        // multCount[f] = number of items whose factor is a multiple of f
        int[] multCount = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            for (int m = f; m <= n; m += f) {
                multCount[f] += freq[m];
            }
        }

        // Special copies: (price, outDegree)
        List<long[]> specials = new ArrayList<>();
        long threshold = 2 * pMin;

        for (int[] it : items) {
            int f = it[0];
            long price = it[1];

            if (price < threshold) {
                long out = multCount[f] - 1; // other items it can give free copies to
                if (out > 0) {
                    specials.add(new long[]{price, out});
                }
            }
        }

        specials.sort(Comparator.comparingLong(a -> a[0]));

        long remaining = budget;
        long totalSpecial = 0;

        for (long[] sp : specials) {
            long price = sp[0];
            long out = sp[1];

            if (remaining < price) break;

            long take = Math.min(out, remaining / price);
            totalSpecial += take;
            remaining -= take * price;

            if (take < out) break; // cannot afford more special copies
        }

        long ordinary = remaining / pMin;
        long ans = 2 * totalSpecial + ordinary;

        return (int) ans;
    }
}