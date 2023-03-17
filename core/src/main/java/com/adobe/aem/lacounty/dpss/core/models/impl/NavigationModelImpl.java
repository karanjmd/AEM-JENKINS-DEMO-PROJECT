package com.adobe.aem.lacounty.dpss.core.models.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.lacounty.dpss.core.models.NavigationModel;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = NavigationModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavigationModelImpl implements NavigationModel{

	@ValueMapValue(name = "jcr:title")
	@Default(values = "NAVIGATION")
	private String title;

	@Override
	public String getTitle() {
		return title;
	}

}
