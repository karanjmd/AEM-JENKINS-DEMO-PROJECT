package com.adobe.aem.lacounty.dpss.core.model.impl;

import java.util.Calendar;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.lacounty.dpss.core.models.TeaserModel;
import com.adobe.aem.lacounty.dpss.core.utils.ComponentUtils;



@Model(adaptables = SlingHttpServletRequest.class, adapters = TeaserModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TeaserModelImpl implements TeaserModel{
	
	public static final String RESOURCE_TYPE = "dpss/components/teaser";
	private static final String PN_TAB = "openInTab";
	
	
	@SlingObject
	@Inject
	Resource componentResource;

	@ValueMapValue
	private Calendar date;
	
	@ValueMapValue(name = PN_TAB)
	private String openInNewTab;

	@Override
	public Calendar getTeaserDate() {
		
		return date;
	}

	@Override
	public String getOpenInNewTab() {
		if (StringUtils.isNotBlank(componentResource.getValueMap().get("openInTab", String.class))) {
			Boolean tabValue = componentResource.getValueMap().get("openInTab", Boolean.class);	
			this.openInNewTab=ComponentUtils.processOpenInTabValue(tabValue);
			}else {
				this.openInNewTab= "_self";
			}
		return openInNewTab;
	}

	
	



}
