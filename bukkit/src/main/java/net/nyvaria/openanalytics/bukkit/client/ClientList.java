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
package net.nyvaria.openanalytics.bukkit.client;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Paul Thompson
 */
public class ClientList implements Iterable<Client> {
    private HashMap<Player, Client> map;

    public ClientList() {
        map = new HashMap<Player, Client>();
    }

    public boolean containsKey(Player player) {
        return map.containsKey(player);
    }

    public boolean containsKey(OfflinePlayer offlinePlayer) {
        return containsKey(offlinePlayer.getPlayer());
    }

    public void put(Player player) {
        if (!containsKey(player)) {
            Client client = new Client(player);
            map.put(player, client);
        }
    }

    public void put(OfflinePlayer offlinePlayer) {
        put(offlinePlayer.getPlayer());
    }

    public void remove(Player player) {
        if (map.containsKey(player)) {
            map.remove(player);
        }
    }

    public void remove(OfflinePlayer offlinePlayer) {
        remove(offlinePlayer.getPlayer());
    }

    public Client get(Player player) {
        if (map.containsKey(player)) {
            return map.get(player);
        }
        return null;
    }

    public Client get(OfflinePlayer offlinePlayer) {
        return get(offlinePlayer.getPlayer());
    }

    public void clear() {
        map.clear();
    }

    @Override
    public Iterator<Client> iterator() {
        return map.values().iterator();
    }
}
