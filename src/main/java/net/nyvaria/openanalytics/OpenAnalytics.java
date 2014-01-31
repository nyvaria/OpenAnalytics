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

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import net.nyvaria.component.exception.CannotEnablePluginException;
import net.nyvaria.component.hook.MetricsHook;
import net.nyvaria.component.hook.MultiverseHook;
import net.nyvaria.component.hook.SignShopHook;
import net.nyvaria.component.hook.VaultHook;
import net.nyvaria.component.hook.ZPermissionsHook;
import net.nyvaria.component.wrapper.NyvariaPlugin;
import net.nyvaria.openanalytics.client.ClientList;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Paul Thompson
 *
 */
public class OpenAnalytics extends NyvariaPlugin {
	public  static final String  PERM_ROOT = "openanalytics";
	private static OpenAnalytics instance  = null;
	
	// Listener and Tracker and Lists (oh my)
	private OpenAnalyticsListener listener     = null;
	private OpenAnalyticsTracker  tracker      = null;
	private SignShopListener      shopListener = null;
	private ClientList            clientList   = null;
	
	// Client config file variables 
	private final File        playerConfigFile = new File(this.getDataFolder(), "players.yml");
	private FileConfiguration playerConfig     = null;
	
	public static OpenAnalytics getInstance() {
		return instance;
	}
	
	/*********************************************/
	/* Override the onEnable & onDisable methods */
	/*********************************************/
	
	@Override
	public void onEnable() {
		try {
			// First set the instance
			OpenAnalytics.instance = this;
			
			// Initialise or update the configuration
			this.saveDefaultConfig();
			this.getConfig().options().copyDefaults(true);
			
			// Load the client config
			this.playerConfig = loadPlayerConfig();
			
			// Create an empty client list and tracker
			this.clientList = new ClientList();
			this.tracker = new OpenAnalyticsTracker(this);
			
			// Then create and register the listener (order is important) 
			this.listener = new OpenAnalyticsListener(this);
			
			// Initialise required hooks
			if (!VaultHook.initialize(this)) {
				throw new CannotEnablePluginException("Vault not found");
			}
			
			// Initialise optional hooks
			MetricsHook.initialize(this);
			MultiverseHook.initialize(this);
			SignShopHook.initialize(this);
			ZPermissionsHook.initialize(this);
			
			if (SignShopHook.is_hooked()) {
				this.shopListener = new SignShopListener(this);
			}
		
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
		
		// Destroy the client map
		this.clientList = null;
		
		// And null the instance
		OpenAnalytics.instance = null;
		
		// Print a lovely log message
		this.log("Disabling %s succesful", this.getNameAndVersion());
	}
	
	private FileConfiguration loadPlayerConfig() throws CannotEnablePluginException {
		FileConfiguration config = new YamlConfiguration();
		
		if (playerConfigFile.isFile()) {
			// Attempt to load the player configuration file
			try {
				this.log("Loading player configuration file - %1$s", playerConfigFile.getName());
				config.load(playerConfigFile);
				
			} catch (IOException e) {
				throw new CannotEnablePluginException("Cannot read player configuration file", e);
				
			} catch (InvalidConfigurationException e) {
				throw new CannotEnablePluginException("Invalid player configuraiton file", e);
			}
			
		} else {
			// Attempt to create a new player configuration file
			try {
				this.log("Player configuration file not found");
				this.log("Creating new player configuration file - %1$s", playerConfigFile.getName());
				config.save(playerConfigFile);
				
			} catch (IOException e) {
				throw new CannotEnablePluginException("Cannot create new player configuration file", e);
			}
		}
		
		return config;
	}
	
	public void savePlayerConfig() {
		try {
			this.playerConfig.save(this.playerConfigFile);
		} catch (IOException e) {
			this.log(Level.WARNING, "Cannot save player configuration file");
			e.printStackTrace();
		}
	}

	/***********/
	/* Getters */
	/***********/
	
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
	
	public FileConfiguration getPlayerConfig() {
		return playerConfig;
	}
}
