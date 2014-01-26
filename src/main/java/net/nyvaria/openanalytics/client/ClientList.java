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

import java.util.HashMap;

import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public class ClientList {
	private HashMap<Player, Client> list;

	public ClientList() {
		this.list = new HashMap<Player, Client>();
	}
	
	public void put(Player player) {
		if (!this.list.containsKey(player)) {
			Client client = new Client(player);
			this.list.put(player, client);
		}
	}
	
	public void remove(Player player) {
		if (this.list.containsKey(player)) {
			this.list.remove(player);
		}
	}
	
	public Client get(Player player) {
		if (this.list.containsKey(player)) {
			return this.list.get(player);
		}
		return null;
	}
	
	public void clear() {
		this.list.clear();
	}
}
