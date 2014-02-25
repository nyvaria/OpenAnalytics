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

import net.nyvaria.component.exception.CannotEnablePluginException;
import net.nyvaria.component.hook.*;
import net.nyvaria.component.wrapper.NyvariaPlugin;
import net.nyvaria.openanalytics.bukkit.client.ClientList;
import net.nyvaria.openanalytics.bukkit.cmd.AnalyticsCommand;
import net.nyvaria.openanalytics.bukkit.listener.OpenAnalyticsListener;
import net.nyvaria.openanalytics.bukkit.listener.SignShopListener;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 */
public class OpenAnalytics extends NyvariaPlugin {
    public static final String PERM_ROOT = "openanalytics";

    // Gotta stay with an instance variable until spigot updates to 1.7.4
    private static OpenAnalytics instance = null;

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
            // Save the instance
            OpenAnalytics.instance = this;

            // Initialise or update the configuration
            saveDefaultConfig();
            getConfig().options().copyDefaults(true);
            saveConfig();

            // Initialise required hooks
            if (!VaultHook.enable(this)) {
                throw new CannotEnablePluginException("Vault not found");
            }

            // Initialise optional hooks
            MetricsHook.enable(this);
            MultiverseHook.enable(this);
            ZPermissionsHook.enable(this);

            // Hook SignShop if enabled
            if (OpenAnalyticsConfig.getUseSignShop()) {
                SignShopHook.enable(this);
            }

            // Hook BungeeCord if enabled
            if (OpenAnalyticsConfig.getInBungeeCord()) {
                BungeeHook.enable(this);
            }

            // Create the tracker
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
            OpenAnalytics.instance = null;

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
        BungeeHook.disable();
        SignShopHook.disable();
        ZPermissionsHook.disable();
        MultiverseHook.disable();
        MetricsHook.disable();
        VaultHook.disable();

        // Destroy the client list
        clientList = null;

        // Clear the instance
        OpenAnalytics.instance = null;

        // Print a lovely log message
        log("Disabling %s successful", getNameAndVersion());
    }

    /**
     * Get the instance of the OpenAnalytics plugin from Bukkit.
     * @return OpenAnalytics
     */
    public static OpenAnalytics getInstance() {
        //return JavaPlugin.getPlugin(OpenAnalytics.class);
        return instance;
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
