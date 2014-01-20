package methods;

import gp.objects.Author;
import gp.objects.BaseOfAllBooks;
import gp.objects.Book;
import gp.objects.InstanceValue;
import gp.utils.Utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class Voting extends Choose {

	public static InstanceValue getFactoByVoting(Book b, BaseOfAllBooks allBooks) {

		ArrayList<String> listOfBooks_asString = allBooks.getBookByISBN(b
				.getISBN());

		ArrayList<Book> books = getListOfAllBooks(listOfBooks_asString);

		return countMatchAuthors(b, books);

	}

	private static ArrayList<Book> getListOfAllBooks(ArrayList<String> allBooks) {

		ArrayList<Book> listOfBooks = new ArrayList<Book>();

		for (Iterator<String> iterator = allBooks.iterator(); iterator
				.hasNext();) {
			String string = (String) iterator.next();
			Book b = Utils.getBook(string);
			// System.out.printf("\tLivro :: %s\n", b.getTitle());
			// System.out.printf("\tAuthor(s) :: %s\n", b.getOriginalAuthors());
			// System.out.printf("\tAuthor(s) :: %s\n",
			// b.getListOfAuthors_toString());
			// System.out.printf("-----\n");
			listOfBooks.add(b);

		}

		return listOfBooks;
	}

	private static InstanceValue countMatchAuthors(Book bGold,
			ArrayList<Book> ListOther) {

		Hashtable<String, Integer> CountOcur = new Hashtable<String, Integer>();

		// System.out.println("Golden\n" + bGold.getListOfAuthors_toString());
		// System.out.println("Outros\n");
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

		for (Iterator<Author> iterAut = authorList.iterator(); iterAut
				.hasNext();) {
			Author author = (Author) iterAut.next();
			System.out.printf("author:: %s\n", author.getFirstAndLastName());
			tmp.append(author.getFirstAndLastName() + ";");
		}

		return tmp.toString();
	}

	@Override
	public Object getResponse() {

		return null;
	}

}
