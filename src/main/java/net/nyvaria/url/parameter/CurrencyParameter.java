package net.nyvaria.url.parameter;

public class CurrencyParameter extends Parameter {
	protected float value;

	public CurrencyParameter(String parameter, Float value) {
		super(parameter);
		this.value = value;
	}
	
	/**
	 * @return the parameter value
	 */
	public String getValue() {
		return String.format("%.2f", value);
	}

	/**
	 * @param value the parameter value to set
	 */
	public void setValue(float value) {
		this.value = value;
	}
}
