class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        // Original tree children
        List<Integer>[] children = new List[n];
        for (int i = 0; i < n; i++) children[i] = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }

        // newParent[i] = closest ancestor of i with the same character as s[i],
        // or original parent[i] if no such ancestor exists.
        int[] newParent = new int[n];
        int[] last = new int[26];
        java.util.Arrays.fill(last, -1);
        int[] prevLast = new int[n];

        // Iterative DFS with enter/exit events.
        // Encode state: node * 2 + state, where state 0 = enter, 1 = exit.
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0 * 2);

        while (!stack.isEmpty()) {
            int code = stack.pop();
            int u = code / 2;
            int state = code % 2;
            int c = s.charAt(u) - 'a';

            if (state == 0) {
                // Enter node u
                if (u == 0) {
                    newParent[u] = -1;
                } else {
                    newParent[u] = (last[c] == -1 ? parent[u] : last[c]);
                }

                prevLast[u] = last[c];
                last[c] = u;

                // Schedule exit after all children
                stack.push(u * 2 + 1);
                for (int v : children[u]) {
                    stack.push(v * 2);
                }
            } else {
                // Exit node u: restore previous last occurrence for its character
                last[c] = prevLast[u];
            }
        }

        // Build final tree
        List<Integer>[] finalChildren = new List[n];
        for (int i = 0; i < n; i++) finalChildren[i] = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            finalChildren[newParent[i]].add(i);
        }

        // Traversal order of the final tree (parents before children)
        int[] order = new int[n];
        int ordLen = 0;
        Deque<Integer> stack2 = new ArrayDeque<>();
        stack2.push(0);

        while (!stack2.isEmpty()) {
            int u = stack2.pop();
            order[ordLen++] = u;

            for (int v : finalChildren[u]) {
                stack2.push(v);
            }
        }

        // Compute subtree sizes bottom-up
        int[] sz = new int[n];
        java.util.Arrays.fill(sz, 1);
        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];
            if (newParent[u] != -1) {
                sz[newParent[u]] += sz[u];
            }
        }

        return sz;
    }
}