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
import net.nyvaria.openanalytics.bungee.OpenAnalyticsProxy;

/**
 * Created by Paul Thompson on 23/02/2014.
 * @author Paul Thompson
 */
public class ProxiedClient {
    private final ProxiedPlayer player;
    private final ProxiedClientConfig config;

    public ProxiedClient(ProxiedPlayer player) {
        this.player = player;
        this.config = new ProxiedClientConfig(player);
    }

    public String getClientID() {
        return config.getClientID();
    }

    public String getIPAddress() {
        return player.getAddress().getAddress().toString().replace("/", "");
    }

    public boolean isOptedIn() {
        return config.isOptedIn();
    }

    public void setOptOut(boolean optout) {
        config.setOptOut(optout);
    }

    public static void setOptOut(ProxiedPlayer player, boolean optout) {
        ProxiedClient client = OpenAnalyticsProxy.getInstance().getProxiedClientList().get(player);

        if (client != null) {
            client.setOptOut(optout);
        } else {
            ProxiedClientConfig config = new ProxiedClientConfig(player);
            config.setOptOut(optout);
        }
    }
}
