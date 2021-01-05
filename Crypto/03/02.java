import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.*;

public class MerkleTree {

    public static byte[] getSha256(byte[] value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value);
            return md.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<Long, byte[]> tree;
    private static Map<Long, String> real;
    private static long sizeTree;

    private static byte[] getByte(long position) {
        if (tree.containsKey(position)) {
            return tree.get(position);
        }
        return null;
    }

    private static void hahahaGoBrr() {
        long[] elements = real.keySet().stream().mapToLong(element -> element + sizeTree - 1).toArray();
        Set<Long> uniq = new HashSet<>();
        while (true) {
            for (long element : elements) {
                long parent = (element - 1) / 2;
                uniq.add(parent);
                findParent(parent);
            }
            if (elements.length == 1 && elements[0] == 0) {
                break;
            }
            elements = uniq.stream().mapToLong(element -> element).toArray();
            uniq.clear();
        }
    }

    public static void main(String[] args) {
        tree = new HashMap<>();
        real = new HashMap<>();
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int h = fastReader.nextInt();
        sizeTree = (long) Math.pow(2, h);
        int n = fastReader.nextInt();
        for (int i = 0; i < n; i++) {
            long position = fastReader.nextLong();
            byte[] parent;
            String x = fastReader.next();
            real.put(position, x);
            parent = getSha256(getParent(Base64.getDecoder().decode(x)));
            tree.put(position + sizeTree - 1, parent);
        }
        hahahaGoBrr();
        int q = fastReader.nextInt();
        for (int i = 0; i < q; i++) {
            long position = fastReader.nextLong();
            printWriter.println(position + " " + real.get(position));
            long realPosition = position + sizeTree - 1;
            while (realPosition != 0) {
                long parent = (realPosition - 1) / 2;
                long neighbour = parent * 4 + 3 - realPosition;
                printWriter.println(toString(getByte(neighbour)));
                realPosition = parent;
            }
        }
        printWriter.close();
    }

    private static void findParent(long position) {
        byte[] left = getByte(position * 2 + 1);
        byte[] right = getByte(position * 2 + 2);
        byte[] result = getParent(left, right);
        tree.put(position, result == null ? null : getSha256(result));
    }

    private static byte[] getParent(byte[] left, byte[] right) {
        if (left == null && right == null) {
            return null;
        }
        int size = 2 + (left == null ? 0 : left.length) + (right == null ? 0 : right.length);
        byte[] result = new byte[size];
        int currentPosition = 0;
        result[currentPosition] = 0x00000001;
        currentPosition++;
        if (left != null) {
            for (byte b : left) {
                result[currentPosition] = b;
                currentPosition++;
            }
        }
        result[currentPosition] = 0x00000002;
        currentPosition++;
        if (right != null) {
            for (byte b : right) {
                result[currentPosition] = b;
                currentPosition++;
            }
        }
        return result;
    }

    private static byte[] getParent(byte[] child) {
        if (child.length == 0) return null;
        byte[] result = new byte[child.length + 1];
        result[0] = 0x00000000;
        int i = 1;
        for (byte b : child) {
            result[i] = b;
            i++;
        }
        return result;
    }

    private static String toString(byte[] array) {
        if (array == null) {
            return "null";
        } else {
            return new String(Base64.getEncoder().encode(array));
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
