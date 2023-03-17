package com.adobe.aem.lacounty.dpss.core.solr.services;

import java.util.List;
import java.util.Set;

import org.apache.solr.common.SolrDocument;

import com.adobe.aem.lacounty.dpss.core.solr.DPSSSolrDocument;

/**
 * This Helper service will be used to fetch the mappings collection for the
 * page path from the configuration
 * 
 * @author abhashkumar.budayak
 *
 */
public interface SolrSearchHelperService {
	/**
	 * The method returns a set of collection mapped against the page in the osgi
	 * configuration. If there is no mapping found then default collection will be
	 * returned. If the input value is empty or null then all the solr collection
	 * will be returned.
	 * 
	 * @param currentPagePath
	 * @return
	 */
	Set<String> createSolrServerUrl(String currentPagePath);

	/**
	 * This method prepares the DOSS Solr search document
	 * @param solrDoc
	 * @param searchString
	 * @return
	 */
	DPSSSolrDocument prepareDPSSSolrDocument(SolrDocument solrDoc, List<String> searchString);
	
	/**
	 * This method returns boolean to consider the property to be part of index or not.
	 * @param property name
	 * @return Boolean
	 */
	Boolean isExcludedProperty(String propertyName);
	
	/**
	 * This method returns boolean to consider the property value to be part of index or not.
	 * @param property value
	 * @return Boolean
	 */
	Boolean isExcludedValue(String propertyValue);
	
}
