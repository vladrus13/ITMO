/**
 * Created by Vlad on 30.09.2018.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.PrintWriter;

public class WordStatWords {
    public static void main(String[] ConsoleInput) {
        TreeMap<String, Integer> data = new TreeMap<String, Integer>();
        BufferedReader in = null;
        try {
            try {
                in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(ConsoleInput[0])), "UTF-8"));
            } catch (FileNotFoundException e) {
                System.out.print("FileNotFound");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("ErrorNameFile");
                in.close();
                System.exit(0);
            }
            try {
                String read = "";
                while ((read = in.readLine()) != null) {
                    read = read.toLowerCase();
                    String temp =  read;
                    String[] mass = temp.split("[^'\\p{L}\\p{Pd}]");
                    for (int i = 0; i < mass.length; i++) {
                        if (mass[i].length() != 0) {
                            data.put(mass[i], data.containsKey(mass[i]) ? data.get(mass[i]) + 1 : 1);
                        }
                    }
                }
            } catch (Exception ex){
                System.out.print("Недостаточно прав для доступа к файлу");
                in.close();
                System.exit(0);
            }
            try {
                PrintWriter out = new PrintWriter(new File(ConsoleInput[1]), "UTF-8");
                for (Map.Entry<String, Integer> entry : data.entrySet()) {
                    out.println(entry.getKey() + " " + entry.getValue());
                }
                out.close();
                in.close();
            } catch (IOException e) {
                // что это ????
            } catch (NullPointerException e) {
                System.out.print("ErrorNameFile");
                in.close();
                System.exit(0);
            }
        } catch (Exception e){
            System.out.print("Увожение. Вы сломали все.");
            try {
                in.close();
            }
            catch (IOException ex){
                // aaaand??? Exception recurtion?
            }
            System.exit(0);
        }
    }
}
