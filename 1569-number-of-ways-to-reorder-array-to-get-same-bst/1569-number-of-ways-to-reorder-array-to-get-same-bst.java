class Solution {
    static final int MOD = 1_000_000_007;
    long[][] comb = new long[1001][1001];

    public int numOfWays(int[] nums) {
        int n = nums.length;

        for (int i = 0; i <= n; i++) {
            comb[i][0] = comb[i][i] = 1;
            for (int j = 1; j < i; j++) {
                comb[i][j] = (comb[i - 1][j - 1]
                            + comb[i - 1][j]) % MOD;
            }
        }

        return (int) ((solve(nums) - 1 + MOD) % MOD);
    }

    private long solve(int[] nums) {
        int n = nums.length;
        if (n <= 2) return 1;

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[0])
                left.add(nums[i]);
            else
                right.add(nums[i]);
        }

        long[] l = left.stream().mapToLong(Integer::longValue).toArray();
        long[] r = right.stream().mapToLong(Integer::longValue).toArray();

        long ways = comb[n - 1][left.size()];
        ways = ways * solve(Arrays.stream(l).mapToInt(x -> (int) x).toArray()) % MOD;
        ways = ways * solve(Arrays.stream(r).mapToInt(x -> (int) x).toArray()) % MOD;

        return ways;
    }
}
