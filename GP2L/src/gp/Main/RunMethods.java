package gp.Main;

import gp.model.DataInstance;
import gp.model.PrepareDataInstance;
import gp.utils.Consts;
import methods.Facto;

public class RunMethods {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new PrepareDataInstance("./bases/livros/book.txt", Consts.BOOK, "ISBN",
				"Source", "listOfAuthorsSTR");

		new Facto(DataInstance.getInstance().getData(), DataInstance
				.getInstance().getSources(), DataInstance.getInstance()
				.getMatrix());

	}

}
