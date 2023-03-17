package com.adobe.aem.lacounty.dpss.core.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adobe.aem.lacounty.dpss.core.services.impl.DpssGlobalServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
public class DpssGlobalConfigServiceImplTest {

	private AemContext aemContext = new AemContext();
	DpssGlobalServiceImpl globalConfig;
	
	@Mock
	DpssGlobalConfig config;

	@BeforeEach
	void setUp() throws NoSuchFieldException {
		MockitoAnnotations.initMocks(this);
		globalConfig = aemContext.registerService(new DpssGlobalServiceImpl());
		when(config.searchAPIEndPoint()).thenReturn(
				"https://locator.lacounty.gov/lac/Search?find=&near=&cat=826&tag=&loc=&lat=&lon=&page=1&pageSize=10");
		when(config.launchConfigUrl())
				.thenReturn("//assets.adobedtm.com/c70179af6cc1/689b8691df2c/launch-8cf082ecf6df-staging.min.js");

		globalConfig.activate(config);
	}

	@Test
	void getSearchAPIEndPoint() {
		assertEquals(
				"https://locator.lacounty.gov/lac/Search?find=&near=&cat=826&tag=&loc=&lat=&lon=&page=1&pageSize=10",
				globalConfig.getSearchAPIEndPoint());
	}

	@Test
	void getSolrServerName() {
		assertEquals("//assets.adobedtm.com/c70179af6cc1/689b8691df2c/launch-8cf082ecf6df-staging.min.js",
				globalConfig.getLaunchConfigUrl());
	}

}
