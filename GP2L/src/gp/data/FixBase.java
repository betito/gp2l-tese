package gp.data;

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

public class FixBase {

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
			ArrayList<String> listOfBooks_asString = allBooks
					.getBookByISBN(book.getISBN());
			ArrayList<Book> books = getListOfAllBooks(listOfBooks_asString);
			ArrayList<Author> goldenAuthors = book.getListOfAuthors();
			
			for (Iterator iterator = books.iterator(); iterator
					.hasNext();) {
				Book booktmp = (Book) iterator.next();
				ArrayList<Author> bookAuthors = booktmp.getListOfAuthors();
				ArrayList<Author> fixedAuthors = compareAuthors(goldenAuthors, bookAuthors);
				
			}
			
			
	
		}
	}

	private static ArrayList<Author> compareAuthors(
			ArrayList<Author> goldenAuthors, ArrayList<Author> bookAuthors) {


		for (Iterator iterator = bookAuthors.iterator(); iterator.hasNext();) {
			Author author = (Author) iterator.next();
			
			
			
			
		}
		
		return null;
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



}
