class Solution {
    private int n;
    private int[] a;
    private long k;
    private long[] rMax, prefRMax, prefA_R, stkVal;
    private int[] stkCnt;

    public long countNonDecreasingSubarrays(int[] nums, int k) {
        n = nums.length;
        a = nums;
        this.k = k;
        rMax = new long[n + 1];
        prefRMax = new long[n + 1];
        prefA_R = new long[n + 1];
        stkVal = new long[n + 1];
        stkCnt = new int[n + 1];
        return solve(0, n - 1);
    }

    private long solve(int L, int R) {
        if (L >= R) return (L == R) ? 1 : 0;
        int mid = (L + R) / 2;
        long ans = solve(L, mid) + solve(mid + 1, R);

        int nR = R - mid;
        rMax[0] = 0; prefRMax[0] = 0; prefA_R[0] = 0;
        long curMax = 0;
        for (int i = mid + 1; i <= R; i++) {
            if ((long) a[i] > curMax) curMax = a[i];
            int idx = i - (mid + 1);
            rMax[idx] = curMax;
            prefRMax[idx + 1] = prefRMax[idx] + curMax;
            prefA_R[idx + 1] = prefA_R[idx] + a[i];
        }

        int top = -1;
        long totalMaxSum = 0;
        long leftMax = 0;
        int p = 0;
        int rPtr = nR - 1;
        long sumA_left = 0;

        for (int l = mid; l >= L; --l) {
            long v = a[l];
            if (v > leftMax) leftMax = v;
            sumA_left += v;

            int cnt = 1;
            long poppedSum = 0;
            while (top >= 0 && stkVal[top] <= v) {
                cnt += stkCnt[top];
                poppedSum += stkVal[top] * stkCnt[top];
                --top;
            }
            totalMaxSum = totalMaxSum - poppedSum + v * cnt;
            ++top;
            stkVal[top] = v;
            stkCnt[top] = cnt;

            long costL = totalMaxSum - sumA_left;

            while (p < nR && rMax[p] <= leftMax) ++p;

            while (rPtr >= 0) {
                long S;
                if (rPtr < p) {
                    int count = rPtr + 1;
                    S = leftMax * count - prefA_R[count];
                } else {
                    S = leftMax * p + (prefRMax[rPtr + 1] - prefRMax[p]) - prefA_R[rPtr + 1];
                }
                if (costL + S <= k) break;
                --rPtr;
            }
            if (rPtr < 0) break;
            ans += (rPtr + 1);
        }
        return ans;
    }
}