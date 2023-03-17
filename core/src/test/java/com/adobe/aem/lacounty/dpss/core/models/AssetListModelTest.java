package com.adobe.aem.lacounty.dpss.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)

public class AssetListModelTest {

	private final AemContext aemContext = new AemContext();

	private AssetListModel assetmodel;

	@BeforeEach
	void setUp() {

		aemContext.addModelsForClasses(AssetListModel.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/assetlist.json", "/content");
		aemContext.currentResource("/content/assetlist");
		assetmodel = aemContext.request().adaptTo(AssetListModel.class);

	}

	@Test
	void getViewallpagepath() {
		String expected = "/content/dpss/en/homepage.html";
		String actual = assetmodel.getViewallpagepath();
		assertEquals(expected, actual);
	}

	@Test
	void getServletpath() {
		String expected = "/content/assetlist.assetlist.json";
		String actual = assetmodel.getServletpath();
		assertEquals(expected, actual);
	}

	@Test
	void getPathexplorer() {
		String expected = "/content/dam/dpss/card-images";
		String actual = assetmodel.getPathexplorer();
		assertEquals(expected, actual);
	}

	@Test
	void getResultLimit() {
		Double expected = 30.0;

		Double actual = assetmodel.getResultLimit();

		assertEquals(expected, actual);
	}

	@Test
	void getViewmorelabel() {
		String expected = "More";

		String actual = assetmodel.getViewmorelabel();

		assertEquals(expected, actual);
	}

	@Test
	void getViewlesslabel() {
		String expected = "Less";
		String actual = assetmodel.getViewlesslabel();
		assertEquals(expected, actual);
	}

	@Test
	void getViewalllabel() {
		String expected = "View All";
		String actual = assetmodel.getViewalllabel();
		assertEquals(expected, actual);
	}

	@Test
	void getFallback() {
		String expected = "fallback message";
		String actual = assetmodel.getFallback();
		assertEquals(expected, actual);
	}
}
