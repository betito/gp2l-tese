package gp.data;

import java.util.regex.Pattern;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String Sigla = "GP2L - Genetic Programming to Learning the thuth!";
		System.out.println(Sigla);

		String tmp[] = {
				"Kennedy, James (Author) Eberhart, Russell C. (Joint Author) Shi, Yuhui (With)",
				"Russell C. Eberhart, et al",
				"EBERHART, RUSSELL KENNEDY, JAMES SHI, YUHUI ET AL",
				"Kennedy, James F./ Eberhart, Russell/ Shi, Yuhui", " Marcel" };

		tmp = removeEmptyValues(tmp);

		for (int i = 0; i < tmp.length; i++) {

			System.out.printf("[%s]\n", tmp[i]);
		}
		
		Integer ik = new Integer (2);
		
		System.out.println(ik.intValue());
		ik++;
		System.out.println(ik.intValue());
		

	}

	private static String[] removeEmptyValues(String[] data) {
		StringBuilder outputData = new StringBuilder();
		for (int i = 0; i < data.length; i++) {

			outputData.append(clearNoise(data[i]) + "\t");

		}
		return outputData.toString().split("\t");
	}

	private static String canonicalValue(String string) {

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
		string = Pattern.compile("\\(author\\)", Pattern.CASE_INSENSITIVE).matcher(string).replaceAll(";");
		string = Pattern.compile("\\(joint\\s+author\\)", Pattern.CASE_INSENSITIVE).matcher(string).replaceAll(";");

		return string.toLowerCase();

	}

}
