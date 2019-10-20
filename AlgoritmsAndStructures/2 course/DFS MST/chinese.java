import java.util.*;

import javafx.util.Pair;

public class ITMO {
    private static int counterComp;
    private static int[] used;
    private static Stack<Integer> stack = new Stack<>();
    private static int[] comp;
    private static int from;

    private static void dfs_out(ArrayList<ArrayList <Pair<Integer, Integer> >> r_g, int v, int color) {
        comp[v] = color;
        for (Pair<Integer, Integer> e : r_g.get(v)) {
            int u = e.getKey();
            if (comp[u] == 0) {
                dfs_out(r_g, u, color);
            }
        }
    }

    private static void dfs_in(ArrayList<ArrayList <Pair<Integer, Integer> >> g, int v) {
        used[v] = 1;
        for (Pair<Integer, Integer> e : g.get(v)) {
            int u = e.getKey();
            if (used[u] == 0) {
                dfs_in(g, u);
            }
        }
        stack.push(v);
    }

    private static int check(ArrayList<ArrayList <Pair<Integer, Integer> >> g) {
        Arrays.fill(used, 1, counterComp + 1, 0);
        dfs_in(g, from);
        stack = new Stack<>();
        for (int v = 1; v <= counterComp; v++) {
            if (used[v] == 0) {
                return 0;
            }
        }
        return 1;
    }

    private static int findComp(ArrayList<ArrayList <Pair<Integer, Integer> >> g,
                                ArrayList<ArrayList <Pair<Integer, Integer> >> r_g) {
        Arrays.fill(used, 1, counterComp + 1, 0);
        Arrays.fill(comp, 1, counterComp + 1, 0);
        for (int v = 1; v <= counterComp; v++) {
            if (used[v] == 0) {
                dfs_in(g, v);
            }
        }
        int current_color = 1;
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (comp[v] == 0) {
                dfs_out(r_g, v, current_color);
                ++current_color;
            }
        }
        return current_color - 1;
    }

    private static long Mst(ArrayList<ArrayList <Pair<Integer, Integer> >> g,
                            ArrayList<ArrayList <Pair<Integer, Integer> >> r_g) {
        long mst = 0;
        int[] dp = new int[counterComp + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (ArrayList<Pair<Integer, Integer>> es : g) {
            for (Pair<Integer, Integer> e : es) {
                int to = e.getKey();
                int weight = e.getValue();
                dp[to] = Math.min(weight, dp[to]);
            }
        }
        for (int v = 1; v <= counterComp; v++) {
            if (v == from) {
                continue;
            }
            mst += dp[v];
        }

        ArrayList <ArrayList <Pair<Integer, Integer> >> nullE = new ArrayList<>();
        ArrayList <ArrayList <Pair<Integer, Integer> >> r_nullE = new ArrayList<>();
        for (int i = 0; i <= counterComp; i++) {
            nullE.add(new ArrayList<>());
            r_nullE.add(new ArrayList<>());
        }
        for (int from = 1; from <= counterComp; ++from) {
            ArrayList<Pair<Integer, Integer>> es = g.get(from);
            for (Pair<Integer, Integer> e : es) {
                int weight = e.getValue();
                int to = e.getKey();
                if (weight == dp[to]) {
                    nullE.get(from).add(new Pair<>(to, 0));
                    r_nullE.get(to).add(new Pair<>(from, 0));
                }
            }
        }
        if (check(nullE) == 1) {
            return mst;
        }
        ArrayList<ArrayList <Pair<Integer, Integer> >> newG = new ArrayList<>();
        ArrayList<ArrayList <Pair<Integer, Integer> >> r_newG = new ArrayList<>();
        counterComp = findComp(nullE, r_nullE);
        for (int i = 0; i <= counterComp; i++) {
            newG.add(new ArrayList<>());
            r_newG.add(new ArrayList<>());
        }
        from = comp[from];
        for (int from = 1; from < g.size(); from++) {
            ArrayList<Pair<Integer, Integer>> es = g.get(from);
            for (Pair<Integer, Integer> e : es) {
                int weight = e.getValue();
                int to = e.getKey();
                if (comp[from] != comp[to]) {
                    newG.get(comp[from]).add(new Pair<>(comp[to], weight - dp[to]));
                    r_newG.get(comp[to]).add(new Pair<>(comp[from], weight - dp[to]));
                }
            }
        }

        mst += Mst(newG, r_newG);
        return mst;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n, m;
        ArrayList<ArrayList <Pair<Integer, Integer> >> g = new ArrayList<>();
        ArrayList<ArrayList <Pair<Integer, Integer> >> r_g = new ArrayList<>();
        n = in.nextInt();
        m = in.nextInt();
        counterComp = n;
        used = new int[n + 1];
        comp = new int[n + 1];
        Arrays.fill(used, 0);
        Arrays.fill(comp, 0);
        for (int i = 0; i <= n; i++) {
            g.add(new ArrayList<>());
            r_g.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int from = in.nextInt();
            int to = in.nextInt();
            int weight = in.nextInt();
            g.get(from).add(new Pair<>(to, weight));
            r_g.get(to).add(new Pair<>(from, weight));
        }
        from = 1;
        if (check(g) == 0) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
            System.out.println(Mst(g, r_g));
        }
    }
}
