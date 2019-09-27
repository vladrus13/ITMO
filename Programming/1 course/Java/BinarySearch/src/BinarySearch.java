import javax.tools.Tool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BinarySearch {
    // NOTE: MODE signified the mod program
    //            0: iterative binary search
    //            1: recursive binary search
    public static final int MODE = 1;
    // NOTE: a is list of numbers, which are given in a input
    //       initialized as an empty array, which will be filled with numbers
    private static ArrayList<Integer> a = new ArrayList<>();

    // pre: a_i in Z.
    // post: boolean = (a_0 >= a_1) & (a_1 >= a_2) & ... & (a_(a.length() - 2) >= a_(a.length() - 1))
    private static boolean ordered() {
        // answer is answer (?)
        boolean answer = true;
        // pre: answer = true
        // post: answer = (a_0 >= a_1) & (a_1 >= a_2) & ... & (a_(a.length() - 2) >= a_(a.length() - 1))
        for (int i = 1; i < a.size(); i++) {
            // pre: (i = 1 & answer = true) | (i != 1 & answer = (a_0 >= a_1) & (a_1 >= a_2) & ... & (a_(i - 2) >= a_(i - 1))
            // post: answer = (a_0 >= a_1) & (a_1 >= a_2) & ... & (a_(i - 1) >= a_(i))
            answer &= (a.get(i - 1) >= a.get(i));
        }
        return answer;
    }

    // pre: (a_0 >= a_1) & (a_1 >= a_2) & ... & (a_(a.length() - 2) >= a_(a.length() - 1)) & x in Z
    // post: N = a_i <= x & (any N j < i & a_i > x) & i in [0; a.length()) or (i = -1 if i not exist)
    private static int iterativeBinarySearch(int x) {
        // initialize l, r, m.
        //                    l - left border of binary search
        //                    r - right border of binary search
        //                    m - middle line [l, r]
        int l = 0, r = a.size() - 1, m;
        // inv: answer in a[l..r] or not exist
        // post: r - l < 1
        while (1 < r - l) {
            m = (l + r) / 2;
            // inv: answer in a[l..r] or not exist
            // post: interval decreased
            if (a.get(m) <= x) {
                // pre: answer in a[m..r] or not exist
                // post: l' = m
                // post post: answer in a['l..r] or not exist
                r = m;
            } else {
                // pre: answer in a[l..m] or not exist
                // post: r' = m
                // post post: answer in a[l..r'] or not exist
                l = m;
            }
        }
        // inv: (answer in [l..r] or not exist) and r - l < 2
        // post: if a[l] <= x, is is answer
        if (a.get(l) <= x) return l;
        // inv: (answer in [r] or not exist)
        // post if a[r] <= x, its answer
        if (a.get(r) <= x) return r;
        // inv: answer not exist
        return -1;
    }

    // pre: (a_l >= a_(l + 1)) & ... (a_(r - 1) >= a_r) & x in Z
    // post: N = a_i <= x & (any N j < i & a_i > x) & i in [l, r] or (i = -1 if i not exist)
    private static int recursiveBinarySearch(int l, int r, int x) {
        // pre: r - l < 2
        // post: N = a_i <= x & (any N j < i & a_i > x) & i in [l, r] or (i = -1 if i not exist)
        if (r - l < 2) {
            // pre: a_l <= x
            // post: N = a_i <= x & (any N j < i & a_i > x) & i in [l, r]
            if (a.get(l) <= x) return l;
            // pre: a_r <= x
            // post: N = a_i <= x & (any N j < i & a_i > x) & i in [l, r]
            if (a.get(r) <= x) return r;
            // pre: a_l > x & a_r > x & x in a[l..r] -> not exist i, satisfying the requirements. Return -1
            return -1;
        }
        // initialize middle, middle of line
        int m = (l + r) / 2;
        // pre: r - l >= 2
        // post: N = a_i <= x & (any N j < i & a_i > x) & i in [l, r]
        if (a.get(m) <= x) {
            // pre: a_m <= x
            // post: N = a_i <= x & (any N j < i & a_i > x) & i in [l, r] or (i == -1 if i not exist)
            return recursiveBinarySearch(l, m, x);
        } else {
            // pre: a_m > x
            // post: N = a_i <= x & (any N j < i & a_i > x) & i in [l, r] or (i == -1 if i not exist)
            return recursiveBinarySearch(m, r, x);
        }
    }

    public static void main(String[] argm) {
        // initialize x and readX
        // x is x
        // readX:
        //       = true: if x has been read
        //       = false: else
        int x = 0;
        boolean readX = false;
        // pre: argm contain many characters
        // post: a is set all numbers from argm, x is first number from argm
        for (int i = 0; i < argm.length; i++) {
            // pre: number is empty string
            // post: number is number from argm
            StringBuilder number = new StringBuilder("");
            for (int j = 0; j < argm[i].length(); j++) {
                if (Character.isDigit(argm[i].charAt(j)) || (argm[i].charAt(j) == '-' && number.length() == 0)) {
                    // pre: argm[i][j] is part on number
                    // post: append to number
                    number.append(argm[i].charAt(j));
                }
                else {
                    // pre: argm[i][j] is not part on number
                    // post: finish number and add to a or x
                    if (number.length() != 0) {
                        try {
                            if (!readX) {
                                // pre: if x not initialize
                                // post: initialize x
                                x = Integer.parseInt(new String(number));
                                readX = true;
                            } else {
                                // pre: if x initialize
                                // post: add to a
                                a.add(Integer.parseInt(new String(number)));
                            }
                        } catch (NumberFormatException e) {
                            Tools.send(0011);
                        }
                    }
                }
            }
            if (number.length() != 0) {
                try {
                    if (!readX) {
                        // pre: if x not initialize
                        // post: initialize x
                        x = Integer.parseInt(new String(number));
                        readX = true;
                    } else {
                        // pre: if x initialize
                        // post: add to a
                        a.add(Integer.parseInt(new String(number)));
                    }
                } catch (NumberFormatException e) {
                    Tools.send(0011);
                }
            }
        }
        if (a.size() == 0) {
            System.out.print("Error. Empty size of numbers!");
            System.exit(0);
        }
        if (!ordered()){
            // pre: a is not oredered
            // post: finish program
            System.out.print("Error. Not ordered numbers!");
            System.exit(0);
        }
        System.out.print((MODE == 0 ? iterativeBinarySearch(x) : recursiveBinarySearch(0, a.size() - 1, x)) + 1);
        PrintWriter out = null;
        try {
            out = new PrintWriter(new File("test.out"));
        } catch (FileNotFoundException e){

        }
        out.print((MODE == 0 ? iterativeBinarySearch(x) : recursiveBinarySearch(0, a.size() - 1, x)) + 1);
    }
}
