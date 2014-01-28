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

import org.bukkit.scheduler.BukkitTask;

import net.nyvaria.googleanalytics.MeasurementProtocolClient;
import net.nyvaria.openanalytics.client.Client;

/**
 * @author Paul Thompson
 *
 */
public class OpenAnalyticsTracker {
	private static OpenAnalyticsTracker instance;
	private static MeasurementProtocolClient mpClient = null;
	private static BukkitTask scheduled_task = null;
	
	private final  OpenAnalytics plugin;
	
	private String  tracking_id;
	private String  host_name;
	private boolean is_tracking;
	
	public OpenAnalyticsTracker(OpenAnalytics plugin) {
		this.plugin = plugin;
		this.is_tracking = initializeMeasurementProtocol();
		
		if (is_tracking) {
			int interval_secs  = 60;
			int interval_ticks = interval_secs * 20;
			
			scheduled_task = plugin.getServer().getScheduler()
					.runTaskTimerAsynchronously(this.plugin, new ScheduledAsynchronousTask(), interval_ticks, interval_ticks);
		}
	}
	
	public static OpenAnalyticsTracker getInstance() {
		return instance;
	}
	
	public boolean initializeMeasurementProtocol() {
		// Create the measurements protocol client
		if (plugin.getConfig().contains("tracking-id")) {
			tracking_id = plugin.getConfig().getString("tracking-id");
			host_name   = plugin.getConfig().getString("host-name");
			OpenAnalytics.getInstance().log(String.format("Creating Google Analytics Measurement Protocol client for %s / %s", host_name, tracking_id));
			
			mpClient = MeasurementProtocolClient.getInstance(host_name, tracking_id);
			return true;
		}
		return false;
	}
	
	public static MeasurementProtocolClient getMeasurementProtocolClient() {
		return mpClient;
	}
	
	public static BukkitTask getScheduledTask() {
		return scheduled_task;
	}
	
	public void trackPlayerJoin(Client client) {
		if (is_tracking) {
			mpClient.sendAsynchronously(client.createPlayerJoinHit());
			mpClient.sendAsynchronously(client.createWorldHit());
		}
	}
	
	public void trackPlayerQuit(Client client) {
		if (is_tracking) {
			mpClient.sendAsynchronously(client.createPlayerQuitHit());
		}
	}
	
	public void trackPlayerKick(Client client) {
		if (is_tracking) {
			mpClient.sendAsynchronously(client.createPlayerKickHit());
		}
	}
	
	public void trackPlayerChangedWorld(Client client) {
		if (is_tracking) {
			mpClient.sendAsynchronously(client.createWorldHit());
		}
	}
	
	private class ScheduledAsynchronousTask implements Runnable {
		public void run() {
			for (Client client : OpenAnalytics.getInstance().clientList) {
				OpenAnalyticsTracker.getMeasurementProtocolClient().send(client.createWorldHit());
			}
		}
	}
}
