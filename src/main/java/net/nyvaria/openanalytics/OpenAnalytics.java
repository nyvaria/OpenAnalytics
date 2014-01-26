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

import net.nyvaria.googleanalytics.MeasurementProtocolClient;
import net.nyvaria.metrics.MetricsHandler;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Paul Thompson
 *
 */
public class OpenAnalytics extends JavaPlugin {
	protected static OpenAnalytics instance = null;
	
	// Open Analytics Listener and Metrics Handler
	protected OpenAnalyticsListener listener = null;
	protected MetricsHandler        metrics  = null;
	
	// Create a Measurements Protocol Client
	protected MeasurementProtocolClient client = null;
	
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
		
		// Create and register the listener
		this.listener = OpenAnalyticsListener.getInstance();
		this.getServer().getPluginManager().registerEvents(this.listener, this);
		
		// Create the measurements protocol client
		if (this.getConfig().contains("tracking-id")) {
			this.client = new MeasurementProtocolClient(this.getConfig().getString("tracking-id"));
		}
		
		// Initialise metrics
		this.metrics = MetricsHandler.initialiseMetrics(this);
		
		// Print a lovely log message
		this.log("Enabling " + this.getNameAndVersion() + " successful");
	}
	
	@Override
	public void onDisable() {
		// Destroy the metrics handler
		this.metrics = null;
		
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
