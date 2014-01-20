package gp.objects;


public class GenericObject {

	private String text1 = null;
	private double value1 = 0.0;

	public GenericObject() {
		super();
		text1 = "";
		value1 = 0.0;
	}

	public GenericObject(String t, double v1) {
		super();
		text1 = t;
		value1 = v1;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public double getValue1() {
		return value1;
	}

	public void setValue1(double value1) {
		this.value1 = value1;
	}
	
}
