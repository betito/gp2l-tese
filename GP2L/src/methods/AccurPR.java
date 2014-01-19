package methods;

import gp.objects.Book;
import gp.utils.Consts;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class AccurPR {

	ArrayList<Book> Values = null;
	double[] CurrentVoteCount = null;
	double[] TCurrentRound = null;
	double[] TLastRound = null;
	int[][] matrix = null;
	Hashtable<String, Integer> data_id = null;
	Hashtable<String, Integer> srcs_id = null;

	public AccurPR(Hashtable<String, Integer> datas,
			Hashtable<String, Integer> srcs, int[][] matrix) {
		super();

		if (matrix != null) {

			this.data_id = datas;
			this.srcs_id = srcs;
			this.matrix = matrix;

			// printHashtable(this.data_id);
			// printHashtable(this.srcs_id);

			initCurrentVoteCount();

			System.out.println("MATRIX");
			System.out.println("DATA :: " + this.matrix.length);
			System.out.println("SRCS  :: " + this.matrix[0].length);

			computeVote();

		} else {

			System.err.println(this.getClass().getCanonicalName()
					+ ": Data Instance is NULL!");

		}

	}

	private void initCurrentVoteCount() {
		int factsLen = this.data_id.size();
		this.CurrentVoteCount = new double[factsLen];
		this.TCurrentRound = new double[factsLen];
		this.TLastRound = new double[factsLen];

		for (int i = 0; i < this.CurrentVoteCount.length; i++) {
			this.CurrentVoteCount[i] = 0.0;
			this.TCurrentRound[i] = Consts.INITIAL_TRUST;
			this.TLastRound[i] = Consts.INITIAL_TRUST;
		}

	}

	/*
	 * compute the C(T_i): for the sum of the vote for all source who has that
	 * value.
	 */
	private void computeVote() {
		double sum = 0.0;
		boolean keep_iteration = true;

		for (int round = 0; keep_iteration && (round < 10); round++) {
			/*
			 * compute the fact score
			 */
			for (Enumeration<String> eni = this.data_id.keys(); eni
					.hasMoreElements();) {
				String fname = eni.nextElement();
				int pos = (this.data_id.get(fname)).intValue();
				int values[] = matrix[pos];

				for (int j = 0; j < values.length; j++) {
					if (values[j] != 0) {
						sum += calculateLn(this.TCurrentRound[j]);
					}
				}
				// System.out
				// .printf("------------------\nSum for Fact\t[%s]\t%.3f\n______________________\n",
				// fname, sum);

				this.CurrentVoteCount[pos] = sum;
				sum = 0.0;

			}

			String stri = "";
			String strj = "";
			int avgcount = 0;

			/*
			 * compute the source thrustness score
			 */
			for (Enumeration<String> eni = this.srcs_id.keys(); eni
					.hasMoreElements();) {
				stri = eni.nextElement();
				int possrc = (this.srcs_id.get(stri)).intValue();
				strj = "";
				avgcount = 0;
				sum = 0.0;
				// System.out.printf("[%s]\n", stri);

				for (Enumeration<String> enj = this.data_id.keys(); enj
						.hasMoreElements();) {
					strj = enj.nextElement();
					int posf = (this.data_id.get(strj)).intValue();

					// System.out.printf("\n[%s]-[%s]-[%d]-[%d]=[%d]", strj,
					// stri,
					// posf, possrc, matrix[posf][possrc]);
					if (matrix[posf][possrc] == Consts.OCCUR) {
						// System.out.printf("\t[%.3f]",
						// this.CurrentVoteCount[posf]);
						// System.out.printf("\t[%.3f]",
						// calculateTrust(this.CurrentVoteCount[posf]));
						sum += calculateTrust(this.CurrentVoteCount[posf]);
						avgcount++;
					}
				}

				// System.out.printf("\n[%s]\t[%.3f]/[%d]\t=\t[%.5f]\n", stri,
				// sum, avgcount, (sum / (double) avgcount));

				this.TCurrentRound[possrc] = (sum / (double) avgcount);

			}

			System.out
					.printf("\n======================================= \nRound :: [%d]\n",
							round);
			// printCurrentVoteCount();
			// System.out.println();
			// printTrustCurrentRound();

			double sim = (CosineSim.measureCosineSimilarity(this.TCurrentRound,
					this.TLastRound));

			System.out.printf("SIM\t::\t[%.5f]\n", sim);
			if (1 - sim < 0.0001) {
				keep_iteration = false;
			}

			saveCurrentRound();

		}

	}

	private void saveCurrentRound() {
		for (int x = 0; x < this.TCurrentRound.length; x++) {
			this.TLastRound[x] = this.TCurrentRound[x];
		}

	}

	private double calculateLn(double trust) {

		Double d = new Double(0.0);

		d = -Math.log(1.0 - trust);
		// System.out.println("Entrou\t" + trust + "\ncalculou\t" + d);

		return d;

	}

	private double calculateTrust(double votecount) {

		return (1.0 - Math.pow(Math.E, (Consts.EXP_TRUST * votecount)));

	}

}