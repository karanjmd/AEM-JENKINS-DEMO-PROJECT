package com.adobe.aem.lacounty.dpss.core.solr.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.models.SearchResultModel;
import com.adobe.aem.lacounty.dpss.core.solr.services.SolrSearchService;

/**
 * The Servlet is responsible for searching the content based on the input
 * String
 * 
 * @author abhashkumar.budayak
 *
 */

@Component(service = Servlet.class, property = { 
		Constants.SERVICE_DESCRIPTION + "=DPSS Solr Search Servlet",
		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=" + SolrIndexSearch.SOLR_SEARCH_RESOURCETYPE,
		ServletResolverConstants.SLING_SERVLET_SELECTORS + "=" + SolrIndexSearch.SOLR_SEARCH_SELECTOR, 
		ServletResolverConstants.SLING_SERVLET_EXTENSIONS + "="+SolrIndexSearch.JSON_EXT, 
		ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET
})
public class SolrIndexSearch extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SolrIndexSearch.class);
	
	protected static final String SOLR_SEARCH_SELECTOR = "solrsearch";
	protected static final String PARAM_SEARCHTEXT = "searchText";
	protected static final String PARAM_START = "start";
	protected static final String PARAM_ROWS = "rows";
	protected static final String PARAM_FQ = "fq";
	
	protected static final String APPLICATION_TXT_HTML = "text/html";
	protected static final String SOLR_SEARCH_RESOURCETYPE = "sling/servlet/default";
	protected static final String JSON_EXT = "json";

	@Reference
	transient SolrSearchService solrSearchService;
	     
	
	/**
	 * This Servlet do get method is communicating for front end to serve the solr result. 
	 *
	 * @param SlingHttpServletRequest
	 * @param SlingHttpServletResponse
	 */
	@Override
	public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		String result = "";
		
		response.setContentType(APPLICATION_TXT_HTML);
		String searchString = request.getParameter(PARAM_SEARCHTEXT);
		String start = request.getParameter(PARAM_START);
		String rows = request.getParameter(PARAM_ROWS);
		String fq = request.getParameter(PARAM_FQ);
		fq = fq.replace("=", "&fq=");
		LOG.debug("SOLR: Search start with search string: " + searchString);
	    if(StringUtils.isNoneBlank(searchString)){
			result = solrSearchService.searchSolrContent(searchString.trim(),start,rows,fq);
			
			
		}
		LOG.debug("SOLR: Result: " + result);
		
		response.getWriter().write(result);
		LOG.debug("SOLR: Search end with search string: " + searchString);
	}
	
}