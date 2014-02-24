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
public class UserTimingHit extends Hit {
    @Parameter(format = Parameter.FORMAT_TEXT, required = true, name = MeasurementProtocol.HIT_TYPE)
    private static final String HIT_TYPE = "timing";

    /**
     * Constructor & Methods
     */

    public UserTimingHit(Client client) {
        super(client, UserTimingHit.HIT_TYPE);
    }

    /**
     * User Timing Parameters
     */

    @Parameter(format = Parameter.FORMAT_TEXT, required = false, name = MeasurementProtocol.USER_TIMING_CATEGORY)
    public String user_timing_category;

    @Parameter(format = Parameter.FORMAT_TEXT, required = false, name = MeasurementProtocol.USER_TIMING_VARIABLE_NAME)
    public String user_timing_variable_name;

    @Parameter(format = Parameter.FORMAT_INTEGER, required = false, name = MeasurementProtocol.USER_TIMING_TIME)
    public Integer user_timing_time;

    @Parameter(format = Parameter.FORMAT_TEXT, required = false, name = MeasurementProtocol.USER_TIMING_LABEL)
    public String user_timing_label;

    @Parameter(format = Parameter.FORMAT_INTEGER, required = false, name = MeasurementProtocol.PAGE_LOAD_TIME)
    public Integer page_load_time;

    @Parameter(format = Parameter.FORMAT_INTEGER, required = false, name = MeasurementProtocol.DNS_TIME)
    public Integer dns_time;

    @Parameter(format = Parameter.FORMAT_INTEGER, required = false, name = MeasurementProtocol.PAGE_DOWNLOAD_TIME)
    public Integer page_download_time;

    @Parameter(format = Parameter.FORMAT_INTEGER, required = false, name = MeasurementProtocol.REDIRECT_RESPONSE_TIME)
    public Integer redirect_response_time;

    @Parameter(format = Parameter.FORMAT_INTEGER, required = false, name = MeasurementProtocol.TCP_CONNECT_TIME)
    public Integer tcp_connect_time;

    @Parameter(format = Parameter.FORMAT_INTEGER, required = false, name = MeasurementProtocol.SERVER_RESPONSE_TIME)
    public Integer server_response_time;
}
