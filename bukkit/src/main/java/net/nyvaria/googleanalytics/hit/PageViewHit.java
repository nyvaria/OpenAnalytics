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
package net.nyvaria.googleanalytics.hit;

import net.nyvaria.googleanalytics.MeasurementProtocol;
import net.nyvaria.googleanalytics.Parameter;
import net.nyvaria.openanalytics.bukkit.client.Client;

/**
 * @author Paul Thompson
 */
public class PageViewHit extends Hit {
    @Parameter(format = Parameter.FORMAT_TEXT, required = true, name = MeasurementProtocol.HIT_TYPE)
    private static final String HIT_TYPE = "pageview";

    /**
     * Constructor & Methods
     */

    public PageViewHit(Client client) {
        super(client, PageViewHit.HIT_TYPE);
    }
}
