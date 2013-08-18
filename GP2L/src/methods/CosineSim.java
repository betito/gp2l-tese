package methods;

public class CosineSim {

	private static double CalcDotProduct(double[] a, double[] b) {
		double output = 0;
		int V_LENGTH = a.length;

		if (V_LENGTH > b.length) {
			V_LENGTH = b.length;
		}

		if (a.length != b.length) {
			System.err
					.println("Different array length \nUsing the shortest length = "
							+ V_LENGTH);
		}

		for (int i = 0; i < V_LENGTH; i++) {
			output += a[i] * b[i];
		}

		System.out.printf("\nCalc Dot\nOutput\t=\t[%.5f]\n", output);

		return output;
	}

	private static double CalcMagnitude(double[] x) {
		double output = 0;
		double part_sum = 0;
		int V_LENGTH = x.length;

		for (int i = 0; i < V_LENGTH; i++) {
			part_sum += Math.pow(x[i], 2);
		}

		output = Math.sqrt(part_sum);

		System.out.printf("Calc Mag\nOutput\t=\t[%.5f]\n", output);

		return output;
	}

	public static double measureCosineSimilarity(double[] a, double[] b) {

		double cos = 0;
		double num = CalcDotProduct(a, b);
		double den1 = CalcMagnitude(a);
		double den2 = CalcMagnitude(b);

		if ((den1 != 0) && (den2 != 0)) {
			cos = num / (den1 * den2);
		}

		System.out.println("Cosine \t " + cos);
		
		return cos;

	}
}
