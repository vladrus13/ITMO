import java.math.BigInteger;

public class SumBigInteger {
	public static void main(String[] input) {
		BigInteger numbers[][] = new BigInteger[input.length][];
		// processing input
		for (int i = 0; i < input.length; i++){
			StringBuffer temp = new StringBuffer("");
			for (int j = 0; j < input[i].length(); j++) {
				if ((input[i].charAt(j) <= '9' && input[i].charAt(j) >= '0') || input[i].charAt(j) == '-') {
					temp.append(input[i].charAt(j));
				} else {
					temp.append(' ');
				}
			}
			String stringNumbers[] = (new String(temp)).split(" ");
			numbers[i] = new BigInteger[stringNumbers.length];
			for (int j = 0; j < stringNumbers.length; j++) {
				if (stringNumbers[j].length() > 0) {
					numbers[i][j] = new BigInteger(stringNumbers[j]);
				} else {
					numbers[i][j] = BigInteger.ZERO;
				}
			}
        	}
		// getSum
		BigInteger answer = BigInteger.ZERO;
		for (int i = 0; i < numbers.length; i++){
			for (int j = 0; j < numbers[i].length; j++){
				answer = answer.add(numbers[i][j]);
			}
		}
		System.out.println(answer);
	}
}