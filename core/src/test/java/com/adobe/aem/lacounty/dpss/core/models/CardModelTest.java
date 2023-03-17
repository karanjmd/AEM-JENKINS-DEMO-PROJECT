package com.adobe.aem.lacounty.dpss.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class CardModelTest {
	private final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

	@BeforeEach
	void setUp() {
		// MockitoAnnotations.initMocks(this);
		aemContext.addModelsForClasses(CardModel.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/dpsscardcomp.json", "/content");
	}

	@Test
	void getCardTitle() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		String expected = "Card";
		String actual = card.getCardTitle();
		assertEquals(expected, actual);
	}

	@Test
	void getCardImage() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		String expected = "/content/dam/dpss/burst-kUqqaRjJuw0-unsplash.png";
		String actual = card.getCardImage();
		assertEquals(expected, actual);
	}

	@Test
	void getCardImageAltTxt() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		String expected = "Card Alt";
		String actual = card.getCardImageAltTxt();
		assertEquals(expected, actual);
	}

	@Test
	void getExpandLabel() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		String expected = "more";
		String actual = card.getExpandLabel();
		assertEquals(expected, actual);
	}

	@Test
	void getCollapseLabel() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		String expected = "less";
		String actual = card.getCollapseLabel();
		assertEquals(expected, actual);
	}

	@Test
	void getDescription() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		String expected = "<p>This is the card description</p>";
		String actual = card.getDescription();
		assertEquals(expected, actual);
	}

	@Test
	void getCardSubtitle() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		String expected = "This is card-sub title";
		String actual = card.getCardSubtitle();
		assertEquals(expected, actual);
	}

	@Test
	void getCardMobileImage() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		String expected = "/content/dam/dpss/burst-kUqqaRjJuw0-unsplash.png.transform/mobileTransformedCardImage/img.png";
		String actual = card.getCardMobileImage();
		assertEquals(expected, actual);
	}

	@Test
	void getCardTabletImage() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		String expected = "/content/dam/dpss/burst-kUqqaRjJuw0-unsplash.png.transform/tabTransformedCardImage/img.png";
		String actual = card.getCardTabletImage();
		assertEquals(expected, actual);
	}

	@Test
	void isHideDescription() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		Boolean expected = true;
		boolean actual = card.isHideDescription();
		assertEquals(expected, actual);
	}

	@Test
	void getAreaLandmark() {
		aemContext.currentResource("/content/dpsscard");
		CardModel card = aemContext.request().adaptTo(CardModel.class);
		String expected = "dpsscard-1844857856";
		String actual = card.getAreaLandmark();
		assertEquals(expected, actual);
	}

}