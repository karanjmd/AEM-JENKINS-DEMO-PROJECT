package com.adobe.aem.lacounty.dpss.core.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
public class ComponentUtilsTest {

	private AemContext aemContext = new AemContext();

	@Mock
	SlingSettingsService slingSettingsService;

	@Mock
	SlingHttpServletRequest slingHttpServletRequest;

	@Mock
	PageManager pageManager;

	@Mock
	Page page;

	@Mock
	ResourceResolver resourceResolver;

	@BeforeEach
	void setUp() throws NoSuchFieldException {
		aemContext.registerService(ComponentUtils.class);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void processOpenInTabValueTest() {
		assertEquals("_blank", ComponentUtils.processOpenInTabValue(Boolean.TRUE));
		assertEquals("", ComponentUtils.processOpenInTabValue(Boolean.FALSE));
	}

	@Test
	void linkCheckerTest() {
		assertEquals("/content/dpss/en.html", ComponentUtils.linkChecker("/content/dpss/en/"));
		assertEquals("/content/dpss/en.html", ComponentUtils.linkChecker("/content/dpss/en"));
	}

	@Test
	void getTransformImgPathTest() {
		assertEquals("testimage.svg",
				ComponentUtils.getTransformImgPath("testimage.svg", "mobileTransformedCardImage"));
		assertEquals("testimage.jpg.transform/tabTransformedCardImage/img.jpg",
				ComponentUtils.getTransformImgPath("testimage.jpg", "tabTransformedCardImage"));
	}

	@Test
	void getURLTest() {
		when(slingHttpServletRequest.getResourceResolver()).thenReturn(resourceResolver);
		when(pageManager.getPage("/content/dpss/en/")).thenReturn(page);
		when(page.getVanityUrl()).thenReturn("/content/dpss/en/");
		ComponentUtils.getURL(slingHttpServletRequest, pageManager, "/content/dpss/en/");
	}

	@Test
	void getURLEmptyVanityURLTest() {
		when(slingHttpServletRequest.getResourceResolver()).thenReturn(resourceResolver);
		when(pageManager.getPage("/content/dpss/en/")).thenReturn(page);
		when(page.getVanityUrl()).thenReturn(StringUtils.EMPTY);
		ComponentUtils.getURL(slingHttpServletRequest, pageManager, "/content/dpss/en/");
	}
	
	@Test
	void getLocaleTest() {
		when(pageManager.getPage("/content/dpss/en/")).thenReturn(page);
		ComponentUtils.getLocale(slingHttpServletRequest, page);
	}
	
	@Test
	void getLocaleEmptyPageTest() {
		ComponentUtils.getLocale(slingHttpServletRequest, null);
	}

}
