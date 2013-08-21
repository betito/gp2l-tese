package gp.utils;

import gp.objects.Author;
import gp.objects.Book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

public class Utils {

	public static ArrayList<Object> prepareInput(String basePath, int clazz) {

		ArrayList<Object> arrayObj = new ArrayList<Object>();

		String[] records = Utils.readFile(basePath).toString()
				.split(Consts.TEXT_RECORD_DELIMITER);

		switch (clazz) {
		case Consts.BOOK:

			for (String str : records) {

				Object obj = Utils.getBook(str);

				arrayObj.add(obj);

			}

			break;
		}

		return arrayObj;

	}

	public static StringBuilder readFile(String strFileName) {

		StringBuilder strFile = new StringBuilder();

		// System.out.printf("\nConteúdo do arquivo texto:\n");
		try {
			FileReader arq = new FileReader(strFileName);
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine(); // lê a primeira linha
			strFile.append(linha + Consts.TEXT_RECORD_DELIMITER);
			// a variável "linha" recebe o valor "null" quando o processo
			// de repetição atingir o final do arquivo texto
			while (linha != null) {
				// System.out.printf("%s\n", linha);
				linha = lerArq.readLine(); // lê da segunda até a última linha
				if (linha != null)
					strFile.append(linha + Consts.TEXT_RECORD_DELIMITER);
			}

			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
		}

		return strFile;

	}

	public static Book getBook(String record) {

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
			book.setOriginalAuthors(data[3]);
			book.setListOfAuthorsSTR(data[3]);
		} else {
			book.setListOfAuthors(formatAuthors("Not Available"));
			book.setOriginalAuthors("Not Available");
			book.setListOfAuthorsSTR("Not Available");
		}

		return book;
	}

	private static ArrayList<Author> formatAuthors(String string) {
		ArrayList<Author> listOfauthors = null;
		String data[] = null;

		// System.out.printf("FORMAT (BEFORE) :: %s\n", string);
		string = clearNoise(string);
		// System.out.printf("FORMAT (AFTER)  :: %s\n", string);

		if (listOfauthors == null) {	
			listOfauthors = new ArrayList<Author>();
		}

		if (!(string.equals(""))) {
			string = canonicalValue(string);
			data = string.split(";");
			// data = string.split(";");
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

					if (tmp.length > 3) {
						tmpAuthor.setFirstName(tmp[tmp.length - 1]);
						tmpAuthor.setMiddleName(getMiddle(tmp));
						tmpAuthor.setLastName(tmp[0]);
						// System.out.printf("MEIO [%s]",
						// tmpAuthor.getMiddleName());
					}

//					 System.out.println("FN : [" + tmpAuthor.getFirstName() +
//					 "] MD : ["
//					 + tmpAuthor.getMiddleName() + "] LN : [" +
//					 tmpAuthor.getLastName()
//					 + "]");

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

	private static String getMiddle(String[] tmp) {

		String output = "";

		for (int i = 2; i < tmp.length - 1; i++) {
			output += tmp[i];
		}

		return output;
	}

	public static String canonicalValue(String string) {

		string = string.replaceAll("\\s+and\\s+", ";");
		string = string.replaceAll("|", ";");
		string = string.replaceAll("^\\s+", "");
		string = string.replaceAll("$\\s+", "");
		string = string.replaceAll("\\n", "");
		string = string.replaceAll("\\r", "");
		string = string.replaceAll(";\\s+", ";");
		string = string.replaceAll("\\.", "");
		string = string.replaceAll(",", "");
		string = string.replaceAll("\\s\\s+", "");

		return string.toLowerCase();
	}

	private static String clearNoise(String string) {

		string = string.replaceAll("/", ";");
		string = Pattern.compile("\\(author\\)", Pattern.CASE_INSENSITIVE)
				.matcher(string).replaceAll(";");
		string = Pattern
				.compile("\\(joint\\s+author\\)", Pattern.CASE_INSENSITIVE)
				.matcher(string).replaceAll(";");
		string = Pattern.compile("\\s+;\\s+", Pattern.CASE_INSENSITIVE)
				.matcher(string).replaceAll(";");
		string = Pattern.compile("\\s+and\\s+", Pattern.CASE_INSENSITIVE)
				.matcher(string).replaceAll(";");
		if (!(string.contains(";"))) {
			string = Pattern.compile("\\s*,\\s*", Pattern.CASE_INSENSITIVE)
					.matcher(string).replaceAll(";");
		}

		return string.toLowerCase();

	}

	private static String[] removeEmptyValues(String[] data) {
		StringBuilder outputData = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (!(data[i].trim().equals(""))) {
				outputData.append(canonicalValue(data[i]) + "\t");
			}

		}
		return outputData.toString().split("\t");
	}

	public static void printBook(ArrayList<String> list) {
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
			Book book = Utils.getBook(string);
			System.out.printf("Source :: [%s]\n", book.getSource());
			System.out.printf("ISBN :: [%s]\n", book.getISBN());
			System.out.printf("Title :: [%s]\n", book.getTitle());
			System.out.printf("Author(s) :: [%s]\n",
					book.getListOfAuthors_toString());
			System.out.println("======================================");

		}

	}

	public static void printBookAuthors(ArrayList<String> list) {
		for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			// System.out.println(string);
			Book book = Utils.getBook(string);
			System.out.printf("Author(s) :: [%s]\n", book.getOriginalAuthors());
			System.out.println("======================================");

		}

	}

	// public static String formatValuesToBrackets(String string) {
	// String[] output = null;
	// String tmp;
	//
	// output = string.split(";");
	//
	// // return output;
	//
	// return null;
	// }

}
