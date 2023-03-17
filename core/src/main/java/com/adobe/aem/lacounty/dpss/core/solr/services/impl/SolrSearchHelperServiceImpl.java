package com.adobe.aem.lacounty.dpss.core.solr.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrDocument;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.solr.DPSSSolrDocument;
import com.adobe.aem.lacounty.dpss.core.solr.SolrConstants;
import com.adobe.aem.lacounty.dpss.core.solr.services.DPSSSolrConfigService;
import com.adobe.aem.lacounty.dpss.core.solr.services.SolrSearchHelperService;


@Component
public class SolrSearchHelperServiceImpl implements SolrSearchHelperService {

	private static final Logger LOG = LoggerFactory.getLogger(SolrSearchHelperServiceImpl.class);

	@Reference
	DPSSSolrConfigService solrConfigurationService;

	/**
	 * The method returns a set of collection mapped against the page in the osgi
	 * configuration. If there is no mapping found then default collection will be
	 * returned. If the input value is empty or null then all the solr collection
	 * will be returned.
	 * 
	 * @param currentPagePath
	 * @return
	 */
	@Override
	public Set<String> createSolrServerUrl(String pagePath) {
		Set<String> solrServerCollections = new HashSet<>();

		String serverName = solrConfigurationService.getQuerySolrServerName();
		
		String indexServerName = solrConfigurationService.getIndexSolrServerName();

		String defaultCollectionName = solrConfigurationService.getCollectionName();

		String[] mappingCollections = solrConfigurationService.getCollectionMapping();

		List<String> list = Arrays.asList(mappingCollections);
		Iterator<String> collectionMappingItr = list.iterator();

		if (StringUtils.isNotEmpty(pagePath)) {
			LOG.info("SOLR: fetching the solr collections for indexing on : ", pagePath);
			String URL = null;
			while (collectionMappingItr.hasNext()) {
				String pageMap = collectionMappingItr.next();
				String split[] = pageMap.split(":", 0);

				String pagesResourcePath = split[0];
				String collectionName = split[1];
				if (pagePath.equals(pagesResourcePath)) {
					URL =  indexServerName + SolrConstants.BACK_SLASH
							+ SolrConstants.SOLR_NAME + SolrConstants.BACK_SLASH + collectionName;
					solrServerCollections.add(URL);
					break;
				}
			}
			if (StringUtils.isEmpty(URL)) {
				LOG.info("SOLR: Preparing the default solr collection when there mapping found  ", pagePath);
				URL = indexServerName + SolrConstants.BACK_SLASH
						+ SolrConstants.SOLR_NAME + SolrConstants.BACK_SLASH + defaultCollectionName;
				solrServerCollections.add(URL);
			}
		} else {
			LOG.info("SOLR: Preparing the solr collection list for searching.");
			String URL = null;
			while (collectionMappingItr.hasNext()) {
				String pageMap = collectionMappingItr.next();
				String split[] = pageMap.split(":", 0);
				String collectionName = split[1];
				URL =  serverName + SolrConstants.BACK_SLASH
						+ SolrConstants.SOLR_NAME + SolrConstants.BACK_SLASH + collectionName;
				solrServerCollections.add(URL);
				LOG.info("SOLR: URL Info"+URL);
			}
			URL = serverName + SolrConstants.BACK_SLASH + SolrConstants.SOLR_NAME
					+ SolrConstants.BACK_SLASH + defaultCollectionName;
			solrServerCollections.add(URL);
		}

		return solrServerCollections;
	}

	/**
	 * This method prepares the DOSS Solr search document
	 * @param solrDoc
	 * @param searchString
	 * @return
	 */
	@Override
	public DPSSSolrDocument prepareDPSSSolrDocument(SolrDocument solrDoc, List<String> searchList) {
		String id = (String) solrDoc.getFirstValue("id");
		String title = (String) solrDoc.getFirstValue("title");
		String description = (String) solrDoc.getFirstValue("description");
		String categorys = (String) solrDoc.getFirstValue("categorys");
		String groups = (String) solrDoc.getFirstValue("groups");
		String imageLink = (String) solrDoc.getFirstValue("imageLink");
		String body = (String) solrDoc.getFirstValue("body");
		String link = (String) solrDoc.getFirstValue("url");

		for (String searchKey : searchList) {
			title = getMarkedString(title, searchKey);
			if (description.length() > 1) {
				description = getMarkedString(description, searchKey);
			}
			if (description.length() == 1 && description.contains(SolrConstants.SOLR_DEFAULT_CHAR)) {
				description = StringUtils.EMPTY;
			}
			if (imageLink.length() == 1 && imageLink.contains(SolrConstants.SOLR_DEFAULT_CHAR)) {
				imageLink = StringUtils.EMPTY;
			}
		}

		DPSSSolrDocument document = new DPSSSolrDocument(id, title, description, categorys, groups, imageLink, body,
				link);
		return document;
	}
		
	private String getMarkedString(String inputString, String searchKeyWord) {
		inputString = inputString.replace(searchKeyWord, "<mark>" + searchKeyWord + "</mark>");

		if (!inputString.contains("<mark>")) {
			inputString = inputString.replace(searchKeyWord.toUpperCase(),
					"<mark>" + searchKeyWord.toUpperCase() + "</mark>");
		}
		if (!inputString.contains("<mark>")) {
			inputString = inputString.replace(searchKeyWord.toLowerCase(),
					"<mark>" + searchKeyWord.toLowerCase() + "</mark>");
		}
		if (!inputString.contains("<mark>")) {
			String initAlphabate = searchKeyWord.substring(0, 1).toUpperCase();
			searchKeyWord = initAlphabate + searchKeyWord.substring(1);
			inputString = inputString.replace(searchKeyWord, "<mark>" + searchKeyWord + "</mark>");
		}
		return inputString;
	}
	
	/**
	 * This method returns boolean to consider the property to be part of index or not.
	 * @param property name
	 * @return Boolean
	 */
	@Override
	public Boolean isExcludedProperty(String propertyName) {
		Boolean isExcluded = false;

		if (StringUtils.isNotEmpty(propertyName) && propertyName.startsWith("jcr")) {
			isExcluded = true;
		} else if (StringUtils.isNotEmpty(propertyName) && propertyName.startsWith("sling")) {
			isExcluded = true;
		} else if (StringUtils.isNotEmpty(propertyName) && propertyName.startsWith("cq")) {
			isExcluded = true;
		}

		return isExcluded;
	}
	
	/**
	 * This method is is used to exclude the value in the search indexing 
	 * @param propertyValue String
	 * @return Boolean
	 */
	@Override
	public Boolean isExcludedValue(String propertyValue) {
		Boolean isExcluded = false;

		if (StringUtils.isNotEmpty(propertyValue) && propertyValue.equals("true")) {
			isExcluded = true;
		} else if (StringUtils.isNotEmpty(propertyValue) && propertyValue.equals("false")) {
			isExcluded = true;
		}

		return isExcluded;
	}
	
}
