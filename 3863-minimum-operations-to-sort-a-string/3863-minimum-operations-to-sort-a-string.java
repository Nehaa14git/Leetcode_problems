
class Solution {
    public int minOperations(String s) {
        int n = s.length();
        char[] original = s.toCharArray();
        char[] sorted = original.clone();

        Arrays.sort(sorted);

        if (Arrays.equals(original, sorted)) {
            return 0;
        }

        if (n == 2) {
            return -1;
        }

        int l = 0, r = n - 1;

        while (l < n && original[l] == sorted[l]) {
            l++;
        }

        while (r >= 0 && original[r] == sorted[r]) {
            r--;
        }

        if (l > 0 || r < n - 1) {
            return 1;
        }

        char min = sorted[0];
        char max = sorted[n - 1];

        int minCount = 0, maxCount = 0;

        for (char c : original) {
            if (c == min) minCount++;
            if (c == max) maxCount++;
        }

        if (original[0] == max && original[n - 1] == min
                && maxCount == 1 && minCount == 1) {
            return 3;
        }

        return 2;
    }
}
