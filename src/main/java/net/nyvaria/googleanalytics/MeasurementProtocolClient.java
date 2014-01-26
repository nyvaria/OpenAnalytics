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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.nyvaria.googleanalytics.hit.Hit;
import net.nyvaria.openanalytics.OpenAnalytics;
import net.nyvaria.url.parameter.Parameter;
import net.nyvaria.url.parameter.TextParameter;
import net.nyvaria.utils.StringUtils;

/**
 * @author Paul Thompson
 *
 */
public class MeasurementProtocolClient {
	private static MeasurementProtocolClient instance;
	private TextParameter tracking_id;
	private URL           endpoint_url;
	
	private MeasurementProtocolClient(String tracking_id) throws MalformedURLException {
		setTrackingID(tracking_id);
		endpoint_url = new URL(MeasurementProtocol.ENDPOINT);
	}
	
	public static MeasurementProtocolClient getInstance(String tracking_id) {
		if (instance == null) {
			try {
				instance = new MeasurementProtocolClient(tracking_id);
			} catch (MalformedURLException e) {
				OpenAnalytics.getInstance().log(Level.WARNING, "Error with Google Analytics endpoint URL -- " + MeasurementProtocol.ENDPOINT);
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	public static MeasurementProtocolClient getInstance() {
		return instance;
	}
	
	public void send(Hit hit) {
		try {
			// Create the request payload data
			List<String> list = new ArrayList<String>();
			for (Parameter parameter : hit.getParameterList()) {
				list.add(parameter.toString());
			}
			String request_data = StringUtils.join(list, "&");
			
			// Make the connection
			HttpURLConnection connection = (HttpURLConnection) endpoint_url.openConnection();
			
			// Send the request
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			DataOutputStream request = new DataOutputStream(connection.getOutputStream());
			request.writeBytes(request_data);
			request.flush();
			request.close();
			
			// Get the response code and skip the response
			int responseCode = connection.getResponseCode();
			InputStream response = connection.getInputStream();
			
			int available;
			while ((available = response.available()) != 0) {
				response.skip(available);
			}
			response.close();
			
		} catch (Exception e) {
			OpenAnalytics.getInstance().log(Level.WARNING, "Error sending data to Google Analytics");
			e.printStackTrace();
		}
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
