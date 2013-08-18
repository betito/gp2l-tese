package methods;

import gp.objects.Book;
import gp.utils.Consts;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Facto {

	ArrayList<Book> Values = null;
	double[] CurrentVoteCount = null;
	double[] TCurrentRound = null;
	double[] TLastRound = null;
	int[][] matrix = { { 1, 1, 0, 1, 1 }, { 1, 0, 1, 0, 1 }, { 1, 1, 1, 0, 1 } };
	Hashtable<String, Integer> facts_id = null;
	Hashtable<String, Integer> srcs_id = null;

	public Facto(final String[] facts_names, final String[] sources_names) {
		super();

		this.facts_id = new Hashtable<String, Integer>();

		for (int i = 0; i < facts_names.length; i++) {
			facts_id.put(facts_names[i], new Integer(i));
		}

		this.srcs_id = new Hashtable<String, Integer>();

		for (int i = 0; i < sources_names.length; i++) {
			srcs_id.put(sources_names[i], new Integer(i));
		}

		printHashtable(this.facts_id);
		printHashtable(this.srcs_id);

		initCurrentVoteCount();

		computeVote();

	}

	private void initCurrentVoteCount() {
		this.CurrentVoteCount = new double[this.srcs_id.size()];
		this.TCurrentRound = new double[this.srcs_id.size()];
		this.TLastRound = new double[this.srcs_id.size()];

		for (int i = 0; i < this.CurrentVoteCount.length; i++) {
			this.CurrentVoteCount[i] = 0.0;
			this.TCurrentRound[i] = Consts.INITIAL_TRUST;
			this.TLastRound[i] = Consts.INITIAL_TRUST;
		}

	}

	private void printHashtable(final Hashtable<String, Integer> attribute) {

		for (Enumeration<String> en = attribute.keys(); en.hasMoreElements();) {
			String fk = en.nextElement();
			int fid = ((Integer) attribute.get(fk)).intValue();

			System.out.printf("[%s] :: [%d]\n", fk, fid);
		}

		System.out.println("...");
	}

	public Facto(ArrayList<Book> values) {
		super();
		Values = values;

	}

	public ArrayList<Book> getValues() {
		return Values;
	}

	public void setValues(ArrayList<Book> values) {
		Values = values;
	}

	private boolean prepareMatrix() {

		return false;
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
			for (Enumeration<String> eni = this.facts_id.keys(); eni
					.hasMoreElements();) {
				String fname = eni.nextElement();
				int pos = (this.facts_id.get(fname)).intValue();
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

				for (Enumeration<String> enj = this.facts_id.keys(); enj
						.hasMoreElements();) {
					strj = enj.nextElement();
					int posf = (this.facts_id.get(strj)).intValue();

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
			printCurrentVoteCount();
			System.out.println();
			printTrustCurrentRound();


			double sim = (CosineSim.measureCosineSimilarity(
					this.TCurrentRound, this.TLastRound));
			
			System.out.printf("SIM\t::\t[%.5f]\n", sim);
			if (1 - sim < 0.0001){
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

	private void printTrustCurrentRound() {
		int pos = 0;
		String fname = "";
		for (Enumeration<String> eni = this.srcs_id.keys(); eni
				.hasMoreElements();) {
			fname = eni.nextElement();
			pos = (this.srcs_id.get(fname)).intValue();
			System.out.printf("[%s]\t::\t[%.5f]\n", fname,
					this.TCurrentRound[pos]);
		}

		for (Enumeration<String> eni = this.srcs_id.keys(); eni
				.hasMoreElements();) {
			fname = eni.nextElement();
			pos = (this.srcs_id.get(fname)).intValue();
			System.out.printf("%.5f, ", this.TLastRound[pos]);
		}
		System.out.println("");
		for (Enumeration<String> eni = this.srcs_id.keys(); eni
				.hasMoreElements();) {
			fname = eni.nextElement();
			pos = (this.srcs_id.get(fname)).intValue();
			System.out.printf("%.5f, ", this.TCurrentRound[pos]);
		}

	}

	private void printCurrentVoteCount() {
		int pos = 0;
		String fname = "";
		for (Enumeration<String> eni = this.facts_id.keys(); eni
				.hasMoreElements();) {
			fname = eni.nextElement();
			pos = (this.facts_id.get(fname)).intValue();
			System.out.printf("[%s]\t::\t[%.5f]\n", fname,
					this.CurrentVoteCount[pos]);
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
