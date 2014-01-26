package net.nyvaria.url.parameter;

public class IntegerParameter extends Parameter {
	protected int value;

	public IntegerParameter(String parameter, Integer value) {
		super(parameter);
		this.value = value;
	}
	
	/**
	 * @return the parameter value
	 */
	public String getValue() {
		return String.format("%d", value);
	}

	/**
	 * @param value the parameter value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
