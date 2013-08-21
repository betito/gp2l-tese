package gp.foo;

import gp.model.PrepareDataInstance;
import gp.utils.Consts;

public class RunPrepareMatrix {

	/*
	 * This method is the most generic way i've gotten to get the attribute
	 * values os any object to fill GeneralData
	 */

	public static void main(String[] args) {

		new PrepareDataInstance("./bases/livros/book.txt", Consts.BOOK, "ISBN",
				"Source", "listOfAuthorsSTR");

	}

}
