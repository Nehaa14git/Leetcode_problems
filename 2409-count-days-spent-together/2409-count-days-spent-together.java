class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice,
                                 String arriveBob, String leaveBob) {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // prefix[m - 1] = number of days before month m
        int[] prefix = new int[13];
        for (int i = 1; i <= 12; i++) {
            prefix[i] = prefix[i - 1] + days[i - 1];
        }

        int aliceStart = toDay(arriveAlice, prefix);
        int aliceEnd   = toDay(leaveAlice,  prefix);
        int bobStart   = toDay(arriveBob,   prefix);
        int bobEnd     = toDay(leaveBob,    prefix);

        int overlapStart = Math.max(aliceStart, bobStart);
        int overlapEnd   = Math.min(aliceEnd,   bobEnd);

        return Math.max(0, overlapEnd - overlapStart + 1);
    }

    private int toDay(String date, int[] prefix) {
        int month = Integer.parseInt(date.substring(0, 2));
        int day   = Integer.parseInt(date.substring(3, 5));
        return prefix[month - 1] + day;
    }
}