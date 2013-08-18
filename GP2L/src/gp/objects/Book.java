package gp.objects;

import java.util.ArrayList;
import java.util.Iterator;

public class Book {

	private String Source = null;
	private String ISBN = null;
	private String Title = null;
	private String OriginalAuthors = null;
	private ArrayList<Author> listOfAuthors = null;
	private String listOfAuthorsSTR = null;

	public Book() {
		super();
		Source = "-";
		ISBN = "-";
		Title = "-";
		OriginalAuthors = "-";
		listOfAuthorsSTR = "-";

	}

	public Book(String iSBN, String title, String listOfAuthors) {
		super();
		Source = "";
		ISBN = iSBN;
		Title = title;
		OriginalAuthors = listOfAuthors.toLowerCase();
		listOfAuthorsSTR = getListOfAuthors_toString();
	}

	public Book(String iSBN, String title, ArrayList<Author> listOfAuthors) {
		super();
		Source = "";
		ISBN = iSBN;
		Title = title;
		this.listOfAuthors = listOfAuthors;
		listOfAuthorsSTR = getListOfAuthors_toString();
	}

	public Book(String iSBN, String title, ArrayList<Author> listOfAuthors,
			String strListOfAuthors) {
		super();
		Source = "";
		ISBN = iSBN;
		Title = title;
		this.listOfAuthors = listOfAuthors;
		OriginalAuthors = strListOfAuthors.toLowerCase();
		listOfAuthorsSTR = getListOfAuthors_toString();
	}

	public Book(String source, String iSBN, String title,
			ArrayList<Author> listOfAuthors) {
		super();
		Source = source;
		ISBN = iSBN;
		Title = title;
		this.listOfAuthors = listOfAuthors;
		listOfAuthorsSTR = getListOfAuthors_toString();
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source.toLowerCase();
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public ArrayList<Author> getListOfAuthors() {
		return listOfAuthors;
	}

	public String getListOfAuthors_toString() {
		String tmp = "";
		for (Iterator<Author> iterator = listOfAuthors.iterator(); iterator
				.hasNext();) {
			Author author = (Author) iterator.next();
			if (author.getMiddleName().equals("")) {
				tmp = tmp + "[" + author.getFirstName() + " "
						+ author.getLastName() + "]\n";
			} else {
				tmp = tmp + "[" + author.getFirstName() + " "
						+ author.getMiddleName() + " " + author.getLastName()
						+ "]\n";
			}
		}

		tmp = tmp.replaceAll("$\\n", "");

		return tmp.toLowerCase();

	}

	public String getListOfAuthors_FirsAndLastName_toString() {
		String tmp = "";
		for (Iterator<Author> iterator = listOfAuthors.iterator(); iterator
				.hasNext();) {
			Author author = (Author) iterator.next();

			tmp = tmp + "[" + author.getFirstName() + " "
					+ author.getLastName() + "]\n";
		}

		tmp = tmp.replaceAll("$\\n", "");

		return tmp.toLowerCase();

	}

	public void setListOfAuthors(ArrayList<Author> listOfAuthors) {
		this.listOfAuthors = listOfAuthors;
	}

	public void addAuthors(Author author) {
		this.listOfAuthors.add(author);
	}

	public String getOriginalAuthors() {
		return OriginalAuthors;
	}

	public void setOriginalAuthors(String originalAuthors) {
		OriginalAuthors = originalAuthors.toLowerCase();
	}

	public String getListOfAuthorsSTR() {
		return listOfAuthorsSTR;
	}

	public void setListOfAuthorsSTR(String listOfAuthorsSTR) {
		this.listOfAuthorsSTR = listOfAuthorsSTR;
	}
	
	
	

}
