package com.adobe.aem.lacounty.dpss.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.lacounty.dpss.core.models.AboutUsBannerModel;
import com.day.cq.commons.jcr.JcrConstants;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = AboutUsBannerModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AboutUsBannerModelImpl implements AboutUsBannerModel{
	
	private static final String PN_BG_IMAGE = "bgImage";
	private static final String PN_SUB_TEXT = "subText";

	@ValueMapValue(name = JcrConstants.JCR_TITLE)
	private String title;

	@ValueMapValue(name = PN_SUB_TEXT)
	private String subText;

	@ValueMapValue(name = PN_BG_IMAGE)
	private String bgImagePath = "";

	@Override
	public String getBgImagePath() {
		return bgImagePath;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getSubText() {
		return subText;
	}

}
