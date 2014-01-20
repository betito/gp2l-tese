package gp.model;

import gp.objects.GeneralData;
import gp.utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class PrepareDataInstance {

	/*
	 * This method is the most generic way i've gotten to get the attribute
	 * values os any object to fill GeneralData
	 */

	public PrepareDataInstance(String basePath, int ClassType, String Id,
			String sourceField, String dataField) {

		System.err.println("PARSING BASE :: " + basePath);
		
		ArrayList<Object> records = Utils.prepareInput(basePath, ClassType);

		System.err.println("COUNTING OCCURING..." );
		
		runPrepareMatrix(records, Id, sourceField, dataField);
		
		System.err.println("DATA INSTANCE READY...");

	}

	private static GeneralData getGeneralDataFrom(Object obj, String Id,
			String sourceField, String dataField) {

		GeneralData generalData = new GeneralData();

		try {

			Class<?> c = obj.getClass();

			Field fields[] = c.getDeclaredFields();

			for (Field f : fields) {

				f.setAccessible(true);

				if (f.getName().equals(Id)) {
					generalData.setId(f.get(obj).toString());
				}

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

	private static boolean runPrepareMatrix(ArrayList<Object> records,
			String Id, String attrSource, String attrData) {

		Hashtable<String, Integer> sourcesIds = new Hashtable<String, Integer>();
		Hashtable<String, Integer> dataIds = new Hashtable<String, Integer>();
		Hashtable<String, String> dataAndGeneralId = new Hashtable<String, String>(); // esse aqui eh para guardar o ID de verdade, como o ISBN
		int Occur[][] = null;
		int iSourceId, jDataId; // use only as temp index

		ArrayList<GeneralData> auxAllObjects = new ArrayList<GeneralData>();

		int sourceIdCount = 0;
		int dataIdCount = 0;

		for (Object obj : records) {

			GeneralData genData = getGeneralDataFrom(obj, Id, attrSource,
					attrData);
			auxAllObjects.add(genData);

			// gerar um id em cada source
			if (!(sourcesIds.containsKey(genData.getSource()))) {
				sourcesIds.put(genData.getSource(),
						Integer.valueOf(sourceIdCount++));

			}

			// gerar um id para cada lista de authors

			if (!(dataIds.containsKey(genData.getData()))) {

				dataIds.put(genData.getData(), Integer.valueOf(dataIdCount++));

				// System.out.println("IDS DATA\t::\t[" + genData.getData() +
				// "] "
				// + dataIds.get(genData.getData()));

			}
			
			// apontar um data a um ID da base (quando houver)
			if (!(dataAndGeneralId.containsKey(genData.getId()))) {

				dataAndGeneralId.put(genData.getData(), genData.getId());

			}
			

		}

//		System.out.printf("sources :: %d\nData :: %d\n", sourcesIds.size(),
//				dataIds.size());

		Occur = new int[dataIds.size()][sourcesIds.size()];
		for (int i = 0; i < Occur.length; i++) {
			for (int j = 0; j < Occur[i].length; j++) {
				Occur[i][j] = 0;
			}
		}

		for (Iterator<GeneralData> it = auxAllObjects.iterator(); it.hasNext();) {
			GeneralData gd = it.next();

			iSourceId = getInt(sourcesIds, gd.getSource());
			jDataId = getInt(dataIds, gd.getData());
			Occur[jDataId][iSourceId] = 1;

//			System.out.println("ID\t::\t[" + gd.getId() + "] ");
//			System.out.println("SOURCE\t::\t["
//					+ sourcesIds.get(gd.getSource()).toString() + "] "
//					+ gd.getSource());
//			System.out.println("DATA\t::\t["
//					+ dataIds.get(gd.getData()).toString() + "] "
//					+ gd.getData());
//			System.out.println("=======================");
//
//			System.out.println("Source :: "
//					+ getInt(sourcesIds, gd.getSource()));
//			System.out.println("Data :: " + getInt(dataIds, gd.getData()));

		}

		DataInstance.getInstance().setMatrix(Occur);
		DataInstance.getInstance().setSources(sourcesIds);
		DataInstance.getInstance().setData(dataIds);

		return true;
	}

	private static int getInt(Hashtable<String, Integer> table, String key) {

		// System.out.println("GETINT :: " + (table.get(key)).intValue());
		return (table.get(key)).intValue();
	}

}
