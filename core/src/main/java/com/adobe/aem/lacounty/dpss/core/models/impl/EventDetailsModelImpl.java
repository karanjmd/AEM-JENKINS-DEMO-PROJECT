
package com.adobe.aem.lacounty.dpss.core.models.impl;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.lacounty.dpss.core.models.EventDetailsModel;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

/**
 * Defines the {@code EventDetails} Sling Model used for the
 * {@code dpss/components/content/event-details} component.
 * 
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = EventDetailsModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EventDetailsModelImpl implements EventDetailsModel {

	public static final String RESOURCE_TYPE = "dpss/components/event-details";
	private static final String PN_TITLE = "eventTitle";
	private static final String PN_GMAP_LINK = "googleMapLink";
	private static final String PN_GMAP_LINK_LABEL = "googleMapLinkText";
	private static final String PN_START_DATE = "startDate";
	private static final String PN_END_DATE = "endDate";
	private static final String PN_BACK_URL = "backButtonURL";

	//SimpleDateFormat df = new SimpleDateFormat("EEE MMM d");

	@SlingObject
	protected Resource currentResource;

	@Self
	private SlingHttpServletRequest request;

	@ValueMapValue(name = PN_TITLE)
	private String title;
	
	@ValueMapValue(name = PN_BACK_URL)
	private String backButtonURL;

	@ValueMapValue(name = PN_GMAP_LINK)
	private String googleMapLink;

	@ValueMapValue(name = PN_GMAP_LINK_LABEL)
	private String googleMapLinkLabel;
	
	@ValueMapValue(name = PN_START_DATE)

	private Calendar startDate;

	@ValueMapValue(name = PN_END_DATE)
	private Calendar endDate;
	
	private String startEventDate;

	private String endEventDate;

	@Override
	public boolean isSameDayEvent() {
		
		if(getStartEventDate().equals(getEndEventDate())) {			
		      return true;
		 }else {
			 return false;
		 }
		
		
		
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getGoogleMapLink() {
		return googleMapLink;
	}

	@Override
	public String getGoogleMapLinkLabel() {
		return googleMapLinkLabel;
	}

	public String getStartEventDate() {
		Calendar sd = (Calendar)currentResource.getValueMap().get(PN_START_DATE);
        Date d = sd.getTime();
		TimeZone pstTimeZone = TimeZone.getTimeZone("America/Los_Angeles");

		DateFormat formatter = DateFormat.getDateInstance(); // just date, you might want something else
		formatter.setTimeZone(pstTimeZone);
	
		return startEventDate = formatter.format(d);
		
		

	}

	public String getEndEventDate() {
		Calendar ed = (Calendar) currentResource.getValueMap().get(PN_END_DATE);
        Date e = ed.getTime();
		TimeZone pstTimeZone = TimeZone.getTimeZone("America/Los_Angeles");

		DateFormat formatter = DateFormat.getDateInstance(); // just date, you might want something else
		formatter.setTimeZone(pstTimeZone);
		return endEventDate = formatter.format(e);

	}

	@Override
	public Calendar getStartDate() {

		return startDate;
	}

	@Override
	public Calendar getEndDate() {
		return endDate;
	}

	@PostConstruct
	protected void initModel() {
		Page currentPage = request.getResourceResolver().adaptTo(PageManager.class).getContainingPage(currentResource);
		if (StringUtils.isBlank(title) && null != currentPage) {
			title = StringUtils.defaultIfEmpty(currentPage.getPageTitle(), currentPage.getTitle());
		}

	}

	@Override
	public String getBackButtonUrl() {
		backButtonURL= request.getHeader(HttpHeaders.REFERER);
		
		return backButtonURL;
	}

}