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

import java.util.ArrayList;
import java.util.List;

import net.nyvaria.googleanalytics.MeasurementProtocol;
import net.nyvaria.googleanalytics.MeasurementProtocolClient;
import net.nyvaria.url.parameter.*;

/**
 * @author Paul Thompson
 *
 */
public abstract class Hit {
	// Required Parameters
	private TextParameter client_id;
	private TextParameter hit_type;
	
	// General Parameters
	private BooleanParameter anonymize_ip;
	private IntegerParameter queue_time;
	private TextParameter    cache_buster;
	
	// Session Parameters
	private TextParameter    session_control;
	
	// Traffic Source Parameters
	private TextParameter    document_referrer;
	private TextParameter    campaign_name;
	private TextParameter    campaign_source;
	private TextParameter    campaign_medium;
	private TextParameter    campaign_keyword;
	private TextParameter    campaign_content;
	private TextParameter    campaign_id;
	private TextParameter    adwords_id;
	private TextParameter    display_ads_id;
	
	// System Info Parameters
	private TextParameter    screen_resolution;
	private TextParameter    viewport_size;
	private TextParameter    document_encoding;
	private TextParameter    screen_colors;
	private TextParameter    user_language;
	private BooleanParameter java_enabled;
	private TextParameter    flash_version;
	
	// Hit Parameters
	private BooleanParameter non_interaction_hit;
	
	// Content Information Parameters
	private TextParameter    document_location_url;
	private TextParameter    document_host_name;
	private TextParameter    document_path;
	private TextParameter    document_title;
	private TextParameter    content_description;
	private TextParameter    link_id;
	
	// Custom Dimensions / Metric Parameters
	// Not Supported Yet
	
	// Content Experiment Parameters
	private TextParameter    experiment_id;
	private TextParameter    experiment_variant;
	
	public Hit(TextParameter client_id, TextParameter hit_type) {
		this.client_id = client_id;
		this.hit_type  = hit_type;
	}
	
	public List<Parameter> getParameterList() {
		List<Parameter> list = new ArrayList<Parameter>();
		
		// Add the required parameters
		list.add(MeasurementProtocol.getProtocolVersion());
		list.add(MeasurementProtocolClient.getInstance().getTrackingID());
		list.add(this.client_id);
		list.add(this.hit_type);
		
		// Add the general parameters
		if (anonymize_ip          != null) list.add(anonymize_ip);
		if (queue_time            != null) list.add(queue_time);
		if (cache_buster          != null) list.add(cache_buster);
		
		// Add the visitor parameters
		if (client_id             != null) list.add(client_id);
		
		// Add the session parameters
		if (session_control       != null) list.add(session_control);
		
		// Add the traffic sources parameters
		if (document_referrer     != null) list.add(document_referrer);
		if (campaign_name         != null) list.add(campaign_name);
		if (campaign_source       != null) list.add(campaign_source);
		if (campaign_medium       != null) list.add(campaign_medium);
		if (campaign_keyword      != null) list.add(campaign_keyword);
		if (campaign_content      != null) list.add(campaign_content);
		if (campaign_id           != null) list.add(campaign_id);
		if (adwords_id            != null) list.add(adwords_id);
		if (display_ads_id        != null) list.add(display_ads_id);
		
		// Add the system info parameters
		if (screen_resolution     != null) list.add(screen_resolution);
		if (viewport_size         != null) list.add(viewport_size);
		if (document_encoding     != null) list.add(document_encoding);
		if (screen_colors         != null) list.add(screen_colors);
		if (user_language         != null) list.add(user_language);
		if (java_enabled          != null) list.add(java_enabled);
		if (flash_version         != null) list.add(flash_version);
		
		// Add the hit parameters
		if (non_interaction_hit != null) list.add(non_interaction_hit);
		
		// Content Information Parameters
		if (document_location_url != null) list.add(document_location_url);
		if (document_host_name    != null) list.add(document_host_name);
		if (document_path         != null) list.add(document_path);
		if (document_title        != null) list.add(document_title);
		if (content_description   != null) list.add(content_description);
		if (link_id               != null) list.add(link_id);
		
		// Add the content experiment parameters
		if (experiment_id         != null) list.add(experiment_id);
		if (experiment_variant    != null) list.add(experiment_variant);
		
		return list;
	}
	
	// General Parameter Setters
	public void setAnonymizeIP(boolean anonymize_ip) {
		this.anonymize_ip = new BooleanParameter(MeasurementProtocol.ANONYMIZE_IP_PARAMETER, anonymize_ip);
	}
	
	public void setQueueTime(int queue_time) {
		this.queue_time = new IntegerParameter(MeasurementProtocol.QUEUE_TIME_PARAMETER, queue_time);
	}
	
	public void setCacheBuster(String cache_buster) {
		this.cache_buster = new TextParameter(MeasurementProtocol.CACHE_BUSTER_PARAMETER, cache_buster);
	}
	
	// Session Parameter Setters
	public void setSessionControl(String cache_buster) {
		this.session_control = new TextParameter(MeasurementProtocol.SESSION_CONTROL_PARAMETER, cache_buster);
	}
	
	// Traffic Source Parameter Setters
	public void setDocumentReferrer(String document_referrer) {
		this.document_referrer = new TextParameter(MeasurementProtocol.DOCUMENT_REFERRER_PARAMETER, document_referrer);
	}
	
	public void setCampaignName(String campaign_name) {
		this.campaign_name = new TextParameter(MeasurementProtocol.CAMPAIGN_NAME_PARAMETER, campaign_name);
	}
	
	public void setCampaignSource(String campaign_source) {
		this.campaign_source = new TextParameter(MeasurementProtocol.CAMPAIGN_SOURCE_PARAMETER, campaign_source);
	}
	
	public void setCampaignMedium(String campaign_medium) {
		this.campaign_medium = new TextParameter(MeasurementProtocol.CAMPAIGN_MEDIUM_PARAMETER, campaign_medium);
	}
	
	public void setCampaignKeyword(String campaign_keyword) {
		this.campaign_keyword = new TextParameter(MeasurementProtocol.CAMPAIGN_KEYWORD_PARAMETER, campaign_keyword);
	}
	
	public void setCampaignContent(String campaign_content) {
		this.campaign_content = new TextParameter(MeasurementProtocol.CAMPAIGN_CONTENT_PARAMETER, campaign_content);
	}
	
	public void setCampaignID(String campaign_id) {
		this.campaign_id = new TextParameter(MeasurementProtocol.CAMPAIGN_ID_PARAMETER, campaign_id);
	}
	
	public void setAdwordsID(String adwords_id) {
		this.adwords_id = new TextParameter(MeasurementProtocol.ADWORDS_ID_PARAMETER, adwords_id);
	}
	
	public void setDisplayAdsID(String display_ads_id) {
		this.display_ads_id = new TextParameter(MeasurementProtocol.DISPLAY_ADS_ID_PARAMETER, display_ads_id);
	}
	
	// System Info Parameter Setters
	public void setScreenResolution(String screen_resolution) {
		this.screen_resolution = new TextParameter(MeasurementProtocol.SCREEN_RESOLUTION_PARAMETER, screen_resolution);
	}
	
	public void setViewportSize(String viewport_size) {
		this.viewport_size = new TextParameter(MeasurementProtocol.VIEWPORT_SIZE_PARAMETER, viewport_size);
	}
	
	public void setDocumentEncoding(String document_encoding) {
		this.document_encoding = new TextParameter(MeasurementProtocol.DOCUMENT_ENCODING_PARAMETER, document_encoding);
	}
	
	public void setScreenColors(String screen_colors) {
		this.screen_colors = new TextParameter(MeasurementProtocol.SCREEN_COLORS_PARAMETER, screen_colors);
	}
	
	public void setUserLanguage(String user_language) {
		this.user_language = new TextParameter(MeasurementProtocol.USER_LANGUAGE_PARAMETER, user_language);
	}
	
	public void setJavaEnabled(boolean java_enabled) {
		this.java_enabled = new BooleanParameter(MeasurementProtocol.JAVA_ENABLED_PARAMETER, java_enabled);
	}
	
	public void setFlashEnabled(String flash_version) {
		this.flash_version = new TextParameter(MeasurementProtocol.FLASH_VERSION_PARAMETER, flash_version);
	}
	
	// Hit Parameter Setters
	public void setNonInteractionHit(boolean non_interaction_hit) {
		this.non_interaction_hit = new BooleanParameter(MeasurementProtocol.NON_INTERACTION_HIT_PARAMETER, non_interaction_hit);
	}
	
	// Content Information Parameter Setters
	public void setDocumentLocationURL(String document_location_url) {
		this.document_location_url = new TextParameter(MeasurementProtocol.DOCUMENT_LOCATION_URL_PARAMETER, document_location_url);
	}
	
	public void setDocumentHostName(String document_host_name) {
		this.document_host_name = new TextParameter(MeasurementProtocol.DOCUMENT_HOST_NAME_PARAMETER, document_host_name);
	}
	
	public void setDocumentPath(String document_path) {
		this.document_path = new TextParameter(MeasurementProtocol.DOCUMENT_PATH_PARAMETER, document_path);
	}
	
	public void setDocumentTitle(String document_title) {
		this.document_title = new TextParameter(MeasurementProtocol.DOCUMENT_TITLE_PARAMETER, document_title);
	}
	
	public void setContentDescription(String content_description) {
		this.content_description = new TextParameter(MeasurementProtocol.CONTENT_DESCRIPTION_PARAMETER, content_description);
	}
	
	public void setLinkID(String link_id) {
		this.link_id = new TextParameter(MeasurementProtocol.LINK_ID_PARAMETER, link_id);
	}
	
	// Content Experiment Parameter Setters
	public void setExperimentID(String experiment_id) {
		this.experiment_id = new TextParameter(MeasurementProtocol.EXPERIMENT_ID_PARAMETER, experiment_id);
	}
	
	public void setExperimentVariant(String experiment_id) {
		this.experiment_variant = new TextParameter(MeasurementProtocol.EXPERIMENT_VARIANT_PARAMETER, experiment_id);
	}

}
