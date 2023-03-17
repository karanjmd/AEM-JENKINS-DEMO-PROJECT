package com.adobe.aem.lacounty.dpss.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class AboutUsBannerModelTest {
	private final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(AboutUsBannerModel.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/aboutusbanner.json", "/content");
	}

	@Test
	void getTitle() {
		aemContext.currentResource("/content/aboutus");
		AboutUsBannerModel aub = aemContext.request().adaptTo(AboutUsBannerModel.class);
		String expected = "LAVA";
		String actual = aub.getTitle();
		assertEquals(expected, actual);
	}

	@Test
	void getSubText() {
		aemContext.currentResource("/content/aboutus");
		AboutUsBannerModel aub = aemContext.request().adaptTo(AboutUsBannerModel.class);
		String expected = "<p>this is about us</p>\r\n";
		String actual = aub.getSubText();
		assertEquals(expected, actual);
	}

	@Test
	void getBgImagePath() {
		aemContext.currentResource("/content/aboutus");
		AboutUsBannerModel aub = aemContext.request().adaptTo(AboutUsBannerModel.class);
		String expected = "/content/dam/dpss/burst-kUqqaRjJuw0-unsplash.png";
		String actual = aub.getBgImagePath();
		assertEquals(expected, actual);
	}
}
