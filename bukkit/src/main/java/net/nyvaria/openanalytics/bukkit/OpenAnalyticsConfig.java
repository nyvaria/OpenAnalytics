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
package net.nyvaria.openanalytics.bukkit;

/**
 * @author Paul Thompson
 */
public class OpenAnalyticsConfig {
    public static final String TRACKING_ID   = "tracking-id";
    public static final String HOST_NAME     = "host-name";
    public static final String USE_METRICS   = "use-metrics";
    public static final String USE_SIGNSHOP  = "use-signshop";
    public static final String IN_BUNGEECORD = "in-bungeecord";

    private OpenAnalyticsConfig() {
        // Prevent instantiation
    }

    // Set Defautlts
    public static void setDefaults() {

    }

    // Tracking ID

    public static String getTrackingID() {
        if (OpenAnalytics.getInstance().getConfig().contains(TRACKING_ID)) {
            return OpenAnalytics.getInstance().getConfig().getString(TRACKING_ID);
        }
        return null;
    }

    public static void setTrackingID(String trackingID) {
        OpenAnalytics.getInstance().getConfig().set(TRACKING_ID, trackingID);
        OpenAnalytics.getInstance().saveConfig();
    }

    // Hostname

    public static String getHostName() {
        if (OpenAnalytics.getInstance().getConfig().contains(HOST_NAME)) {
            return OpenAnalytics.getInstance().getConfig().getString(HOST_NAME);
        }
        return null;
    }

    public static void setHostName(String hostname) {
        OpenAnalytics.getInstance().getConfig().set(HOST_NAME, hostname);
        OpenAnalytics.getInstance().saveConfig();
    }

    // Use Metrics

    public static Boolean getUseMetrics() {
        if (OpenAnalytics.getInstance().getConfig().contains(USE_METRICS)) {
            return OpenAnalytics.getInstance().getConfig().getBoolean(USE_METRICS);
        }
        return null;
    }

    public static void setUseMetrics(boolean useMetrics) {
        OpenAnalytics.getInstance().getConfig().set(USE_METRICS, useMetrics);
        OpenAnalytics.getInstance().saveConfig();
    }

    // Use SignShop

    public static Boolean getUseSignShop() {
        if (OpenAnalytics.getInstance().getConfig().contains(USE_SIGNSHOP)) {
            return OpenAnalytics.getInstance().getConfig().getBoolean(USE_SIGNSHOP);
        }
        return null;
    }

    public static void setUseSignShop(boolean useSignShop) {
        OpenAnalytics.getInstance().getConfig().set(USE_SIGNSHOP, useSignShop);
        OpenAnalytics.getInstance().saveConfig();
    }

    // In BungeeCord

    public static Boolean getInBungeeCord() {
        if (OpenAnalytics.getInstance().getConfig().contains(IN_BUNGEECORD)) {
            return OpenAnalytics.getInstance().getConfig().getBoolean(IN_BUNGEECORD);
        }
        return null;
    }

    public static void setInBungeeCord(boolean inBungeeCord) {
        OpenAnalytics.getInstance().getConfig().set(IN_BUNGEECORD, inBungeeCord);
        OpenAnalytics.getInstance().saveConfig();
    }
}
