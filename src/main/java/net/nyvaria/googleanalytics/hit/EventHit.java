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
import net.nyvaria.url.parameter.IntegerParameter;
import net.nyvaria.url.parameter.Parameter;
import net.nyvaria.url.parameter.TextParameter;

/**
 * @author Paul Thompson
 *
 */
public class EventHit extends Hit {
	private static final TextParameter HIT_TYPE = new TextParameter(MeasurementProtocol.HIT_TYPE_PARAMETER, "event");
	
	// Event Parameters
	private TextParameter    event_category;
	private TextParameter    event_action;
	private TextParameter    event_label;
	private IntegerParameter event_value;
	
	public EventHit(TextParameter client_id, String event_category, String event_action) {
		super(client_id, EventHit.HIT_TYPE);
		this.setEventCategory(event_category);
		this.setEventAction(event_action);
	}
	
	/* (non-Javadoc)
	 * @see net.nyvaria.googleanalytics.hit.Hit#getParameterList()
	 */
	@Override
	public List<Parameter> getParameterList() {
		List<Parameter> list = super.getParameterList();
		
		// Add the required parameters
		list.add(event_category);
		list.add(event_action);
		
		// Add the optional parameters
		if (event_label != null) list.add(event_label);
		if (event_value != null) list.add(event_value);
		
		return list;
	}

	// Event Parameter Setters
	public void setEventCategory(String event_category) {
		this.event_category = new TextParameter(MeasurementProtocol.EVENT_CATEGORY_PARAMETER, event_category);
	}
	
	public void setEventAction(String event_action) {
		this.event_action = new TextParameter(MeasurementProtocol.EVENT_ACTION_PARAMETER, event_action);
	}
	
	public void setEventLabel(String event_label) {
		this.event_label = new TextParameter(MeasurementProtocol.EVENT_LABEL_PARAMETER, event_label);
	}
	
	public void setEventValue(int event_value) {
		this.event_value = new IntegerParameter(MeasurementProtocol.EVENT_VALUE_PARAMETER, event_value);
	}
	
}
