class ProductOfNumbers {
    private List<Integer> pref;   // pref[0] = 1; pref[i] = product since last zero
    private int countSinceZero;   // number of elements after the most recent zero

    public ProductOfNumbers() {
        pref = new ArrayList<>();
        pref.add(1);
        countSinceZero = 0;
    }

    public void add(int num) {
        if (num == 0) {
            // A zero resets all products before it.
            pref = new ArrayList<>();
            pref.add(1);
            countSinceZero = 0;
        } else {
            pref.add(pref.get(pref.size() - 1) * num);
            countSinceZero++;
        }
    }

    public int getProduct(int k) {
        // If k reaches back past the most recent zero, the product is 0.
        if (k > countSinceZero) return 0;

        int n = pref.size();
        return pref.get(n - 1) / pref.get(n - 1 - k);
    }
}