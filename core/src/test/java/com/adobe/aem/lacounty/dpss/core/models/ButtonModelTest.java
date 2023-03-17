package com.adobe.aem.lacounty.dpss.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class ButtonModelTest {
	private final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(ButtonModel.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/button.json", "/content");
	}

	@Test
	void getTitle() {
		aemContext.currentResource("/content/button");
		ButtonModel bm = aemContext.request().adaptTo(ButtonModel.class);
		String expected = "Button";
		String actual = bm.getText();
		assertEquals(expected, actual);
	}
	@Test
	void getLink() {
		aemContext.currentResource("/content/button");
		ButtonModel bm = aemContext.request().adaptTo(ButtonModel.class);
		String expected = "/content/dpss/en/Homepage/DetailPage";
		String actual = bm.getLink();
		assertEquals(expected, actual);
	}
	@Test
	void getOpenInNewTab() {
		aemContext.currentResource("/content/button");
		ButtonModel bm = aemContext.request().adaptTo(ButtonModel.class);
		String expected = "_blank";
		String actual = bm.getOpenInNewTab();
		assertEquals(expected, actual);
	}
	

}
