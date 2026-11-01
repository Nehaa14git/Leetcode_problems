class Solution {
    public int minMoves(int[] nums) {
        long sum = 0;
        int mn = Integer.MAX_VALUE;

        for (int x : nums) {
            sum += x;
            mn = Math.min(mn, x);
        }

        return (int)(sum - (long) nums.length * mn);
    }
}