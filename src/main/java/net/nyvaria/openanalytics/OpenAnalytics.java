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

import java.util.logging.Level;

import net.nyvaria.component.hook.MetricsHook;
import net.nyvaria.component.hook.MultiverseHook;
import net.nyvaria.component.hook.SignShopHook;
import net.nyvaria.openanalytics.client.ClientList;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Paul Thompson
 *
 */
public class OpenAnalytics extends JavaPlugin {
	protected static OpenAnalytics instance  = null;
	
	// Open Analytics Listener and Tracker
	protected OpenAnalyticsListener listener = null;
	protected OpenAnalyticsTracker  tracker  = null;
	
	// SignShop Listener
	protected SignShopListener signshop_listener = null;
	
	// Client List
	public ClientList clientList = null;
	
	public static OpenAnalytics getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		// First set the instance
		OpenAnalytics.instance = this;
		
		// Initialise or update the configuration
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		
		// Create an empty client map
		this.clientList = new ClientList();
		
		// Create and register the listener
		this.listener = new OpenAnalyticsListener(this);
		
		// Create the tracker
		this.tracker = new OpenAnalyticsTracker(this);
		
		// Initialise hooks
		MetricsHook.initialize(this);
		MultiverseHook.initialize(this);
		SignShopHook.initialize(this);
		
		if (SignShopHook.is_hooked()) {
			this.signshop_listener = new SignShopListener(this);
		}
		
		// Print a lovely log message
		this.log("Enabling " + this.getNameAndVersion() + " successful");
	}
	
	@Override
	public void onDisable() {
		// Destroy the tracker
		this.tracker = null;
		
		// Destroy the client map
		this.clientList = null;
		
		// And null the instance
		OpenAnalytics.instance = null;
		
		// Print a lovely log message
		this.log("Disabling " + this.getNameAndVersion() + " successful");
	}
	
	public void log(String msg) {
		this.log(Level.INFO, msg);
	}
	
	public void log(Level level, String msg) {
		this.getLogger().log(level, msg);
	}
	
	private String getNameAndVersion() {
		return this.getName() + " v" + this.getDescription().getVersion();
	}
}
