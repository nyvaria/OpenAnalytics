package net.nyvaria.url.parameter;

public class BooleanParameter extends Parameter {
	protected boolean value;

	public BooleanParameter(String parameter, boolean value) {
		super(parameter);
		this.value = value;
	}
	
	/**
	 * @return the parameter value
	 */
	public String getValue() {
		return (value ? "1" : "0");
	}

	/**
	 * @param value the parameter value to set
	 */
	public void setValue(boolean value) {
		this.value = value;
	}
}
