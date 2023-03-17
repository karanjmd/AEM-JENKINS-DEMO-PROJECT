package com.adobe.aem.lacounty.dpss.core.helper;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.utils.ComponentUtils;

public class MegaMenuModelHelper {
	private static final Logger LOG = LoggerFactory.getLogger(MegaMenuModelHelper.class);
	private String title;
	private String links;
	private String openInTab;
	private String filter;

	public String getFilter() {
		return filter;
	}

	public String getTitle() {
		return title;
	}

	public String getLinks() {
		return links;
	}

	public String getOpenInTab() {
		return openInTab;
	}

	public MegaMenuModelHelper(Resource resource) {

		if (StringUtils.isNotBlank(resource.getValueMap().get("title", String.class))) {
			this.title = resource.getValueMap().get("title", String.class);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("filter", String.class))) {
			this.filter = resource.getValueMap().get("filter", String.class);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("links", String.class))) {
			String url = resource.getValueMap().get("links", String.class);
			this.links = ComponentUtils.linkChecker(url);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("openInTab", String.class))) {
			Boolean tabValue = resource.getValueMap().get("openInTab", Boolean.class);
			this.openInTab = ComponentUtils.processOpenInTabValue(tabValue);
		}

	}
}
