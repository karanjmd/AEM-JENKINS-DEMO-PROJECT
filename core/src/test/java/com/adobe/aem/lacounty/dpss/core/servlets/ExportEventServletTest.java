package com.adobe.aem.lacounty.dpss.core.servlets;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ExportEventServletTest {

	public final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	void doGetNull() throws Exception {
		ExportEventServlet servlet = aemContext.registerInjectActivateService(new ExportEventServlet());
		MockSlingHttpServletRequest request = aemContext.request();
		MockSlingHttpServletResponse response = aemContext.response();
		servlet.doGet(request, response);
	}

	@Test
	void doGet() throws Exception {

		ExportEventServlet servlet = aemContext.registerInjectActivateService(new ExportEventServlet());
		MockSlingHttpServletRequest request = aemContext.request();
		MockSlingHttpServletResponse response = aemContext.response();
		Map<String, Object> ap = new HashMap<String, Object>();
		ap.put("startDate", "2022-08-10T06:43:00.000-07:00");
		ap.put("endDate", "2022-08-10T06:43:00.000-07:00");
		ap.put("location", "fkjdjf");
		ap.put("eventName", "Services");
		request.setParameterMap(ap);
		servlet.doGet(request, response);

	}

}
