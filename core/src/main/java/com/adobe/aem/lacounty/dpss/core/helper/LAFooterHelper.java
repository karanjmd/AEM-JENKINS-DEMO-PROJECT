package com.adobe.aem.lacounty.dpss.core.helper;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.utils.ComponentUtils;

public class LAFooterHelper {

	private static final Logger LOG = LoggerFactory.getLogger(LAFooterHelper.class);
	private String linktext;
	private String blinktext;
	private String plinktext;
	private String slinkicon;
	private String linkURL;
	private String blinkURL;
	private String plinkURL;
	private String slinkURL;
	private String gtext;

	private String openInTab;
	private String bopenInTab;
	private String popenInTab;
	private String sopenInTab;

	public String getLinkText() {
		return linktext;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public String getBlinktext() {
		return blinktext;
	}

	public String getBlinkURL() {
		return blinkURL;
	}

	public String getPlinktext() {
		return plinktext;
	}

	public String getPlinkURL() {
		return plinkURL;
	}

	public String getSlinkicon() {
		return slinkicon;
	}

	public String getSlinkURL() {
		return slinkURL;
	}

	public String getOpenInTab() {
		return openInTab;
	}

	public String getGtext() {
		return gtext;
	}

	public String getBopenInTab() {
		return bopenInTab;
	}

	public String getPopenInTab() {
		return popenInTab;
	}

	public String getSopenInTab() {
		return sopenInTab;
	}

	public LAFooterHelper(Resource resource) {

		if (StringUtils.isNotBlank(resource.getValueMap().get("linktext", String.class))) {
			this.linktext = resource.getValueMap().get("linktext", String.class);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("linkURL", String.class))) {
			String url = resource.getValueMap().get("linkURL", String.class);
			this.linkURL = ComponentUtils.linkChecker(url);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("blinktext", String.class))) {
			this.blinktext = resource.getValueMap().get("blinktext", String.class);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("blinkURL", String.class))) {
			String url = resource.getValueMap().get("blinkURL", String.class);
			this.blinkURL = ComponentUtils.linkChecker(url);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("plinktext", String.class))) {
			this.plinktext = resource.getValueMap().get("plinktext", String.class);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("plinkURL", String.class))) {
			String url = resource.getValueMap().get("plinkURL", String.class);
			this.plinkURL = ComponentUtils.linkChecker(url);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("slinkicon", String.class))) {
			this.slinkicon = resource.getValueMap().get("slinkicon", String.class);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("slinkURL", String.class))) {
			String url = resource.getValueMap().get("slinkURL", String.class);
			this.slinkURL = ComponentUtils.linkChecker(url);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("gtext", String.class))) {
			this.gtext = resource.getValueMap().get("gtext", String.class);
		}	
		if (StringUtils.isNotBlank(resource.getValueMap().get("openInTab", String.class))) {
			Boolean tabValue = resource.getValueMap().get("openInTab", Boolean.class);
			this.openInTab = ComponentUtils.processOpenInTabValue(tabValue);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("bopenInTab", String.class))) {
			Boolean tabValue = resource.getValueMap().get("bopenInTab", Boolean.class);
			this.bopenInTab = ComponentUtils.processOpenInTabValue(tabValue);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("popenInTab", String.class))) {
			Boolean tabValue = resource.getValueMap().get("popenInTab", Boolean.class);
			this.popenInTab = ComponentUtils.processOpenInTabValue(tabValue);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("sopenInTab", String.class))) {
			Boolean tabValue = resource.getValueMap().get("sopenInTab", Boolean.class);
			this.sopenInTab = ComponentUtils.processOpenInTabValue(tabValue);
		}

	}
}
