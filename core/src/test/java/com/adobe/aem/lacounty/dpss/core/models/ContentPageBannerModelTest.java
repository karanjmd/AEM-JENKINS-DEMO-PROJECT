package com.adobe.aem.lacounty.dpss.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class ContentPageBannerModelTest {

	public final AemContext aemContext = new AemContext();
	private ContentPageBannerModel emodel;

	@BeforeEach
	void setUp() {

		aemContext.addModelsForClasses(ContentPageBannerModel.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/page.json", "/content");
		aemContext.currentResource("/content/notification/jcr:content");
		emodel = aemContext.request().adaptTo(ContentPageBannerModel.class);

	}

	@Test
	void getText() {
		String expected = "Notifications";
		String actual = emodel.getText();
		assertEquals(expected, actual);

	}

	@Test
	void getDescription() {
		String expected = "This is content page banner";
		String actual = emodel.getDescription();
		assertEquals(expected, actual);

	}

	@Test
	void getBgImagePath() {
		String expected = "";
		String actual = emodel.getBgImagePath();
		assertEquals(expected, actual);

	}

	@Test
	void getSubText() {
		String expected = "What are you looking for ?";
		String actual = emodel.getSubText();
		assertEquals(expected, actual);

	}

	@Test
	void getLinks() {
		int expected = 2;
		assertEquals(expected, emodel.getLinks().size());
		assertEquals("link1", emodel.getLinks().get(0).getLinkText());
		assertEquals("_blank", emodel.getLinks().get(0).getOpenInNewTab());
		assertEquals("/content/dpss/en/Homepage", emodel.getLinks().get(0).getLinkUrl());
	}

	@Test
	void getLinkURL() {
		String expected = "/content/dpss/en/Homepage";
		String actual = emodel.getLinkURL();
		assertEquals(expected, actual);
	}

	@Test
	void getHideDescription() {
		Boolean expected = true;
		Boolean actual = emodel.isHideDescription();
		assertEquals(expected, actual);

	}

}
