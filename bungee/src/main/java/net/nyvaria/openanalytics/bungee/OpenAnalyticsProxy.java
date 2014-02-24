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
package net.nyvaria.openanalytics.bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.nyvaria.openanalytics.bungee.client.ProxiedClientList;

import java.util.logging.Level;

/**
 * Created by Paul Thompson on 23/02/2014.
 * @author Paul Thompson
 */
public class OpenAnalyticsProxy extends Plugin {
    private static OpenAnalyticsProxy instance = null;

    // Listener and list
    private OpenAnalyticsProxyListener listener = null;
    private ProxiedClientList proxiedClientList = null;

    /**
     * Override the onEnable & onDisable methods
     */

    @Override
    public void onEnable() {
        // Save the instance
        OpenAnalyticsProxy.instance = this;

        // Create the proxied client list and add all currently logged in players
        proxiedClientList = new ProxiedClientList();
        for (ProxiedPlayer player : getProxy().getPlayers()) {
            proxiedClientList.put(player);
        }

        // Create and register the listeners
        listener = new OpenAnalyticsProxyListener(this);

        // Log a happy message
        log("Enabling %1$s successful", getNameAndVersion());
    }

    @Override
    public void onDisable() {
        // Destroy the client list
        proxiedClientList = null;

        // Clear the instance
        OpenAnalyticsProxy.instance = null;

        // Log an unhappy message
        log("Disabling %1$s successful", getNameAndVersion());
    }

    /**
     * Get the instance of the OpenAnalyticsProxy plugin from the Proxy.
     * @return OpenAnalyticsProxy
     */
    public static OpenAnalyticsProxy getInstance() {
        return instance;
    }

    /**
     * Getters
     */

    public OpenAnalyticsProxyListener getListener() {
        return listener;
    }

    public ProxiedClientList getProxiedClientList() {
        return proxiedClientList;
    }

    /**
     * Logging
     */

    public void log(String msg) {
        log(Level.INFO, msg);
    }

    public void log(String msg, Object... args) {
        log(String.format(msg, args));
    }

    public void log(Level level, String msg) {
        getLogger().log(level, msg);
    }

    public void log(Level level, String msg, Object... args) {
        log(level, String.format(msg, args));
    }

    protected String getNameAndVersion() {
        return getDescription().getName() + " v" + getDescription().getVersion();
    }
}
