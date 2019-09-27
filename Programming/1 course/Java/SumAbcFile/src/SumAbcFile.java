/**
 * Created by Vlad on 02.10.2018.
 */

import java.util.*;
import java.io.*;
import java.lang.StringBuffer;

public class SumAbcFile {

    public static LongNumber sum(LongNumber a, LongNumber b) {
        int ym = 0, temp = 0;
        if (a.num.length() < b.num.length()) {
            LongNumber t = a;
            a = b;
            b = t;
        }
        for (int i = 0; i < a.num.length(); i++) {
            temp = (a.num.charAt(i) + b.num.charAt(i) + ym > 'z' ? 1 : 0);
            a.num.setCharAt(i, (char) (a.num.charAt(i) + b.num.charAt(i) + ym > 'z' ? a.num.charAt(i) + b.num.charAt(i) + ym - 'z' : a.num.charAt(i) + b.num.charAt(i) + ym));
            ym = temp;
        }
        if (ym == 1) {
            a.num.append(1);
        }
        return a;
    }

    public static void main(String[] Console) throws IOException{
        Scanner in = null;
        FileWriter out = null;
        try {
            try {
                in = new Scanner(new File(Console[0]));
            } catch (FileNotFoundException e) {
                System.out.print("ОЧЕНЬ ЖАЛЬ. Файл " + Console[0] + " не существует");
                in.close();
                System.exit(0);
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.print("ОЧЕНЬ ЖАЛЬ. Вы дали неверное имя файла");
                in.close();
                System.exit(0);
            }
            LongNumber answer = new LongNumber();
            while (in.hasNext()) {
                String temp = in.next();
                StringBuffer real = new StringBuffer("");
                for (int i = 0; i < temp.length(); i++) {
                    if ((temp.charAt(i) <= 'a' && temp.charAt(i) >= 'z') || temp.charAt(i) == '-') {
                        real.append(temp.charAt(i));
                    } else {
                        real.append(' ');
                    }
                }
                String[] numbers = real.toString().split(" ");

                for (int i = 0; i < numbers.length; i++) {
                    try {
                        answer = sum(answer, new LongNumber(numbers[i]));
                    } catch (NumberFormatException e) {
                        System.out.print("ОЧЕНЬ ЖАЛЬ. Введено не число");
                    }
                }
            }
            try {
                out = new FileWriter(new File(Console[1]));
                out.write(answer.num.toString());
                out.close();
                in.close();
            }
            catch (NullPointerException e){
                System.out.print("ОЧЕНЬ ЖАЛЬ. Неверное имя файла");
            }
        }
        catch (Exception e){
            System.out.print("ОЧЕНЬ ЖАЛЬ. Вы выиграли. Необработанная ошибка");
            try {
                in.close();
            } catch (NullPointerException ex) {
                System.out.print("ОЧЕНЬ ЖАЛЬ. Неверное имя файла " + Console[0]);
            }
            try {
                out.close();
            } catch (NullPointerException ex) {
                System.out.print("ОЧЕНЬ ЖАЛЬ. Неверное имя файла " + Console[1]);
            }
        }
    }
}

class LongNumber {
    StringBuffer num;

    LongNumber() {
        num = new StringBuffer(num);
    }

    LongNumber(String s) {
        this.num = (new StringBuffer(s)).reverse();
    }

}