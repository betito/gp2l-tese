package methods;

import gp.objects.Book;
import gp.objects.GenericObject;
import gp.objects.ValueComparatorAsc;
import gp.objects.ValueComparatorDesc;
import gp.utils.Consts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class Facto {

	ArrayList<Book> Values = null;
	double[] CurrentVoteCount = null;
	double[] TCurrentRound = null;
	double[] TLastRound = null;
	int[][] matrix = null;
	Hashtable<String, Integer> facts_id = null;
	Hashtable<String, Integer> srcs_id = null;
	Hashtable<String, String> general_facts_id = null;
	double exec_time;

	public Facto(Hashtable<String, Integer> facts,
			Hashtable<String, Integer> srcs,
			Hashtable<String, String> generalids, int[][] matrix) {
		super();

		if (matrix != null) {
			this.facts_id = facts;
			this.srcs_id = srcs;
			this.general_facts_id = generalids;
			this.matrix = matrix;

			// printHashtable(this.facts_id);
			// printHashtable(this.srcs_id);

			initCurrentVoteCount();

			System.out.println("MATRIX");
			System.out.println("DATA :: " + this.matrix.length);
			System.out.println("SRCS :: " + this.matrix[0].length);

			computeVote();

		} else {

			System.err.println(this.getClass().getCanonicalName()
					+ ": Data Instance is NULL!");

		}

	}

	private void initCurrentVoteCount() {
		int factsLen = this.facts_id.size();
		this.CurrentVoteCount = new double[factsLen];
		this.TCurrentRound = new double[factsLen];
		this.TLastRound = new double[factsLen];

		for (int i = 0; i < this.CurrentVoteCount.length; i++) {
			this.CurrentVoteCount[i] = 0.0;
			this.TCurrentRound[i] = Consts.INITIAL_TRUST;
			this.TLastRound[i] = Consts.INITIAL_TRUST;
		}

	}

	@SuppressWarnings("unused")
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

	/*
	 * compute the C(T_i): for the sum of the vote for all source who has that
	 * value.
	 */
	public void computeVote() {
		double sum = 0.0, initT, endT;
		boolean keep_iteration = true;

		initT = System.currentTimeMillis();
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
			// printCurrentVoteCount();
			// System.out.println();
			// printTrustCurrentRound();

			double sim = (CosineSim.measureCosineSimilarity(this.TCurrentRound,
					this.TLastRound));

			System.out.printf("SIM\t::\t[%.5f]\n", sim);
			if (1 - sim < Consts.VARIATION) {
				keep_iteration = false;
			}

			saveCurrentRound();

		}

		endT = System.currentTimeMillis();
		this.exec_time = endT - initT;
		// System.err.printf("Exec time\t%g\n", this.exec_time);

	}

	private void saveCurrentRound() {
		for (int x = 0; x < this.TCurrentRound.length; x++) {
			this.TLastRound[x] = this.TCurrentRound[x];
		}

	}

	@SuppressWarnings("unused")
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

	public void printFinalTrust() {
		int pos = 0;
		String fname = "";
		System.out.println("Final Trust\n------------------");
		for (Enumeration<String> eni = this.srcs_id.keys(); eni
				.hasMoreElements();) {
			fname = eni.nextElement();
			pos = (this.srcs_id.get(fname)).intValue();
			System.out.printf("%s\t=\t%.5f\n", fname, this.TCurrentRound[pos]);
		}

	}

	public void printFinalTrustAsc() {
		List<GenericObject> trustSrcList = new ArrayList<GenericObject>();
		int pos = 0;
		String fname = "";
		System.out.println("Final Trust Asc Order\n------------------");
		for (Enumeration<String> eni = this.srcs_id.keys(); eni
				.hasMoreElements();) {
			fname = eni.nextElement();
			pos = (this.srcs_id.get(fname)).intValue();
			trustSrcList.add(new GenericObject(fname, this.TCurrentRound[pos]));
		}

		
		// sort in ascending order
		Collections.sort(trustSrcList, new ValueComparatorAsc());

		for (Iterator iterator = trustSrcList.iterator(); iterator.hasNext();) {
			GenericObject genObj = (GenericObject) iterator.next();
			System.out.printf("%s\t=\t%.3g\n", genObj.getText1(),
					genObj.getValue1());
		}

	}
	
	public void printFinalTrustDesc() {
		List<GenericObject> trustSrcList = new ArrayList<GenericObject>();
		int pos = 0;
		String fname = "";
		System.out.println("Final Trust Asc Order\n------------------");
		for (Enumeration<String> eni = this.srcs_id.keys(); eni
				.hasMoreElements();) {
			fname = eni.nextElement();
			pos = (this.srcs_id.get(fname)).intValue();
			trustSrcList.add(new GenericObject(fname, this.TCurrentRound[pos]));
		}

		
		// sort in ascending order
		Collections.sort(trustSrcList, new ValueComparatorDesc());

		for (Iterator iterator = trustSrcList.iterator(); iterator.hasNext();) {
			GenericObject genObj = (GenericObject) iterator.next();
			System.out.printf("%s\t=\t%.3g\n", genObj.getText1(),
					genObj.getValue1());
		}

	}
	
	
	@SuppressWarnings("unused")
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

	public void getBestFacts() {
		double sum = 0.0;
		double maxvalue = 0.0;
		String source_name = "";
		String fact_value = "";
		String generalId = "";
		String stri = "";
		String strj = "";

		for (Enumeration<String> eni = this.srcs_id.keys(); eni
				.hasMoreElements();) {
			stri = eni.nextElement();
			int possrc = (this.srcs_id.get(stri)).intValue();
			strj = "";
			sum = 0.0;
			fact_value = stri;
			maxvalue = 0.0;
			generalId = "";

			for (Enumeration<String> enj = this.facts_id.keys(); enj
					.hasMoreElements();) {
				strj = enj.nextElement();
				int posf = (this.facts_id.get(strj)).intValue();

				// System.out.printf("\n[%s]-[%s]-[%d]-[%d]=[%d]", strj,
				// stri,
				// posf, possrc, matrix[posf][possrc]);
				if (matrix[posf][possrc] > maxvalue) {
					maxvalue = matrix[posf][possrc];
					source_name = strj;
					generalId = this.general_facts_id.get(stri);
					fact_value = stri;
				}
			}

		}

	}

	public String getBestFacts(String fact) {
		double sum = 0.0;
		double maxvalue = 0.0;
		String source_name = "";
		String fact_value = "";
		String generalId = "";
		String stri = "";
		String strj = "";

		for (Enumeration<String> eni = this.srcs_id.keys(); eni
				.hasMoreElements();) {
			stri = eni.nextElement();
			int possrc = (this.srcs_id.get(stri)).intValue();
			strj = "";
			sum = 0.0;
			fact_value = stri;
			maxvalue = 0.0;
			generalId = "";

			strj = fact;
			int posf = (this.facts_id.get(strj)).intValue();

			// System.out.printf("\n[%s]-[%s]-[%d]-[%d]=[%d]", strj,
			// stri,
			// posf, possrc, matrix[posf][possrc]);
			if (matrix[posf][possrc] > maxvalue) {
				maxvalue = matrix[posf][possrc];
				source_name = strj;
				generalId = this.general_facts_id.get(stri);
			}

		}

		return source_name;

	}

	public double getExec_time() {
		return exec_time;
	}

	public double getExec_time_inSec() {
		return (this.exec_time / 1000.0);
	}

	public void setExec_time(double exec_time) {
		this.exec_time = exec_time;
	}

	private void rankTrustworthiness() {

	}

}
