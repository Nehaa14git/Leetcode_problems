class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);

        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        int[] dp = new int[mx + 3]; // dp[v] = longest consecutive sequence ending at v
        int ans = 0;

        for (int x : nums) {
            // Case 1: increase x to x + 1.
            // Must be done before updating dp[x] so x is not reused twice.
            dp[x + 1] = Math.max(dp[x + 1], dp[x] + 1);

            // Case 2: keep x unchanged.
            dp[x] = Math.max(dp[x], dp[x - 1] + 1);

            ans = Math.max(ans, Math.max(dp[x], dp[x + 1]));
        }

        return ans;
    }
}