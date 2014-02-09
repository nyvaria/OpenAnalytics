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

    // Listener and Tracker and Lists (oh my)
    private OpenAnalyticsListener listener = null;
    private OpenAnalyticsTracker tracker = null;
    private SignShopListener shopListener = null;
    private ClientList clientList = null;

    // Commands
    private AnalyticsCommand cmdAnalytics = null;

    /**
     * Get the instance of the OpenAnalytics plugin from Bukkit.
     * @return OpenAnalytics
     */
    public static OpenAnalytics getInstance() {
        return JavaPlugin.getPlugin(OpenAnalytics.class);
    }

    /**
     * Override the onEnable & onDisable methods
     */

    @Override
    public void onEnable() {
        try {
            // Initialise or update the configuration
            this.saveDefaultConfig();
            this.getConfig().options().copyDefaults(true);

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
            this.tracker = new OpenAnalyticsTracker(this);

            // Create the client list and add all currently logged in players
            this.clientList = new ClientList();
            for (Player player : this.getServer().getOnlinePlayers()) {
                this.clientList.put(player);
            }

            // Create and register the listeners
            this.listener = new OpenAnalyticsListener(this);
            if (SignShopHook.isHooked()) {
                this.shopListener = new SignShopListener(this);
            }

            // Create and set the commands
            this.cmdAnalytics = new AnalyticsCommand();
            this.getCommand(AnalyticsCommand.CMD).setExecutor(cmdAnalytics);

        } catch (CannotEnablePluginException e) {
            this.log("Enabling %1$s failed - %2$s", this.getNameAndVersion(), e.getMessage());
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);

        } finally {
            this.log("Enabling %1$s successful", this.getNameAndVersion());
        }
    }

    @Override
    public void onDisable() {
        // Destroy the tracker
        this.tracker = null;

        // Disable the hooks
        VaultHook.disable();
        ZPermissionsHook.disable();
        SignShopHook.disable();
        MultiverseHook.disable();
        MetricsHook.disable();

        // Destroy the client map
        this.clientList = null;

        // Print a lovely log message
        this.log("Disabling %s successful", this.getNameAndVersion());
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
