package com.adobe.aem.lacounty.dpss.core.services.impl;

import java.util.Arrays;

import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.adobe.aem.lacounty.dpss.core.config.ErrorPageHandlerConfig;
import com.adobe.aem.lacounty.dpss.core.services.ErrorPageHandlerService;

@Component(service = ErrorPageHandlerService.class, immediate = true)
@Designate(ocd = ErrorPageHandlerConfig.class)

public class ErrorPageHandlerServiceImpl implements ErrorPageHandlerService {
	private String[] pagePath;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Activate
	public void activate(ErrorPageHandlerConfig config) {
		pagePath = config.getPagePath();
	}

	@Override
	public String[] getPagePath() {
		return Arrays.copyOf(pagePath, pagePath.length);
	}

}
