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
import net.nyvaria.utils.StringUtils;

/**
 * @author Paul Thompson
 *
 */
public class MeasurementProtocolClient {
	private static MeasurementProtocolClient instance;
	private URL endpoint_url;
	
	@Parameter(format="text", required=true, name=MeasurementProtocol.TRACKING_ID)
	public String tracking_id;
	
	private MeasurementProtocolClient(String tracking_id) throws MalformedURLException {
		this.tracking_id = tracking_id;
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
			String request_data = StringUtils.join(hit.getParameterList(), "&");
			
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
}
