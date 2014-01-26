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
import net.nyvaria.url.parameter.Parameter;
import net.nyvaria.url.parameter.TextParameter;

/**
 * @author Paul Thompson
 *
 */
public class SocialInteractionHit extends Hit {
	private static final TextParameter HIT_TYPE = new TextParameter(MeasurementProtocol.HIT_TYPE_PARAMETER, "social");
	
	// Social Interaction Parameters
	private TextParameter social_network;
	private TextParameter social_action;
	private TextParameter social_action_target;
	
	public SocialInteractionHit(TextParameter client_id, String social_network, String social_action, String social_action_target) {
		super(client_id, SocialInteractionHit.HIT_TYPE);
		this.setSocialNetwork(social_network);
		this.setSocialAction(social_action);
		this.setSocialActionTarget(social_action_target);
	}
	
	/* (non-Javadoc)
	 * @see net.nyvaria.googleanalytics.hit.Hit#getParameterList()
	 */
	@Override
	public List<Parameter> getParameterList() {
		List<Parameter> list = super.getParameterList();
		
		// Add the required parameters
		list.add(social_network);
		list.add(social_action);
		list.add(social_action_target);
		
		return list;
	}

	// Social Interaction Parameters
	public void setSocialNetwork(String social_network) {
		this.social_network = new TextParameter(MeasurementProtocol.SOCIAL_NETWORK_PARAMETER, social_network);
	}
	
	public void setSocialAction(String social_action) {
		this.social_action = new TextParameter(MeasurementProtocol.SOCIAL_ACTION_PARAMETER, social_action);
	}
	
	public void setSocialActionTarget(String social_action_target) {
		this.social_action_target = new TextParameter(MeasurementProtocol.SOCIAL_ACTION_TARGET_PARAMETER, social_action_target);
	}
}
