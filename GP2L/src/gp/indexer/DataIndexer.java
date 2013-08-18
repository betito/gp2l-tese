package gp.indexer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DataIndexer {

	HashMap<Object, Object> listOfObjects = null;
	ArrayList<Object> originalObjects = null;

	DataIndexer() {
		listOfObjects = new HashMap<Object, Object>();
		originalObjects = new ArrayList<Object>();
	}

	DataIndexer(ArrayList<Object> originalObjects, Object obj,  String index_attr) {
		super();
		
		this.originalObjects = originalObjects;

		createIndex(obj, index_attr);

	}

	private void createIndex(Object obj, String attr_name) {

		if (this.listOfObjects == null) {
			listOfObjects = new HashMap<Object, Object>();
		}

		for (Iterator iterator = originalObjects.iterator(); iterator.hasNext();) {
			try {
				Object tmpObj = iterator.next();
				Field field = tmpObj.getClass().getField(attr_name);
				System.out.println(":: " + field.getName());
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}

		}

	}

	public HashMap<Object, Object> getListOfObjects() {
		return listOfObjects;
	}

	public void setListOfObjects(HashMap<Object, Object> listOfObjects) {
		this.listOfObjects = listOfObjects;
	}

	public ArrayList<Object> getOriginalObjects() {
		return originalObjects;
	}

	public void setOriginalObjects(ArrayList<Object> originalObjects) {
		this.originalObjects = originalObjects;
	}

}
