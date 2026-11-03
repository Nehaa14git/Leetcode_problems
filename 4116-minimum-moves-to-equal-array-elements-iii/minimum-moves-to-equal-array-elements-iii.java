class Solution {
    public int minMoves(int[] nums) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        int moves = 0;
        for (int x : nums) {
            moves += mx - x;
        }

        return moves;
    }
}