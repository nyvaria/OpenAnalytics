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
package net.nyvaria.openanalytics.client;

import java.util.UUID;

import net.nyvaria.openanalytics.OpenAnalytics;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public class ClientConfig {
	private final Player player;
	private final UUID anonymizedID;
	private final String clientId;
	private       boolean optout;
	
	public ClientConfig(OfflinePlayer offlinePlayer) {
		this(offlinePlayer.getPlayer());
	}
	
	public ClientConfig(Player player) {
		Configuration playerConfig = OpenAnalytics.getInstance().getPlayerConfig();
		ConfigurationSection configSection;
		
		// Load attributes from the player config or create and save it
		if (playerConfig.contains(player.getName())) {
			configSection = playerConfig.getConfigurationSection(player.getName());
		} else {
			configSection = playerConfig.createSection(player.getName());
			playerConfig.set(player.getName() + ".anonymized-id", UUID.randomUUID().toString());
			playerConfig.set(player.getName() + ".opt-out", false);
			OpenAnalytics.getInstance().savePlayerConfig();
		}
		
		this.player       = player;
		this.anonymizedID = UUID.fromString(configSection.getString("anonymized-id"));
		this.clientId     = anonymizedID.toString();
		this.optout       = configSection.getBoolean("opt-out");
	}
	
	public UUID getAnonymizedID() {
		return anonymizedID;
	}
	
	public String getClientID() {
		return clientId;
	}
	
	public boolean isOptedOut() {
		return optout;
	}
	
	public void setOptOut(boolean optout) {
		this.optout = optout;
		OpenAnalytics.getInstance().getPlayerConfig().set(player.getName() + ".opt-out", optout);
		OpenAnalytics.getInstance().savePlayerConfig();
	}
}
