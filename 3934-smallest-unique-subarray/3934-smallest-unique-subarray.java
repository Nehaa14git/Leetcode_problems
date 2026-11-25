class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        // ----- coordinate compression -----
        int maxVal = 0;
        for (int x : nums) maxVal = Math.max(maxVal, x);
        int[] map = new int[maxVal + 1];
        Arrays.fill(map, -1);

        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 || sorted[i] != sorted[m - 1]) {
                sorted[m++] = sorted[i];
            }
        }
        for (int i = 0; i < m; i++) map[sorted[i]] = i;

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = map[nums[i]];

        // ----- suffix array (O(n log n), radix sort) -----
        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] sa2 = new int[n];
        int[] rank2 = new int[n];
        int[] order = new int[n];
        int[] cnt2 = new int[n + 2];
        int[] cnt1 = new int[n];

        // initial sorting by first character
        int[] cnt = new int[m];
        for (int i = 0; i < n; i++) cnt[arr[i]]++;
        for (int i = 1; i < m; i++) cnt[i] += cnt[i - 1];
        for (int i = n - 1; i >= 0; i--) sa[--cnt[arr[i]]] = i;

        rank[sa[0]] = 0;
        for (int i = 1; i < n; i++) {
            rank[sa[i]] = rank[sa[i - 1]] + (arr[sa[i]] != arr[sa[i - 1]] ? 1 : 0);
        }
        m = rank[sa[n - 1]] + 1;

        for (int step = 1; step < n; step <<= 1) {
            // sort by second key
            int maxKey = m + 1;
            Arrays.fill(cnt2, 0, maxKey + 1, 0);
            for (int i = 0; i < n; i++) {
                int key2 = (i + step < n) ? rank[i + step] + 1 : 0;
                cnt2[key2]++;
            }
            for (int i = 1; i <= maxKey; i++) cnt2[i] += cnt2[i - 1];
            for (int i = n - 1; i >= 0; i--) {
                int key2 = (i + step < n) ? rank[i + step] + 1 : 0;
                order[--cnt2[key2]] = i;
            }

            // sort by first key
            Arrays.fill(cnt1, 0, m, 0);
            for (int i = 0; i < n; i++) cnt1[rank[i]]++;
            for (int i = 1; i < m; i++) cnt1[i] += cnt1[i - 1];
            for (int i = n - 1; i >= 0; i--) {
                int idx = order[i];
                sa2[--cnt1[rank[idx]]] = idx;
            }

            // new ranks
            rank2[sa2[0]] = 0;
            for (int i = 1; i < n; i++) {
                int cur = sa2[i];
                int prev = sa2[i - 1];
                int cur2 = (cur + step < n) ? rank[cur + step] : -1;
                int prev2 = (prev + step < n) ? rank[prev + step] : -1;
                if (rank[cur] != rank[prev] || cur2 != prev2) {
                    rank2[cur] = rank2[prev] + 1;
                } else {
                    rank2[cur] = rank2[prev];
                }
            }
            m = rank2[sa2[n - 1]] + 1;

            // swap
            int[] t = sa; sa = sa2; sa2 = t;
            t = rank; rank = rank2; rank2 = t;

            if (m == n) break;
        }

        // ----- LCP array (Kasai) -----
        int[] lcp = new int[n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int r = rank[i];
            if (r == 0) {
                k = 0;
                continue;
            }
            int j = sa[r - 1];
            while (i + k < n && j + k < n && nums[i + k] == nums[j + k]) {
                k++;
            }
            lcp[r] = k;
            if (k > 0) k--;
        }

        // ----- find shortest unique substring -----
        int ans = n;
        for (int i = 0; i < n; i++) {
            int pos = rank[i];
            int lcpPrev = (pos > 0) ? lcp[pos] : 0;
            int lcpNext = (pos + 1 < n) ? lcp[pos + 1] : 0;
            int maxLCP = Math.max(lcpPrev, lcpNext);
            int len = maxLCP + 1;
            if (len <= n - i) {
                if (len < ans) ans = len;
            }
        }
        return ans;
    }
}