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

import net.nyvaria.googleanalytics.MeasurementProtocol;
import net.nyvaria.googleanalytics.MeasurementProtocolClient;
import net.nyvaria.googleanalytics.Parameter;
import net.nyvaria.googleanalytics.hit.EventHit;
import net.nyvaria.googleanalytics.hit.PageViewHit;
import net.nyvaria.hook.MultiverseHook;

import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public class Client {
	private final Player player;
	
	// According to the Google Measurement Protocol Policy, the UUID used for the Client ID has to be something
	// that Google cannot use to identify the person involved. For expediency, this initial hack will use the
	// Minecraft user's UUID. However, that will need to change. What we will do is generate a random UUID per
	// user and save that in a data file of some sorts.
	private UUID unique_id;
	
	@Parameter(format="text", required=true, name=MeasurementProtocol.CLIENT_ID)
	private String client_id;
	
	public Client(Player player) {
		this.player    = player;
		this.unique_id = player.getUniqueId();
		this.client_id = unique_id.toString();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getClientID() {
		return client_id;
	}
	
	public String getIPAddress() {
		return this.player.getAddress().getAddress().toString().replace("/", "");
	}
	
	/**********************/
	/* Event Hit Creation */
	/**********************/
	
	public EventHit createPlayerJoinHit() {
		EventHit eventHit = new EventHit(this, "Player Connection", "Join");
		eventHit.event_label = "Player Join";
		eventHit.session_control = "start";
		return eventHit;
	}
	
	public EventHit createPlayerQuitHit() {
		EventHit eventHit = new EventHit(this, "Player Connection", "Quit");
		eventHit.event_label = "Player Quit";
		eventHit.session_control = "end";
		return eventHit;
	}
	
	public EventHit createPlayerKickHit() {
		EventHit eventHit = new EventHit(this, "Player Connection", "Kick");
		eventHit.event_label = "Player Kick";
		eventHit.session_control = "end";
		return eventHit;
	}
	
	/**************************/
	/* Page View Hit Creation */
	/**************************/
	
	public PageViewHit createWorldHit() {
		PageViewHit worldHit = new PageViewHit(this);
		worldHit.document_path  = "/world/" + this.getPlayer().getLocation().getWorld().getName();
		worldHit.document_title = "World - " + MultiverseHook.getWorldAlias(this.getPlayer().getLocation().getWorld().getName());
		worldHit.document_host_name = MeasurementProtocolClient.getInstance().document_host_name;
		return worldHit;
	}
}
