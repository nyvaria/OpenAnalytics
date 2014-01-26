package net.nyvaria.url.parameter;

public class TextParameter extends Parameter {
	protected String value;

	public TextParameter(String parameter, String value) {
		super(parameter);
		this.value = value;
	}
	
	/**
	 * @return the parameter value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the parameter value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
