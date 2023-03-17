package com.adobe.aem.lacounty.dpss.core.solr.services.impl;

import java.util.Arrays;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.Designate;

import com.adobe.aem.lacounty.dpss.core.solr.config.DPSSSOLRConfig;
import com.adobe.aem.lacounty.dpss.core.solr.services.DPSSSolrConfigService;

@Component(service = DPSSSolrConfigService.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = DPSSSOLRConfig.class)
public class DPSSSolrConfigurationImpl implements DPSSSolrConfigService {

	private DPSSSOLRConfig config;


	private String solrQueryServerName;
	private String solrIndexServerName;

	private String contentPagePath;
	
	private String userName;
	
	private String password;
	
	private String collectionName;

	private String[] collectionMapping;

	/**
	 * This method activates the solr configuration for 
	 * @param DPSSSOLRConfig
	 * @return
	 */
	@Activate
	public void activate(DPSSSOLRConfig config) {
		this.config = config;
		this.solrQueryServerName = config.queryServerName();
		this.solrIndexServerName = config.indexSeverName();
		this.contentPagePath = config.sitePath();
		this.userName = config.userName();
		this.password = config.password();
		this.collectionName = config.collectionName();
		this.collectionMapping = config.collectionMappings();
	}


	public String getQuerySolrServerName() {
		return this.solrQueryServerName;
	}
	public String getIndexSolrServerName() {
		return this.solrIndexServerName;
	}

	public String getContentPagePath() {
		return this.contentPagePath;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public String[] getCollectionMapping() {
		return Arrays.copyOf(collectionMapping, collectionMapping.length);
	}

}