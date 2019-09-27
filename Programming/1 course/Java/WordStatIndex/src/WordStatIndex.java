import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.NoSuchFileException;
import java.util.*;

public class WordStatIndex {
    public static void main(String[] args) {
        int DEBUG = 1;
        LinkedHashMap<String, TreeSet<Integer>> data = new LinkedHashMap<>();
        FastScanner in = null;
        in = (DEBUG == 0 ? new FastScanner(new File("test.txt")) : new FastScanner(new File(args[0])));
        int numWords = 1;
        String word = "";
        while (in.hasNext()) {
            word = in.nextString();
            if (word.length() != 0) {
                word = word.toLowerCase();
                if (!data.containsKey(word)) {
                    data.put(word, new TreeSet<>());
                }
                data.get(word).add(numWords);
                numWords++;
            }
        }
        in.close();
        Iterator<Map.Entry<String, TreeSet<Integer>>> it = data.entrySet().iterator();
        PrintWriter out = null;
        try {
            out = (DEBUG == 0 ? new PrintWriter(new File("test.out")) : new PrintWriter(new File(args[1]), "UTF-8"));
        } catch (FileNotFoundException e) {
            Tools.send(0001);
        } catch (UnsupportedEncodingException e) {
            Tools.send(0004);
        }
        SortedSet<Integer> temp;
        while (it.hasNext()) {
            Map.Entry<String, TreeSet<Integer>> entry = it.next();
            out.print(entry.getKey() + " " + entry.getValue().size() + ' ');
            temp = entry.getValue();
            Iterator itSet = temp.iterator();
            while (itSet.hasNext()) {
                out.print(itSet.next().toString() + (itSet.hasNext() ? " " : ""));
            }
            out.print('\n');
        }
        out.close();
        //System.exit(0);
    }
}
