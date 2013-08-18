package gp.foo;

import methods.CosineSim;

public class RunCosineSim {

	
	public static void main(String[] args) {

		double[] a = {0.8, 0.8, 0.8, 0.8, 0.8};
		double[] b = {0.85504, 0.82505, 0.82505, 0.85504, 0.81006};
		
		CosineSim.measureCosineSimilarity(a, b);
		


	}

}
