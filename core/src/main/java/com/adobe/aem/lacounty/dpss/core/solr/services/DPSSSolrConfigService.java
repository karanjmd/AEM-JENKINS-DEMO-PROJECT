package com.adobe.aem.lacounty.dpss.core.solr.services;

public interface DPSSSolrConfigService {



	public String getQuerySolrServerName();
	public String getIndexSolrServerName();

	public String getContentPagePath();

	public String getUserName();

	public String getPassword();

	public String[] getCollectionMapping();

	public String getCollectionName();

}
