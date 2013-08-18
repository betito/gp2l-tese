package gp.foo;

import gp.objects.Book;
import gp.objects.GeneralData;
import gp.utils.Consts;
import gp.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class RunPrepareMatrix {

	private static GeneralData getGeneralDataFrom(Object obj,
			String sourceField, String dataField) {

		GeneralData generalData = new GeneralData();

		try {

			Class<?> c = obj.getClass();

			Field fields[] = c.getDeclaredFields();

			for (Field f : fields) {

				f.setAccessible(true);

				if (f.getName().equals(sourceField)) {
					generalData.setSource(f.get(obj).toString());
				}

				if (f.getName().equals(dataField)) {
					generalData.setData(f.get(obj).toString());
				}

			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return generalData;

	}

	public static boolean prepareMatrix(String base, String nameOfTheClass,
			String attrSource, String attrData) {

		Hashtable<String, Integer> sourcesIds = new Hashtable<String, Integer>();
		Hashtable<String, Integer> dataIds = new Hashtable<String, Integer>();
		int Occur[][] = null;

		String[] records = Utils.readFile(base).toString()
				.split(Consts.TEXT_RECORD_DELIMITER);
		ArrayList<GeneralData> auxAllObjects = new ArrayList<GeneralData>();

		int sourceIdCount = 0;
		int dataIdCount = 0;

		for (String str : records) {

			Object obj = Utils.getBook(str);
			GeneralData genData = getGeneralDataFrom(obj, attrSource, attrData);
			auxAllObjects.add(genData);

			// gerar um id em cada source
			if (!(sourcesIds.containsKey(genData.getSource()))) {
				sourcesIds.put(genData.getSource(),
						Integer.valueOf(sourceIdCount++));

			}

			// gerar um id para cada lista de authors

			if (!(dataIds.containsKey(genData.getData()))) {
				dataIds.put(genData.getData(), Integer.valueOf(dataIdCount++));

				System.out.println("IDS DATA\t::\t[" + genData.getData() + "] "
						+ dataIds.get(genData.getData()));

			}

		}

		System.out.printf("sources :: %d\nData :: %d\n", sourcesIds.size(),
				dataIds.size());

		Occur = new int[sourcesIds.size()][dataIds.size()];
		for (int i = 0; i < Occur.length; i++) {
			for (int j = 0; j < Occur[i].length; j++) {
				Occur[i][j] = 0;
			}
		}

		for (Iterator<GeneralData> it = auxAllObjects.iterator(); it.hasNext();) {
			GeneralData gd = it.next();
			System.out.println("SOURCE\t::\t["
					+ sourcesIds.get(gd.getSource()).toString() + "] "
					+ gd.getSource());
			System.out.println("DATA\t::\t["
					+ dataIds.get(gd.getData()).toString() + "] "
					+ gd.getData());
			System.out.println("=======================");

			System.out.println("Source :: "
					+ getInt(sourcesIds, gd.getSource()));
			System.out.println("Data :: " + getInt(dataIds, gd.getData()));

			Occur[getInt(sourcesIds, gd.getSource())][getInt(dataIds,
					gd.getData())] = 1;

		}

		return true;
	}

	private static int getInt(Hashtable<String, Integer> table, String key) {

		// System.out.println("GETINT :: " + (table.get(key)).intValue());
		return (table.get(key)).intValue();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// prepareMatrix("./bases/livros/book.txt", "",
		// "Source","OriginalAuthors");
		prepareMatrix("./bases/livros/book.txt", "", "Source",
				"listOfAuthorsSTR");

	}

}
