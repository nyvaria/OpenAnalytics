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
public class SocialInteractionHit extends Hit {
	@Parameter(format="text", required=true, name=MeasurementProtocol.HIT_TYPE)
	private static final String HIT_TYPE = "social";
	
	/*********************************/
	/* Social Interaction Parameters */
	/*********************************/
	
	@Parameter(format="text", required=true, name=MeasurementProtocol.SOCIAL_NETWORK)
	private String social_network;
	
	@Parameter(format="text", required=true, name=MeasurementProtocol.SOCIAL_ACTION)
	private String social_action;
	
	@Parameter(format="text", required=true, name=MeasurementProtocol.SOCIAL_ACTION_TARGET)
	private String social_action_target;
	
	/*************************/
	/* Constructor & Methods */
	/*************************/
	
	public SocialInteractionHit(String client_id, String social_network, String social_action, String social_action_target) {
		super(client_id, SocialInteractionHit.HIT_TYPE);
		this.social_network       = social_network;
		this.social_action        = social_action;
		this.social_action_target = social_action_target;
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
