package gp.Main;

import gp.objects.Author;
import gp.objects.BaseOfAllBooks;
import gp.objects.Book;
import gp.objects.GoldenSilverBooks;
import gp.objects.InstanceValue;
import gp.utils.Utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import methods.Voting;

public class RunVoting {

	public static void main(String[] args) {

		// System.out.println(System.getProperty("user.dir"));

		GoldenSilverBooks gsb_golden = new GoldenSilverBooks(
				"./bases/livros/book_golden.txt");
		// GoldenSilverBooks gb_silver = new
		// GoldenSilverBooks("./bases/livros/book_silver.txt");
		BaseOfAllBooks allBooks = new BaseOfAllBooks(
				"./bases/livros/book.txt");
		ArrayList<Book> auxAllBooks = allBooks.getListOfBooks();

		ArrayList<Book> goldenBooks = gsb_golden.getListOfBooks();

		int x = 0;
		for (Iterator<Book> en = goldenBooks.iterator(); en.hasNext(); x++) {
			Book book = en.next();
			System.out.println("ISBN :: " + book.getISBN());
			System.out.println("AUTHORS\n" + book.getListOfAuthors_toString());
//			ArrayList<String> listOfBooks_asString = allBooks
//					.getBookByISBN(book.getISBN());
//			ArrayList<Book> books = getListOfAllBooks(listOfBooks_asString);
//			InstanceValue insValue = countMatchAuthors(book, books);
			InstanceValue insValue = Voting.getFactoByVoting(book, allBooks);

			System.out.printf("[%d]\t==\t[%s]\n", insValue.getFreq(),
					insValue.getValue());

			// Utils.printBook(aux);
		}
	}

	private static ArrayList<Book> getListOfAllBooks(ArrayList<String> allBooks) {

		ArrayList<Book> listOfBooks = new ArrayList<Book>();

		for (Iterator<String> iterator = allBooks.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			Book b = Utils.getBook(string);
//			System.out.printf("\tLivro :: %s\n", b.getTitle());
//			System.out.printf("\tAuthor(s) :: %s\n", b.getOriginalAuthors());
//			System.out.printf("\tAuthor(s) :: %s\n",
//					b.getListOfAuthors_toString());
//			System.out.printf("-----\n");
			listOfBooks.add(b);

		}

		return listOfBooks;
	}

	private static InstanceValue countMatchAuthors(Book bGold,
			ArrayList<Book> ListOther) {

		Hashtable<String, Integer> CountOcur = new Hashtable<String, Integer>();

//		System.out.println("Golden\n" + bGold.getListOfAuthors_toString());
//		System.out.println("Outros\n");
		for (Iterator<Book> iterator = ListOther.iterator(); iterator.hasNext();) {

			Book b = iterator.next();
			ArrayList<Author> autlist = b.getListOfAuthors();
			String tmpFactValue = FactoValue(autlist); // based on the author's
														// first and last names;

			if (CountOcur.containsKey(tmpFactValue)) {
				Integer count = CountOcur.get(tmpFactValue);
				count++;
				CountOcur.put(tmpFactValue, count);
			} else {
				CountOcur.put(tmpFactValue, new Integer(1));
			}

			// System.out.println(b.getListOfAuthors_toString() +
			// "\n----------------");

		}

		return getMaxCount(CountOcur);

	}

	private static InstanceValue getMaxCount(
			Hashtable<String, Integer> countOcur) {

		int max = -1;
		String maxValue = "";
		InstanceValue instValue = new InstanceValue();

		for (Enumeration<String> en = countOcur.keys(); en.hasMoreElements();) {

			String value = (String) en.nextElement();
			Integer itmp = (Integer) countOcur.get(value);
			if (itmp.intValue() > max) {
				max = itmp.intValue();
				maxValue = value;
			}

		}

		instValue.setFreq(max);
		instValue.setValue(maxValue);

		return instValue;
	}

	private static String FactoValue(ArrayList<Author> authorList) {
		StringBuilder tmp = new StringBuilder();

		for (Iterator iterAut = authorList.iterator(); iterAut.hasNext();) {
			Author author = (Author) iterAut.next();
//			System.out.printf("author:: %s\n", author.getFirstAndLastName());
			tmp.append(author.getFirstAndLastName()+";");
		}

		return tmp.toString();
	}

}
