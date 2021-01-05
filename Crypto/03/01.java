import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.StringTokenizer;
 
public class MerkleTree {
 
    public static String getSha256(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
 
    public static byte[] getSha256(byte[] value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value);
            return md.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
 
    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int h = fastReader.nextInt();
        byte[] shaRoot = Base64.getDecoder().decode(fastReader.nextLine());
        int q = fastReader.nextInt();
        for (int i = 0; i < q; i++) {
            int position = fastReader.nextInt();
            String temp = fastReader.next();
            byte[] data = new byte[0];
            boolean isCurrentNull = true;
            if (!temp.equals("null")) {
                isCurrentNull = false;
                data = Base64.getDecoder().decode(temp);
                byte[] newData = new byte[data.length + 1];
                newData[0] = 0x00000000;
                int j = 1;
                for (byte b : data) {
                    newData[j] = b;
                    j++;
                }
                data = getSha256(newData);
            }
            ArrayList<byte[]> neighbours = new ArrayList<>();
            ArrayList<Boolean> isEmpty = new ArrayList<>();
            for (int j = 0; j < h; j++) {
                temp = fastReader.nextLine();
                isEmpty.add("null".equals(temp));
                if (!temp.equals("null")) {
                    neighbours.add(Base64.getDecoder().decode(temp));
                } else {
                    neighbours.add(null);
                }
            }
            for (int j = 0; j < h; j++) {
                byte[] neib = (isEmpty.get(j) ? new byte[0] : neighbours.get(j));
                byte[] current = (isCurrentNull ? new byte[0] : data);
                int size = 2 + neib.length + current.length;
                byte[] result = new byte[size];
                isCurrentNull = true;
                if (size != 2) {
                    isCurrentNull = false;
                    if (position % 2 == 0) {
                        // left
                        setBytes(current, neib, result);
                    } else {
                        // right
                        setBytes(neib, current, result);
                    }
                } else {
                    result = new byte[0];
                }
                data = getSha256(result);
                position /= 2;
            }
            printWriter.println(Arrays.equals(data, shaRoot) ? "YES" : "NO");
        }
        printWriter.close();
    }
 
    private static void setBytes(byte[] neib, byte[] current, byte[] result) {
        int currentPosition = 0;
        result[currentPosition] = 0x00000001;
        currentPosition++;
        for (byte b : neib) {
            result[currentPosition] = b;
            currentPosition++;
        }
        result[currentPosition] = 0x00000002;
        currentPosition++;
        for (byte b : current) {
            result[currentPosition] = b;
            currentPosition++;
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
