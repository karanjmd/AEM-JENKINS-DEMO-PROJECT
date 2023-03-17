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

public class EventDetailsModelTest {

	private final AemContext aemContext = new AemContext();

	private EventDetailsModel emodel;

	@BeforeEach
	void setUp() {

		aemContext.addModelsForClasses(EventDetailsModel.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/eventdetails.json", "/content");
		aemContext.currentResource("/content/events");
		emodel = aemContext.request().adaptTo(EventDetailsModel.class);

	}

	@Test
	void getGoogleMapLink() {
		String expected = "www.google.com";
		String actual = emodel.getGoogleMapLink();
		assertEquals(expected, actual);
	}

	@Test
	void getTitle() {
		String expected = "Event Details";
		String actual = emodel.getTitle();
		assertEquals(expected, actual);
	}

	@Test
	void getGoogleMapLinkLabel() {
		String expected = "Test location";
		String actual = emodel.getGoogleMapLinkLabel();
		assertEquals(expected, actual);
	}

	@Test
	void getStartDate() {
		String expected = "Sat Aug 06 2022 15:55:00";
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
		Calendar actual = emodel.getStartDate();

		assertEquals(expected, formatter.format(actual.getTime()));
	}

	@Test
	void getEndDate() {
		String expected = "Sun Aug 07 2022 15:55:00";
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
		Calendar actual = emodel.getEndDate();

		assertEquals(expected, formatter.format(actual.getTime()));
	}

	@Test
	void isSameDayEvent() {
		Boolean expected = false;
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss");
		Boolean sde = emodel.isSameDayEvent();

		assertEquals(expected, sde);
	}
}
