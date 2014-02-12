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

import net.nyvaria.component.exception.CannotEnablePluginException;
import net.nyvaria.component.hook.*;
import net.nyvaria.component.wrapper.NyvariaPlugin;
import net.nyvaria.openanalytics.client.ClientList;
import net.nyvaria.openanalytics.cmd.AnalyticsCommand;
import net.nyvaria.openanalytics.listener.OpenAnalyticsListener;
import net.nyvaria.openanalytics.listener.SignShopListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Paul Thompson
 */
public class OpenAnalytics extends NyvariaPlugin {
    public static final String PERM_ROOT = "openanalytics";

    // Tracker and Listeners and a List (oh my)
    private OpenAnalyticsTracker  tracker      = null;
    private OpenAnalyticsListener listener     = null;
    private SignShopListener      shopListener = null;
    private ClientList            clientList   = null;

    /**
     * Override the onEnable & onDisable methods
     */

    @Override
    public void onEnable() {
        try {
            // Initialise or update the configuration
            saveDefaultConfig();
            getConfig().options().copyDefaults(true);

            // Initialise required hooks
            if (!VaultHook.enable(this)) {
                throw new CannotEnablePluginException("Vault not found");
            }

            // Initialise optional hooks
            MetricsHook.enable(this);
            MultiverseHook.enable(this);
            SignShopHook.enable(this);
            ZPermissionsHook.enable(this);

            // Create the tracker and client list
            tracker = new OpenAnalyticsTracker(this);

            // Create the client list and add all currently logged in players
            clientList = new ClientList();
            for (Player player : getServer().getOnlinePlayers()) {
                clientList.put(player);
            }

            // Create and register the listeners
            listener = new OpenAnalyticsListener(this);
            if (SignShopHook.isHooked()) {
                shopListener = new SignShopListener(this);
            }

            // Create the commands and set the executors and completers
            AnalyticsCommand cmdAnalytics = new AnalyticsCommand();
            getCommand(AnalyticsCommand.CMD).setExecutor(cmdAnalytics);
            getCommand(AnalyticsCommand.CMD).setTabCompleter(cmdAnalytics);

        } catch (CannotEnablePluginException e) {
            log("Enabling %1$s failed - %2$s", getNameAndVersion(), e.getMessage());
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);

        } finally {
            log("Enabling %1$s successful", getNameAndVersion());
        }
    }

    @Override
    public void onDisable() {
        // Destroy the listener and tracker
        listener = null;
        tracker  = null;

        // Disable the hooks
        VaultHook.disable();
        ZPermissionsHook.disable();
        SignShopHook.disable();
        MultiverseHook.disable();
        MetricsHook.disable();

        // Destroy the client map
        clientList = null;

        // Print a lovely log message
        log("Disabling %s successful", getNameAndVersion());
    }

    /**
     * Get the instance of the OpenAnalytics plugin from Bukkit.
     * @return OpenAnalytics
     */
    public static OpenAnalytics getInstance() {
        return JavaPlugin.getPlugin(OpenAnalytics.class);
    }

    /**
     * Getters
     */

    public OpenAnalyticsListener getListener() {
        return listener;
    }

    public OpenAnalyticsTracker getTracker() {
        return tracker;
    }

    public SignShopListener getShopListener() {
        return shopListener;
    }

    public ClientList getClientList() {
        return clientList;
    }
}
