package com.adobe.aem.lacounty.dpss.core.solr.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.sling.servlethelpers.MockSlingHttpServletRequest;
import org.apache.sling.servlethelpers.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adobe.aem.lacounty.dpss.core.solr.services.SolrSearchService;
import com.adobe.aem.lacounty.dpss.core.solr.servlets.SolrIndexSearch;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class })
public class SolrIndexSearchTest {

	private AemContext aemContext = new AemContext();
	private SolrIndexSearch solrIndexSearch = new SolrIndexSearch();

	@Mock
	SolrSearchService solrSearchService;

	@BeforeEach
	void setUp() throws NoSuchFieldException {
		aemContext.registerService(SolrSearchService.class);
		MockitoAnnotations.initMocks(this);
		PrivateAccessor.setField(solrIndexSearch, "solrSearchService", solrSearchService);
	}

	/*@Test
	void doGetTest() throws ServletException, IOException {
		MockSlingHttpServletRequest request = aemContext.request();
		MockSlingHttpServletResponse response = aemContext.response();
		aemContext.request().setQueryString("searchText=solrsearchtest");
		String result = "[{\n\t\"id\": \"b6e2bdd0-1427-4627-a922-77acd21c4d7a\",\n\t\"title\": \"Solr Search\",\n\t\"description\": \"Search Description\",\n\t\"categorys\": \"NA\",\n\t\"groups\": \"NA\",\n\t\"imageLink\": \"Northgate Gonzales Market_002.jpg\",\n\t\"body\": \"MÁS FRESCO PROGRAM HELPS INCREASE SHOPPING BUDGETS FOR CALFRESH HOUSEHOLDS|MÁS FRESCO PROGRAM HELPS INCREASE SHOPPING BUDGETS FOR CALFRESH HOUSEHOLDS\",\n\t\"link\": \"/content/dpss/en/Homepage/news/2020/06/DPSS-WEBSITE.html\"\n}]";
		//when(solrSearchService.searchSolrContent("solrsearchtest")).thenReturn(result);
		solrIndexSearch.doGet(request, response);
		assertEquals(result, response.getOutputAsString());
	}*/
}
