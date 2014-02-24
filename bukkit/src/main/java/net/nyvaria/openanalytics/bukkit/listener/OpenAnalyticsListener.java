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
package net.nyvaria.openanalytics.bukkit.listener;

import net.nyvaria.openanalytics.bukkit.OpenAnalytics;
import org.apache.commons.lang.Validate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Paul Thompson
 */
public final class OpenAnalyticsListener implements Listener {
    private final OpenAnalytics plugin;

    public OpenAnalyticsListener(OpenAnalytics plugin) {
        Validate.notNull(plugin, "OpenAnalyticsListener cannot have a null plugin");
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getClientList().put(event.getPlayer());
        plugin.getTracker().trackPlayerJoin(plugin.getClientList().get(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // When a PlayerQuitEvent is called following a PlayerKickEvent, we do not want to do this
        if (plugin.getClientList().containsKey(event.getPlayer())) {
            plugin.getTracker().trackPlayerQuit(plugin.getClientList().get(event.getPlayer()));
            plugin.getClientList().remove(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerKick(PlayerKickEvent event) {
        plugin.getTracker().trackPlayerKick(plugin.getClientList().get(event.getPlayer()));
        plugin.getClientList().remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        plugin.getTracker().trackPlayerChangedWorld(plugin.getClientList().get(event.getPlayer()));
    }

}
