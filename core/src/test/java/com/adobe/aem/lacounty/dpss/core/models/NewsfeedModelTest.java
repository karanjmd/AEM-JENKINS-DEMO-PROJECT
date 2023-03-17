package com.adobe.aem.lacounty.dpss.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class NewsfeedModelTest {
	private final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(NewsfeedModel.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/newsfeed.json", "/content");
	}

	@Test
	void getTitle() {
		aemContext.currentResource("/content/newsfeed");
		NewsfeedModel newsfeed = aemContext.request().adaptTo(NewsfeedModel.class);
		String expected = "Homepage Newsfeed";
		String actual = newsfeed.getTitle();
		assertEquals(expected, actual);
	}

	@Test
	void getViewAllLabel() {
		aemContext.currentResource("/content/newsfeed");
		NewsfeedModel newsfeed = aemContext.request().adaptTo(NewsfeedModel.class);
		String expected = "newsfeed";
		String actual = newsfeed.getViewAllLabel();
		assertEquals(expected, actual);
	}

	@Test
	void getViewAllLink() {
		aemContext.currentResource("/content/newsfeed");
		NewsfeedModel newsfeed = aemContext.request().adaptTo(NewsfeedModel.class);
		String expected = "/content/dpss/en/Homepage/ResultPage";
		String actual = newsfeed.getViewAllLink();
		assertEquals(expected, actual);
	}

	@Test
	void getOpenInNewTab() {
		aemContext.currentResource("/content/newsfeed");
		NewsfeedModel newsfeed = aemContext.request().adaptTo(NewsfeedModel.class);
		String expected = "_blank";
		String actual = newsfeed.getOpenInNewTab();
		assertEquals(expected, actual);
	}

	@Test
	void getListPath() {
		aemContext.currentResource("/content/newsfeed");
		NewsfeedModel newsfeed = aemContext.request().adaptTo(NewsfeedModel.class);
		String expected = "/content/dpss/en/Homepage/DetailPage";
		String actual = newsfeed.getListPath();
		assertEquals(expected, actual);
	}

	@Test
	void getBgImagePath() {
		aemContext.currentResource("/content/newsfeed");
		NewsfeedModel newsfeed = aemContext.request().adaptTo(NewsfeedModel.class);
		String expected = "/content/dam/dpss/burst-kUqqaRjJuw0-unsplash.png";
		String actual = newsfeed.getBgImagePath();
		assertEquals(expected, actual);
	}
}
