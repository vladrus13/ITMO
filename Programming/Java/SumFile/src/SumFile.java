/**
 * Created by Vlad on 27.09.2018.
 */

import java.util.*;
import java.io.*;
import java.lang.StringBuffer;

public class SumFile {
    public static void main(String[] ConsoleInput) {
        Scanner in = null;
        try {
            try {
                in = new Scanner(new File(ConsoleInput[0]));
            } catch (FileNotFoundException e) {
                System.out.print("ОЧЕНЬ ЖАЛЬ. Файл " + ConsoleInput[0] + " не существует");
                in.close();
                System.exit(0);
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.print("ОЧЕНЬ ЖАЛЬ. Вы дали неверное имя файла");
                in.close();
                System.exit(0);
            }
            int answer = 0;
            while (in.hasNext()) {
                String temp = in.next();
                StringBuffer real = new StringBuffer("");
                for (int i = 0; i < temp.length(); i++) {
                    if ((temp.charAt(i) <= '9' && temp.charAt(i) >= '0') || temp.charAt(i) == '-') {
                        real.append(temp.charAt(i));
                    } else {
                        real.append(' ');
                    }
                }
                String[] numbers = real.toString().split(" ");

                for (int i = 0; i < numbers.length; i++) {
                    try {
                        answer += Integer.parseInt(numbers[i], 10);
                    } catch (NumberFormatException e) {
                        System.out.print("ОЧЕНЬ ЖАЛЬ. Введено не число");
                    }
                }
            }
            try {
                FileWriter out = new FileWriter(new File(ConsoleInput[1]));
                out.write(String.valueOf(answer));
                out.close();
                in.close();
            }
            catch (IOException e){
                // что это ????
            }
            catch (NullPointerException e){
                System.out.print("ОЧЕНЬ ЖАЛЬ. Неверное имя файла");
            }
        } catch (Exception e) {
            System.out.print("ОЧЕНЬ ЖАЛЬ. Вы выиграли. Необработанная ошибка");
        }
        finally {
            try {
                in.close();
            } catch (NullPointerException e) {
                System.out.print("ОЧЕНЬ ЖАЛЬ. Неверное имя файла");
            }
        }
    }
}
