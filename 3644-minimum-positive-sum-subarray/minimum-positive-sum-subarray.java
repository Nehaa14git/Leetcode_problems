class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();

        int[] pref = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pref[i + 1] = pref[i] + nums.get(i);
        }

        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            for (int len = l; len <= r && i + len <= n; len++) {
                int sum = pref[i + len] - pref[i];
                if (sum > 0) {
                    ans = Math.min(ans, sum);
                }
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}