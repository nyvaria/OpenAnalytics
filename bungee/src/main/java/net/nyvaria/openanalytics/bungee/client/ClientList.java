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
package net.nyvaria.openanalytics.bungee.client;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Paul Thompson on 23/02/2014.
 * @author Paul Thompson
 */
public class ClientList implements Iterable<Client> {
    HashMap<ProxiedPlayer, Client> map;

    public ClientList() {
        map = new HashMap<ProxiedPlayer, Client>();
    }

    public boolean containsKey(ProxiedPlayer player) {
        return map.containsKey(player);
    }

    public void put(ProxiedPlayer player) {
        if (!containsKey(player)) {
            map.put(player, new Client(player));
        }
    }

    public void remove(ProxiedPlayer player) {
        if (map.containsKey(player)) {
            map.remove(player);
        }
    }

    public Client get(ProxiedPlayer player) {
        if (map.containsKey(player)) {
            return map.get(player);
        }
        return null;
    }

    public void clear() {
        map.clear();
    }

    @Override
    public Iterator<Client> iterator() {
        return map.values().iterator();
    }
}
