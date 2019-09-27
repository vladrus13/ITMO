import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ReverseMin {
    public static void main(String[] agrr){
        FastScanner in = new FastScanner(System.in);
        ArrayList <Integer> md = new ArrayList<>();
        ArrayList <Integer> mv = new ArrayList<>();
        ArrayList <String[]> data = new ArrayList<>();
        while (in.hasNext()) {
            data.add(Tools.parseStringInt(in.nextLine()));
        }
        for (int i = 0; i < data.size(); i++){
            for (int j = 0; j < data.get(i).length; j++){
                try {
                    while (md.size() <= i) {
                        md.add(Integer.MAX_VALUE);
                    }
                    while (mv.size() <= j) {
                        mv.add(Integer.MAX_VALUE);
                    }
                    if (data.get(i)[j].length() != 0) {
                        md.set(i, Math.min(md.get(i), Integer.parseInt(data.get(i)[j])));
                        mv.set(j, Math.min(mv.get(j), Integer.parseInt(data.get(i)[j])));
                    }
                } catch (IndexOutOfBoundsException e){
                    System.out.print("Выход за границы массива");
                }
            }
        }
        for (int i = 0; i < data.size(); i++){
            for (int j = 0; j < data.get(i).length; j++){
                try {
                    System.out.print(String.valueOf(Math.min(md.get(i), mv.get(j))) + ' ');
                }
                catch (Exception ex) {}
            }
                System.out.println("");
        }
    }
}
