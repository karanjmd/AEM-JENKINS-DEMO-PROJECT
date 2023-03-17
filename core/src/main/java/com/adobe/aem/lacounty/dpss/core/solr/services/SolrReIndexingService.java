package com.adobe.aem.lacounty.dpss.core.solr.services;

import com.adobe.aem.lacounty.dpss.core.solr.DPSSSolrDocument;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.io.IOException;

/**
 * This service will be used to communicate with SOLR server for creating index
 * on the SOLR. And retrieving the content from the SOLR.
 * 
 * @author venkatanaveen.gajula
 *
 */
public interface SolrReIndexingService {

	/**
	 * This method crawl on the input page and its child pages to find the content
	 * which need to be indexed. And created index on Solr
	 * 
	 * @param resourcePath
	 * @return Boolean
	 */
	Boolean crawlAndCreateIndex(String resourcePath);

	/**
	 * This method prepare the sol document and create index on the Solr server
	 * 
	 * @param dpssSolrDoc
	 * @param server
	 * @return Boolean
	 * @throws SolrServerException
	 * @throws IOException
	 */
	Boolean indexPageToSolr(DPSSSolrDocument dpssSolrDoc, HttpSolrClient server)
			throws SolrServerException, IOException;
	
	/**
	 * This method search the indexes on all the collection on solr server and
	 * result in the JSONArray object (in map)
	 * 
	 * @param searchString
	 * @return JSONArray
	 */
	String searchSolrContent(String searchString, String start, String rows);
	long totalNumber();
}