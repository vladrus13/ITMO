import java.util.Scanner;

public class Reverse {
	public static void main(String[] stdinputargsr) {
		int START_SIZE = 1;
		Scanner intput = new Scanner(System.in);
		String data[] = new String[START_SIZE];
		// we dont know about size of input. let's model the stacks behavior. if the massive is overflowed, then increase its size by K times. therefore, we will overwrite the array only log(K)(n) times. its be optimal (optimal, however, it was necessary to give the input size).
		int K = 10;

		// okeey, read
		for (int i = 0; i == i; i++) {
			if (!intput.hasNextLine()) break; // if we read all, run out
			data[i] = intput.nextLine(); // read from console
			if (data.length - 1 == i) {
				// we increase size
				String temp[] = data;
				data = new String [temp.length * K];
				// rewrite data
				for (int j = 0; j <= i; j++) {
					data[j] = temp[j];
				}
			}
		}
		// write the massive/
		for (int i = data.length - 1; i >= 0; i--) {
			if (data[i] == null) {

				continue;

			}
			String temp[] = data[i].split(" ");
			for (int j = temp.length - 1; j >= 0; j--) {
				System.out.print(temp[j] + ' ');
			}
			if (i > 0) {
				System.out.print("\n");
			}
		}
	}
}
// K = 2 Finished in 6062 ms
// K = 3 Finished in 5852 ms
// K = 4 Finished in 5835 ms
// K = 10 Finished in 5831 ms
// K = 100 Finished in 5907 ms
// K = 10000 Finished in 11742 ms