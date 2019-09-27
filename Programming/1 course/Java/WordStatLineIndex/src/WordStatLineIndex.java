import javafx.util.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.NoSuchFileException;
import java.util.*;

public class WordStatLineIndex {
    public static void main(String[] args) {
        int DEBUG = 1;
        TreeMap<String, TreeSet<NPair>> data = new TreeMap<>();
        FastScanner in = null;
        in = (DEBUG == 0 ? new FastScanner(new File("test.txt")) : new FastScanner(new File(args[0])));
        int numLine = 1;
        String[] word;
        while (in.hasNext()) {
            word = Tools.parseStringInt(in.nextLine());
            for (int i = 0; i < word.length; i++) {
                if (word[i].length() != 0) {
                    word[i] = word[i].toLowerCase();
                    if (!data.containsKey(word[i])) {
                        data.put(word[i], new TreeSet<>());
                    }
                    NPair usagee = new NPair (numLine, i + 1);
                    data.get(word[i]).add(usagee);
                }
            }
            numLine++;
        }
        in.close();
        Iterator<Map.Entry<String, TreeSet<NPair>>> it = data.entrySet().iterator();
        PrintWriter out = null;
        try {
            out = (DEBUG == 0 ? new PrintWriter(new File("test.out")) : new PrintWriter(new File(args[1]), "UTF-8"));
        } catch (FileNotFoundException e) {
            Tools.send(0001);
        } catch (UnsupportedEncodingException e){
            Tools.send(0004);
        }
        SortedSet<NPair> temp;
        while (it.hasNext()) {
            Map.Entry<String, TreeSet<NPair>> entry = it.next();
            out.print(entry.getKey() + " " + entry.getValue().size() + ' ');
            temp = entry.getValue();
            Iterator itSet = temp.iterator();
            while (itSet.hasNext()) {
                NPair pai = (NPair) itSet.next();
                out.print(pai.getFirst().toString() + ':' + pai.getSecond().toString() + (itSet.hasNext() ? " " : ""));
            }
            out.println("");
        }
        out.close();
        //System.exit(0);
    }
}
