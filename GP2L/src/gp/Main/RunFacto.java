package gp.Main;

import methods.Facto;

public class RunFacto {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String[] facts_names = { "f1", "f2", "f3" };
		String[] sources_names = { "w1", "w2", "w3", "w4", "w5"};
		new Facto(facts_names, sources_names);

	}

}

