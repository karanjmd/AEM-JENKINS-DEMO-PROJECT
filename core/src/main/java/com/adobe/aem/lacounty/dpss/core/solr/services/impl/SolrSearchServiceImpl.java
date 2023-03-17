package com.adobe.aem.lacounty.dpss.core.solr.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.solr.DPSSSolrDocument;
import com.adobe.aem.lacounty.dpss.core.solr.SolrConstants;
import com.adobe.aem.lacounty.dpss.core.solr.services.DPSSSolrConfigService;
import com.adobe.aem.lacounty.dpss.core.solr.services.SolrSearchHelperService;
import com.adobe.aem.lacounty.dpss.core.solr.services.SolrSearchService;
import com.adobe.aem.lacounty.dpss.core.solr.utils.SolrUtils;
import com.adobe.aem.lacounty.dpss.core.utils.ServiceUserUtil;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SolrSearchServiceImpl implements SolrSearchService {

	private static final Logger LOG = LoggerFactory.getLogger(SolrSearchServiceImpl.class);
	
	long totalNumber;

	@Reference
	private QueryBuilder queryBuilder;

	@Reference
	private SlingRepository repository;
	
	@Reference
	private ResourceResolverFactory resolverFactory;

	@Reference
	SolrSearchHelperService solrSearchHelperService;

	@Reference
	DPSSSolrConfigService solrConfigurationService;
	
	/**
	 * This method crawl on the input page and its child pages to find the content
	 * which need to be indexed. And created index on Solr
	 * 
	 * @param resourcePath
	 * @return Boolean
	 */
	@Override
	public Boolean crawlAndCreateIndex(String resourcePath) {
		boolean isAuthoredCotentIndexCreated = false;

		LOG.info("Solr: the following page was activated in AEM" + resourcePath);

		Map<String, String> params = new HashMap<String, String>();
		params.put("path", resourcePath);
		params.put("type", SolrConstants.CQ_PAGE_CONTENT);
		params.put("p.offset", "0");
		params.put("p.limit", "1000");
		

		HttpSolrClient solrClient = null;
		
		ResourceResolver resolver = null;
		Session session=null;

		try {

			String solrUser =  solrConfigurationService.getUserName();
			String solrPass = solrConfigurationService.getPassword();
			resolver = resolverFactory.getServiceResourceResolver(ServiceUserUtil.getWorkflowServiceUserParam());
		    session = repository.login(new SimpleCredentials(solrUser,
					solrPass.toCharArray()));
			


			Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);
			SearchResult searchResults = query.getResult();
			LOG.info("SOLR: CRAWL on : " +resourcePath);
			LOG.info("found matches for query:" + searchResults.getTotalMatches());
			for (Hit hit : searchResults.getHits()) {
				Resource pageContent = hit.getResource();
				LOG.info("SOLR: pageContent : " +pageContent);
				Node node = session.getNode(pageContent.getPath());

				String currentResourcePath = pageContent.getPath().replace(SolrConstants.BACK_SLASH.concat(JcrConstants.JCR_CONTENT),
						SolrConstants.EMPTY_STRING);
				if (currentResourcePath.equalsIgnoreCase(resourcePath)){
					String indexId = pageContent.getValueMap().get(JcrConstants.JCR_UUID, String.class);
					String pageTitle = pageContent.getValueMap().get(JcrConstants.JCR_TITLE, String.class);
					String pageDescription = pageContent.getValueMap().get(JcrConstants.JCR_DESCRIPTION, String.class);
					String pagePath = currentResourcePath + SolrConstants.HTML_EXT;

					Set<String> solrServerUrlSet = solrSearchHelperService.createSolrServerUrl(currentResourcePath);
					Iterator<String> solrIter =  solrServerUrlSet.iterator();
					String solrServerUrl = StringUtils.EMPTY;
					while(solrIter.hasNext()) {
						solrServerUrl = solrIter.next();
						break;
					}
					LOG.info("solr url: " + solrServerUrl);

					solrClient = new HttpSolrClient(solrServerUrl);
					LOG.info("SOLR: The page to be indexed : " + pagePath);


					String indexData = pageTitle.concat(SolrConstants.CONTENT_SEPARATOR).concat(pageTitle)
							.concat(SolrConstants.CONTENT_SEPARATOR).concat(pagePath);
					indexData = indexPageContent(node, indexData);
					String imageLink = getImagePathForSearchResult(indexData);

					if (StringUtils.isEmpty(pageDescription)) {
						pageDescription = SolrConstants.SOLR_DEFAULT_CHAR;
					}
					if (StringUtils.isEmpty(indexData)) {
						indexData = SolrConstants.SOLR_DEFAULT_CHAR;
					}

					LOG.info("SOLR: Indexed data : " +  indexData);

					String categorys = SolrConstants.SOLR_DEFAULT_CHAR;
					String groups = SolrConstants.SOLR_DEFAULT_CHAR;

					DPSSSolrDocument dpssDoc = new DPSSSolrDocument(indexId, pageTitle, pageDescription, categorys, groups,
							imageLink, indexData, pagePath);
					isAuthoredCotentIndexCreated = indexPageToSolr(dpssDoc, solrClient);
				}
			}

		} catch (RepositoryException | SolrServerException | IOException e) {
			LOG.error("SOLR INDEX CREATION: Exception due to" + e);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != solrClient) {
				try {
					solrClient.close();
				} catch (IOException e) {
					LOG.error("SOLR: SEARCH: Exception due to " +  e);
				}
			}
		}
		return isAuthoredCotentIndexCreated;
	}

	/**
	 * This method prepare the sol document and create index on the Solr server
	 * 
	 * @param dpssSolrDoc
	 * @param solrClient
	 * @return Boolean
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Override
	public Boolean indexPageToSolr(DPSSSolrDocument dpssSolrDoc, HttpSolrClient solrClient)
			throws SolrServerException, IOException {
		if (null != dpssSolrDoc) {


			//Preparing the Solr document
			SolrInputDocument doc = new SolrInputDocument();

			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.setAction( UpdateRequest.ACTION.COMMIT, false, false);
			String user = solrConfigurationService.getUserName();
			String pass = solrConfigurationService.getPassword();
			updateRequest.setBasicAuthCredentials(user,pass);
			SolrInputDocument solrDocument = new SolrInputDocument();

			String id = dpssSolrDoc.getId();
			String title = dpssSolrDoc.getTitle();
			String description = dpssSolrDoc.getDescription();
			String categorys = dpssSolrDoc.getCategorys();
			String groups = dpssSolrDoc.getGroups();
			String imageLink = dpssSolrDoc.getImageLink();
			String body = dpssSolrDoc.getBody();
			String link = dpssSolrDoc.getLink();
			String site = "employee portal"; //need to get site based on content path or  core
			String contentType = "html";//need to get dynamically


			solrDocument.addField("id", id);
			solrDocument.addField("title", title);
			solrDocument.addField("description",description);
			solrDocument.addField("categorys",categorys);
			solrDocument.addField("groups",groups);
			solrDocument.addField("imageLink",imageLink);
			solrDocument.addField("body",body);
			solrDocument.addField("site",site); //need to make dynamic based on core
			solrDocument.addField("url",link);
			solrDocument.addField("contentType",contentType);


			updateRequest.add( solrDocument);


			//NoOpResponseParser rawJsonResponseParser = new NoOpResponseParser();
			//rawJsonResponseParser.setWriterType("json");
			//updateRequest.setResponseParser(rawJsonResponseParser);

			String lineSeparator = System.getProperty("line.separator");
			String solrDoc = updateRequest.getXML();
			LOG.debug(lineSeparator+"Solr Final Document " +  solrDoc);


			UpdateResponse rsp = updateRequest.process(solrClient);
			LOG.debug(lineSeparator+"Solr Final Document1 " +  rsp.getResponse());
			LOG.debug(lineSeparator+"Solr Response Status " +  rsp.getStatus());


			return true;
		}
		return false;
	}

	/**
	 * This method connects to the SOLR server and returns the result from authored
	 * page content.
	 * @return solr document list
	 */
	@Override
	public String searchSolrContent(String searchWord, String start, String rows,String fq) {
		HttpSolrClient solrClient = null;
		String result = StringUtils.EMPTY;
		Set<String> resultIds = new HashSet<>();
		List<Map<String, Object>> documentList  = new ArrayList<>();
		Set<String> solrServerCollections = solrSearchHelperService.createSolrServerUrl(null);
		List<String> searchlist = fetchSearchString(searchWord);
		for (String solrServerCollection : solrServerCollections) {
			try {
				solrClient = new HttpSolrClient(solrServerCollection);
				//solrClient.request(req);
				for (String searchString : searchlist) {
					SolrDocumentList results = queryResult(solrClient, "*" + searchString.trim() + "*", start, rows,fq);
					long totalNumber = results.getNumFound();
					for (SolrDocument doc : results) {
						String id = (String) doc.getFirstValue("id");
						if (resultIds.add(id)) {
							DPSSSolrDocument document = solrSearchHelperService.prepareDPSSSolrDocument(doc,
									searchlist);
							Map<String, Object> map = new ObjectMapper().convertValue(document,
									new TypeReference<Map<String, Object>>() {
									});
							map.put("totalNumber", totalNumber);
							documentList.add(map);
							
							try {
								result = new ObjectMapper().writeValueAsString(documentList);
							} catch (JsonProcessingException e) {
								LOG.error("JsonProcessingException while searching" +  e);
							}
						}
					}
				}
			} finally {
				if (null != solrClient) {
					try {
						solrClient.close();
					} catch (IOException e) {
						LOG.error("SOLR: SEARCH: Exception due to " + e);
					}
				}
			}
		}
		return result;
	}

	private SolrDocumentList queryResult(HttpSolrClient client, String searchString,String start,String rows,String fq) {
		
		LOG.debug("SOLR: Searching for: " + searchString);
		SolrDocumentList results = null;
		if (StringUtils.isNotBlank(searchString)) {
				LOG.debug("Inside the qeury result search string param : " + searchString);
				//ModifiableSolrParams params = new ModifiableSolrParams();
				//params.set("q", "body:" + searchString);
				//params.set("start", "0");
				//params.set("rows",Integer.MAX_VALUE);


			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery(searchString);
			//solrQuery.set("fl","*");
			solrQuery.setRows(Integer.parseInt(rows));
			solrQuery.setStart(Integer.parseInt(start));
			solrQuery.setFilterQueries(fq);
			//solrQuery.setParam("q", "body:" + searchString);
			//solrQuery.setParam("start", "0");
			//solrQuery.setParam("rows", "20");

			QueryRequest queryRequest = new QueryRequest(solrQuery);
			queryRequest.setBasicAuthCredentials(solrConfigurationService.getUserName(),solrConfigurationService.getPassword());
			LOG.debug("Credential : " + solrConfigurationService.getUserName(),solrConfigurationService.getPassword());

				try {
					LOG.debug("Inside try block ");
					//QueryResponse queryResponse = client.query(params);
					QueryResponse queryResponse =queryRequest.process(client);

					results = queryResponse.getResults();
					LOG.debug("SOLR: Number of data found : " + results.getNumFound());
					if (results.getNumFound() > 0) {
						results.addAll(results);
					}else {
						LOG.debug("Solr: No Result Found ");
					}
				} catch (SolrServerException | IOException e) {
					LOG.error("Exception while quering the solr server", e);
				}
		}
		return results;
	}

	private String indexPageContent(Node node, String indexData) {
		try {
			PropertyIterator propItr = node.getProperties();
			while (propItr.hasNext()) {
				Property property = propItr.nextProperty();
				if (!solrSearchHelperService.isExcludedProperty(property.getName())) {
					if (!property.isMultiple()) {
						String propValue = property.getValue().getString();
						if (!solrSearchHelperService.isExcludedValue(propValue)) {
							propValue = SolrUtils.replaceHTMLChar(propValue);
							indexData = indexData + SolrConstants.CONTENT_SEPARATOR + propValue;
						}}
				}
			}
			NodeIterator itr = node.getNodes();
			while (itr.hasNext()) {
				indexData = indexPageContent(itr.nextNode(), indexData);
			}
		} catch (RepositoryException e) {
			LOG.error("Repository Exception due to "+ e);
		}
		return indexData;
	}

	private String getImagePathForSearchResult(String indexData) {
		if (StringUtils.isNotEmpty(indexData)) {
			StringTokenizer tokens = new StringTokenizer(indexData, SolrConstants.CONTENT_SEPARATOR);
			while (tokens.hasMoreTokens()) {
				String token = tokens.nextToken();
				if (StringUtils.isNotEmpty(token) && SolrUtils.isImageUrl(token.trim())) {
					return token.trim();
				}
			}
		}
		return SolrConstants.SOLR_DEFAULT_CHAR;
	}
	
	private List<String> fetchSearchString(String searchWord) {
		List<String> searchlist = Arrays.asList(searchWord.split(" "));
		List<String> fulltextSearchlist = new ArrayList<>();
		
		for (String searchString : searchlist) {
			if (StringUtils.isNotEmpty(searchString) && searchString.contains("-")) {
				searchString = searchString.substring(0, searchString.indexOf("-"));
				
			}
			if (StringUtils.isNotEmpty(searchString) && searchString.contains("(")) {
				searchString = searchString.replace("(", "");
			}
			if (StringUtils.isNotEmpty(searchString) && searchString.contains(")")) {
				searchString = searchString.replace(")", "");
			}
			fulltextSearchlist.add(searchString);
		}
		return fulltextSearchlist;
	}
	@Override
	public long totalNumber() {
		// TODO Auto-generated method stub
		return totalNumber;
	}
	
	
	/**
	 * MEthod to Delete Solr Index of given payload
	 * @param payloadPath
	 */
	@Override
	public void solrDeleteContent(String payloadPath) {

		Set<String> solrCollections = solrSearchHelperService.createSolrServerUrl(payloadPath);
		for (String collection : solrCollections) {
			LOG.debug("SOLR :: SolrDeleteContent  started on path : " + payloadPath +" and collection :"+collection);
			HttpSolrClient server = new HttpSolrClient(collection);

			Map<String, String> params = new HashMap<String, String>();
			params.put("path", payloadPath);
			params.put("type", SolrConstants.CQ_PAGE_CONTENT);
			params.put("p.offset", "0");
			params.put("p.limit", "1000");

			ResourceResolver resolver = null;
			Session session = null;
			try {
				String solrUser = solrConfigurationService.getUserName();
				String solrPass = solrConfigurationService.getPassword();
				resolver = resolverFactory.getServiceResourceResolver(ServiceUserUtil.getWorkflowServiceUserParam());
				session = repository.login(new SimpleCredentials(solrUser,
						solrPass.toCharArray()));

				Query query = queryBuilder.createQuery(PredicateGroup.create(params), session);
				SearchResult searchResults = query.getResult();
				LOG.debug("SOLR :: Get child pages query has matches :" + searchResults.getTotalMatches());
				for (Hit hit : searchResults.getHits()) {
					Resource pageContent = hit.getResource();
					LOG.debug("SOLR: pageContent : " +pageContent);
					Node node = session.getNode(pageContent.getPath());
					String currentResourcePath = pageContent.getPath().replace(SolrConstants.BACK_SLASH.concat(JcrConstants.JCR_CONTENT),
							SolrConstants.EMPTY_STRING);
					String indexId = pageContent.getValueMap().get(JcrConstants.JCR_UUID, String.class);
					if (indexId != null && !indexId.isEmpty()){
						triggerSolrDelete(collection,currentResourcePath,indexId);
					}else{
						LOG.info("SOLR::: No IndexId found for "+currentResourcePath +"--"+indexId);
					}

					LOG.debug("SOLR::: JCR_UUID for page "+currentResourcePath +"--"+indexId);
				}
			}catch (Exception e) {
				LOG.error("Exception found "+ e);
			}
		}
		LOG.info("SOLR :: SolrDeleteContent completed for payload  : " + payloadPath);
	}

	/**
	 * Method to call to Solr Delete
	 * @param collection
	 * @param payloadPath
	 * @throws SolrServerException
	 * @throws IOException
	 */
	private void triggerSolrDelete(String collection, String payloadPath, String indexId) throws SolrServerException, IOException{
		HttpSolrClient server =null;
		try{
			server = new HttpSolrClient(collection);
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.setAction( UpdateRequest.ACTION.COMMIT, false, false);
			String user = solrConfigurationService.getUserName();
			String pass = solrConfigurationService.getPassword();
			updateRequest.setBasicAuthCredentials(user,pass);

			String queryString = "id:"+indexId;
			LOG.debug("SOLR::: Query string "+ queryString);
			server.request(updateRequest.deleteByQuery(queryString));
			LOG.info("SOLR::: Deleted all the indexes from solr server for "+ payloadPath);
		} catch (SolrServerException | IOException e) {
			LOG.error("DPSS::: Exception due to", e);
		}finally {
			try {
				if (null != server) {
					server.close();
				}
			} catch (IOException e) {
				LOG.error("DPSS::: Exception due to"+ e);
				LOG.error(e.getMessage());
			}
		}

	}
}
