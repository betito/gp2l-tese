package gp.objects;

import java.util.Hashtable;

public class Books {

	Hashtable<String, Hashtable<String, Book>> Source = null;

	public Books() {
		super();
		Source = new Hashtable<String, Hashtable<String, Book>>(10);
	}

	public Books(Hashtable<String, Hashtable<String, Book>> source) {
		super();
		Source = source;
	}

	public Hashtable<String, Hashtable<String, Book>> getSource() {
		return Source;
	}

	public void setSource(Hashtable<String, Hashtable<String, Book>> source) {
		Source = source;
	}

	public void addBook(String source, Book book) {
		Hashtable<String, Book> tmp = null;
		tmp = this.Source.get(source);

		if (tmp == null) {
			tmp = new Hashtable<String, Book>(10);
		}
		tmp.put(book.getISBN(), book);
		this.Source.put(source, tmp);
	}

}
