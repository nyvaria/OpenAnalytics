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
package net.nyvaria.googleanalytics;

import net.nyvaria.url.parameter.TextParameter;

/**
 * @author Paul Thompson
 *
 */
public abstract class MeasurementProtocol {
	private static final TextParameter PROTOCOL_VERSION = new TextParameter(MeasurementProtocol.PROTOCOL_VERSION_PARAMETER, "1");
	public  static final String ENDPOINT = "http://www.google-analytics.com";
	
	public static TextParameter getProtocolVersion() {
		return MeasurementProtocol.PROTOCOL_VERSION;
	}
	
	// General Parameters
	public static final String PROTOCOL_VERSION_PARAMETER          = "v";
	public static final String TRACKING_ID_PARAMETER               = "tid";
	public static final String ANONYMIZE_IP_PARAMETER              = "aip";
	public static final String QUEUE_TIME_PARAMETER                = "qt";
	public static final String CACHE_BUSTER_PARAMETER              = "z";

	// Visitor Parameters
	public static final String CLIENT_ID_PARAMETER                 = "cid";
	
	// Session Parameters
	public static final String SESSION_CONTROL_PARAMETER           = "sc";
	
	// Traffic Source Parameters
	public static final String DOCUMENT_REFERRER_PARAMETER         = "dr";
	public static final String CAMPAIGN_NAME_PARAMETER             = "cn";
	public static final String CAMPAIGN_SOURCE_PARAMETER           = "cs";
	public static final String CAMPAIGN_MEDIUM_PARAMETER           = "cm";
	public static final String CAMPAIGN_KEYWORD_PARAMETER          = "ck";
	public static final String CAMPAIGN_CONTENT_PARAMETER          = "cc";
	public static final String CAMPAIGN_ID_PARAMETER               = "ci";
	public static final String ADWORDS_ID_PARAMETER                = "gclid";
	public static final String DISPLAY_ADS_ID_PARAMETER            = "dclid";
	
	// System Info Parameters
	public static final String SCREEN_RESOLUTION_PARAMETER         = "sr";
	public static final String VIEWPORT_SIZE_PARAMETER             = "vp";
	public static final String DOCUMENT_ENCODING_PARAMETER         = "de";
	public static final String SCREEN_COLORS_PARAMETER             = "sd";
	public static final String USER_LANGUAGE_PARAMETER             = "ul";
	public static final String JAVA_ENABLED_PARAMETER              = "je";
	public static final String FLASH_VERSION_PARAMETER             = "fl";
	
	// Hit Parameters
	public static final String HIT_TYPE_PARAMETER                  = "t";
	public static final String NON_INTERACTION_HIT_PARAMETER       = "ni";
	
	// Content Information Parameters
	public static final String DOCUMENT_LOCATION_URL_PARAMETER     = "dl";
	public static final String DOCUMENT_HOST_NAME_PARAMETER        = "dh";
	public static final String DOCUMENT_PATH_PARAMETER             = "dp";
	public static final String DOCUMENT_TITLE_PARAMETER            = "dt";
	public static final String CONTENT_DESCRIPTION_PARAMETER       = "cd";
	public static final String LINK_ID_PARAMETER                   = "linkid";
	
	// Event Tracking Parameters
	public static final String EVENT_CATEGORY_PARAMETER            = "ec";
	public static final String EVENT_ACTION_PARAMETER              = "ea";
	public static final String EVENT_LABEL_PARAMETER               = "el";
	public static final String EVENT_VALUE_PARAMETER               = "ev";
	
	// E-Commerce Parameters
	public static final String TRANSACTION_ID_PARAMETER            = "ti";
	public static final String TRANSACTION_AFFILIATION_PARAMETER   = "ta";
	public static final String TRANSACTION_REVENUE_PARAMETER       = "tr";
	public static final String TRANSACTION_SHIPPING_PARAMETER      = "ts";
	public static final String TRANSACTION_TAX_PARAMETER           = "tt";
	public static final String ITEM_NAME_PARAMETER                 = "in";
	public static final String ITEM_PRICE_PARAMETER                = "ip";
	public static final String ITEM_QUANTITY_PARAMETER             = "iq";
	public static final String ITEM_CODE_PARAMETER                 = "ic";
	public static final String ITEM_CATEGORY_PARAMETER             = "iv";
	public static final String CURRENCY_CODE_PARAMETER             = "cu";
	
	// Social Interaction Parameters
	public static final String SOCIAL_NETWORK_PARAMETER            = "sn";
	public static final String SOCIAL_ACTION_PARAMETER             = "sa";
	public static final String SOCIAL_ACTION_TARGET_PARAMETER      = "st";
	
	// Timing Parameters
	public static final String USER_TIMING_CATEGORY_PARAMETER      = "utc";
	public static final String USER_TIMING_VARIABLE_NAME_PARAMETER = "utv";
	public static final String USER_TIMING_TIME_PARAMETER          = "utt";
	public static final String USER_TIMING_LABEL_PARAMETER         = "utl";
	public static final String PAGE_LOAD_TIME_PARAMETER            = "plt";
	public static final String DNS_TIME_PARAMETER                  = "dns";
	public static final String PAGE_DOWNLOAD_TIME_PARAMETER        = "pdt";
	public static final String REDIRECT_RESPONSE_TIME_PARAMETER    = "rrt";
	public static final String TCP_CONNECT_TIME_PARAMETER          = "tcp";
	public static final String SERVER_RESPONSE_TIME_PARAMETER      = "srt";
	
	// Exception Parameters
	public static final String EXCEPTION_DESCRIPTION_PARAMETER     = "exd";
	public static final String EXCEPTION_IS_FATAL_PARAMETER        = "exf";
	
	// Custom Dimension / Metric Parameter Prefixes
	public static final String CUSTOM_DIMENSION_PARAMETER_PREFIX   = "cd";
	public static final String CUSTOM_METRIC_PARAMETER_PREFIX      = "cm";
	
	// Content Experiment Parameters
	public static final String EXPERIMENT_ID_PARAMETER             = "xid";
	public static final String EXPERIMENT_VARIANT_PARAMETER        = "xvar";
	
}
