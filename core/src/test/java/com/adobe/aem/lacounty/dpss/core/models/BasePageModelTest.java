package com.adobe.aem.lacounty.dpss.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Locale;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.settings.SlingSettingsService;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.aem.lacounty.dpss.core.services.DpssGlobalService;
import com.adobe.aem.lacounty.dpss.core.services.impl.DpssGlobalServiceImpl;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class BasePageModelTest {
	BasePageModel basePageModel;

	public final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

	@Mock
	SlingSettingsService slingSettingsService;

	@Mock
	private SlingHttpServletRequest request;

	@Mock
	DpssGlobalService launchService;

	@Mock
	PageManager pageManager;

	@Mock
	Page currentPage;

	@Mock
	Externalizer externalizer;

	@Mock
	ResourceResolver resourceResolver;

	DpssGlobalServiceImpl globalConfig;

	@BeforeEach
	void setUp() throws Exception {

		basePageModel = new BasePageModel();
		String path1 = "/content/dpss/en/Homepage/";

		Field field = BasePageModel.class.getDeclaredField("request");
		field.setAccessible(true);
		field.set(basePageModel, request);

		Field field2 = BasePageModel.class.getDeclaredField("slingSettingsService");
		field2.setAccessible(true);
		field2.set(basePageModel, slingSettingsService);

		Field field7 = BasePageModel.class.getDeclaredField("currentPage");
		field7.setAccessible(true);
		field7.set(basePageModel, currentPage);

		Field field33 = BasePageModel.class.getDeclaredField("path");
		field33.setAccessible(true);
		field33.set(basePageModel, path1);

		when(currentPage.getLanguage()).thenReturn(Locale.US);
		when(currentPage.getPath()).thenReturn("/content/dpss/en/Homepage/");
		when(request.getResourceResolver()).thenReturn(resourceResolver);
		when(resourceResolver.adaptTo(Externalizer.class)).thenReturn(externalizer);

		basePageModel.init();
	}

	@Test
	void populateSiteSection() {
		assertEquals(currentPage.getPath(), "/content/dpss/en/Homepage/");
		when(currentPage.getPath()).thenReturn("/content/dpss/en/Homepage");
		assertEquals(currentPage.getPath(), "/content/dpss/en/Homepage");
		basePageModel.init();

	}

	@Test
	void getExternalizedURL() throws Exception {
		String externalizedURL = "/content/dpss/en/homepage.html";
		Field field6 = BasePageModel.class.getDeclaredField("externalizedURL");
		field6.setAccessible(true);
		field6.set(basePageModel, externalizedURL);
		assertEquals(basePageModel.getExternalizedURL(), externalizedURL);
	}

	@Test
	void getDomainName() throws Exception {
		String domainName = "dpss";
		Field field4 = BasePageModel.class.getDeclaredField("domainName");
		field4.setAccessible(true);
		field4.set(basePageModel, domainName);
		assertEquals(basePageModel.getDomainName(), domainName);
	}

	@Test
	void getlangAttribute() throws Exception {
		String langAttribute = "en";
		Field field5 = BasePageModel.class.getDeclaredField("langAttribute");
		field5.setAccessible(true);
		field5.set(basePageModel, langAttribute);
		assertEquals(basePageModel.getLangAttribute(), langAttribute);
	}

	@Test
	void getSiteSection() throws Exception {
		String siteSection = "Homepage";
		Field field = BasePageModel.class.getDeclaredField("siteSection");
		field.setAccessible(true);
		field.set(basePageModel, siteSection);
		assertEquals(basePageModel.getSiteSection(), siteSection);
	}

	@Test
	void getLaunchConfigUrl() throws Exception {
		launchService = Mockito.mock(DpssGlobalService.class);
		Field field = BasePageModel.class.getDeclaredField("launchService");
		field.setAccessible(true);
		field.set(basePageModel, launchService);
		String launchConfigUrl = "//assets.adobedtm.com/c70179af6cc1/689b8691df2c/launch-8cf082ecf6df-staging.min.js";
		when(basePageModel.getLaunchConfigUrl()).thenReturn(launchConfigUrl);
		assertEquals(launchService.getLaunchConfigUrl(), launchConfigUrl);
	}

}
