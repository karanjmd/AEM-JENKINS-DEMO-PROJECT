package com.adobe.aem.lacounty.dpss.core.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.lacounty.dpss.core.models.TeaserModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class TeaserModelImplTest {

	private final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(TeaserModelImpl.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/model/impl/teaser.json", "/content");

	}

	@Test
	void getOpenInNewTab() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/teaser");
		TeaserModel tsr = aemContext.request().adaptTo(TeaserModel.class);
		String expected = "_self";
		String actual = tsr.getOpenInNewTab();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}
	@Test
	void getTeaserDate() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/teaser");
		TeaserModel tsr = aemContext.request().adaptTo(TeaserModel.class);
		String expected = "Wed Nov 09 2022 00:00:00";
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
		Calendar actual = tsr.getTeaserDate();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, formatter.format(actual.getTime()));

	}


	
}
