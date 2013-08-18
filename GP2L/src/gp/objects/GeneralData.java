package gp.objects;

public class GeneralData {

	private String source = "";
	private String data = "";

	public GeneralData() {
		super();
	}

	public GeneralData(String source, String data) {
		super();
		this.source = source;
		this.data = data;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
