package gp.model;

import java.util.Hashtable;

public class DataInstance {

	private Hashtable<String, Integer> Sources = null;
	private Hashtable<String, Integer> Data = null;
	private Hashtable<String, String> DataAndId = null;
	private int[][] Matrix = null;
	private static DataInstance instance = null;

	protected DataInstance() {

	}

	public static DataInstance getInstance() {
		if (instance == null) {
			instance = new DataInstance();
		}
		return instance;
	}

	public Hashtable<String, Integer> getSources() {

		return this.Sources;
	}

	public Hashtable<String, Integer> getData() {
		return Data;
	}

	public void setData(Hashtable<String, Integer> data) {
		Data = data;
	}

	public void setSources(Hashtable<String, Integer> sources) {
		Sources = sources;
	}

	public static void setInstance(DataInstance instance) {
		DataInstance.instance = instance;
	}

	public int[][] getMatrix() {
		return Matrix;
	}

	public void setMatrix(int[][] matrix) {
		Matrix = matrix;
	}
	
	public void checkMatrix(int i, int j, int value) {
		Matrix[i][j] = value;
	}

	public Hashtable<String, String> getDataAndId() {
		return DataAndId;
	}

	public void setDataAndId(Hashtable<String, String> dataAndId) {
		DataAndId = dataAndId;
	}
	
	
	

}
