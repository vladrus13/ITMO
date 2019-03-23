public class Sum {
	public static void main(String[] input) {
		double numbers[][] = new double[input.length][];
		// processing input
		for (int i = 0; i < input.length; i++){
			input[i].replaceAll("\\s", " ");
			double tempNumbers[] = new double[input[i].length()];
			int j = 0, countNumbers = 0;
			while (j < input[i].length()){
				boolean negative = false;
				while (j < input[i].length() && Character.isWhitespace(input[i].charAt(j))){
					j++;
				}
				if (j != input[i].length()){
					countNumbers++;
					tempNumbers[countNumbers - 1] = 0;
					if (input[i].charAt(j) == '-') {
						negative = true;
						j++;
					}
					if (input[i].charAt(j) == '+') {
						j++;
					}
				}
				int mantiss = 1;
				boolean be_mantiss = false;
				while (j < input[i].length() && (input[i].charAt(j) >= '0' && input[i].charAt(j) <= '9' || input[i].charAt(j) == '.')){
					if (input[i].charAt(j) == '.') {
						be_mantiss = true;
					} else {
						if (be_mantiss == true) mantiss *= 10;
						tempNumbers[countNumbers - 1] = tempNumbers[countNumbers - 1] * 10 + (input[i].charAt(j) - '0');
						j++;
					}
				}
				if (countNumbers > 0) tempNumbers[countNumbers - 1] /= mantiss;
				if (negative == true) {
					tempNumbers[countNumbers - 1] *= -1;
					negative = false;
				}
			}
			double realNumbers[] = new double[countNumbers];
			for (j = 0; j < countNumbers; j++){
				realNumbers[j] = tempNumbers[j];
			}
			numbers[i] = realNumbers;
        	}
		// getSum
		double answer = 0;
		for (int i = 0; i < numbers.length; i++){
			for (int j = 0; j < numbers[i].length; j++){
				answer += numbers[i][j];
			}
		}
		System.out.println(answer);
	}
}