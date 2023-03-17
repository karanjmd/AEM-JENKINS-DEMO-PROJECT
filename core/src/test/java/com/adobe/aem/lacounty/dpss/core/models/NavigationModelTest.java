package com.adobe.aem.lacounty.dpss.core.models;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class NavigationModelTest {
	
	private final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(NavigationModel.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/epolicynav.json", "/content");
	}

	@Test
	void getTitle() {
		aemContext.currentResource("/content/epolicy");
		NavigationModel epolicynav = aemContext.request().adaptTo(NavigationModel.class);
		String expected = "NAVIGATION";
		String actual = epolicynav.getTitle();
		assertEquals(expected, actual);
	}

}
