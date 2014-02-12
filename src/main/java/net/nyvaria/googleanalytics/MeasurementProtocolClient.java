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

import net.nyvaria.component.util.StringUtils;
import net.nyvaria.googleanalytics.hit.Hit;
import net.nyvaria.openanalytics.OpenAnalytics;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Paul Thompson
 */
public class MeasurementProtocolClient {
    private static MeasurementProtocolClient instance;
    private URL endpoint_url;

    @Parameter(format = "text", required = true, name = MeasurementProtocol.DOCUMENT_HOST_NAME)
    public String document_host_name;

    @Parameter(format = "text", required = true, name = MeasurementProtocol.TRACKING_ID)
    public String tracking_id;

    private MeasurementProtocolClient(String host_name, String tracking_id) throws MalformedURLException {
        this.document_host_name = host_name;
        this.tracking_id = tracking_id;
        endpoint_url = new URL(MeasurementProtocol.ENDPOINT);
    }

    public static MeasurementProtocolClient getInstance(String host_name, String tracking_id) {
        if (instance == null) {
            try {
                instance = new MeasurementProtocolClient(host_name, tracking_id);
            } catch (MalformedURLException e) {
                OpenAnalytics.getInstance().log(Level.WARNING, "MalformedURLException while initializing Measurement Protocol Client (" + MeasurementProtocol.ENDPOINT + ")");
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static MeasurementProtocolClient getInstance() {
        return instance;
    }

    public void send(Hit hit) {
        HttpURLConnection connection = null;
        String payload_data = StringUtils.join(hit.getParameterList(), "&");
        String ip_address = hit.getClient().getIPAddress();

        // Log the query string for now
        OpenAnalytics.getInstance().log(Level.FINE, "Sending data to Google Analytics: " + payload_data);
        OpenAnalytics.getInstance().log(Level.FINE, "Sending data for IP address: " + ip_address);

        try {
            // Create and setup the connection
            connection = (HttpURLConnection) endpoint_url.openConnection();
            connection.setRequestMethod(MeasurementProtocol.ENDPOINT_REQUEST_METHOD);
            connection.setRequestProperty("X-Forwarded-For", ip_address);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Send request
            OutputStreamWriter requestWriter = new OutputStreamWriter(connection.getOutputStream(), MeasurementProtocol.ENDPOINT_ENCODING);
            requestWriter.write(payload_data);
            requestWriter.flush();
            requestWriter.close();

            // Get the response code
            int responseCode = connection.getResponseCode();
            OpenAnalytics.getInstance().log(Level.FINE, "HTTP Response Code " + responseCode);

            /************************************************************************
             * The response is apparently a single pixel gif (so I am ignoring it). *
			 * Google says that any response code other then 2xx should result in   *
			 * stopping sending of metrics and figuring out what is wrong. It would *
			 * be good to check the response code and stop or pause if we get some  *
			 * quantity of non-2xx response codes.                                  *
             ************************************************************************/

            // Read response
            //BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), MeasurementProtocol.ENDPOINT_ENCODING));
            //
            //String responseLine;
            //StringBuilder response = new StringBuilder();
            //while ((responseLine = responseReader.readLine()) != null) {
            //	response.append(responseLine).append('\n');
            //}
            //responseReader.close();

        } catch (UnsupportedEncodingException uee) {
            OpenAnalytics.getInstance().log(Level.WARNING, "UnsupportedEncodingException while sending data to Google Analytics");
            uee.printStackTrace();

        } catch (ProtocolException pe) {
            OpenAnalytics.getInstance().log(Level.WARNING, "ProtocolException while sending data to Google Analytics");
            pe.printStackTrace();

        } catch (IOException ioe) {
            OpenAnalytics.getInstance().log(Level.WARNING, "IOException while sending data to Google Analytics");
            ioe.printStackTrace();
        }

        if (connection != null) {
            connection.disconnect();
        }
    }

    public void send(List<Hit> hitList) {
        for (Hit hit : hitList) {
            send(hit);
        }
    }

    public void sendAsynchronously(final Hit hit) {
        OpenAnalytics.getInstance().getServer().getScheduler().runTaskAsynchronously(OpenAnalytics.getInstance(), new Runnable() {
            public void run() {
                send(hit);
            }
        });
    }

    public void sendAsynchronously(final List<Hit> hitList) {
        OpenAnalytics.getInstance().getServer().getScheduler().runTaskAsynchronously(OpenAnalytics.getInstance(), new Runnable() {
            public void run() {
                send(hitList);
            }
        });
    }
}
