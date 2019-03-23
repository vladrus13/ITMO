import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Vlad on 07.10.2018.
 */
public class SumHexFile {
    public static void main(String[] ConsoleInput) {
        Scanner in = null;
        try {
            try {
                //in = new Scanner(new File("test.txt"));
                in = new Scanner(new File(ConsoleInput[0]), "UTF-8");
            } catch (FileNotFoundException e) {
                System.out.print("ОЧЕНЬ ЖАЛЬ. Файл " + ConsoleInput[0] + " не существует");
                in.close();
                System.exit(0);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("ОЧЕНЬ ЖАЛЬ. Вы дали неверное имя файла");
                in.close();
                System.exit(0);
            }
            long answer = 0;
            while (in.hasNext()) {
                String temp = in.next();
                temp = temp.toLowerCase();
                StringBuffer real = new StringBuffer("");
                for (int i = 0; i < temp.length(); i++) {
                    if ((temp.charAt(i) <= '9' && temp.charAt(i) >= '0') || temp.charAt(i) == '-' || temp.charAt(i) == 'x' || (temp.charAt(i) >= 'a' && temp.charAt(i) <= 'f')) {
                        real.append(temp.charAt(i));
                    } else {
                        real.append(' ');
                    }
                }
                String[] numbers = real.toString().split(" ");
                for (int i = 0; i < numbers.length; i++) {
                    if (numbers[i].length() > 0) {
                        try {
                            if (numbers[i].length() > 2 && numbers[i].charAt(1) == 'x') {
                                answer += Integer.decode(numbers[i]);
                            } else
                                answer += Integer.parseInt(numbers[i], 10);
                        } catch (NumberFormatException e) {
                            System.out.print("ОЧЕНЬ ЖАЛЬ. Введено не число");
                        }
                    }
                }
            }
            try {
                FileWriter out = new FileWriter(new File(ConsoleInput[1]), "UTF-8");
                //FileWriter out = new FileWriter(new File("test.out"));
                out.write(String.valueOf(answer));
                out.close();
                in.close();
            } catch (IOException e) {
                // что это ????
            } catch (NullPointerException e) {
                System.out.print("ОЧЕНЬ ЖАЛЬ. Неверное имя файла");
            }
        } catch (Exception e) {
            System.out.print("ОЧЕНЬ ЖАЛЬ. Вы выиграли. Необработанная ошибка");
        } finally {
            try {
                in.close();
            } catch (NullPointerException e) {
                System.out.print("ОЧЕНЬ ЖАЛЬ. Неверное имя файла");
            }
        }
    }
}