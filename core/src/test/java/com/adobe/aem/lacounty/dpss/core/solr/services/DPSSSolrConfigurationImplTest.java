package com.adobe.aem.lacounty.dpss.core.solr.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adobe.aem.lacounty.dpss.core.solr.config.DPSSSOLRConfig;
import com.adobe.aem.lacounty.dpss.core.solr.services.impl.DPSSSolrConfigurationImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
public class DPSSSolrConfigurationImplTest {

	private AemContext aemContext = new AemContext();
	DPSSSolrConfigurationImpl solrConfig;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	DPSSSOLRConfig config;

	@BeforeEach
	void setUp() throws NoSuchFieldException {
		MockitoAnnotations.initMocks(this);
		solrConfig = aemContext.registerService(new DPSSSolrConfigurationImpl());
		when(config.queryServerName()).thenReturn("localhost");
		when(config.sitePath()).thenReturn("/content/dpss");
		when(config.userName()).thenReturn("admin");
		when(config.password()).thenReturn("admin");
		when(config.collectionName()).thenReturn("dpss_public");
		when(config.collectionMappings()).thenReturn(new String[] { "/content/dpss/en:dpss_public" });

		solrConfig.activate(config);
	}


	@Test
	void getSolrServerName() {
		assertEquals("localhost", solrConfig.getQuerySolrServerName());
	}


	@Test
	void getContentPagePath() {
		assertEquals("/content/dpss", solrConfig.getContentPagePath());
	}

	@Test
	void getUserName() {
		assertEquals("admin", solrConfig.getUserName());
	}

	@Test
	void getPassword() {
		assertEquals("admin", solrConfig.getPassword());
	}

	@Test
	void getCollectionName() {
		assertEquals("dpss_public", solrConfig.getCollectionName());
	}

	@Test
	void getCollectionMapping() {
		String collectionValue = solrConfig.getCollectionMapping().clone()[0];
		assertEquals("/content/dpss/en:dpss_public", collectionValue);
	}

}