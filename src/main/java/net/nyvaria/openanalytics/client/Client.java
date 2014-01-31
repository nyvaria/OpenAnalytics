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

import net.nyvaria.component.wrapper.NyvariaPlayer;
import net.nyvaria.component.wrapper.NyvariaWorld;
import net.nyvaria.googleanalytics.MeasurementProtocolClient;
import net.nyvaria.googleanalytics.hit.EventHit;
import net.nyvaria.googleanalytics.hit.PageViewHit;
import net.nyvaria.openanalytics.OpenAnalytics;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public class Client extends NyvariaPlayer {
	private final ClientConfig config;
	
	public Client(Player player) {
		super(player);
		this.config = new ClientConfig(player);
	}
	
	public Client(OfflinePlayer offlinePlayer) {
		super(offlinePlayer);
		this.config = new ClientConfig(offlinePlayer);
	}

	public String getClientID() {
		return config.getClientID();
	}
	
	public String getIPAddress() {
		return getPlayer().getAddress().getAddress().toString().replace("/", "");
	}
	
	public boolean isOptedOut() {
		return config.isOptedOut();
	}
	
	public void setOptOut(boolean optout) {
		config.setOptOut(optout);
	}
	
	public static void setOptOut(OfflinePlayer offlinePlayer, boolean optout) {
		
	}
	
	public static void setOptOut(Player player, boolean optout) {
		// See if we need to set an online player as opted out
		Client client = OpenAnalytics.getInstance().getClientList().get(player);
		if (client != null) {
			client.setOptOut(optout);
		}
		
		// And create a throwaway client config and set the value
		ClientConfig config = new ClientConfig(player);
		config.setOptOut(optout);
	}
	
	/**********************/
	/* Event Hit Creation */
	/**********************/
	
	public EventHit createPlayerJoinHit() {
		EventHit eventHit = new EventHit(this, "Player Connection", "Join");
		eventHit.event_label           = "Player Join";
		eventHit.session_control       = "start";
		eventHit.document_location_url = getWorldURL();
		eventHit.document_title        = getWorldTitle();
		return eventHit;
	}
	
	public EventHit createPlayerQuitHit() {
		EventHit eventHit = new EventHit(this, "Player Connection", "Quit");
		eventHit.event_label           = "Player Quit";
		eventHit.session_control       = "end";
		eventHit.document_location_url = getWorldURL();
		eventHit.document_title        = getWorldTitle();
		return eventHit;
	}
	
	public EventHit createPlayerKickHit() {
		EventHit eventHit = new EventHit(this, "Player Connection", "Kick");
		eventHit.event_label           = "Player Kick";
		eventHit.session_control       = "end";
		eventHit.document_location_url = getWorldURL();
		eventHit.document_title        = getWorldTitle();
		return eventHit;
	}
	
	/**************************/
	/* Page View Hit Creation */
	/**************************/
	
	public PageViewHit createWorldHit() {
		PageViewHit worldHit = new PageViewHit(this);
		worldHit.document_location_url = getWorldURL();
		worldHit.document_title = getWorldTitle();
		return worldHit;
	}
	
	/**********************/
	/* Supporting Methods */
	/**********************/
	
	private String getWorldURL() {
		return "http://"
				+ MeasurementProtocolClient.getInstance().document_host_name
				+ "/world/"
				+ getPlayer().getLocation().getWorld().getName();
	}
	
 	private String getWorldTitle() {
		return "World - " + NyvariaWorld.getWorldAlias(getPlayer().getLocation());
	}
}
