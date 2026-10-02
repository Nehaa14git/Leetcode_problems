class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        backtrack(result, "", 0, 0, n);

        return result;
    }

    private void backtrack(List<String> result, String current,
                            int open, int close, int n) {

        // If the string has 2*n brackets, it is complete
        if (current.length() == 2 * n) {
            result.add(current);
            return;
        }

        // Add '(' if we still have opening brackets available
        if (open < n) {
            backtrack(result, current + "(", open + 1, close, n);
        }

        // Add ')' only if there is an unmatched '('
        if (close < open) {
            backtrack(result, current + ")", open, close + 1, n);
        }
    }
}