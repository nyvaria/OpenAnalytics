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

import java.util.List;

import net.nyvaria.googleanalytics.MeasurementProtocol;
import net.nyvaria.url.parameter.IntegerParameter;
import net.nyvaria.url.parameter.Parameter;
import net.nyvaria.url.parameter.TextParameter;

/**
 * @author Paul Thompson
 *
 */
public class UserTimingHit extends Hit {
	private static final TextParameter HIT_TYPE = new TextParameter(MeasurementProtocol.HIT_TYPE_PARAMETER, "timing");
	
	// User Timing Parameters
	private TextParameter    user_timing_category;
	private TextParameter    user_timing_variable_name;
	private IntegerParameter user_timing_time;
	private TextParameter    user_timing_label;
	private IntegerParameter page_load_time;
	private IntegerParameter dns_time;
	private IntegerParameter page_download_time;
	private IntegerParameter redirect_response_time;
	private IntegerParameter tcp_connect_time;
	private IntegerParameter server_response_time;
	
	public UserTimingHit(TextParameter client_id) {
		super(client_id, UserTimingHit.HIT_TYPE);
	}
	
	/* (non-Javadoc)
	 * @see net.nyvaria.googleanalytics.hit.Hit#getParameterList()
	 */
	@Override
	public List<Parameter> getParameterList() {
		List<Parameter> list = super.getParameterList();
		
		// Add the optional parameters
		if (user_timing_category      != null) list.add(user_timing_category);
		if (user_timing_variable_name != null) list.add(user_timing_variable_name);
		if (user_timing_time          != null) list.add(user_timing_time);
		if (user_timing_label         != null) list.add(user_timing_label);
		if (page_load_time            != null) list.add(page_load_time);
		if (dns_time                  != null) list.add(dns_time);
		if (page_download_time        != null) list.add(page_download_time);
		if (redirect_response_time    != null) list.add(redirect_response_time);
		if (tcp_connect_time          != null) list.add(tcp_connect_time);
		if (server_response_time      != null) list.add(server_response_time);
		
		return list;
	}

	// User Timing Parameter Setters
	public void setUserTimingCategory(String user_timing_category) {
		this.user_timing_category = new TextParameter(MeasurementProtocol.USER_TIMING_CATEGORY_PARAMETER, user_timing_category);
	}

	public void setUserTimingVariableName(String user_timing_variable_name) {
		this.user_timing_variable_name = new TextParameter(MeasurementProtocol.USER_TIMING_VARIABLE_NAME_PARAMETER, user_timing_variable_name);
	}

	public void setUserTimingTime(int user_timing_time) {
		this.user_timing_time = new IntegerParameter(MeasurementProtocol.USER_TIMING_TIME_PARAMETER, user_timing_time);
	}

	public void setUserTimingLabel(String user_timing_label) {
		this.user_timing_label = new TextParameter(MeasurementProtocol.USER_TIMING_LABEL_PARAMETER, user_timing_label);
	}

	public void setPageLoadTime(int page_load_time) {
		this.page_load_time = new IntegerParameter(MeasurementProtocol.PAGE_LOAD_TIME_PARAMETER, page_load_time);
	}

	public void setDNSTime(int dns_time) {
		this.dns_time = new IntegerParameter(MeasurementProtocol.DNS_TIME_PARAMETER, dns_time);
	}

	public void setPageDownloadTime(int page_download_time) {
		this.page_download_time = new IntegerParameter(MeasurementProtocol.PAGE_DOWNLOAD_TIME_PARAMETER, page_download_time);
	}

	public void setRedirectResponseTime(int redirect_response_time) {
		this.redirect_response_time = new IntegerParameter(MeasurementProtocol.REDIRECT_RESPONSE_TIME_PARAMETER, redirect_response_time);
	}

	public void setTCPConnectTime(int tcp_connect_time) {
		this.tcp_connect_time = new IntegerParameter(MeasurementProtocol.TCP_CONNECT_TIME_PARAMETER, tcp_connect_time);
	}

	public void setServerResponseTime(int server_response_time) {
		this.server_response_time = new IntegerParameter(MeasurementProtocol.SERVER_RESPONSE_TIME_PARAMETER, server_response_time);
	}
}
