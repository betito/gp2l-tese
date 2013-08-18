package gp.objects;

import gp.utils.Consts;
import gp.utils.Utils;

import java.util.ArrayList;
import java.util.Hashtable;

public class GoldenSilverBooks {

	private Hashtable<String, Book> listOfGoldenBooks = null;
	private ArrayList<Book> listOfBooks = null;
	private String goldenBookBase = null;

	public GoldenSilverBooks(String file) {
		super();
		goldenBookBase = file;

		loadBase();

	}

	private void loadBase() {

		String tmp = "";
		StringBuilder data = Utils.readFile(this.goldenBookBase);
		String lines[] = data.toString().split(Consts.TEXT_RECORD_DELIMITER);
		if (listOfBooks == null) {
			listOfBooks = new ArrayList<Book>();
		}
		for (int i = 0; i < lines.length; i++) {
			tmp = lines[i].trim();
			
//			System.out.println(tmp);
			
			if (!(tmp.equals("")) && !(tmp.equals("null"))) {
//				System.out.println("Linha :: " + tmp);

				Book b = getBook(tmp);
//				System.out.println("ISBN\t" + b.getISBN());
//				System.out.println("AUTHORS\n" + b.getListOfAuthors_toString());
				listOfBooks.add(b);
			}
		}


	}

	private Book getBook(String record) {

		Book book = new Book();

		String data[] = record.split("\t");
		book.setISBN(data[0]);

		book.setListOfAuthors(formatAuthors(data[1]));
		book.setOriginalAuthors(data[1]);

		return book;
	}

	private ArrayList<Author> formatAuthors(String string) {
		ArrayList<Author> listOfauthors = null;
		String data[] = null;

		string = canonicalValue(string);
		data = string.split(";");
		// System.out.println("tamanho do data :: " + data.length);
		listOfauthors = new ArrayList<Author>();
		// System.out.println("No format");
		for (int i = 0; i < data.length; i++) {
			String tmp[] = data[i].split("\\s+");

			Author tmpAuthor = new Author();
			if (tmp.length == 2) {
				tmpAuthor.setFirstName(tmp[0]);
			}

			if (tmp.length == 2) {
				tmpAuthor.setFirstName(tmp[1]);
				tmpAuthor.setLastName(tmp[0]);
			}

			if (tmp.length == 3) {
				tmpAuthor.setFirstName(tmp[1]);
				tmpAuthor.setMiddleName(tmp[2]);
				tmpAuthor.setLastName(tmp[0]);
			}

			// System.out.println("FN : [" + tmpAuthor.getFirstName() +
			// "] MD : ["
			// + tmpAuthor.getMiddleName() + "] LN : [" +
			// tmpAuthor.getLastName()
			// + "]");

			listOfauthors.add(tmpAuthor);
		}
		// System.out.println("Fim format");

		return listOfauthors;
	}

	private String canonicalValue(String string) {

		string = string.replaceAll(";\\s+", ";");
		string = string.replaceAll("\\.", "");
		string = string.replaceAll(",", "");
		string = string.replaceAll("\\s\\s+", "");

		return string.toLowerCase();
	}

	public Hashtable<String, Book> getListOfGoldenBooks() {
		return listOfGoldenBooks;
	}

	public void setListOfGoldenBooks(Hashtable<String, Book> listOfGoldenBooks) {
		this.listOfGoldenBooks = listOfGoldenBooks;
	}

	public String getGoldenBookBase() {
		return goldenBookBase;
	}

	public void setGoldenBookBase(String goldenBookBase) {
		this.goldenBookBase = goldenBookBase;
	}

	public ArrayList<Book> getListOfBooks() {
		return listOfBooks;
	}

	public void setListOfBooks(ArrayList<Book> listOfBooks) {
		this.listOfBooks = listOfBooks;
	}

}
