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

import net.nyvaria.googleanalytics.MeasurementProtocol;
import net.nyvaria.googleanalytics.Parameter;

/**
 * @author Paul Thompson
 *
 */
public class EventHit extends Hit {
	@Parameter(format="text", required=true, name=MeasurementProtocol.HIT_TYPE)
	private static final String HIT_TYPE = "event";
	
	/********************/
	/* Event Parameters */
	/********************/
	
	@Parameter(format="text", required=true,  name=MeasurementProtocol.EVENT_CATEGORY)
	public String event_category;
	
	@Parameter(format="text", required=true,  name=MeasurementProtocol.EVENT_ACTION)
	public String event_action;
	
	@Parameter(format="text", required=false, name=MeasurementProtocol.EVENT_LABEL)
	public String event_label;
	
	@Parameter(format="text", required=false, name=MeasurementProtocol.EVENT_VALUE)
	public Integer event_value;
	
	/*************************/
	/* Constructor & Methods */
	/*************************/
	
	public EventHit(String client_id, String event_category, String event_action) {
		super(client_id, EventHit.HIT_TYPE);
		this.event_category = event_category;
		this.event_action   = event_action;
	}
}
