class Solution {
    public int digitSum(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    public int minSwaps(int[] nums) {
        int n = nums.length;
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = digitSum(nums[i]);
            arr[i][1] = nums[i];
        }

        Arrays.sort(arr, (a, b) -> {
            if (a[0] != b[0])
                return a[0] - b[0];
            return a[1] - b[1];
        });

        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < n; i++)
            pos.put(nums[i], i);

        int swaps = 0;

        for (int i = 0; i < n; i++) {
            int target = arr[i][1];

            if (nums[i] == target)
                continue;

            int j = pos.get(target);

            pos.put(nums[i], j);
            pos.put(nums[j], i);

            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;

            swaps++;
        }

        return swaps;
    }
}