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

import java.lang.reflect.Field;
import java.util.List;

import net.nyvaria.googleanalytics.MeasurementProtocol;
import net.nyvaria.googleanalytics.Parameter;

/**
 * @author Paul Thompson
 *
 */
public class ExceptionHit extends Hit {
	@Parameter(format="text", required=true, name=MeasurementProtocol.HIT_TYPE)
	private static final String HIT_TYPE = "exception";
	
	/************************/
	/* Exception Parameters */
	/************************/
	
	@Parameter(format="text",    required=true, name=MeasurementProtocol.EXCEPTION_DESCRIPTION)
	private String exception_description;
	
	@Parameter(format="boolean", required=true, name=MeasurementProtocol.EXCEPTION_IS_FATAL)
	private Boolean exception_is_fatal;
	
	/*************************/
	/* Constructor & Methods */
	/*************************/
	
	public ExceptionHit(String client_id, String exception_description, boolean exception_is_fatal) {
		super(client_id, ExceptionHit.HIT_TYPE);
		this.exception_description = exception_description;
		this.exception_is_fatal    = exception_is_fatal;
	}
	
	public ExceptionHit(String client_id, String exception_description) {
		this(client_id, exception_description, false);
	}
	
	/* (non-Javadoc)
	 * @see net.nyvaria.googleanalytics.hit.Hit#getParameterList()
	 */
	@Override
	public List<String> getParameterList() {
		List<String> list = super.getParameterList();
		
		for (Field field : this.getClass().getFields()) {
			Parameter parameter = (Parameter) field.getAnnotation(Parameter.class);
			
			if (parameter != null) {
				Object value = null;
				
				try {
					value = field.get(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String result = formatParameter(parameter, value);
				if (result != null) {
					list.add(result);
				}
			}
		}
		
		return list;
	}
}
