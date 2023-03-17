package com.adobe.aem.lacounty.dpss.core.solr.services;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.jcr.api.SlingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adobe.aem.lacounty.dpss.core.solr.services.impl.SolrSearchServiceImpl;
import com.day.cq.search.QueryBuilder;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class })
public class SolrSearchServiceTest {

	private AemContext aemContext = new AemContext();
	private SolrSearchServiceImpl solrSearchService = new SolrSearchServiceImpl();

	@Mock
	private QueryBuilder queryBuilder;

	@Mock
	private SlingRepository repository;

	@Mock
	SolrSearchHelperService solrSearchHelperService;

	@Mock
	DPSSSolrConfigService solrConfigurationService;

	Map<String, String> params = new HashMap<String, String>();
	
	Boolean isSolrServerActive = false;

	@BeforeEach
	void setUp() throws NoSuchFieldException, IOException {
		aemContext.registerService(SolrSearchService.class);
		MockitoAnnotations.initMocks(this);

		PrivateAccessor.setField(solrSearchService, "queryBuilder", queryBuilder);
		PrivateAccessor.setField(solrSearchService, "repository", repository);
		PrivateAccessor.setField(solrSearchService, "solrSearchHelperService", solrSearchHelperService);
		PrivateAccessor.setField(solrSearchService, "solrConfigurationService", solrConfigurationService);

		params.put("path", "/content/dpss/en");
		params.put("type", "cq:PageContent");
		params.put("p.offset", "0");
		params.put("p.limit", "1");
		
		URL url = new URL("http://localhost:8983/solr/dpss_public");
		URLConnection conn = url.openConnection();
		if(conn !=  null && StringUtils.isNotEmpty(conn.getContentType())) {
			isSolrServerActive = true;
		}
	}

	@Test
	void crawlAndCreateIndexTest() throws LoginException, RepositoryException {
		when(solrConfigurationService.getUserName()).thenReturn("admin");
		when(solrConfigurationService.getPassword()).thenReturn("admin");

		Session session = repository.login(new SimpleCredentials(solrConfigurationService.getUserName(),
				solrConfigurationService.getPassword().toCharArray()));

		Set<String> serverSet = new HashSet();
		serverSet.add("http://localhost:8983/solr/dpss_public");
		when(solrSearchHelperService.createSolrServerUrl("/content/dpss/en")).thenReturn(serverSet);
		// when(queryBuilder.createQuery(PredicateGroup.create(params),
		// session)).thenReturn(queryBuilder.createQuery(PredicateGroup.create(params),
		// session));
		// when(query.getResult()).thenReturn(searchResult);
		//solrSearchService.crawlAndCreateIndex("/content/dpss/en");
	}

	/*@Test
	void searchSolrContent() {
		Set<String> serverSet = new HashSet();
		serverSet.add("http://localhost:8983/solr/dpss_public");

		when(solrSearchHelperService.createSolrServerUrl(null)).thenReturn(serverSet);
		if (isSolrServerActive) {
			solrSearchService.searchSolrContent("solrsearch solrcontent solrdata");
		}
	}

	@Test
	void searchSolrContentDefault() {
		Set<String> serverSet = new HashSet();
		serverSet.add("http://localhost:8983/solr/dpss_public");

		when(solrSearchHelperService.createSolrServerUrl(null)).thenReturn(serverSet);
		if (isSolrServerActive) {
			solrSearchService.searchSolrContent("solr-search (solrcontent)");
		}
	}*/

}
