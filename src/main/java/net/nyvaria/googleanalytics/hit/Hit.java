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
import net.nyvaria.googleanalytics.MeasurementProtocolClient;
import net.nyvaria.googleanalytics.Parameter;
import net.nyvaria.openanalytics.OpenAnalytics;
import net.nyvaria.openanalytics.client.Client;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Paul Thompson
 */
public abstract class Hit {
    private Client client;

    /**
     * Constructor & Methods
     */

    public Hit(Client client, String hit_type) {
        this.client = client;
        this.protocol_version = MeasurementProtocol.ENDPOINT_PROTOCOL_VERSION;
        this.tracking_id = MeasurementProtocolClient.getInstance().tracking_id;
        this.hit_type = hit_type;
        this.client_id = client.getClientID();
    }

    public Client getClient() {
        return client;
    }

    public List<String> getParameterList() {
        List<String> list = new ArrayList<String>();

        for (Field field : this.getClass().getFields()) {
            Parameter parameter = (Parameter) field.getAnnotation(Parameter.class);

            if (parameter != null) {
                Object value = null;

                try {
                    value = field.get(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String result = formatParameter(parameter, value);
                if (result != null) {
                    list.add(result);
                }
            }
        }

        return list;
    }

    protected String formatParameter(Parameter parameter, Object value) {
        String result = null;

        if (parameter.required() || value != null) {
            String text = null;

            switch (parameter.format()) {
                case "text":
                    text = (String) value;
                    break;

                case "boolean":
                    text = (((Boolean) value).booleanValue() ? "1" : "0");
                    break;

                case "integer":
                    text = String.format("%d", ((Integer) value).intValue());
                    break;

                case "currency":
                    text = String.format("%.2f", ((Float) value).floatValue());
                    break;
            }

            try {
                result = parameter.name() + "=" + URLEncoder.encode(text, MeasurementProtocol.ENDPOINT_ENCODING);
            } catch (UnsupportedEncodingException e) {
                OpenAnalytics.getInstance().log(Level.WARNING, String.format("Error encoding parameter %s with value '%s'", parameter.name(), text));
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * Required Parameters
     */

    @Parameter(format = "text", required = true, name = MeasurementProtocol.PROTOCOL_VERSION)
    public String protocol_version;

    @Parameter(format = "text", required = true, name = MeasurementProtocol.TRACKING_ID)
    public String tracking_id;

    @Parameter(format = "text", required = true, name = MeasurementProtocol.CLIENT_ID)
    public String client_id;

    @Parameter(format = "text", required = true, name = MeasurementProtocol.HIT_TYPE)
    public String hit_type;

    /**
	 * General Parameters
     */

    @Parameter(format = "boolean", required = false, name = MeasurementProtocol.ANONYMIZE_IP)
    public Boolean anonymize_ip;

    @Parameter(format = "integer", required = false, name = MeasurementProtocol.QUEUE_TIME)
    public Integer queue_time;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.CACHE_BUSTER)
    public String cache_buster;

    /**
	 * Session Parameters
     */

    @Parameter(format = "text", required = false, name = MeasurementProtocol.SESSION_CONTROL)
    public String session_control;

    /**
	 * Traffic Source Parameters
     */

    @Parameter(format = "text", required = false, name = MeasurementProtocol.DOCUMENT_REFERRER)
    public String document_referrer;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.CAMPAIGN_NAME)
    public String campaign_name;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.CAMPAIGN_SOURCE)
    public String campaign_source;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.CAMPAIGN_MEDIUM)
    public String campaign_medium;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.CAMPAIGN_KEYWORD)
    public String campaign_keyword;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.CAMPAIGN_CONTENT)
    public String campaign_content;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.CAMPAIGN_ID)
    public String campaign_id;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.ADWORDS_ID)
    public String adwords_id;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.DISPLAY_ADS_ID)
    public String display_ads_id;

    /**
     * System Info Parameters
     */

    @Parameter(format = "text", required = false, name = MeasurementProtocol.SCREEN_RESOLUTION)
    public String screen_resolution;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.VIEWPORT_SIZE)
    public String viewport_size;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.DOCUMENT_ENCODING)
    public String document_encoding;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.SCREEN_COLORS)
    public String screen_colors;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.USER_LANGUAGE)
    public String user_language;

    @Parameter(format = "boolean", required = false, name = MeasurementProtocol.JAVA_ENABLED)
    public Boolean java_enabled;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.FLASH_VERSION)
    public String flash_version;

    /**
	 * Hit Parameters
     */

    @Parameter(format = "boolean", required = false, name = MeasurementProtocol.NON_INTERACTION_HIT)
    public Boolean non_interaction_hit;

    /**
	 * Content Information Parameters
     */

    @Parameter(format = "text", required = false, name = MeasurementProtocol.DOCUMENT_LOCATION_URL)
    public String document_location_url;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.DOCUMENT_HOST_NAME)
    public String document_host_name;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.DOCUMENT_PATH)
    public String document_path;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.DOCUMENT_TITLE)
    public String document_title;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.CONTENT_DESCRIPTION)
    public String content_description;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.LINK_ID)
    public String link_id;

    /**
	 * Custom Dimensions / Metric Parameters
     */

    // Not Supported Yet
    //public HashMap<Integer, String>  custom_dimension;
    //public HashMap<Integer, Integer> custom_metric;

    /**
	 * Content Experiment Parameters
     */

    @Parameter(format = "text", required = false, name = MeasurementProtocol.EXPERIMENT_ID)
    public String experiment_id;

    @Parameter(format = "text", required = false, name = MeasurementProtocol.EXPERIMENT_VARIANT)
    public String experiment_variant;
}
