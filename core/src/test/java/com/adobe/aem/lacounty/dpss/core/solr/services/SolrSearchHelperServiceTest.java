package com.adobe.aem.lacounty.dpss.core.solr.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adobe.aem.lacounty.dpss.core.solr.DPSSSolrDocument;
import com.adobe.aem.lacounty.dpss.core.solr.SolrConstants;
import com.adobe.aem.lacounty.dpss.core.solr.services.impl.SolrSearchHelperServiceImpl;
import com.day.cq.search.Query;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class })
public class SolrSearchHelperServiceTest {

	private AemContext aemContext = new AemContext();
	private SolrSearchHelperServiceImpl solrSearchHelperService = new SolrSearchHelperServiceImpl();
	Query query;
	Session session;

	@Mock
	DPSSSolrConfigService solrConfigurationService;

	@Mock
	SolrDocument document;

	Map<String, String> params = new HashMap<String, String>();

	@BeforeEach
	void setUp() throws NoSuchFieldException {
		aemContext.registerService(DPSSSolrConfigService.class);
		MockitoAnnotations.initMocks(this);
		PrivateAccessor.setField(solrSearchHelperService, "solrConfigurationService", solrConfigurationService);
		when(solrConfigurationService.getUserName()).thenReturn("admin");
		when(solrConfigurationService.getPassword()).thenReturn("admin");
		when(solrConfigurationService.getQuerySolrServerName()).thenReturn("localhost");

	}

	/*	@Test
	void createSolrServerUrlTest() {
		when(solrConfigurationService.getCollectionMapping())
				.thenReturn(new String[] { "/content/dpss/en:dpss_public" });
		Set<String> serverUrlSet = solrSearchHelperService.createSolrServerUrl("/content/dpss/en");
		Iterator<String> serverUrlItr = serverUrlSet.iterator();
		while (serverUrlItr.hasNext()) {
			String serverUrl = serverUrlItr.next();
			assertEquals(serverUrl, "localhost/solr/dpss_public");
		}
	}

	@Test
	void createSolrServerUrlDefaultURLTest() {
		when(solrConfigurationService.getCollectionMapping()).thenReturn(new String[] {});
		Set<String> serverUrlSet = solrSearchHelperService.createSolrServerUrl("/content/dpss/en");
		Iterator<String> serverUrlItr = serverUrlSet.iterator();
		while (serverUrlItr.hasNext()) {
			String serverUrl = serverUrlItr.next();
			assertEquals(serverUrl, "localhost/solr/null");
		}
	}*/

	@Test
	void createSolrServerUrlEmptyPathTest() {
		String serverUrl = StringUtils.EMPTY;
		when(solrConfigurationService.getCollectionMapping())
				.thenReturn(new String[] { "/content/dpss/en:dpss_public" });
		Set<String> serverUrlSet = solrSearchHelperService.createSolrServerUrl("");
		Iterator<String> serverUrlItr = serverUrlSet.iterator();
		while (serverUrlItr.hasNext()) {
			serverUrl = serverUrlItr.next();
			break;
		}
		assertEquals(serverUrl, "localhost/solr/dpss_public");
	}

	@Test
	void isExcludedPropertyTrue() {
		Boolean isExclusionCQTest = solrSearchHelperService.isExcludedProperty("cq:pageContent");
		Boolean isExclusionJCRTest = solrSearchHelperService.isExcludedProperty("jcr:title");
		Boolean isExclusionSLINGTest = solrSearchHelperService.isExcludedProperty("sling:folder");
		assertEquals(isExclusionCQTest, Boolean.TRUE);
		assertEquals(isExclusionJCRTest, Boolean.TRUE);
		assertEquals(isExclusionSLINGTest, Boolean.TRUE);
	}

	@Test
	void isExcludedPropertyFalse() {
		Boolean isExclusionCQTest = solrSearchHelperService.isExcludedProperty("pageContent");
		Boolean isExclusionJCRTest = solrSearchHelperService.isExcludedProperty("title");
		Boolean isExclusionSLINGTest = solrSearchHelperService.isExcludedProperty("folder");
		assertEquals(isExclusionCQTest, Boolean.FALSE);
		assertEquals(isExclusionJCRTest, Boolean.FALSE);
		assertEquals(isExclusionSLINGTest, Boolean.FALSE);
	}

	@Test
	void isExcludedValueExpectedInput() {
		Boolean isExclusionExpectedInputTrue = solrSearchHelperService.isExcludedValue("true");
		Boolean isExclusionExpectedInputFalse = solrSearchHelperService.isExcludedValue("false");
		assertEquals(isExclusionExpectedInputTrue, Boolean.TRUE);
		assertEquals(isExclusionExpectedInputFalse, Boolean.TRUE);
	}

	@Test
	void isExcludedValueUnExpectedInput() {
		Boolean isExclusionUnExpectedInputTrue = solrSearchHelperService.isExcludedValue("junittest");
		Boolean isExclusionUnExpectedInputFalse = solrSearchHelperService.isExcludedValue("dpsstest");
		assertEquals(isExclusionUnExpectedInputTrue, Boolean.FALSE);
		assertEquals(isExclusionUnExpectedInputFalse, Boolean.FALSE);
	}

	@Test
	void prepareDPSSSolrDocumentTest() {
		when(document.getFirstValue("id")).thenReturn("testid");
		when(document.getFirstValue("title")).thenReturn("testtitle");
		when(document.getFirstValue("description")).thenReturn("testdescription");
		when(document.getFirstValue("categorys")).thenReturn("testcategorys");
		when(document.getFirstValue("groups")).thenReturn("testgroups");
		when(document.getFirstValue("imageLink")).thenReturn("testImageLink");
		when(document.getFirstValue("url")).thenReturn("testLink");
		when(document.getFirstValue("body")).thenReturn("testbody");

		List<String> searchList = new ArrayList<>();
		searchList.add("testtitle");
		searchList.add("testdescription");

		DPSSSolrDocument dpssDocument = solrSearchHelperService.prepareDPSSSolrDocument(document, searchList);

		assertEquals(dpssDocument.getId(), "testid");
		assertEquals(dpssDocument.getTitle(), "<mark>testtitle</mark>");
		assertEquals(dpssDocument.getDescription(), "<mark>testdescription</mark>");
		assertEquals(dpssDocument.getCategorys(), "testcategorys");
		assertEquals(dpssDocument.getGroups(), "testgroups");
		assertEquals(dpssDocument.getImageLink(), "testImageLink");
		assertEquals(dpssDocument.getLink(), "testLink");
		assertEquals(dpssDocument.getBody(), "testbody");

	}

	@Test
	void prepareDPSSSolrDocumentDefaultDataTest() {
		when(document.getFirstValue("title")).thenReturn(SolrConstants.SOLR_DEFAULT_CHAR);
		when(document.getFirstValue("description")).thenReturn(SolrConstants.SOLR_DEFAULT_CHAR);
		when(document.getFirstValue("imageLink")).thenReturn(SolrConstants.SOLR_DEFAULT_CHAR);

		List<String> searchList = new ArrayList<>();
		searchList.add(SolrConstants.SOLR_DEFAULT_CHAR);
		searchList.add(SolrConstants.SOLR_DEFAULT_CHAR);

		DPSSSolrDocument dpssDocument = solrSearchHelperService.prepareDPSSSolrDocument(document, searchList);
		assertEquals(dpssDocument.getDescription(), StringUtils.EMPTY);
		assertEquals(dpssDocument.getImageLink(), StringUtils.EMPTY);
	}
}
