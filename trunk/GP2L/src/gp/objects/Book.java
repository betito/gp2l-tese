package gp.objects;

import gp.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

	public boolean isAuthorExac(String words) {

		String[] ws = Utils.getOnlyLetters(words).split(" ");
		String[] authors = Utils.getOnlyLetters(
				getListOfAuthors_FirsAndLastName_toString()).split(" ");
		Set<String> setws = new HashSet<String>();
		Set<String> setauthors = new HashSet<String>();

		for (String a : ws) {
			setws.add(a);
		}

		for (String a : authors) {
			setauthors.add(a);
		}
		
		int count = 0;
		for (Iterator iterator = setauthors.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();

			if (setws.contains(string)) {
				count++;
			}
		}
		
//		System.out.printf("Count = %d\n", count);

		if (count == setauthors.size()) {

			return true;
		}

		return false;
	}
	
	public boolean isAuthorIn(String words) {

		String[] ws = Utils.getOnlyLetters(words).split(" ");
		String[] authors = Utils.getOnlyLetters(
				getListOfAuthors_FirsAndLastName_toString()).split(" ");
		Set<String> setws = new HashSet<String>();
		Set<String> setauthors = new HashSet<String>();

		for (String a : ws) {
			setws.add(a);
		}

		for (String a : authors) {
			setauthors.add(a);
		}
		
//		System.out.println("Authos");
//		for (Iterator iterator = setauthors.iterator(); iterator.hasNext();) {
//			String string = (String) iterator.next();
//			System.out.println(string);
//		}
//		
//		System.out.println("\n===========\nLocal");
//		for (Iterator iterator = setws.iterator(); iterator.hasNext();) {
//			String string = (String) iterator.next();
//			System.out.println(string);
//		}

		int count = 0;
		for (Iterator iterator = setauthors.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();

			if (setws.contains(string)) {
				count++;
			}
		}
		
//		System.out.printf("Count = %d\n", count);

		if (count == setws.size()) {

			return true;
		}

		return false;
	}


}
