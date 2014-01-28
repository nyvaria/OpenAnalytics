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
package net.nyvaria.metrics;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Paul Thompson
 *
 */
public class MetricsHandler {
	private static final String METRICS_URL_PREFIX = "http://mcstats.org/plugin/";
	protected      final String metrics_url;
	
	@SuppressWarnings("unused")
	private        final JavaPlugin plugin;
	
	private MetricsHandler(JavaPlugin plugin) {
		this.metrics_url = METRICS_URL_PREFIX + plugin.getName();
		this.plugin      = plugin;
	}
	
	public static MetricsHandler initialise(JavaPlugin plugin) {
		MetricsHandler metricsHandler = null;
		
		if (!plugin.getConfig().getBoolean("use-metrics")) {
			plugin.getLogger().log(Level.INFO, "Skipping metrics");
		
		} else {
			metricsHandler = new MetricsHandler(plugin);
			
			try {
				metricsHandler.run();
				plugin.getLogger().log(Level.INFO, "Metrics started: " + metricsHandler.metrics_url);
			} catch (IOException e) {
				plugin.getLogger().log(Level.WARNING, "Failed to start metrics");
				e.printStackTrace();
			}
		}
		
		return metricsHandler;
	}
	
	protected void run() throws IOException {
		//Metrics metrics = new Metrics(plugin);
		//metrics.start();
	}
}
