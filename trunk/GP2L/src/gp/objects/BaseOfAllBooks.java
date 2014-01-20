package gp.objects;

import gp.utils.Consts;
import gp.utils.Utils;

import java.util.ArrayList;
import java.util.Hashtable;

public class BaseOfAllBooks {

	private Hashtable<String, ArrayList<String>> listOfOriginalRecord = null;
	private Hashtable<String, Book> listOfGoldenBooks = null;
	private ArrayList<Book> listOfBooks = null;
	private String goldenBookBase = null;

	public BaseOfAllBooks(String file) {
		super();
		goldenBookBase = file;

		// loadBaseToObject();
		loadInvertedIndex();

	}

	private void loadInvertedIndex() {

		ArrayList<String> recordStr = null;
		String tmp = "";
		StringBuilder data = Utils.readFile(this.goldenBookBase);
		String lines[] = data.toString().split(Consts.TEXT_RECORD_DELIMITER);
		if (listOfOriginalRecord == null) {
			listOfOriginalRecord = new Hashtable<String, ArrayList<String>>();
		}
		for (int i = 0; i < lines.length; i++) {
			tmp = lines[i].trim();
//			System.out.println(tmp);
			if (!(tmp.equals("")) && !(tmp.equals("null"))) {
//				System.out.println("Linha :: " + tmp);

				String record[] = tmp.split("\\t");
				String isbn = record[1];
				if (!(isbn.equals(""))) {
					if (listOfOriginalRecord.containsKey(isbn)) {
						recordStr = listOfOriginalRecord.get(isbn);
						recordStr.add(tmp);
					} else {
						recordStr = new ArrayList<String>();
						recordStr.add(tmp);
					}

					listOfOriginalRecord.put(isbn, recordStr);

				}

//				System.out.printf("Source :: [%s]\n", b.getSource());

			}
		}

//		System.out.println("Final aqui Indice Invertido...");

	}

	public ArrayList<String> getBookByISBN(String isbn) {

		if (listOfOriginalRecord == null) {
			return null;
		}

		return this.listOfOriginalRecord.get(isbn);

	}
	
	public String getBookByISBN_asStringInLines(String isbn) {

		StringBuilder strs = new StringBuilder();
		
		if (listOfOriginalRecord == null) {
			return null;
		}

		ArrayList<String> books = this.listOfOriginalRecord.get(isbn);
		
		for (String string : books) {
			strs.append(string+"\n");
		}
		
		
		return strs.toString();

	}


	private void loadBaseToObject() {

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
//				System.out.printf("Source :: [%s]\n", b.getSource());
//				System.out.printf("ISBN :: [%s]\n", b.getISBN());
//				System.out.printf("Title :: [%s]\n", b.getTitle());
//				System.out.printf("Author(s) :: [%s]\n", b.getListOfAuthors_toString());
				listOfBooks.add(b);
			}
		}

//		System.out.println("Final aqui...");

	}

	private Book getBook(String record) {

		Book book = new Book();

		String data[] = record.split(Consts.FIELD_DELIMITER);
		if (data.length > 0) {
			book.setSource(data[0]);
		}
		if (data.length > 1) {
			book.setISBN(data[1]);
		}
		if (data.length > 2) {
			book.setTitle(data[2]);
		}
		if (data.length > 3) {
			book.setListOfAuthors(formatAuthors(data[3]));
		} else {
			book.setListOfAuthors(formatAuthors("Not AVailable"));
		}

		return book;
	}

	private ArrayList<Author> formatAuthors(String string) {
		ArrayList<Author> listOfauthors = null;
		String data[] = null;

		System.out.printf("FORMAT :: %s\n", string);

		if (!(string.equals(""))) {
			string = Utils.canonicalValue(string);
			data = string.split(";");
			data = removeEmptyValues(data);
			// System.out.println("tamanho do data :: " + data.length);
			listOfauthors = new ArrayList<Author>();
			// System.out.println("No format");
			for (int i = 0; i < data.length; i++) {
				String tmp[] = data[i].split("\\s+");
				if (!(tmp[0].equals("Not Available"))) {
					Author tmpAuthor = new Author();
					if (tmp.length == 1) {
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
			}
		} else {
			Author tmpAuthor = new Author();
			tmpAuthor.setFirstName("Not Available");
			listOfauthors.add(tmpAuthor);

		}
		// System.out.println("Fim format");

		return listOfauthors;
	}

	private String[] removeEmptyValues(String[] data) {
		StringBuilder outputData = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (!(data[i].trim().equals(""))) {
				outputData.append(Utils.canonicalValue(data[i]) + "\t");
			}

		}
		return outputData.toString().split("\t");
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
