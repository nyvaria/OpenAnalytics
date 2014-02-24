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

/**
 * @author Paul Thompson
 */
public abstract class MeasurementProtocol {
    public static final String ENDPOINT = "http://www.google-analytics.com/collect";
    public static final String ENDPOINT_PROTOCOL_VERSION = "1";
    public static final String ENDPOINT_ENCODING         = "UTF-8";
    public static final String ENDPOINT_REQUEST_METHOD   = "POST";

    // General Parameters
    public static final String PROTOCOL_VERSION          = "v";
    public static final String TRACKING_ID               = "tid";
    public static final String ANONYMIZE_IP              = "aip";
    public static final String QUEUE_TIME                = "qt";
    public static final String CACHE_BUSTER              = "z";
    public static final String IP_OVERRIDE               = "uip";
    public static final String USER_AGENT_OVERRIDE       = "ua";

    // Visitor Parameters
    public static final String CLIENT_ID                 = "cid";

    // Session Parameters
    public static final String SESSION_CONTROL           = "sc";

    // Traffic Source Parameters
    public static final String DOCUMENT_REFERRER         = "dr";
    public static final String CAMPAIGN_NAME             = "cn";
    public static final String CAMPAIGN_SOURCE           = "cs";
    public static final String CAMPAIGN_MEDIUM           = "cm";
    public static final String CAMPAIGN_KEYWORD          = "ck";
    public static final String CAMPAIGN_CONTENT          = "cc";
    public static final String CAMPAIGN_ID               = "ci";
    public static final String ADWORDS_ID                = "gclid";
    public static final String DISPLAY_ADS_ID            = "dclid";

    // System Info Parameters
    public static final String SCREEN_RESOLUTION         = "sr";
    public static final String VIEWPORT_SIZE             = "vp";
    public static final String DOCUMENT_ENCODING         = "de";
    public static final String SCREEN_COLORS             = "sd";
    public static final String USER_LANGUAGE             = "ul";
    public static final String JAVA_ENABLED              = "je";
    public static final String FLASH_VERSION             = "fl";

    // Hit Parameters
    public static final String HIT_TYPE                  = "t";
    public static final String NON_INTERACTION_HIT       = "ni";

    // Content Information Parameters
    public static final String DOCUMENT_LOCATION_URL     = "dl";
    public static final String DOCUMENT_HOST_NAME        = "dh";
    public static final String DOCUMENT_PATH             = "dp";
    public static final String DOCUMENT_TITLE            = "dt";
    public static final String CONTENT_DESCRIPTION       = "cd";
    public static final String LINK_ID                   = "linkid";

    // Event Tracking Parameters
    public static final String EVENT_CATEGORY            = "ec";
    public static final String EVENT_ACTION              = "ea";
    public static final String EVENT_LABEL               = "el";
    public static final String EVENT_VALUE               = "ev";

    // E-Commerce Parameters
    public static final String TRANSACTION_ID            = "ti";
    public static final String TRANSACTION_AFFILIATION   = "ta";
    public static final String TRANSACTION_REVENUE       = "tr";
    public static final String TRANSACTION_SHIPPING      = "ts";
    public static final String TRANSACTION_TAX           = "tt";
    public static final String ITEM_NAME                 = "in";
    public static final String ITEM_PRICE                = "ip";
    public static final String ITEM_QUANTITY             = "iq";
    public static final String ITEM_CODE                 = "ic";
    public static final String ITEM_CATEGORY             = "iv";
    public static final String CURRENCY_CODE             = "cu";

    // Social Interaction Parameters
    public static final String SOCIAL_NETWORK            = "sn";
    public static final String SOCIAL_ACTION             = "sa";
    public static final String SOCIAL_ACTION_TARGET      = "st";

    // Timing Parameters
    public static final String USER_TIMING_CATEGORY      = "utc";
    public static final String USER_TIMING_VARIABLE_NAME = "utv";
    public static final String USER_TIMING_TIME          = "utt";
    public static final String USER_TIMING_LABEL         = "utl";
    public static final String PAGE_LOAD_TIME            = "plt";
    public static final String DNS_TIME                  = "dns";
    public static final String PAGE_DOWNLOAD_TIME        = "pdt";
    public static final String REDIRECT_RESPONSE_TIME    = "rrt";
    public static final String TCP_CONNECT_TIME          = "tcp";
    public static final String SERVER_RESPONSE_TIME      = "srt";

    // Exception Parameters
    public static final String EXCEPTION_DESCRIPTION     = "exd";
    public static final String EXCEPTION_IS_FATAL        = "exf";

    // Custom Dimension / Metric Parameter Prefixes
    public static final String CUSTOM_DIMENSION_PREFIX   = "cd";
    public static final String CUSTOM_METRIC_PREFIX      = "cm";

    // Content Experiment Parameters
    public static final String EXPERIMENT_ID             = "xid";
    public static final String EXPERIMENT_VARIANT        = "xvar";
}
