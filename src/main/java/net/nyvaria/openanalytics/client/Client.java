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
import net.nyvaria.url.parameter.TextParameter;

import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public class Client {
	private final Player   player;
	private UUID           unique_id; // These needs to be changed to something that Google cannot use to identify the client (i.e. our own random UUID)
	private TextParameter  client_id;
	
	public Client(Player player) {
		this.player    = player;
		this.unique_id = player.getUniqueId();
		this.client_id = new TextParameter(MeasurementProtocol.CLIENT_ID_PARAMETER, unique_id.toString());
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public TextParameter getClientID() {
		return client_id;
	}
}
