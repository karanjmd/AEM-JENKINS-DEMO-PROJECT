package com.adobe.aem.lacounty.dpss.core.components.models;
import java.util.HashSet;
import java.util.Set;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class },adapters = GetRunMode.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GetRunModeImpl implements GetRunMode {
	private static final Logger LOG = LoggerFactory.getLogger(GetRunMode.class);
	  @OSGiService
	    private SlingSettingsService slingSettingsService;

	@Override
	public boolean getAuthor() {
		Set<String> hs=new HashSet<String>();
		hs=this.slingSettingsService.getRunModes();
		LOG.info("Set="+hs);
		 return this.slingSettingsService.getRunModes().contains("author");
	}

	@Override
	public boolean getPublisher() {
		 return this.slingSettingsService.getRunModes().contains("publish");
	}

	@Override
	public String getDev() {
		if(this.slingSettingsService.getRunModes().contains("dev"))
		{
			return "dev";
		}
		else
		{
			return "";
		}
		
	}

	@Override
	public String getStage() {
		if(this.slingSettingsService.getRunModes().contains("stage"))
		{
			return "stage";
		}
		else
		{
			return "";
		}
	}

	@Override
	public String getProdAuthor() {
		if(this.slingSettingsService.getRunModes().contains("prod")&&this.slingSettingsService.getRunModes().contains("author"))
		{
			return "prodauthor";
		}
		else
		{
			return "";
		}
	}

	@Override
	public String getProdPublisher() {
		if(this.slingSettingsService.getRunModes().contains("prod")&&this.slingSettingsService.getRunModes().contains("publish"))
		{
			return "prodpublish";
		}
		else
		{
			return "";
		}
	}

}
