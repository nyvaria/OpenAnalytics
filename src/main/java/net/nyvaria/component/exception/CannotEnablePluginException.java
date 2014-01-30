/**
 * 
 */
package net.nyvaria.component.exception;

/**
 * @author Paul Thompson
 *
 */
public class CannotEnablePluginException extends Exception {
	private static final long serialVersionUID = 1L;

	public CannotEnablePluginException() {
		
	}
	
	public CannotEnablePluginException(final Throwable cause) {
		super(cause);
	}
	
	public CannotEnablePluginException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public CannotEnablePluginException(final String message) {
		super(message);
	}
}
