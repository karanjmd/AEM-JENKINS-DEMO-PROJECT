package com.adobe.aem.lacounty.dpss.core.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adobe.aem.lacounty.dpss.core.services.impl.ErrorPageHandlerServiceImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
public class ErrorPageHandlerServiceImplTest {
	private AemContext aemContext = new AemContext();
	ErrorPageHandlerServiceImpl errorPageHandlerConfig;
	
	@Mock
	ErrorPageHandlerConfig config;

	@BeforeEach
	void setUp() throws NoSuchFieldException {
		MockitoAnnotations.initMocks(this);
		errorPageHandlerConfig = aemContext.registerService(new ErrorPageHandlerServiceImpl());
		when(config.getPagePath()).thenReturn(new String[] { "/content/dpss/en" });
		errorPageHandlerConfig.activate(config);
	}

	@Test
	void getSearchAPIEndPoint() {
		String pagePath = errorPageHandlerConfig.getPagePath()[0];
		assertEquals("/content/dpss/en", pagePath);
	}
}
