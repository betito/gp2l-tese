package gp.objects;

public class InstanceValue {

	int Freq;
	String Value;

	public InstanceValue() {
		super();
	}

	public InstanceValue(int freq, String value) {
		super();
		Freq = freq;
		Value = value;
	}

	public int getFreq() {
		return Freq;
	}

	public void setFreq(int freq) {
		Freq = freq;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

}
