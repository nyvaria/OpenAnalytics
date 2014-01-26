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
package net.nyvaria.openanalytics;

import net.nyvaria.googleanalytics.MeasurementProtocolClient;
import net.nyvaria.googleanalytics.hit.EventHit;
import net.nyvaria.openanalytics.client.Client;

/**
 * @author Paul Thompson
 *
 */
public class OpenAnalyticsTracker {
	private static OpenAnalyticsTracker instance;
	private final  OpenAnalytics plugin;
	private        MeasurementProtocolClient mpClient;
	
	public OpenAnalyticsTracker(OpenAnalytics plugin) {
		this.plugin = plugin;
		initializeMeasurementProtocol();
	}
	
	public static OpenAnalyticsTracker getInstance() {
		return instance;
	}
	
	public boolean initializeMeasurementProtocol() {
		// Create the measurements protocol client
		if (plugin.getConfig().contains("tracking-id")) {
			mpClient = MeasurementProtocolClient.getInstance(plugin.getConfig().getString("tracking-id"));
			return true;
		}
		return false;
	}
	
	public void trackPlayerJoin(Client client) {
		EventHit eventHit = new EventHit(client.getClientID(), "player-connection", "join");
		mpClient.send(eventHit);
	}
	
	public void trackPlayerQuit(Client client) {
		EventHit eventHit = new EventHit(client.getClientID(), "player-connection", "quit");
		mpClient.send(eventHit);
	}
	
	public void trackPlayerKick(Client client) {
		EventHit eventHit = new EventHit(client.getClientID(), "player-connection", "kick");
		mpClient.send(eventHit);
	}
}
