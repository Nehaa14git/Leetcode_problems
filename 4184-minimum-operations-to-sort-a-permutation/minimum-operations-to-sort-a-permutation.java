class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int p = -1;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                p = i;
                break;
            }
        }

        if (p == -1) return -1;

        int ans = Integer.MAX_VALUE;

        // Even number of reversals
        if (check(nums, p, false)) {
            ans = Math.min(ans, Math.min(p, n - p + 2));
        }

        // Odd number of reversals
        int q = n - 1 - p;

        if (check(nums, q, true)) {
            ans = Math.min(ans, 1 + Math.min(q, n - q));
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private boolean check(int[] nums, int shift, boolean reversed) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int idx = (shift + i) % n;
            int val = reversed ? nums[n - 1 - idx] : nums[idx];

            if (val != i) {
                return false;
            }
        }

        return true;
    }
}