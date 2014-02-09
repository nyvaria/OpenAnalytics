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
import net.nyvaria.openanalytics.client.Client;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Level;

/**
 * @author Paul Thompson
 */
public class OpenAnalyticsTracker {
    private static OpenAnalyticsTracker instance;
    private static MeasurementProtocolClient mpClient = null;
    private static BukkitTask scheduled_task = null;

    private final OpenAnalytics plugin;

    private String tracking_id;
    private String host_name;
    private boolean is_tracking = false;

    public OpenAnalyticsTracker(OpenAnalytics plugin) {
        this.plugin = plugin;
        initializeMeasurementProtocol();
        scheduleAsynchronousTask();
    }

    public static OpenAnalyticsTracker getInstance() {
        return instance;
    }

    public boolean initializeMeasurementProtocol() {
        if (!is_tracking) {
            // Create the measurements protocol client
            tracking_id = OpenAnalyticsConfig.getTrackingID();
            host_name = OpenAnalyticsConfig.getHostName();

            // Check if any of the required config is missing
            if ((tracking_id == null) || (host_name == null)) {
                if (tracking_id == null) {
                    OpenAnalytics.getInstance().log(Level.SEVERE, String.format("Configuration missing for %s", OpenAnalyticsConfig.TRACKING_ID));
                }

                if (host_name == null) {
                    OpenAnalytics.getInstance().log(Level.SEVERE, String.format("Configuration missing for %s", OpenAnalyticsConfig.HOST_NAME));
                }

                return false;
            }

            // Otherwise log a happy message and create the measurement protocol client
            OpenAnalytics.getInstance().log(String.format("Creating Google Analytics Measurement Protocol client for %s / %s", host_name, tracking_id));
            mpClient = MeasurementProtocolClient.getInstance(host_name, tracking_id);
        }

        return (is_tracking = true);
    }

    public static MeasurementProtocolClient getMeasurementProtocolClient() {
        return mpClient;
    }

    public static BukkitTask getScheduledTask() {
        return scheduled_task;
    }

    public boolean isTracking() {
        return is_tracking;
    }

    public void trackPlayerJoin(Client client) {
        if (is_tracking && !client.isOptedOut()) {
            mpClient.sendAsynchronously(client.createPlayerJoinHit());
            mpClient.sendAsynchronously(client.createWorldHit());
        }
    }

    public void trackPlayerQuit(Client client) {
        if (is_tracking && !client.isOptedOut()) {
            mpClient.sendAsynchronously(client.createPlayerQuitHit());
        }
    }

    public void trackPlayerKick(Client client) {
        if (is_tracking && !client.isOptedOut()) {
            mpClient.sendAsynchronously(client.createPlayerKickHit());
        }
    }

    public void trackPlayerChangedWorld(Client client) {
        if (is_tracking && !client.isOptedOut()) {
            mpClient.sendAsynchronously(client.createWorldHit());
        }
    }

    public void scheduleAsynchronousTask() {
        if (is_tracking) {
            int interval_secs = 60;
            int interval_ticks = interval_secs * 20;

            scheduled_task = plugin.getServer().getScheduler()
                    .runTaskTimerAsynchronously(this.plugin, new ScheduledAsynchronousTask(), interval_ticks, interval_ticks);
        }
    }

    private class ScheduledAsynchronousTask implements Runnable {
        public void run() {
            for (Client client : OpenAnalytics.getInstance().getClientList()) {
                if (!client.isOptedOut()) {
                    OpenAnalyticsTracker.getMeasurementProtocolClient().send(client.createWorldHit());
                }
            }
        }
    }
}
