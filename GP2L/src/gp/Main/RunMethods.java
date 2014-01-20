package gp.Main;

import java.util.Hashtable;

import gp.model.DataInstance;
import gp.model.PrepareDataInstance;
import gp.utils.Consts;
import methods.Hub;

public class RunMethods {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new PrepareDataInstance("./bases/livros/book.txt", Consts.BOOK, "ISBN",
				"Source", "listOfAuthorsSTR");

		Hashtable<String, Integer> data_id = new Hashtable<String, Integer>();
		Hashtable<String, Integer> srcs_id = new Hashtable<String, Integer>();

		int matrix[][] = { { 1, 1, 0, 1, 1 }, { 1, 0, 1, 0, 1 },
				{ 1, 0, 0, 0, 1 } };

		data_id = new Hashtable<String, Integer>();
		data_id.put("f1", new Integer(0));
		data_id.put("f2", new Integer(1));
		data_id.put("f3", new Integer(2));

		srcs_id = new Hashtable<String, Integer>();
		srcs_id.put("w1", new Integer(0));
		srcs_id.put("w2", new Integer(1));
		srcs_id.put("w3", new Integer(2));
		srcs_id.put("w4", new Integer(2));
		srcs_id.put("w5", new Integer(2));


		// // Facto facto = new Facto(DataInstance.getInstance().getData(),
		// // DataInstance.getInstance().getSources(), DataInstance
		// // .getInstance().getDataAndId(), DataInstance
		// // .getInstance().getMatrix());
		//
		// facto.printFinalTrust();
		//
		// System.out.printf("Exec Time:\t%g ms\n", facto.getExec_time_inSec());

		// System.out.printf("Source :: %s\n",
		//
		// facto.getBestFacts("o'leary, timothy j.;  o'leary, linda i.;"));

		// AccurPR accupr = new AccurPR(DataInstance.getInstance().getData(),
		// DataInstance.getInstance().getSources(), DataInstance
		// .getInstance().getDataAndId(), DataInstance
		// .getInstance().getMatrix());

		Hub hub = new Hub(data_id, srcs_id, null, matrix);

		System.err.println("FIM...");

	}

}
