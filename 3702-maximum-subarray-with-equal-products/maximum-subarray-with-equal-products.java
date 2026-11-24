class Solution {
    public int maxLength(int[] nums) {
        int[] primes = {2, 3, 5, 7};
        int n = nums.length;
        int ans = 0;

        for (int l = 0; l < n; l++) {
            int[] sum = new int[4];
            int[] mn = new int[4];
            int[] mx = new int[4];
            java.util.Arrays.fill(mn, Integer.MAX_VALUE);

            for (int r = l; r < n; r++) {
                int x = nums[r];
                int[] e = new int[4];

                for (int p = 0; p < 4; p++) {
                    while (x % primes[p] == 0) {
                        x /= primes[p];
                        e[p]++;
                    }
                }

                boolean ok = true;

                for (int p = 0; p < 4; p++) {
                    sum[p] += e[p];
                    mn[p] = Math.min(mn[p], e[p]);
                    mx[p] = Math.max(mx[p], e[p]);

                    if (sum[p] != mn[p] + mx[p]) {
                        ok = false;
                    }
                }

                if (ok) {
                    ans = Math.max(ans, r - l + 1);
                }
            }
        }

        return ans;
    }
}