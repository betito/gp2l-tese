package gp.objects;

public class GeneralData {

	private String id = "";
	private String source = "";
	private String data = "";

	public GeneralData() {
		super();
	}

	public GeneralData(String id, String source, String data) {
		super();
		this.id = id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
