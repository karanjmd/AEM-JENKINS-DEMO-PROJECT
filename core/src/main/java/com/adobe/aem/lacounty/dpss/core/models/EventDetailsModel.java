
package com.adobe.aem.lacounty.dpss.core.models;

import java.util.Calendar;

/**
 * Defines the {@code EventDetails} Sling Model used for the
 * {@code dpss/components/content/event-details} component.
 * 
 */
public interface EventDetailsModel {

	public boolean isSameDayEvent();

	public String getTitle();

	public String getGoogleMapLink();

	public String getGoogleMapLinkLabel();

	public Calendar getStartDate();
	
	public Calendar getEndDate();
	
	public String getStartEventDate();

	public String getEndEventDate();

	public String getBackButtonUrl();

}