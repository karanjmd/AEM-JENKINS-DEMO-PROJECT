package com.adobe.aem.lacounty.dpss.core.models;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL, resourceType = "dpss/components/content/asset-list")

@Exporter(name = "jackson", extensions = "json")

public class AssetListModel {

	private static final Logger LOG = LoggerFactory.getLogger(AssetListModel.class);
	
	/*Test*/

	@Self
	SlingHttpServletRequest request;

	@ScriptVariable
	private Resource resource;

	@ValueMapValue(name = "pathexplorer")
	private String pathexplorer;

	@ValueMapValue(name = "resultLimit")
	private Double resultLimit;

	@ValueMapValue(name = "viewmorelabel")
	@Default(values = "More")
	private String viewmorelabel;

	@ValueMapValue(name = "viewlesslabel")
	@Default(values = "Less")
	private String viewlesslabel;

	@ValueMapValue(name = "viewalllabel")
	@Default(values = "View All")
	private String viewalllabel;

	@ValueMapValue(name = "fallback")
	private String fallback;

	@ValueMapValue(name = "viewallpagepath")
	private String viewallpagepath;

	public String getViewallpagepath() {
		return viewallpagepath;
	}

	public void setViewallpagepath(String viewallpagepath) {
		this.viewallpagepath = viewallpagepath;
	}

	
	private String servletpath;

	public String getServletpath() {
		return servletpath;
	}

	public void setServletpath(String servletpath) {
		this.servletpath = servletpath;
	}

	
	@PostConstruct
	protected void initModel() {
		if (StringUtils.isNoneEmpty(pathexplorer)) {

			String path = resource.getPath();
			this.servletpath = path + ".assetlist.json";
			this.viewallpagepath = viewallpagepath + ".html";
			LOG.info("Executing AssetListModel Model");
			LOG.info("AssetListModel Model execution sucessfull");
		}
	}

	public String getPathexplorer() {
		return pathexplorer;
	}

	public Double getResultLimit() {
		return resultLimit;
	}

	public String getViewmorelabel() {
		return viewmorelabel;
	}

	public String getViewlesslabel() {
		return viewlesslabel;
	}

	public String getViewalllabel() {
		return viewalllabel;
	}

	public String getFallback() {
		return fallback;
	}

}