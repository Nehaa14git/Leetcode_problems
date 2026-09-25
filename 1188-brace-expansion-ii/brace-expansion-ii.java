import java.util.*;

class Solution {

    private String expression;
    private int index;

    public List<String> braceExpansionII(String expression) {
        this.expression = expression;
        this.index = 0;

        TreeSet<String> result = parseExpression();

        return new ArrayList<>(result);
    }

    // Parses concatenation and union expressions
    private TreeSet<String> parseExpression() {
        TreeSet<String> result = new TreeSet<>();

        while (index < expression.length()
                && expression.charAt(index) != '}'
                && expression.charAt(index) != ',') {

            TreeSet<String> current = parseTerm();

            if (result.isEmpty()) {
                result.addAll(current);
            } else {
                result = concatenate(result, current);
            }
        }

        return result;
    }

    // Parses expressions inside braces
    private TreeSet<String> parseTerm() {
        char ch = expression.charAt(index);

        if (ch == '{') {
            index++; // Skip '{'

            TreeSet<String> result = new TreeSet<>();

            while (true) {
                TreeSet<String> current = parseExpression();

                result.addAll(current);

                if (expression.charAt(index) == ',') {
                    index++; // Skip ','
                } else {
                    break;
                }
            }

            index++; // Skip '}'

            return result;
        }

        // Single lowercase letter
        index++;

        TreeSet<String> result = new TreeSet<>();
        result.add(String.valueOf(ch));

        return result;
    }

    // Cartesian product of two sets of words
    private TreeSet<String> concatenate(
            TreeSet<String> first,
            TreeSet<String> second) {

        TreeSet<String> result = new TreeSet<>();

        for (String a : first) {
            for (String b : second) {
                result.add(a + b);
            }
        }

        return result;
    }
}