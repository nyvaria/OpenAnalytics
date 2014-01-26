/**
 * Copyright (c) 2013-2014
 * Paul Thompson <captbunzo@gmail.com> / Nyvaria <geeks@nyvaria.net>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 
 */
package net.nyvaria.googleanalytics.hit;

import java.util.List;

import net.nyvaria.googleanalytics.MeasurementProtocol;
import net.nyvaria.url.parameter.BooleanParameter;
import net.nyvaria.url.parameter.Parameter;
import net.nyvaria.url.parameter.TextParameter;

/**
 * @author Paul Thompson
 *
 */
public class ExceptionHit extends Hit {
	private static final TextParameter HIT_TYPE = new TextParameter(MeasurementProtocol.HIT_TYPE_PARAMETER, "exception");
	
	// Exception Parameters
	private TextParameter    exception_description;
	private BooleanParameter exception_is_fatal;
	
	public ExceptionHit(TextParameter client_id, String exception_description, boolean exception_is_fatal) {
		super(client_id, ExceptionHit.HIT_TYPE);
		setExceptionDescription(exception_description);
		setExceptionIsFatal(exception_is_fatal);
	}
	
	public ExceptionHit(TextParameter client_id, String exception_description) {
		this(client_id, exception_description, false);
	}
	
	/* (non-Javadoc)
	 * @see net.nyvaria.googleanalytics.hit.Hit#getParameterList()
	 */
	@Override
	public List<Parameter> getParameterList() {
		List<Parameter> list = super.getParameterList();
		
		// Add the required parameters
		list.add(exception_description);
		list.add(exception_is_fatal);
		
		return list;
	}
	
	// User Timing Parameter Setters
	public void setExceptionDescription(String exception_description) {
		this.exception_description = new TextParameter(MeasurementProtocol.EXCEPTION_DESCRIPTION_PARAMETER, exception_description);
	}
	
	public void setExceptionIsFatal(boolean exception_is_fatal) {
		this.exception_is_fatal = new BooleanParameter(MeasurementProtocol.EXCEPTION_IS_FATAL_PARAMETER, exception_is_fatal);
	}
}
