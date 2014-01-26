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
package net.nyvaria.googleanalytics;

import net.nyvaria.url.parameter.TextParameter;

/**
 * @author Paul Thompson
 *
 */
public class MeasurementProtocolClient {
	private static MeasurementProtocolClient instance;
	private TextParameter tracking_id;
	
	public MeasurementProtocolClient(String tracking_id) {
		setTrackingID(tracking_id);
	}
	
	public static MeasurementProtocolClient getInstance() {
		return instance;
	}
	
	/**
	 * @return the tracking_id
	 */
	public TextParameter getTrackingID() {
		return tracking_id;
	}
	
	/**
	 * @param tracking_id the tracking_id to set
	 */
	private void setTrackingID(String tracking_id) {
		this.tracking_id = new TextParameter(MeasurementProtocol.TRACKING_ID_PARAMETER, tracking_id);
	}

}
