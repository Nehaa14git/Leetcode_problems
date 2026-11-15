class Solution {
    public int numOfSubarrays(int[] arr) {
        final int MOD = 1_000_000_007;

        long even = 1, odd = 0;
        long ans = 0;
        int parity = 0; // current prefix sum parity: 0 even, 1 odd

        for (int x : arr) {
            if ((x & 1) == 1) parity ^= 1;

            if (parity == 1) {
                ans = (ans + even) % MOD;
                odd++;
            } else {
                ans = (ans + odd) % MOD;
                even++;
            }
        }

        return (int) ans;
    }
}