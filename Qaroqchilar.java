import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Qaroqchilar {

    public static void main(String[] args) {
        List<Integer> dangerSide = new ArrayList<>(Arrays.asList(1, 3, 7, 14));
        List<Integer> safeSide = new ArrayList<>();
        System.out.println(minCrossTime(dangerSide, safeSide));
    }

    private static int minCrossTime(List<Integer> dangerSide, List<Integer> safeSide) {
        if (dangerSide.isEmpty()) {
            //nobody = no problem
            return 0;

        } else if (dangerSide.size() == 1) {
            //if there is only one guy, so he can just cross
            return dangerSide.get(0);

        } else if (dangerSide.size() == 2) {
            //or if there are only two of them, they can also just cross (with the slowest speed - maximizing the time)
            return Math.max(dangerSide.get(0), dangerSide.get(1));

        } else {
            //r is the final result (minimum time to cross)
            int r = Integer.MAX_VALUE;

            //now pick two guys (a and b) from array.
            for (int aIndex = 0; aIndex < dangerSide.size(); aIndex++) {
                for (int bIndex = aIndex + 1; bIndex < dangerSide.size(); bIndex++) {
                    int a = dangerSide.get(aIndex);
                    int b = dangerSide.get(bIndex);

                    int wayForward = Math.max(a, b);

                    //for back way we have either a or b to return.
                    List<Integer> dangerSideRemaining = new ArrayList(dangerSide);
                    //a and b left the danger side (remove by value now)
                    dangerSideRemaining.remove(new Integer(a));
                    dangerSideRemaining.remove(new Integer(b));

                    List<Integer> safeSideNew = new ArrayList(safeSide);
                    //a and b came to safe side
                    safeSideNew.add(a);
                    safeSideNew.add(b);

                    //now. Half way is done, so (r2 is the result candidate)
                    int r2 = wayForward;

                    //now the fastest will come back, So we need to add to dangerSideRemaining back
                    Integer fastest = getFastestFromSafeSide(safeSideNew);
                    dangerSideRemaining.add(fastest);

                    // and we need to remove from safeSide, since he left safeSide (remove by Value)
                    safeSideNew.remove(fastest);

                    // and we need to add wayBack time to r2 (which is fastest, he is coming back for help)
                    r2 += fastest;
                    // and we need to add the time for others to finish crossing
                    r2 += minCrossTime(dangerSideRemaining, safeSideNew);
                    //overall is:
                    //r2 = wayForward + wayBack + minCrossTime(dangerSideRemaining, safeSideNew);

                    //and we need to minimize the time
                    r = Math.min(r, r2);
                }
            }
            return r;
        }
    }

    private static Integer getFastestFromSafeSide(List<Integer> safeSideNew) {
        return safeSideNew.stream().min(Integer::compare).get();
    }
}
